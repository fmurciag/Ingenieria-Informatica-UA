<?php

use Illuminate\Database\Seeder;
use App\Order;
use App\User;

class OrdersTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        //Borramos los datos de la tabla
        DB::table('orders')->delete();

        // Recuperar el segundo usuario (cliente).
        $usuario2 = User::find(2);

        // Añadimos una entrada a esta tabla
        $pedido1 = new Order([
            'fecha' => date_create('2021-03-07'),
            'estado' => 'Completado',
            'direccion' => $usuario2 -> direccion,
            'pago' => $usuario2 -> pago,
        ]);
        $pedido1->user()->associate($usuario2);
        $pedido1->save();

        $pedido2 = new Order([
            'fecha' => date_create('2021-04-08'),
            'estado' => 'En envío',
            'direccion' => $usuario2 -> direccion,
            'pago' => $usuario2 -> pago,
        ]);
        $pedido2->user()->associate($usuario2);
        $pedido2->save();

        $pedido3 = new Order([
            'fecha' => date_create('2021-03-09'),
            'estado' => 'En envío',
            'direccion' => $usuario2 -> direccion,
            'pago' => $usuario2 -> pago,
        ]);
        $pedido3->user()->associate($usuario2);
        $pedido3->save();
    }
}
