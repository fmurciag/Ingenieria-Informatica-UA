<?php

use Illuminate\Database\Seeder;
use App\Product;

class ProductsTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        // Borramos los datos de la tabla
        DB::table('products')->delete();

        //Añadimos chorizos [id 1]
        $chorizo1 = new Product([
             'nombre' => 'CHORIZO BLANCO',
             'descripcion' => 'Un chorizo tradicional, sus bolas de pimienta le dan un sabor particular totalmente diferente a cualquier otro chorizo y salchichón.',
             'precio' => 1.09,
             'imagen' => 'https://ideyaverde.es/4482-large_default/embutido-tipo-bull-blanco-taula-verda-.jpg',
             'stock'=> 12,
             'category_id' => 1
         ]);
         $chorizo1->save(); 

        $chorizo2 = new Product([
            'nombre' => 'CHORIZO DE VENADO',
            'descripcion' => 'Un chorizo ideal si lo que quieres es un bajo contenido en grasa. Tiene un sabor muy rico que recuerda a ese sabor de pueblo de toda la vida.',
            'precio' => 4.72,
            'imagen' => 'https://losrejos.com/wp-content/uploads/2020/07/chorizo-venado02.jpg',
            'stock'=> 12,
            'category_id' => 1
        ]);
        $chorizo2->save(); 

        //Añadimos salchichones [id 2]
        $salchichon1 = new Product([
            'nombre' => 'FUET TARRADELLAS',
            'descripcion' => 'Quien no conoce este fuet, hay que reconocer que su sabor y textura es inimitable, aunque muchas otras marcas lo han intentado el fuet de Tarradellas es único.',
            'precio' => 2.80,
            'imagen' => 'https://sgfm.elcorteingles.es/SGFM/dctm/MEDIA03/201909/24/00118387700265____1__600x600.jpg',
            'stock'=> 12,
            'category_id' => 2
        ]);
        $salchichon1->save(); 

        //Añadimos embuchados [id 3]
        $embuchado1 = new Product([
           'nombre' => 'LENGUA IBÉRICA EMBUCHADA',
           'descripcion' => 'Sabor similar al de la caña de lomo. Se aconseja corte fino.',
           'precio' => 4.20,
           'imagen' => 'https://i1.wp.com/www.pcsanchezmarcos.es/wp-content/uploads/2017/08/lengua-iberica.jpg',
           'stock'=> 12,
           'category_id' => 3
       ]);
       $embuchado1->save(); 

       //Añadimos quesos [id 4]
       $queso1 = new Product([
        'nombre' => '1/2 QUESO MANCHEGO',
        'descripcion' => 'Leche cruda, pasta dura y cristalizada. Sabor intenso.',
        'precio' => 5.12,
        'imagen' => 'https://www.elpaladar.es/server/Portal_0005448/img/products/queso-manchego-curado-dehesa-de-los-llanos-12-pieza_3217918.jpg',
        'stock'=> 12,
        'category_id' => 4
        ]);
        $queso1->save(); 

        $queso2 = new Product([
         'nombre' => 'QUESO FRESCO DE CABRA',
         'descripcion' => 'Tietar exquisito, de sabor suave y agradable, su textura es suave y es fresco.',
         'precio' => 6.18,
         'imagen' => 'https://www.elpaladar.es/server/Portal_0005448/img/products/queso-cabra-tietar-tierno_9303577_xxl.jpg',
         'stock'=> 12,
         'category_id' => 4
        ]);
        $queso2->save(); 

        //Añadimos lomos [id 5]
        $lomo1 = new Product([
        'nombre' => 'CAÑA LOBO IBÉRICO',
        'descripcion' => 'Carne tierna y muy jugosa por su alto nivel de filtración. Sin duda tienes que probarlo.',
        'precio' => 12.95,
        'imagen' => 'https://i0.wp.com/www.egogalego.com/wp-content/uploads/2020/03/Lomo-ib%C3%A9rico-curado-de-Cebo.-Media-ca%C3%B1a..jpg',
        'stock'=> 12,
        'category_id' => 5
        ]);
        $lomo1->save(); 

        $lomo2 = new Product([
         'nombre' => 'CABECERO DE LOMO',
         'descripcion' => 'Excelente cabecero de lomo. Buen sabor.',
         'precio' => 10.35,
         'imagen' => 'https://tienda.nietomartin.es/1289-large_default/cabecero-lomo-embuchado.jpg',
         'stock'=> 12,
         'category_id' => 5
        ]);
        $lomo2->save(); 

        //Añadimos jamones [id 6]
        $jamon1 = new Product([
        'nombre' => 'CAÑA LOBO IBÉRICO',
        'descripcion' => 'Excelente jamón ibérico cebo sotoalbos. Muy suave.',
        'precio' => 9.00,
        'imagen' => 'https://www.diablocojuelo.com/167/jamon-iberico-sotoalbos.jpg',
        'stock'=> 12,
        'category_id' => 6
        ]);
        $jamon1->save(); 
    }
}
