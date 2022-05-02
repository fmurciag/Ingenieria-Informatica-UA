<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use App\Orderline;
use App\User;

class Order extends Model
{
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'fecha', 'estado', 'direccion', 'pago'
    ];

    //Función que obtiene el usuario que ha realizado un pedido.
    public function user() {
        // Pedido tiene la clave ajena user_id.
        return $this->belongsTo('App\User');
    }

    //Función que obtiene las líneas de pedido de un pedido.
    public function Orderlines() {
        return $this->hasMany('App\Orderline');
    }

    public function calculaPrecioPedido() {
        $total = 0;
        $orderlines = $this->orderlines;
        foreach($orderlines as $ordeline){
            $total += ($ordeline->calculaPrecioLinea());
        }
        return $total;
    }
}
