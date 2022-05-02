<body>
@extends('layouts/footer')
@extends('layouts/menu')
@section('contentmenu')

<br>

<div class="container">
  <div class="row">
    <div class="col">
    <center>
    <h1>Contáctanos</h1>
    </center>
    <div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="well well-sm">
                <form class="form-horizontal" method="POST" action="{{ route('cliente.contactoValidado') }}">
                @csrf

                        <input id="fname" name="name" type="text" placeholder="Nombre" class="form-control">
                        {!! $errors->first('name', '<small class="text-danger">:message</small><br>') !!}
                        <br>

                        <input id="lname" name="secondname" type="text" placeholder="Apellidos" class="form-control">
                        {!! $errors->first('secondname', '<small class="text-danger">:message</small><br>') !!}
                        <br>

                        <input id="email" name="email" type="text" placeholder="Correo Electrónico" class="form-control">
                        {!! $errors->first('email', '<small class="text-danger">:message</small><br>') !!}

                        <br>
                      
                        <input id="phone" name="phone" type="text" placeholder="Teléfono(Opcional)" class="form-control">

                        <br>
              
                        <textarea class="form-control" id="message" name="message" placeholder="Escriba aqui su mensaje" rows="7"></textarea>
           
                        <br>
                        <div class="d-grid gap-2">
                            <button class="btn btn-primary">Enviar</button>
                        </div>
                        @if (session()->has('flash'))
                            <div class= "alert alert-info"> {{ session('flash')}} </div>
                        @endif
                </form>
            </div>
        </div>
    </div>
</div>
    </div>
    <div class="col">
    <iframe class="responsive-iframe" src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3142.8741159150964!2d-0.9804919843794069!3d38.02671410539649!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd639e8347df78c9%3A0x8cc9245de0bfe936!2sCalle%20del%20Cristo%2C%2003313%20El%20Moj%C3%B3n%2C%20Alicante!5e0!3m2!1ses!2ses!4v1622037760791!5m2!1ses!2ses" width="800" height="600" style="border:0;" allowfullscreen="" loading="lazy"></iframe>
    </div>
  </div>
</div>

<br>
    
</body>
@endsection
@section('contentfooter')
@endsection 