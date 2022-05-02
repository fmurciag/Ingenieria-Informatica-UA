
<body>
@extends('layouts/footer')
@extends('layouts/menu')
@section('contentmenu')
    <center>

        <h1> Crear nuevo usuario</h1>
        <form method="POST" action="{{ route('users.storeUsers') }}">
            @csrf
            <label>
                Nombre de usuario: <br>   
                <input value="{{ old('name') }}" type="text" name="name">
                {!! $errors->first('name', '<br><small class="text-danger">:message</small><br>') !!}
            </label>
            <p></p> 
            <label>
                Apellidos del usuario: <br>
                <input value="{{ old('secondnames') }}" type="text" name="secondnames">
                {!! $errors->first('secondnames', '<br><small class="text-danger">:message</small><br>') !!}
            </label>
            <p></p> 
            <label>
                Email del usuario: <br>
                <input value="{{ old('email') }}" type="text" name="email">
                {!! $errors->first('email', '<br><small class="text-danger">:message</small><br>') !!}
            </label>
            <p></p> 
            <label>
                Contraseña: <br>
                <input type="password" name="password">
                {!! $errors->first('password', '<br><small class="text-danger">:message</small><br>') !!}
            </label>
            <p></p> 
            <label>
                Número de teléfono: <br>
                <input value="{{ old('tlf') }}" type="text" name="tlf">
                {!! $errors->first('tlf', '<br><small class="text-danger">:message</small><br>') !!}
            </label>
            <p></p> 
            <label>
                Esadmin:<br>
                <input value="{{ old('admin') }}" type="text" name="admin">
                {!! $errors->first('admin', '<br><small class="text-danger">:message</small><br>') !!}
            </label>
            <p></p> 
            <label>
                Direccion: <br>
                <input value="{{ old('direccion') }}" type="text" name="direccion">
                {!! $errors->first('direccion', '<br><small class="text-danger">:message</small><br>') !!}
            </label>
            <p></p> 
            <label> 
                Pago: <br>
                <input value="{{ old('pago') }}" type="text" name="pago">
                {!! $errors->first('pago', '<br><small class="text-danger">:message</small><br>') !!}
            </label>
            <p></p> 
            <button class="btn btn-primary">Crear Usuario</button>
            </form>
        <center>
</body>
@endsection
@section('contentfooter')
@endsection 


