
<body>
@extends('layouts/footer')
@extends('layouts/menu')
@section('contentmenu')
    <div id="carouselExampleInterval" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-inner">

                <div class="carousel-item active" data-bs-interval="2000">
                    <img src="https://images-ext-1.discordapp.net/external/cDL4_AGLDi-4AMer4wOCLkG2qlI8h2N-NEAM2yhEl6U/https/laexquisitadenin.com/modules/jxmegalayout/extracontent/V6QHIAKeSPLe4hpv.jpg?width=1440&height=638" width="70%" height="70%" class="d-block w-100 img-fluid" >
                </div>
                <div class="carousel-item" data-bs-interval="2000">
                    <img src="https://media.discordapp.net/attachments/489787602044715018/836619172611620884/embutidos-luque-1920x850.png?width=1440&height=638" width="70%" height="70%" class="d-block w-100 img-fluid" >
                </div>
                <div class="carousel-item " data-bs-interval="2000">
                    <img src=" https://laexquisitadenin.com/modules/jxmegalayout/extracontent/BPN1zVRytSR8DWAL.jpg " width="70%" height="70%" class="d-block w-100 img-fluid" >
                </div>
                <div class="carousel-item" data-bs-interval="2000">
                    <img src="https://images-ext-1.discordapp.net/external/t2bUFqGb4d3FrCbSi0XWW0KHlr9LXtvSBaDhGYV_gVk/https/quesosgomezmoreno.com/wp-content/uploads/2015/05/localizacion1.jpg?width=1440&height=638" width="70%" height="70%" class="d-block w-100 img-fluid" >
                </div>
                <div class="carousel-item" data-bs-interval="2000">
                    <img src="https://laexquisitadenin.com/modules/jxmegalayout/extracontent/hs5yrHU1uBU7Y0tD.jpg" width="70%" height="70%" class="d-block w-100 img-fluid" >
                </div>
            </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleInterval" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleInterval" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>
    <br>
    <div class="container-fluid">
        <div class="row">
            <div class="col">
            <center>
                <hr>
                <h1 class="display-3">¿Quiénes somos?</h1>
                
                <p class="lead">Embutodos Gutierrez es una charcuteria familiar 
                    murciana fundada en 1963. A través de nuestra tienda online, enviamos nuestros productos a toda la geografía española.
                </p>
                <hr>
            </center>
            </div>
        </div>
        <div class="row ">
            
            
                    
            <div class="col ">
                <center>
                <img src="./imagenes/frio.jpg" width="70%" height="70%" class="img-fluid img-thumbnail">
                <img src="./imagenes/seur.png" width="70%" height="70%" class="img-fluid img-thumbnail">
                </center>
            </div>
            <div class="col">
            <center>
                <img src="./imagenes/corteJamon.jpg" width="70%" height="70%" class="img-fluid img-thumbnail">
                </center>
            </div>
                

            
            </div>
        </div>
        <br>
        <hr>
        <br>
            <center>
                <h1 class="display-5">Hecha un vistazo a la visita de RTVE a nuestras instalaciones</h1>
            
                <iframe class="responsive-iframe" width="70%" height="70%" src="https://www.youtube.com/embed/o6RqSGNm42E" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
            </center>
            <br>

    </div>
</body>
@endsection
@section('contentfooter')
@endsection 