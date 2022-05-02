
<body>
@extends('layouts/footer')
@extends('layouts/menu')
@section('contentmenu')
<center>
<br>
<div class="container-fluid">
   <div class="row justify-content-center align-items-center">
   <div class="col">
         <form class="form-inline">
            <div class=" col-3 form-group">
            
            <select id="ordenarCategory"  multiple aria-label="multiple select example" name="ordenarCategory" [(ngModel)]="department" class="form-select">
               <option value="id">id</option>
               <option value="nombre">Nombre</option>
               <option value="descripcion">Descripcion</option>
            </select>
            </div>
            <button class="btn btn-dark  my-2 my-sm-0" type="submit">Ordenar</button>
         </form>
      </div>
      <div class="col">
      <div class="d-grid gap-2 col-3 mx-auto">  
            <a class="btn btn-dark" href="{{ route('orderlines.showOrderlines') }}" type="button">Cancelar filtrado</a>
         </div>
      </div>
   </div>
</div>
      <h3> Categoria </h3>
      <div class="container-fluid table-responsive">
            <table class="table table-hover ";  border=“6px”; style="text-align:center">
               <thead style="background: #ff8000">
            <tr>
               <th>ID</th>
               <th>Nombre</th>
               <th>Descripcion</th>
            </tr>
            </thead>
            <tbody style="background: rgba(128, 255, 0, 0.15)">
              
            <tr class="table-warning">
                  <td>{{ $category->id }}</td>
                  <td>{{ $category->nombre }}</td>
                  <td>{{ $category->descripcion }}</td>
               </tr>
               
            </tbody>
         </table>
      </div>
   </center>
</body>
@endsection
@section('contentfooter')
@endsection 