
<body>
@extends('layouts/footer')
@extends('layouts/menu')
@section('contentmenu')
    <center>

        <h1> Editar Usuario</h1>

        <form method='POST' action="{{ route('users.updateUser', $user) }}">
            @csrf @method('PATCH')
            <label>
                Nombre de usuario: <br>
                <input type="text" name="name" value="{{ $user->nombre }}">
                {!! $errors->first('name', '<br><small class="text-danger">:message</small><br>') !!}
            </label>
            <p></p>
            <label>
                Apellidos del usuario: <br>
                <input type="text" name="secondnames" value="{{ $user->apellidos }}">
                {!! $errors->first('secondnames', '<br><small class="text-danger">:message</small><br>') !!}
            </label>
            <p></p>
            <label>
                Email del usuario: <br>
                <input type="text" name="email" disabled value="{{ $user->email }}">
            </label>
            <p></p>
            <label>
                Contraseña: <br>
                <input type="password" name="password" value="{{ $user->password }}">
                {!! $errors->first('password', '<br><small class="text-danger">:message</small><br>') !!}
            </label>
            <p></p>
            <label>
                Número de teléfono: <br>
                <input type="text" name="tlf" value="{{ $user->telefono }}">
                {!! $errors->first('tlf', '<br><small class="text-danger">:message</small><br>') !!}
            </label>
            <p></p>
            <label>
                Esadmin: <br>
                <input type="text" name="admin" value="{{ $user->esAdmin }}">
                {!! $errors->first('admin', '<br><small class="text-danger">:message</small><br>') !!}
            </label>
            <p></p>
            <label>
                Direccion: <br>
                <input type="text" name="direccion" value="{{ $user->direccion }}">
            </label>
            <p></p>
            <label> 
                Pago: <br>
                <input type="text" name="pago" value="{{ $user->pago }}">
                {!! $errors->first('pago', '<br><small class="text-danger">:message</small><br>') !!}
            </label>
            <p></p>
            <button class="btn btn-primary">Editar Usuario</button>
            <a class="btn btn-danger" href="{{ route('users.deleteUser', $user) }}">Borrar<a>
            </form>
            
        <center>
        <br>
        @endsection
@section('contentfooter')
@endsection     
</body>
