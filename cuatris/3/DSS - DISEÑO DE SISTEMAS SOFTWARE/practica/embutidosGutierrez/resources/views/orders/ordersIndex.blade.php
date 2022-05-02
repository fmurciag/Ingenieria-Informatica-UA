
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
            
            <select class="form-select" multiple aria-label="multiple select example" id="ordenarOrder" name="ordenarOrder" [(ngModel)]="department" >
               <option value="id">id</option>
               <option value="user_id">Usuario</option>
               <option value="fecha">Fecha</option>
               <option value="direccion">Direccion</option>
            </select>
            </div>
            <button class="btn btn-dark my-2 my-sm-0" type="submit">Ordenar</button>
         </form>
      </div>
      <div class="col">
      <div class="d-grid gap-2 col-3 mx-auto">  
            <a class="btn btn-dark" href="{{ route('orders.showOrders') }}" type="button">Cancelar filtrado</a>
         </div>
      </div>
   </div>
</div>
   

    <br>
   <h3> Pedidos </h3>
   <div class="container-fluid table-responsive">
            <table class="table table-hover ";  border=“6px”; style="text-align:center">
               <thead style="background: #ff8000">
         <tr>
            <th>ID</th>
            <th>Usuario</th>
            <th>Fecha</th>
            <th>Estado</th>
            <th>Direccion</th>
            <th>Pago</th>
         </tr>
         </thead>
         <tbody style="background: rgba(128, 255, 0, 0.15)">
            @foreach($orders as $order)
            <tr class="table-warning">
                <td>{{ $order->id }}</td>
                <td>{{$order->user_id}}</td>
                <td>{{ $order->fecha }}</td>
                <td>{{ $order->estado }}</td>
                <td>{{ $order -> direccion }}</td>
                <td>{{ $order->pago }}</td>
            </tr>
            @endforeach
         </tbody>
      </table>
      {{$orders-> links()}}
   </div>
   </center>
</body>
@endsection
@section('contentfooter')
@endsection 