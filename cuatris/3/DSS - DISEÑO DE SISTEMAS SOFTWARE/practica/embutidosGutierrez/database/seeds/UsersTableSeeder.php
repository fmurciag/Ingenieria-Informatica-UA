<?php

use Illuminate\Database\Seeder;
use App\User;

class UsersTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        // Borramos los datos de la tabla
        DB::table('users')->delete();

        $usuario1 = new User([
            'nombre' => 'Javi',
            'apellidos' => ' Perez',
            'email' => 'javi18pm@gmail.com',
            'password' => bcrypt('3mbut1d0'),
            'telefono' => '654428170',
            'esAdmin' => true
        ]);
        $usuario1->save();
        
        $usuario2 = new User([
            'nombre' => 'Fran',
            'apellidos' => 'Murcia',
            'email' => 'fmurciag@gmail.com',
            'password' => bcrypt('mur1c4'),
            'telefono' => '649887045',
            'esAdmin' => false,
            'direccion' => 'Calle Murcia',
            'pago' => '385283281'

        ]);
        $usuario2->save();
    }
}
