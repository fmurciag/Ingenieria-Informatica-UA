<?php

use Illuminate\Database\Seeder;
use App\Category;

class CategoriesTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        // Borramos los datos de la tabla
        DB::table('categories')->delete();

        $chorizos = new Category([
            'nombre' => 'Chorizos',
            'descripcion' => 'El producto estrella de Embutidos Gutierrez. Embutido cárnico con sabor complétamente único y con la marca de la casa.'
        ]);
        $chorizos->save();

        $salchichones = new Category([
            'nombre' => 'Salchichones',
            'descripcion' => 'Embutido curado elaborado con carne magra de cerdo y un ligero contenido de tocino.'
        ]);
        $salchichones->save();

        $embuchados = new Category([
            'nombre' => 'Embuchados',
            'descripcion' => 'Embutido de carne de cerdo entera o picada compuesta generalmente por lomo de cerdo.'
        ]);
        $embuchados->save();

        $quesos = new Category([
            'nombre' => 'Quesos',
            'descripcion' => 'El acompañante perfecto. Fruto de una buena maduración de la cuajada de la leche.'
        ]);
        $quesos->save();

        $lomos = new Category([
            'nombre' => 'Lomos',
            'descripcion' => 'Carne fruto del dorsal de nuestros animales.'
        ]);
        $lomos->save();

        $jamones = new Category([
            'nombre' => 'Jamones',
            'descripcion' => 'Carne obtenida de las patas traseras del cerdo. Sabores esquisitos.'
        ]);
        $jamones->save();

        $otros = new Category([
            'nombre' => 'Otros',
            'descripcion' => 'Otras categorías no contempladas.'
        ]);
        $otros->save();
    }
}
