
<body>
@extends('layouts/footer')
@extends('layouts/menu')
@section('contentmenu')
   <center>
   <div class="container-fluid">
      <div class="row justify-content-center align-items-center">
         <div class="col ">
            <form method="POST" action="{{ route('products.buscarUnoPorId') }}">
               @csrf
               
               <div class="mb-3">
                  <input type="text" name="id" placeholder="Id del Poducto">
                  <button class="btn btn-dark">Buscar</button>
               </div>
               
            </form>
         </div>
         <div class="col ">
            <form method="POST" action="{{ route('products.buscarUnoPorNombre') }}">
               @csrf
               <div class="mb-3">
               
                  <input type="text" name="name" placeholder="Nombre del Poducto">
                  <button class="btn btn-dark">Buscar</button>
               </div>
            </form>
         </div>
         <div class="col">
         <form class="form-inline">
               <div class="form-group">
                  <label for="ordenar">Ordenar por:</label>
                  <select class="form-select" multiple aria-label="multiple select example" id="ordenarProduct" name="ordenarProduct">
                     <option value="id">id</option>
                     <option value="nombre">Nombre</option>
                     <option value="precio">Precio</option>
                     <option value="category_id">Categoria</option>
                  </select>
               </div>
               
               <button class="btn btn-dark mt-2" type="submit">Ordenar</button>
            </form>
            </div>
      </div>
      <div class="row justify-content-center align-items-center">
         <div class="d-grid gap-2 col-3 mx-auto">  
            <a class="btn btn-dark" href="{{ route('products.showProducts') }}" type="button">Cancelar filtrado</a>
         </div>
      </div>
      
   </div>
   <style>
       #mitabla{
          vertical-align: middle;
       }
       </style>
   <div class="container-fluid">
         <h3> Producto </h3>
      <div class="container-fluid table-responsive-xl">
      <table id= "mitabla" class="table table-hover ";  border=“6px”; style="text-align:center">
               <thead style="background: #ff8000">
               <tr>
                  <th>ID</th>
                  <th>Nombre</th>
                  <th>Descripcion</th>
                  <th>Precio</th>
                  <th>Categoria</th>
                  <th>Stock</th>
                  <th>Imagen</th>
                  <th>Editar</th>
               </tr>
               </thead>
               
                  
               <tr class="table-warning">
                     <td>{{ $product->id }}</td>
                     <td>{{ $product->nombre }}</td>
                     <td>{{ $product->descripcion }}</td>
                     <td>{{ $product -> precio }}</td>
                     <td>{{ $product -> category_id }}</td>
                     <td>{{ $product -> stock }}</td>
                     <td><img src="{{ $product->imagen }}" width="100" height="100" ></td>
                     <td><a class="btn btn-dark"  href="{{ route('products.editProduct', $product) }}">Editar</a></td>
                  </tr>
                  
               </tbody>
            </table>
      </div>
      </div>
   </center>
</body>
@endsection
@section('contentfooter')
@endsection 