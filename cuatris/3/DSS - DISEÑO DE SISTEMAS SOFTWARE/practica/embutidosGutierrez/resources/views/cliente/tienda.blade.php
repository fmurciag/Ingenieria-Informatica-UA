<body>
@extends('layouts/footer')
@extends('layouts/menu')
@section('contentmenu')


    <div class="container-fluid">
    <hr>
        <h1 class="display-3">Charcuteria</h1>
        <blockquote class="blockquote">
            <p class="p2">En nuestra tienda contamos con charcutería, donde encontrarás embutidos de gran calidad. Jamón serrano, jamón ibérico o de bellota, quesos, chorizo... Los productos más tradicionales de nuestra gastronomía.</p>
        </blockquote>
        <hr>
    </div>
    <br>
    
    <br>
    <div class="container-fluid">
        <div class="row">
            <div class="col-xxl-1">
            <!-- ckeck de categorias-->
                <form class="form-inline">
                    <div class="form-check form-check-inline mt-3">
                        <input class="form-check-input" type="radio" value="1" name="CheckCategory" id="CheckChorizos">
                        <label class="form-check-label" for="CheckChorizos">
                        Chorizos
                        </label>
                    </div>
                    <div class="form-check form-check-inline mt-2">
                        <input class="form-check-input" type="radio" value="2" name="CheckCategory" id="CheckCategory">
                        <label class="form-check-label" for="CheckSalchichones">
                        Salchichones
                        </label>
                    </div>
                    <div class="form-check form-check-inline mt-2">
                        <input class="form-check-input" type="radio" value="3" name="CheckCategory" id="CheckEmbuchados">
                        <label class="form-check-label" for="CheckEmbuchados">
                        Embuchados
                        </label>
                    </div>
                    <div class="form-check form-check-inline mt-2">
                        <input class="form-check-input" type="radio" value="4" name="CheckCategory" id="CheckQuesos">
                        <label class="form-check-label" for="CheckQuesos">
                        Quesos
                        </label>
                    </div>
                    <div class="form-check form-check-inline mt-2">
                        <input class="form-check-input" type="radio" value="5" name="CheckCategory" id="CheckLomos">
                        <label class="form-check-label" for="CheckLomos">
                        Lomos
                        </label>
                    </div>
                    <div class="form-check form-check-inline mt-2">
                        <input class="form-check-input" type="radio" value="6" name="CheckCategory" id="CheckJamones">
                        <label class="form-check-label" for="CheckJamones">
                        Jamones
                        </label>
                    </div>
                    <div class="form-check form-check-inline mt-2">
                        <input class="form-check-input" type="radio" value="7" name="CheckCategory" id="CheckOtros">
                        <label class="form-check-label" for="CheckOtros">
                        Otros
                        </label>
                    </div>
                    <!-- la seleccion de mas barato a mas caro -->
                    <select class="form-select mt-2" id="ordenarProduct" name="ordenarProduct">
                        <option selected value="0">Destacados </option>
                        <option value="1">Mas caros</option>
                        <option value="2">Mas baratos</option>
                    </select>
                    <!-- el boton -->
                    <div class="d-grid gap-2 mx-auto">  
                        <button class="btn btn-dark mt-2" type="submit">Filtrar</button>
                    </div>
                </form>
            </div>
            <div class="col">
                <div class="row">
                <!-- el muestreo de la tienda -->
                    @foreach($products as $product)
                    <div class="col-sm-12 col-mg-5 col-lg-2">
                        <div class="card">
                            <img src={{ $product->imagen }} class="card-img-top" alt="...">
                            <div class="card-body">
                                <h5 class="card-title">{{ $product->nombre }}</h5>
                                <p class="card-text">{{ $product -> precio }} € </p>
                                @guest
                                    <!-- Button trigger modal -->
                                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-cart-plus-fill" viewBox="0 0 16 16">
                                        <path d="M.5 1a.5.5 0 0 0 0 1h1.11l.401 1.607 1.498 7.985A.5.5 0 0 0 4 12h1a2 2 0 1 0 0 4 2 2 0 0 0 0-4h7a2 2 0 1 0 0 4 2 2 0 0 0 0-4h1a.5.5 0 0 0 .491-.408l1.5-8A.5.5 0 0 0 14.5 3H2.89l-.405-1.621A.5.5 0 0 0 2 1H.5zM6 14a1 1 0 1 1-2 0 1 1 0 0 1 2 0zm7 0a1 1 0 1 1-2 0 1 1 0 0 1 2 0zM9 5.5V7h1.5a.5.5 0 0 1 0 1H9v1.5a.5.5 0 0 1-1 0V8H6.5a.5.5 0 0 1 0-1H8V5.5a.5.5 0 0 1 1 0z"/>
                                        </svg> 
                                    </button>

                                    <!-- Modal -->
                                    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="exampleModalLabel">Inicie sesión para poder seguir comprando</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        
                                        <div class="modal-footer">
                                            <a href="{{ route('login')}}" type="button" class="btn btn-primary">Login</a>
                                            
                                            
                                        </div>
                                        </div>
                                    </div>
                                    </div>

                                @else
                                <a type="button" class="btn btn-primary" href="{{ route('cliente.addProductCarrito', $product->id) }}"  >
                                    <svg xmlns="http://www.w3.org/2000/svg"  width="25" height="25" fill="currentColor" class="bi bi-cart-plus-fill" viewBox="0 0 16 16">
                                    <path d="M.5 1a.5.5 0 0 0 0 1h1.11l.401 1.607 1.498 7.985A.5.5 0 0 0 4 12h1a2 2 0 1 0 0 4 2 2 0 0 0 0-4h7a2 2 0 1 0 0 4 2 2 0 0 0 0-4h1a.5.5 0 0 0 .491-.408l1.5-8A.5.5 0 0 0 14.5 3H2.89l-.405-1.621A.5.5 0 0 0 2 1H.5zM6 14a1 1 0 1 1-2 0 1 1 0 0 1 2 0zm7 0a1 1 0 1 1-2 0 1 1 0 0 1 2 0zM9 5.5V7h1.5a.5.5 0 0 1 0 1H9v1.5a.5.5 0 0 1-1 0V8H6.5a.5.5 0 0 1 0-1H8V5.5a.5.5 0 0 1 1 0z"/>
                                    </svg>
                                </a>
                                @endguest
                            </div>
                        </div>
                    </div>
                    @endforeach
                </div>
            </div>

        
        </div>
        <br>
        <center>
            {{$products-> links()}}
        </center>
        
    </div>



</body>
@endsection
@section('contentfooter')
@endsection 