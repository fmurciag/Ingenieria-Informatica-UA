<?php

use Illuminate\Database\Seeder;
use App\Orderline;
use App\Order;
use App\User;
use App\Product;

class OrderLinesTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        // Borramos los datos de la tabla
        DB::table('orderlines')->delete();

        // Recuperamos el primer producto
        $producto = Product::find(1);

        // Recuperamos el primer pedido
        $pedido = Order::find(1);

        //producto inventado a falta de la clase producto
        $orderline = new Orderline([
            'cantidad'=>'2',
            'precioUnidad' => $producto -> precio
        ]);
        $orderline->product()->associate($producto);
        $orderline->order()->associate($pedido);
        $orderline->save();

        //Añadimos otra linea
        $producto2 = Product::find(2);
        $pedido2 = Order::find(1);
        $orderline2 = new Orderline([
            'cantidad'=>'4',
            'precioUnidad' => $producto2 -> precio
        ]);
        $orderline2->product()->associate($producto2);
        $orderline2->order()->associate($pedido2);
        $orderline2->save();
    }
}
