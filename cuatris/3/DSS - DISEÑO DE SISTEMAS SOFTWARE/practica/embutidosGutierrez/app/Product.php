<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use App\Category;

class Product extends Model
{
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'nombre', 'descripcion', 'precio', 'imagen', 'stock','category_id'
    ];

    public function category() {
        // Producto tiene la clave ajena category_id.
        return $this->belongsTo('App\Category');
    }

    public function orderlines() {
        return $this->hasMany('App\OrderLine');
    }

    // Administrador actualiza stock
    public function actualizarStock($cantidad) {
        $this->stock = $cantidad;
    }

    // Comprueba si hay stock de un producto
    public function compruebaStock($cantidad) {
        if($this->stock >= $cantidad){
            return true;
        }
        else {
            return false;
        }
    }

    // Decrementa el stock una vez realizada una compra
    public function decrementarStock($cantidad) {
        $this->stock -= $cantidad;
    }

}
