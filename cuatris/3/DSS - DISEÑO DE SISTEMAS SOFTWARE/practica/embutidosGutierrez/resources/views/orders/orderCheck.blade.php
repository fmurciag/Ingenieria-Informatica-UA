<body>
@extends('layouts/footer')
@extends('layouts/menu')
@section('contentmenu')

<br>
<style>
#mitabla{
    vertical-align: middle;
}
</style>

<div class="container-fluid">
    <div class="row">
        <div class="col">
            <div class="container-fluid">
            <table id= "mitabla" class="table table-hover ";   style="text-align:center">
                <thead>
            <tr>
                <th>Producto</th>
                <!-- <th>Imagen</th> -->
                <th>Cantidad</th>
                <th>Precio</th>
                <th>Total</th>
            </tr>
            </thead>
            <tbody >
                @foreach($orderlines as $orderline)
                <tr >
                    <td>{{ $orderline->product->nombre}}</td>
                    <td>{{ $orderline->cantidad }}</td>
                    <td>{{ $orderline->precioUnidad }}€</td>
                    <td>{{ $orderline->precioUnidad * $orderline->cantidad }}€</td>
                </tr>
                @endforeach
            </tbody>
            
            <tfoot>
            <td><a class="btn btn-dark"  href="/pedidos">Volver a pedidos</a></td>
            </tfoot>
            </table>
            
            </div>
        </div>
        <div class="col-2">
        
                <div class="row ">
                    <h1>Total: {{ $precioTotal }} €
                </div>
                </div>
            </div>
        </div>
        
    </div>
</div>



   

<br>
    






</body>
@endsection
@section('contentfooter')
@endsection 