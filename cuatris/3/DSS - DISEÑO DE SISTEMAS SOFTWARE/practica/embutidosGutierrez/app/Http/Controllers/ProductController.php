<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Product;
use App\Category;
use Illuminate\Validation\Rule;

class ProductController extends Controller
{
    public function showAll(Request $request) {
        $res = $request -> get('ordenarProduct');
        if(!$res){
            $res = 'id';
        }
        $products = Product::orderBy($res,'asc')->paginate(5);
        return view('products/productsIndex', compact('products'));
    }

    public function tienda(Request $request) {

        $CheckCategory = $request -> get('CheckCategory');
        $res = $request -> get('ordenarProduct');
        if($CheckCategory) {

            switch($res) {
                case "1":
                    $products = Product::where('category_id', $CheckCategory)->orderBy('precio','desc')->paginate(12);
                    break;
                case "2":
                    $products = Product::where('category_id', $CheckCategory)->orderBy('precio','asc')->paginate(12);
                    break;
                default: 
                    $products = Product::where('category_id', $CheckCategory)->orderBy('id','asc')->paginate(12);
            }

        } else {

            switch($res) {
                case "1":
                    $products = Product::orderBy('precio','desc')->paginate(12);
                    break;
                case "2":
                    $products = Product::orderBy('precio','asc')->paginate(12);
                    break;
                default: 
                    $products = Product::orderBy('id','asc')->paginate(12);
            }
        }

        return view('cliente/tienda', compact('products'));
    }

    public function showProduct($id){
        $product = Product::find($id);
        if(!$product)
        {
            return view('notFound');
        }
        return view('products/productsShow', ['product' => $product]);
    }

    public function buscarUnoPorId(){

        $id = request('id');
        $products = Product::all()->where('id',$id);
        return view('products/productsShowBy', compact('products'));

    }


    public function createProduct(){
        $categories = Category::All();
        return view('products.productsCreate', [
            'categories'=>$categories
        ]);
    }

    public function buscarUnoPorNombre() {
        $nombre = request('name');
        $products = Product::all()->where('nombre',$nombre);
        return view('products/productsShowBy', compact('products'));
    }

    public function storeProducts(){
    
        request()->validate([
            'name' => 'required',
            'descripcion' => 'required',
            'precio' => 'required|numeric',
            'imagen' => 'required',
            'categoria' => 'required|'. Rule::notIn(['Selecciona', 'null'])
        ]);

        Product::create([

            'nombre' => request('name'),
            'descripcion' => request('descripcion'),
            'precio' => floatval(request('precio')),
            'stock'=> request('stock'),
            'imagen' => request('imagen'),
            'category_id' => intval(request('categoria')),

        ]);

        

        return redirect()->route('products.showProducts');

    }

    public function editProduct($id){
        $product = Product::findOrfail($id);
        $categories = Category::All();

        return view('products.productsEdit', [
            'product' => $product, 'categories' => $categories
        ]);
    }

    public function updateProduct(Product $product){

        request()->validate([
            'name' => 'required',
            'descripcion' => 'required',
            'precio' => 'required|numeric',
            'imagen' => 'required',
            'categoria' => 'required|'. Rule::notIn(['Selecciona', 'null'])
        ]);

        $product->update([
            'nombre' => request('name'),
            'descripcion' => request('descripcion'),
            'precio' => floatval(request('precio')),
            'stock'=> request('stock'),
            'imagen' => request('imagen'),
            'category_id' => intval(request('categoria')),


        ]);

            return redirect()->route('products.showProducts');
    }

    public function deleteProduct(Product $product){
        $product->delete();
        return redirect()->route('products.showProducts');
    }

}
