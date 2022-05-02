
<body>
@extends('layouts/footer')
@extends('layouts/menu')
@section('contentmenu')
    <center>
    <h1>Editar el producto</h1>

        <form method="POST" action="{{ route('products.updateProduct', $product) }}">
        @csrf @method('PATCH')
        <label>
            Nombre del producto: <br>   
            <input type="text" name="name" value="{{ $product->nombre }}">
            {!! $errors->first('name', '<br><small>:message</small><br>') !!}
        </label>
        <p></p>
        <label>
        Descripcion del producto: <br> 
            <textarea name="descripcion">{{ $product->descripcion }}</textarea>
            {!! $errors->first('descripcion', '<br><small class="text-danger">:message</small><br>') !!}
        </label>
        <p></p>
        <label>
        Precio del producto: <br>  
            <input type="text" name="precio" value="{{ $product->precio }}">
            {!! $errors->first('precio', '<br><small>:message</small><br>') !!}
        </label>
        <p></p>
        <label>
        Stock: <br>  
            <input type="text" name="stock" value="{{ $product->stock }}">
            {!! $errors->first('stock', '<br><small>:message</small><br>') !!}
        </label>
        <p></p>
        <label>
        Imagen: <br>  
            <input type="imagen" name="imagen" value="{{ $product->imagen }}">
            {!! $errors->first('imagen', '<br><small>:message</small><br>') !!}
        </label>
        <p></p>
        <label>
        Id de la categoría: <br> 
            <input type="number" name="categoria" value="{{ $product->category_id }}">
            {!! $errors->first('categoria', '<br><small>:message</small><br>') !!}
        </label>
        <p></p> 
        <button class="btn btn-dark">Editar Producto</button>     <a class="btn btn-danger" href="{{ route('products.deleteProduct', $product) }}">Borrar Producto</a>
        </form>
    <center>
</body>
@endsection
@section('contentfooter')
@endsection 
