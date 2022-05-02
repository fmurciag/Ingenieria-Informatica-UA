<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Orderline;
use App\Order;
use App\Product;
use Illuminate\Support\Facades\Auth;

class OrderlineController extends Controller
{
    public function showAll(Request $request){
        $res = $request->get('ordenarOrderlines');
        if(!$res)
        {
            $res = 'id';
        }
        $orderlines = Orderline::orderBy($res,'asc')->paginate(5);
        return view('orderlines/orderlinesIndex', compact('orderlines'));
    }

    public function showOrderLine($id){
        $orderline = Orderline::find($id);
        if(!$orderline)
        {
            return view('notFound');
        }
        return view('orderlines/orderlinesShow', ['orderline' => $orderline]);
    }

    public function deleteOrderline($id){
        $orderline = Orderline::find($id);
        $orderline -> delete();
        return redirect()->route('orderlines.showOrderlines');   

    }

    public function deleteOrderlineClient($id){
        $orderline = Orderline::find($id);
        $orderline -> delete();
        return redirect()->route('cliente.carrito');   
    }

    public function showCarrito(){
        
        $user = Auth::user();
        if ($user) { //Control de usuario
            $userOrders = Auth::user()->orders;
            foreach($userOrders as $carrito){
                if($carrito->estado == "carrito") { //Existe el carrito con productos
    
                    $precioTotal = $carrito->calculaPrecioPedido();
                    $orderlines = $carrito->Orderlines;
                    return view('cliente.carrito', compact('orderlines', 'precioTotal'));
                } 
            }

            //No existe el carrito con productos
            $precioTotal = 0;
            $orderlines = collect();
            return view('cliente.carrito', compact('orderlines', 'precioTotal'));
        }
        
        return redirect()->route('casa'); 
    }

    public function addProductCarrito($id) {

        $user = Auth::user()->orders;
        if ($user) { //Control de usuario
            $producto = Product::findOrfail($id);
            if($producto) {
                $userOrders = Auth::user()->orders;

                foreach($userOrders as $carrito){
                    if($carrito->estado == "carrito") { //Existe el carrito con productos
        
                        $carrito->fecha = date('Y-m-d');
                        $carrito->save();
                        $orderlines = $carrito->Orderlines;
                        

                        //Miramos si se repite el producto
                        foreach($orderlines as $line){
                        
                            if($line->product_id == $id) {

                                $line->cantidad += 1;
                                $line->save();
                                return redirect()->route('cliente.tienda'); 
                            }
                        }

                        //Creamos linea producto desde 0
                        $orderline = new Orderline([
                            'cantidad'=>'1',
                            'precioUnidad' => $producto -> precio
                        ]);
                        $orderline->product()->associate($producto);
                        $orderline->order()->associate($carrito);
                        $orderline->save();

                        return redirect()->route('cliente.tienda'); 
                    } 
                }
                
            
                //No existe el carrito con productos

                //Creamos carrito
                $pedido = new Order([
                    'estado' => 'carrito',
                    'fecha' => date('Y-m-d')
                ]);
                $pedido->user()->associate(Auth::user());
                $pedido->save();

                //Creamos linea producto
                $orderline = new Orderline([
                    'cantidad'=>'1',
                    'precioUnidad' => $producto -> precio
                ]);
                $orderline->product()->associate($producto);
                $orderline->order()->associate($pedido);
                $orderline->save();

                return redirect()->route('cliente.tienda'); 
            }
        }

        return redirect()->route('cliente.tienda'); 
    }

    public function checkOrderLine($id){
        
        $user = Auth::user();
        if ($user) { //Control de usuario
            $pedido = Order::findOrfail($id);
            if($pedido) {
                $precioTotal = $pedido->calculaPrecioPedido();
                $orderlines = $pedido->Orderlines;
                return view('orders.orderCheck', compact('orderlines', 'precioTotal'));
            } 
        }
        
        return redirect()->route('casa'); 
    }

    public function comprar(){
        
        $user = Auth::user();
        if ($user) { //Control de usuario

            $direccion = request('direccion');
            $pago = request('pago');
            if($direccion && $pago) {

                $userOrders = Auth::user()->orders;
                foreach($userOrders as $carrito){
                    if($carrito->estado == "carrito") { //Existe el carrito con productos
        
                        $precioTotal = $carrito->calculaPrecioPedido();
                        $orderlines = $carrito->Orderlines;
                        if($orderlines && $orderlines->count() > 0) {
                            //Existen productos en el carrito
                            $carrito->estado = "Preparando envío";
                            $carrito->fecha = date('Y-m-d');
                            $carrito->direccion = $direccion;
                            $carrito->pago = $pago;
                            $carrito->save();

                            return redirect()->back()->with('flash', 'PEDIDO PROCESADO CON ÉXITO');
                        }
                        else {
                            //No existen productos en el carrito 
                            return redirect()->back()->with('flash', 'AÑADE ARTÍCULOS AL CARRITO PARA COMPRAR');
                        }
                    } 
                }
            } else {
                return redirect()->back()->with('flash', 'INTRODUCE UN MÉTODO DE PAGO Y DIRECCIÓN');
            }

            //No existe el carrito 
            return redirect()->back()->with('flash', 'AÑADE ARTÍCULOS AL CARRITO PARA COMPRAR');
        }
        
        return redirect()->route('casa'); 
    }
}