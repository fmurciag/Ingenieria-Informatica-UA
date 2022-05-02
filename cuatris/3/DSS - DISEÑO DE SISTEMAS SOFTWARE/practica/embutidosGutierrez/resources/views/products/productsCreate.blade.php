
<body>
@extends('layouts/footer')
@extends('layouts/menu')
@section('contentmenu')
   <center>
        <h1>Agregar nuevo producto</h1>

        <form method="POST" action="{{ route('products.storeProducts') }}">
            @csrf
            <label>
                Nombre del producto:<br>
                <input value="{{ old('name') }}" type="text" name="name">
                {!! $errors->first('name', '<br><small class="text-danger">:message</small><br>') !!}
            </label>
            <p></p>
            <label>
                Descripcion del producto: <br>
                <textarea name="descripcion">{{ old('descripcion') }}</textarea>
                {!! $errors->first('descripcion', '<br><small class="text-danger">:message</small><br>') !!}
            </label>
            <p></p>
            <label>
                Precio del producto: <br>
                <input value="{{ old('precio') }}" type="text" name="precio">
                {!! $errors->first('precio', '<br><small class="text-danger">:message</small><br>') !!}
            </label>
            <p></p>
            <label>
                Stock: <br>
                <input value="{{ old('stock') }}" type="text" name="stock">
                {!! $errors->first('stock', '<br><small class="text-danger">:message</small><br>') !!}
            </label>
            <p></p>
            <label>
            Imagen: <br>
                <input value="{{ old('imagen') }}" type="imagen" name="imagen">
                {!! $errors->first('imagen', '<br><small class="text-danger">:message</small><br>') !!}
            </label>
            <p></p>
            <label>
            Id de la categoría: <br>
            <select class="form-control mr-sm-2" value="{{ old('categoria') }}" name="categoria" id="categoria">
                  <option><a value=null selected>Selecciona</a></option>
                      @foreach($categories as $category)
                  <option><a value="{{$category->id}}">{{$category->id}}</a></option>
                      @endforeach
              </select>            
            {!! $errors->first('categoria', '<br><small>:message</small><br>') !!}
            </label>
            <p></p>
            <button class="btn btn-primary">Añadir Producto</button>
            </form>
            
   </center>
</body>
@endsection
@section('contentfooter')
@endsection 