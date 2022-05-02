
<body>
@extends('layouts/footer')
@extends('layouts/menu')
@section('contentmenu')
    <center>
<h1>Agregar nuevo pedido</h1>

<form method="POST" action="{{ route('orders.storeOrder') }}">
    @csrf
    <label>
        Fecha del pedido: <br>   
        <input value="{{ old('fecha') }}" type="text" name="fecha">
        {!! $errors->first('fecha', '<br><small>:message</small><br>') !!}
    </label>
    <p></p>
    <label>
        Estado del pedido: <br>
        <input value="{{ old('estado') }}" type="text" name="estado">
        {!! $errors->first('estado', '<br><small>:message</small><br>') !!}
    </label>
    <p></p>
    <label>
        Direccion del pedido: <br>
        <input value="{{ old('direccion') }}" type="text" name="direccion">
        {!! $errors->first('direccion', '<br><small>:message</small><br>') !!}
    </label>
    <p></p>
    <label>
        Pago del pedido: <br>
        <input value="{{ old('pago') }}" type="text" name="pago">
        {!! $errors->first('pago', '<br><small>:message</small><br>') !!}
    </label>
    <p></p>
    <label>
         Id del usuario: <br>
        <input value="{{ old('usuario') }}" type="number" name="usuario">
        {!! $errors->first('pago', '<br><small>:message</small><br>') !!}
    </label>
    <p></p>
    <button class="btn btn-primary">Añadir pedido</button>
    </form>
    </center>
</body>
@endsection
@section('contentfooter')
@endsection 