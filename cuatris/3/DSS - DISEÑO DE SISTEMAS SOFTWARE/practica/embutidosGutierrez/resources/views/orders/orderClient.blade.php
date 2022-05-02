<body>
@extends('layouts/footer')
@extends('layouts/menu')
@section('contentmenu')
<style>
#mitabla{
    vertical-align: middle;
}
</style>
<div class="container-fluid">
    <br>
    @foreach($orders as $order)
    <div class="row justify-content-center">
        <div class="col-8">
            
            <div class="card">
                <h2 class="card-header">{{ $order->fecha }}</h2>
                <div class="card-body">
                    <h5 class="card-title">Precio: {{ $order->calculaPrecioPedido() }}€</h5>
                    
                        <table id= "mitabla" class="table table-hover ";   style="text-align:center">
                                <thead>
                            <tr>
                                <th>Estado</th>
                                <th>Dirección</th>
                                <th>Productos</th>

                            </tr>
                            </thead>
                            <tbody >
                                <tr >
                                    <td>{{ $order->estado }}</td> 
                                    <td> {{ $order->direccion }} </td>     
                                    <td><a class="btn btn-danger"  href="{{ route('orderlines.checkOrderLine', $order->id)}}">VER</a></td>
                                             
                            </tbody>
                        </table>
                        
                </div>
                <div class="card-footer">
                    <h5 >ID:{{ $order->id }}</h5>
                </div>
            </div>
        </div >
    </div >
    <br>
    @endforeach
    
<br>
</body>
@endsection
@section('contentfooter')
@endsection 