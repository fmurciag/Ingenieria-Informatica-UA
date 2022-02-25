--******************* PKG_tipos.ADS ***************************
-- Paquete con declaraciones globales de constantes y tipos
--*************************************************************

PACKAGE PKG_tipos IS

   -- tiempo de espera utilizado para simular el movimiento de todos los
   -- vehículos
   RETARDO_MOVIMIENTO : CONSTANT Duration := 0.1;

   -- Excepción lanzada si se detecta una colisión entre coches
   DETECTADA_COLISION : EXCEPTION;

   -- Excepción lanzada si se detecta una colisión de un vehículo con el tren
   DETECTADA_COLISION_TREN : EXCEPTION;


   -- colores de los objetos del sistema
   TYPE T_Color IS (Blue, Yellow, Red, Green,Black, White, Grey);

   -- rango de colores para el semáforo
   SUBTYPE T_ColorSemaforo IS T_Color range Red..Black;


   -----------------------------------------------------------------
   -- DECLARACIONES DE TIPOS y CONSTANTES para la interfaz gráfica
   -----------------------------------------------------------------

   -----------------------------------------------------------------
   -- DECLARACIONES DE CONSTANTES
   -----------------------------------------------------------------

   -- Tamaño de la ventana principal
   CANVAS_WIDTH  : constant := 800;
   CANVAS_HEIGHT : constant := 450;

   -- Coordenada X inicial de los coches (margen derecho de la ventana principal)
   X_INICIO_COCHE : constant := CANVAS_WIDTH;

   -- Fila central del carril por donde circulan los taxis
   Y_CARRIL_TAXI : constant := 220;

   -- Coordenada X por la que circula en tren
   X_TREN : constant := 100;

   -- distancia de seguridad entre coches para evitar colisiones
   DIST_SEGURIDAD : CONSTANT := 40;

   TAM_COCHE : CONSTANT := 40;  -- tamaño en pixels de un coche
   TAM_VAGON : CONSTANT := 40;  -- tamaño en pixels de un vagón de tren

   N_MAX_COCHES   : constant := 20;  -- máximo número de coches por carril
   N_MAX_VAGONES  : CONSTANT := 8;   -- máximo número de vagones de un tren
   N_MAX_PEATONES : constant := 5;  -- máximo número de peatones


   -----------------------------------------------------------------
   -- DECLARACIONES DE TIPOS
   -----------------------------------------------------------------

   -- tamaño de cada buffer por carril
   TYPE T_RangoBufferCoches IS mod N_MAX_COCHES;

   -- rango de velocidades de los coches (en cualquier momento)
   type T_RangoVelocidad is new integer range 0..10;

   -- coordenadas (x,y) de velocidad de los vehículos
   TYPE T_Velocidad IS
   RECORD
      X : T_RangoVelocidad;
      Y : T_RangoVelocidad;
   end record;

   -- subtipos y tipos para las coordenadas (x,y) de un objeto en el
   -- área de dibujo
   subtype T_CoordenadaX is integer range 0..CANVAS_WIDTH;
   SUBTYPE T_CoordenadaY IS Integer RANGE 0..CANVAS_HEIGHT+TAM_VAGON*(N_MAX_VAGONES+1);

   TYPE T_Punto IS
   RECORD
      X : T_CoordenadaX;
      Y : T_CoordenadaY;
   end record;



   -----------------------------------------------------------------
   -- DECLARACIONES DE TIPOS y CONSTANTES para los TAXIS
   -----------------------------------------------------------------

   -- Periodo de generación de taxis
   PERIODO_TAXI   : CONSTANT Duration := 20.0;

   -- velocidad constante de todos los taxis
   VELOCIDAD_TAXI   : CONSTANT := 10;

   -- Tiempo mínimo durante el que un taxi está detenido en la parada
   TIEMPO_PARADA_OBLIGATORIA : CONSTANT Duration := 2.0;

   -- Tiempo máximo de espera de un taxi en la parada para recoger peatones
   TIEMPO_RECOGIDA_PASAJEROS : CONSTANT Duration := 5.0;

   -- número máximo de pasajeros en un taxi
   CAPACIDAD_TAXI : CONSTANT := 2;

   -- tipo registro para almacenar los atributos de un taxi
   type T_RecordTaxi is
   RECORD
      Pos       : T_Punto := (X_INICIO_COCHE, Y_CARRIL_TAXI); -- posicion (x,y) del taxi
      Velocidad : T_Velocidad:=(VELOCIDAD_TAXI, 0);
      libre     : boolean:=true; -- visualizar o no el indicador de ocupado
      presente  : boolean:=true; -- visualizar o no el taxi
   end record;

   TYPE Ptr_T_RecordTaxi IS ACCESS T_RecordTaxi;



   -----------------------------------------------------------------
   -- DECLARACIONES DE TIPOS y CONSTANTES para los TRENES
   -----------------------------------------------------------------

   -- Periodo de generación de trenes
   PERIODO_TREN   : CONSTANT Duration := 30.0;

   -- velocidad constante de todos los trenes
   VELOCIDAD_TREN   : CONSTANT := 5;

   -- rango del número de vagones que puede tener un tren
   SUBTYPE T_NumVagones IS Integer RANGE 0..N_MAX_VAGONES;

   -- rango de colores de los trenes
   subtype T_ColorTren  IS T_Color range Blue..Green;


   -- tipo registro para almacenar los atributos de un tren
   type T_RecordTren is
   RECORD
      Pos       : T_Punto := (X_TREN, 0);    -- posicion (x,y) del tren
      velocidad : T_Velocidad := (0, VELOCIDAD_TREN);
      n_vagones : T_NumVagones;
      Color     : T_ColorTren;
      Presente  : Boolean:=true;    -- visualizar o no el tren
   end record;

   TYPE Ptr_T_RecordTren IS ACCESS T_RecordTren;



   -----------------------------------------------------------------
   -- DECLARACIONES DE TIPOS y CONSTANTES para los PEATONES
   -----------------------------------------------------------------

   -- Tiempo máximo de espera de un peatón en la parada de taxis
   TIEMPO_ESPERA_PEATON : CONSTANT Duration := 10.0;

   -- Periodo de generación de grupos de peatones
   PERIODO_PEATONES   : CONSTANT Duration := 15.0;

   -- rango del número de peatones que pueden aparecer en la parada
   subTYPE T_NumPeatones is integer range 1..N_MAX_PEATONES;

   -- identificador numérico de cada peatón en la parada
   TYPE T_IdPeaton IS MOD N_MAX_PEATONES;

   -- rango de colores de los peatones (colores que puede tener en cualquier momento)
   subtype T_ColorPeaton  IS T_Color range Blue..Black;

   -- rango de colores de los peatones (solo para cuando aparece el peatón en la parada)
   subtype T_ColorAparicionPeaton  IS T_Color range Blue..Red;



   -- tipo registro para almacenar los atributos de un peatón
   type T_RecordPeaton is
   record
      id        : T_IdPeaton; -- identificador del peaton
      Pos       : T_Punto;    -- posicion (x,y) del peaton
      Color     : T_ColorPeaton;
   end record;

   type Ptr_T_RecordPeaton is access T_RecordPeaton;



   -----------------------------------------------------------------
   -- DECLARACIONES DE TIPOS y CONSTANTES para los COCHES
   -----------------------------------------------------------------

   -- velocidad constante utilizada para reanudar la marcha de los coches
   -- que han parado (en el semáforo)
   VELOCIDAD_COCHE  : CONSTANT := 5;

   -- máxima velocidad de un coche
   MAX_VELOCIDAD_COCHE : CONSTANT := 10;

   -- rango de la frecuencia (en segundos) de aparición de los coches
   subtype T_RetardoCoches is integer range 2..6;

   -- rango del número de carriles por los que circulan los coches
   type T_Carril is new integer range 1..2;

   -- identificador numérico de cada coche
   TYPE T_IdCoche IS new Natural;

   -- rango de velocidad con la que aparece un coche
   subtype T_RangoVelocidadAparicion is T_RangoVelocidad range VELOCIDAD_COCHE..MAX_VELOCIDAD_COCHE;

   -- rango de colores para los coches
   subtype T_ColorCoche  IS T_Color range Blue..Green;

   -- tipo registro para almacenar los atributos de un coche
   type T_RecordCoche is
   record
      id        : T_IdCoche;  -- identificador del coche
      Pos       : T_Punto;    -- posicion (x,y) del coche
      velocidad : T_Velocidad;
      Color     : T_ColorCoche;
      Carril    : T_Carril;   -- carril por el que circula el coche
   end record;

   type Ptr_T_RecordCoche is access T_RecordCoche;

end PKG_tipos;

