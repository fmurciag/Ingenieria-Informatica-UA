<?php

namespace App;

use Illuminate\Notifications\Notifiable;
use Illuminate\Foundation\Auth\User as Authenticatable;
use App\Order;
use App\Product;

class Orderline extends Authenticatable
{
    use Notifiable;

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'cantidad', 'precioUnidad'
    ];

    //Función que obtiene el pedido que que contiene una linea de pedido.
    public function order() {
        // LineaPedido tiene la clave ajena order_id.
        return $this->belongsTo('App\Order');
    }
    
    // Función que obtiene el producto perteneciente a la linea de pedido.
    public function product() {
        return $this->belongsTo('App\Product');
    }

    public function calculaPrecioLinea() {
        if($this->cantidad && $this->precioUnidad)
            return $this->cantidad * $this->precioUnidad;
        else 
            return 0;
    }
}