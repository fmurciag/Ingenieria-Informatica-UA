
<body>
@extends('layouts/footer')
@extends('layouts/menu')
@section('contentmenu')
   <center>
   <br>
   <div class="container-fluid">
      <div class="row justify-content-center align-items-center">
         <div class="col ">
            <form method="POST" action="{{ route('users.buscarUsuarioPorNombre') }}">
               @csrf
                  <input placeholder="Nombre del Usuario" type="text" name="nombre">
               <button class="btn-dark">Buscar</button>
            </form>
         </div>
         <div class="col ">
            
         </div>
         <div class="col">
         <form class="form-inline">
            <div class="form-group">
            <label for="ordenar">Ordenar por:</label>
            <select class="form-select" multiple aria-label="multiple select example"  id="ordenarUser" name="ordenarUser" [(ngModel)]="department">
               <option value="id">id</option>
               <option value="nombre">Nombre</option>
               <option value="apellidos">Apellidos</option>
               <option value="email">Email</option>
            </select>
            </div>
            <button class="btn btn-dark mt-2" type="submit">Ordenar</button>
         </form>
            </div>
         

      </div>
      <div class="row justify-content-center align-items-center">
         <div class="d-grid gap-2 col-3 mx-auto">  
            <a class="btn btn-dark" href="{{ route('users.showAll') }}" type="button">Cancelar filtrado</a>
         </div>
         <div class="d-grid gap-2 col-3 mx-auto">  
            <a class="btn btn-dark" href="{{ route('users.createUser') }}" type="button">Añadir usuario</a>
         </div>
      </div>
      
   </div>

      <h3> Lista de usuarios </h3>
      <div class="container-fluid table-responsive">
            <table class="table table-hover ";  border=“6px”; style="text-align:center">
               <thead style="background: #ff8000">
            <tr>
               <th>ID</th>
               <th>Nombre</th>
               <th>Apellidos</th>
               <th>Email</th>
               <th>Password</th>
               <th>Telefono</th>
               <th>EsAdmin</th>
               <th>Direccion</th>
               <th>Pago</th>
               <th>Editar</th>
            </tr>
            </thead>
            <tbody style="background: rgba(128, 255, 0, 0.15)">
               @foreach($users as $user)
               <tr class="table-warning">
                  <td>{{ $user->id }}</td>
                  <td>{{ $user->nombre }}</td>
                  <td>{{ $user->apellidos }}</td>
                  <td>{{ $user->email }}</td>
                  <td>{{ $user->password }}</td>
                  <td>{{ $user->telefono }}</td>
                  <td>{{ $user->esAdmin }}</td>
                  <td>{{ $user->direccion }}</td>
                  <td>{{ $user->pago }}</td>
                  <td><a class="btn btn-dark" href="{{ route('users.editUser', $user) }}">Editar</a></td>
               </tr>
               @endforeach
            </tbody> 
         </table>
      </div>  
   </center>
</body>
@endsection
@section('contentfooter')
@endsection 
</html>
