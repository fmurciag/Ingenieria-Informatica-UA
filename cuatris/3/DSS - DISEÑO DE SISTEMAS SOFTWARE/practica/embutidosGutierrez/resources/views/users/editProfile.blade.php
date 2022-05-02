<body>
@extends('layouts/footer')
@extends('layouts/menu')
@section('contentmenu')
    
<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header">{{ __('Editar Perfil') }}</div>

                <div class="card-body">
                    <form method="POST" action="{{ route('users.updateProfile', $user) }}">
                        @csrf

                        <div class="form-group row">

                            <div class="col-md-6">
                                <label for="name" class="col-md-10 col-form-label text-md-right">{{ __('Nombre') }}</label>
                                <input id="name" type="text" class="form-control @error('name') is-invalid @enderror" name="name" value="{{ $user->nombre }}" placeholder="Nuevo nombre" required autocomplete="name" autofocus>

                                @error('name')
                                    <span class="invalid-feedback" role="alert">
                                        <strong>{{ $message }}</strong>
                                    </span>
                                @enderror
                            </div>

                            <div class="col-md-6">
                                <label for="telephone" class="col-md-10 col-form-label text-md-right">{{ __('Teléfono (opcional)') }}</label>
                                <input id="telephone" type="text" class="form-control @error('telephone') is-invalid @enderror" name="telephone" value="{{ $user->telefono }}" placeholder="Nuevo teléfono" autofocus>

                                @error('telephone')
                                    <span class="invalid-feedback" role="alert">
                                        <strong>{{ $message }}</strong>
                                    </span>
                                @enderror
                            </div>

                        </div>

                        <div class="form-group row">

                            <div class="col-md-6">
                                <label for="surname" class="col-md-10 col-form-label text-md-right">{{ __('Apellidos') }}</label>
                                <input id="surname" type="text" class="form-control @error('surname') is-invalid @enderror" name="surname" value="{{ $user->apellidos }}" placeholder="Nuevos apellidos" required autocomplete="surname" autofocus>

                                @error('surname')
                                    <span class="invalid-feedback" role="alert">
                                        <strong>{{ $message }}</strong>
                                    </span>
                                @enderror
                            </div>

                            <div class="col-md-6">
                                <label for="dir" class="col-md-10 col-form-label text-md-right">{{ __('Dirección (opcional)') }}</label>
                                <input id="dir" type="text" class="form-control @error('dir') is-invalid @enderror" name="dir" value="{{ $user->direccion }}" placeholder="Nueva dirección" autofocus>
                                
                                @error('dir')
                                    <span class="invalid-feedback" role="alert">
                                        <strong>{{ $message }}</strong>
                                    </span>
                                @enderror
                            </div>

                        </div>

                        <div class="form-group row">

                            <div class="col-md-6">
                                <label for="password" class="col-md-10 col-form-label text-md-right">{{ __('Contraseña') }}</label>
                                <input id="password" type="password" class="form-control @error('password') is-invalid @enderror" name="password" placeholder="Nueva contraseña" required autocomplete="new-password">

                                @error('password')
                                    <span class="invalid-feedback" role="alert">
                                        <strong>{{ $message }}</strong>
                                    </span>
                                @enderror
                            </div>
                            
                            <div class="col-md-6">
                                <label for="payment" class="col-md-10 col-form-label text-md-right">{{ __('Método de pago (opcional)') }}</label>
                                <input id="payment" type="text" class="form-control @error('payment') is-invalid @enderror" name="payment" value="{{ $user->pago }}" placeholder="Nuevo método de pago" autofocus>
                                @error('payment')
                                    <span class="invalid-feedback" role="alert">
                                        <strong>{{ $message }}</strong>
                                    </span>
                                @enderror
                            </div>

                        </div>

                        <div class="form-group row">

                            <div class="col-md-6">
                                <label for="password-confirm" class="col-md-10 col-form-label text-md-right">{{ __('Confirmar contraseña') }}</label>
                                <input id="password-confirm" type="password" class="form-control" name="password_confirmation" placeholder="Confirmar nueva contraseña" required autocomplete=password>
                                @error('payment')
                                    <span class="invalid-feedback" role="alert">
                                        <strong>{{ $message }}</strong>
                                    </span>
                                @enderror
                            </div>

                           
                        </div>
                        </div>

                        @if (session()->has('flash'))
                                <div class= "alert alert-info"> {{ session('flash')}} </div>
                        @endif

                        <button type="submit" class="btn btn-primary px-4 float-right">
                                        {{ __('Guardar') }}
                        </button>
                    </form>
            </div>
        </div>
    </div>
</div>
</body>
@endsection
@section('contentfooter')
@endsection 
