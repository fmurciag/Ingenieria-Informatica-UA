<?php

namespace Tests\Feature;

use App\Usuario;
use App\Pedido;
use App\LinPed;

use Illuminate\Foundation\Testing\RefreshDatabase;
use Illuminate\Foundation\Testing\WithFaker;
use Tests\TestCase;

class UserTest extends TestCase{

    public function testUsuarioPedido(){

        $usuario = new Usuario();
        $usuario->nombre = 'Jesus';
        $usuario->apellidos = 'Jeruso';
        $usuario->email = 'jlg90@gmail.com';
        $usuario->password = 'holaoscar';
        $usuario->telefono = '965803416';
        $usuario->esAdmin = false;
        $usuario->direccion = 'calle progreso numero 4';
        $usuario->pago = 'tarjeta';
        $usuario->save();

        $pedido = new Pedido();
        $pedido->fecha = date_create('2022-01-24');
        $pedido->estado = 'enviado';
        $pedido->direccion = 'calle progreso numero 4';
        $pedido->pago = 'tarjeta';
        $pedido->usuario()->associate($usuario);
        $pedido->save();

        $this->assertEquals($pedido->usuario->nombre,'Jesus');
        $this->assertEquals($usuario->pedidos[0]->estado,'enviado');

        $pedido->delete();
        $usuario->delete();

    }

    public function testPedidoLinped(){

        $usuario = Usuario::find(2);
        $pedido = new Pedido();
        $pedido->fecha = date_create('2022-01-30');
        $pedido->estado = 'enviado';
        $pedido->direccion = 'calle progreso numero 30';
        $pedido->pago = 'efectivo';
        $pedido->usuario()->associate($usuario);
        $pedido->save();

        $linped = new LinPed();
        $linped->cantidad = 4;
        $linped->producto_id = 20;
        $linped->pedido()->associate($pedido);

        $linped->save();

        $this->assertEquals($linped->pedido->direccion,'calle progreso numero 30');
        $this->assertEquals($pedido->linpeds[0]->cantidad,4);

        $linped->delete();
        $pedido->delete();
        

    }

}
