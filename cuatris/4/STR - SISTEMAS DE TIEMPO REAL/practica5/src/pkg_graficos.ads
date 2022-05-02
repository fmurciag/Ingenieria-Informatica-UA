--************************ PAQUETE PKG_GRAFICOS ***************************
-- Este paquete se encarga de implementar la interfaz gráfica
--*************************************************************************

with pkg_tipos; USE pkg_tipos;
with PKG_bufferGenerico;
with Gdk.Types;         use Gdk.Types;
with Gdk.Cairo;
with Cairo;             use Cairo;
with Pango.Font;        use Pango.Font;
with Gtkada.Style;      use Gtkada.Style;
with Gtk.Window;        use Gtk.Window;
with Gtk.Drawing_Area;  use Gtk.Drawing_Area;
with Gtk.Handlers;      use Gtk.Handlers;
pragma Elaborate_All (Gtk.Handlers);
with Gtk.Main;          use Gtk.Main;
with Glib.Main;         use Glib.Main;
pragma Elaborate_All (Gtk.Main);
with Pango.Layout;       use Pango.Layout;
with Gdk.RGBA;
with Glib;              use Glib;
with Ada.Strings.Bounded;

PACKAGE PKG_graficos IS

   -----------------------------------------------------------------
   -- DECLARACIONES DE PROCEDIMIENTOS Y FUNCIONES PÚBLICAS
   -----------------------------------------------------------------

   -- Procedure que se encarga de redibujar y capturar eventos en la
   -- interfaz gráfica
   procedure Simular_Sistema;

   -- Función que devuelve true si el vehículo ha llegado al semáforo (función sobrecargada)
   FUNCTION Posicion_Stop_Semaforo(Coche: IN T_RecordCoche) RETURN Boolean;
   FUNCTION Posicion_Stop_Semaforo(taxi: IN T_RecordTaxi) RETURN Boolean;

   -- Hacer aparecer el objeto especificado en el sistema (procedimiento sobrecargado)
   PROCEDURE Aparece(taxi: in T_RecordTaxi);
   PROCEDURE Aparece(tren: in T_RecordTren);
   PROCEDURE Aparece(peaton : in T_RecordPeaton);
   PROCEDURE Aparece(coche : in T_RecordCoche);

   -- Eliminar el objeto especificado (procedimiento sobrecargado)
   PROCEDURE Desaparece(taxi: in T_RecordTaxi);
   PROCEDURE Desaparece(peaton : in T_RecordPeaton);
   PROCEDURE Desaparece(tren: in T_RecordTren);
   PROCEDURE Desaparece(coche : in T_RecordCoche);

   -- Función que devuelve true si el vehículo ha alcanzado su posición final (función sobrecargada)
   FUNCTION Pos_Final(Taxi: IN T_RecordTaxi) RETURN Boolean;
   FUNCTION Pos_Final(Tren: IN T_RecordTren) RETURN Boolean;
   FUNCTION Pos_Final(coche : in T_RecordCoche) RETURN Boolean;

   -- Función que devuelve las coordenadas de la posición inicial de un objeto (función sobrecargada)
   FUNCTION Pos_Inicio(Taxi: IN T_RecordTaxi) RETURN T_Punto;
   FUNCTION Pos_Inicio(Tren: IN T_RecordTren) RETURN T_Punto;
   FUNCTION Pos_Inicio(peaton: IN T_RecordPeaton) RETURN T_Punto;
   FUNCTION Pos_Inicio(coche : in T_RecordCoche) RETURN T_Punto;

   -- Actualiza el movimiento de un objeto dependiendo de su velocidad (procedimiento sobrecargado)
   PROCEDURE Actualiza_Movimiento(taxi: IN OUT T_RecordTaxi);
   PROCEDURE Actualiza_Movimiento(tren: IN OUT T_RecordTren);
   PROCEDURE Actualiza_Movimiento(Coche : IN OUT T_RecordCoche);

   -- Actualiza un determinado campo (atributo) del tipo record que caracteriza un objeto (procedimiento sobrecargado)
   PROCEDURE Actualiza_Atributo(posicion : IN T_Punto;
                                taxi : IN OUT T_RecordTaxi);
   PROCEDURE Actualiza_Atributo(posicion : IN T_Punto;
                                tren: IN OUT T_RecordTren);
   PROCEDURE Actualiza_Atributo(posicion : IN T_Punto;
                                peaton: IN OUT T_RecordPeaton);
   PROCEDURE Actualiza_Atributo(posicion : IN T_Punto;
                                coche: IN OUT T_RecordCoche);
   PROCEDURE Actualiza_Atributo(color: IN T_Color;
                                peaton : IN OUT T_RecordPeaton);
   PROCEDURE Actualiza_Atributo(color: IN T_Color;
                                tren : IN OUT T_RecordTren);
   PROCEDURE Actualiza_Atributo(color: IN T_Color;
                                coche: IN OUT T_RecordCoche);
   PROCEDURE Actualiza_Atributo(Velocidad : IN T_RangoVelocidad;
                                taxi : IN OUT T_RecordTaxi);
   PROCEDURE Actualiza_Atributo(Velocidad : IN T_RangoVelocidad;
                                coche: IN OUT T_RecordCoche);
   PROCEDURE Actualiza_Atributo(Velocidad : IN T_RangoVelocidad;
                                tren: IN OUT T_RecordTren);
   PROCEDURE Actualiza_Atributo(id: IN T_IdPeaton;
                                peaton : IN OUT T_RecordPeaton);
   PROCEDURE Actualiza_Atributo(id: IN T_IdCoche;
                               coche: IN OUT T_RecordCoche);
   PROCEDURE Actualiza_Atributo(carril: IN T_Carril;
                                coche: IN OUT T_RecordCoche);
   PROCEDURE Actualiza_Atributo(num_vagones : T_NumVagones;
                                tren: IN OUT T_RecordTren);

   -- Función que devuelve true si el coche tiene velocidad cero
   FUNCTION Coche_Parado(Coche: IN T_RecordCoche) RETURN Boolean;

   -- Función que devuelve true si hay un tren presente en el entorno
   function Hay_Tren return boolean;

   -- Función que devuelve true si el tren está dentro de la zona
   -- de cruce con la carretera
   FUNCTION Dentro_Zona_Cruce(Tren: IN T_RecordTren) RETURN Boolean;

   -- Actualiza el color del semáforo que se visualiza
   PROCEDURE Actualiza_Color_Semaforo(Color: IN T_ColorSemaforo);

   -- Función que devuelve true si hay un taxi presente en el entorno
   function Hay_Taxi return boolean;

   -- Función que devuelve true si el taxi ha alcanzado la parada de taxis
   FUNCTION Pos_Parada_Taxi(taxi: in T_RecordTaxi) RETURN Boolean;

   -- Función que devuelve true si no hay peatones en la parada
   FUNCTION Parada_Taxi_Vacia RETURN Boolean;

   -- Procedimiento que cambia el estado que se visualiza (libre o no) del taxi
   procedure Actualiza_Indicador_Taxi(esta_libre: in boolean; taxi: in out T_RecordTaxi);

   -- Procedure que actualiza el cronometro que se visualiza en el sistema
   procedure Actualiza_Cronometro(tiempo_transcurrido: Duration);

   -- Actualiza el color que se visualiza del peatón
   PROCEDURE Actualiza_Color_Peaton(Color: IN T_ColorPeaton; peaton: in out T_RecordPeaton);

   -- Actualiza el tipo de avería de coche en el carril especificado
   PROCEDURE Actualiza_Tipo_Averia_Carril(Carril : IN T_Carril;
                                          Tipo_Averia:T_Tipo_Averia);

   -- Función que devuelve true si no hay coches en el carril especificado
   function Carril_Vacio(carril: IN T_Carril) return boolean;

   -- Dibuja la señal de prohibido para el carril especificado
   PROCEDURE Dibujar_Carril_Cerrado(Carril : IN T_Carril);

   -- No dibuja la señal de prohibido para el carril especificado
   PROCEDURE Dibujar_Carril_Abierto(Carril : IN T_Carril);

   -- Devuelve true si el coche especificado está averiado
   FUNCTION Tiene_Averia(coche: in T_RecordCoche) RETURN boolean;

   -- Actualiza el contador de reloj de avería del carril especificado
   procedure Actualiza_Reloj_Averia(carril: In T_Carril; Valor_Reloj: IN Integer);


   -----------------------------------------------------------------
   -- DECLARACIONES PRIVADAS
   -----------------------------------------------------------------

   private
      PERIODICIDAD_REDIBUJAR_MILISEGUNDOS : constant Glib.Guint := 100;

      -- Coordenadas X,Y del cronómetro
      X_CRONOMETRO : CONSTANT := 20; --CANVAS_WIDTH-100;
      Y_CRONOMETRO : CONSTANT := 20; --50;

      -- Tamaño máximo (en caracteres) del cronómetro visualizado
      TAM_CRONOMETRO: CONSTANT := 7;

      -- string con los segundos visualizados del cronometro
      hora_segundos : Glib.UTF8_String(1..TAM_CRONOMETRO) := "000.000";


      -- Coordenada X final de los coches (margen izquierdo de la ventana)
      X_FINAL_COCHE : CONSTANT := 0;

      -- Márgenes de la carretera
      Y_SUPERIOR_CARRETERA : CONSTANT := 198;
      Y_INFERIOR_CARRETERA : CONSTANT := 348;

      -- Límites de la zona de paso del tren durante la cual
      -- el semáforo debe estar rojo
      Y_SUPERIOR_ZONA_TREN : CONSTANT := Y_SUPERIOR_CARRETERA-80;
      Y_INFERIOR_ZONA_TREN : CONSTANT := Y_INFERIOR_CARRETERA+20;

      -- Fila central de los carriles por donde circulan los coches
      Y_CARRIL1 : constant := 270;
      Y_CARRIL2 : CONSTANT := 320;

      -- array con las filas centrales de los carriles
      y_carril : array (t_Carril) of integer := (Y_CARRIL1, Y_CARRIL2);

      -- Coordenadas X,Y de la parada del taxi
      X_PARADA_TAXI : CONSTANT := 400;
      Y_PARADA_TAXI : CONSTANT := Y_SUPERIOR_CARRETERA-15;

      -- color actual del semáforo
      color_semaforo : array (1..2) of T_ColorSemaforo := (Black, Green);

      -- flechas de las calles
      NUM_FLECHAS : constant := 5;
      type T_Flechas is array (1..NUM_FLECHAS) of Gdk_Points_Array(1..5);
      flechas1 : T_Flechas;
      flechas2 : T_Flechas;
      flechas3 : T_Flechas;

            -- Crear un tipo string limitado
      PACKAGE T_String_20 IS NEW Ada.Strings.Bounded.Generic_Bounded_Length(20);

      -- Array con los rótulos del estado de las averías
      Rotulo_Averia_Carril : ARRAY (T_Estado_Averia) OF
            T_String_20.Bounded_String := (T_String_20.To_Bounded_String("NO HAY AVERIA"),
                                         T_String_20.To_Bounded_String("COMUNICADA"),
                                         T_String_20.To_Bounded_String("EN REPARACION"));


      -- Array con los rótulos de los tipos de averías
      Rotulo_Tipo_Averia_Carril : ARRAY (T_Tipo_Averia) OF
            T_String_20.Bounded_String := (T_String_20.To_Bounded_String("SIN GASOLINA"),
                                        T_String_20.To_Bounded_String("PINCHAZO"));

      -- Array con el tipo de avería actual en cada carril
      tipo_averia_carril : array(T_Carril) of T_Tipo_Averia;

      -- array con el indicador de si está cerrado cada carril
      carril_cerrado: array(T_Carril) of boolean :=(false,false);


      -- array para los contadores de reloj de las averías en cada carril
      contador_reloj_averia : array (T_Carril) of Integer;



      -- Fuentes de letra utilizados
      fuente_letra : Pango_Font;

   -- Paleta de colores utilizada
      White_RGBA : constant Gdk.RGBA.Gdk_RGBA := (1.0, 1.0, 1.0, 1.0);
      Black_RGBA : constant Gdk.RGBA.Gdk_RGBA := (0.0, 0.0, 0.0, 1.0);
      Grey_RGBA  : constant Gdk.RGBA.Gdk_RGBA := (0.75, 0.75, 0.75, 1.0);
      Green_RGBA : constant Gdk.RGBA.Gdk_RGBA := (0.0, 1.0, 0.0, 1.0);
      Red_RGBA   : constant Gdk.RGBA.Gdk_RGBA := (1.0, 0.0, 0.0, 1.0);
      Yellow_RGBA : constant Gdk.RGBA.Gdk_RGBA := (1.0, 1.0, 0.0, 1.0);
      Blue_RGBA : constant Gdk.RGBA.Gdk_RGBA := (0.0, 0.0, 1.0, 1.0);
      Orange_RGBA : constant Gdk.RGBA.Gdk_RGBA := (1.0, 0.65, 0.0, 1.0);
      Magenta_RGBA : constant Gdk.RGBA.Gdk_RGBA := (1.0, 0.0, 1.0, 1.0);

      White_Gc    : constant Gtkada.Style.Cairo_Color := Gtkada.Style.To_Cairo(White_RGBA);
      Black_Gc    : constant Gtkada.Style.Cairo_Color := Gtkada.Style.To_Cairo(Black_RGBA);
      Grey_Gc     : constant Gtkada.Style.Cairo_Color := Gtkada.Style.To_Cairo(Grey_RGBA);
      Green_Gc    : constant Gtkada.Style.Cairo_Color := Gtkada.Style.To_Cairo(Green_RGBA);
      Red_Gc      : constant Gtkada.Style.Cairo_Color := Gtkada.Style.To_Cairo(Red_RGBA);
      Yellow_Gc   : constant Gtkada.Style.Cairo_Color := Gtkada.Style.To_Cairo(Yellow_RGBA);
      Blue_Gc     : constant Gtkada.Style.Cairo_Color := Gtkada.Style.To_Cairo(Blue_RGBA);
      Orange_Gc     : constant Gtkada.Style.Cairo_Color := Gtkada.Style.To_Cairo(Orange_RGBA);
      Magenta_Gc     : constant Gtkada.Style.Cairo_Color := Gtkada.Style.To_Cairo(Magenta_RGBA);

      PangoLayout: Pango_Layout;

      package Void_Cb is new Gtk.Handlers.Callback (Gtk_Window_Record);
      package Main_Context_Sources is new Glib.Main.Generic_Sources(Gtk_Drawing_Area);
      --package Gint_Timeout is new Gtk.Main.Timeout (Gtk_Drawing_Area);
      package Destroyed is new Gtk.Handlers.Callback (Widget_Type => Gtk_Window_Record);
      package Widget_Handler is new Gtk.Handlers.Callback (Widget_Type => Gtk_Window_Record);

      procedure Draw_Complex (Pixmap : Cairo_Context);

      function Draw_Complex_Buffer (Area : Gtk_Drawing_Area) return Boolean;

      procedure Quit (Win : access Gtk_Window_Record'Class);


      -- DECLARACIÓN DE BUFFERS CIRCULARES PARA GUARDAR INFORMACIÓN DE LA INTERFAZ
      package Pkg_Buffer_coches is new Pkg_bufferGenerico(tipo_dato=>T_RecordCoche, tam=>T_RangoBufferCoches);
      buffer_coches : array(T_Carril) of Pkg_Buffer_coches.T_Buffer;

      package Pkg_Buffer_accidentes is new Pkg_bufferGenerico(tipo_dato=>T_RecordCoche, tam=>T_RangoBufferCoches);
      buffer_accidentes : array(T_Carril) of Pkg_Buffer_accidentes.T_Buffer;

      package Pkg_Buffer_peatones is new Pkg_bufferGenerico(tipo_dato=>T_RecordPeaton, tam=>T_IdPeaton);
      buffer_peatones: Pkg_Buffer_peatones.T_Buffer;

      tren_dibujar : T_RecordTren := ((0,0),(0,0),0, Blue, false);
      taxi_dibujar : T_RecordTaxi := ((0,0),(0,0), true, false);

      -- Procedure que se encarga de inicializar la interfaz gráfica
      procedure Inicializar_Interfaz_Grafica;

      -- Procedure que inicializa el buffer de coches
      procedure Inicializar_Buffer_Coches;

      -- Función que devuelve la posición de un coche dentro del buffer
      -- (está sobrecargada)
      FUNCTION Posicion_Buffer(Id : T_IdCoche;
                               buffer: Pkg_Buffer_coches.T_Buffer) return T_RangoBufferCoches;

      -- Función que devuelve la posición de un peatón dentro del buffer
      -- (está sobrecargada)
      FUNCTION Posicion_Buffer(Id : T_IdPeaton;
                               buffer: Pkg_Buffer_peatones.T_Buffer) return T_IdPeaton;

      -- Procedimiento que devuelve la velocidad del coche de delante y la distancia al mismo
      PROCEDURE Detectar_Coche_Delante(Coche : IN T_RecordCoche;
                                       vel_Delante: OUT T_RangoVelocidad;
                                       distancia : out integer);

      -- Añade un nuevo coche accidentado en el sistema para dibujarlo en la cuneta
      PROCEDURE Coche_Accidentado(coche : in T_RecordCoche);

      -- Función que devuelve true si el coche dispone de sif (Sistema Inteligente de Frenado)
      FUNCTION Tiene_SIF(coche: in T_RecordCoche) return boolean;

      -- Función que devuelve los datos del coche de delante del especificado
      FUNCTION Consulta_Coche_Delante(coche: in T_RecordCoche) return T_RecordCoche;

      -- Función que devuelve el id del último coche del carril especificado
      FUNCTION Id_Ultimo_Coche(carril : T_Carril) RETURN T_IdCoche;


      -- Procedure que se encarga de dibujar en el canvas todos los
      -- objetos del entorno gráfico
      procedure Dibujar_Canvas_Calle(Pixmap : Cairo_Context);

      -- Procedure que se encarga de dibujar en el canvas el semáforo
      procedure Dibujar_Canvas_Semaforo(Pixmap : Cairo_Context);

      -- Procedure que se encarga de dibujar en el canvas un peatón
      -- en una posición y color especificados
      PROCEDURE Dibujar_Canvas_Peaton(Pixmap : Cairo_Context;
                                      posX, posY: in integer;
                                      color_peaton: in Gtkada.Style.Cairo_Color);

      -- Procedure que se encarga de dibujar en el canvas un coche
      -- en una posición y color especificados
      procedure Dibujar_Canvas_Coche(Pixmap : Cairo_Context;
                                     posX, posY: in integer;
                                     color_coche: in Gtkada.Style.Cairo_Color);

      -- Procedure que se encarga de dibujar en el canvas el tren
      procedure Dibujar_Canvas_Tren(Pixmap : Cairo_Context);

      -- Procedure que se encarga de dibujar en el canvas el taxi
      procedure Dibujar_Canvas_Taxi(Pixmap : Cairo_Context);

      -- Procedure que se encarga de dibujar el cronómetro
      procedure Dibujar_Canvas_Cronometro(Pixmap : Cairo_Context);

      -- Función que convierte un color a su correspondiente del
      -- contexto gráfico
      FUNCTION Convertir_Color_GC(Color : T_Color) RETURN Gtkada.Style.Cairo_Color;

      -- Crear las flechas de una calle
      function Crear_Flechas(y : Integer) return T_Flechas;

      -- Dibujar en el canvas las líneas determinadas por un conjunto de puntos
      procedure Dibujar_Lineas(Pixmap : Cairo_Context; color : Gtkada.Style.Cairo_Color; flecha : Gdk_Points_Array; n_puntos : Integer);

      -- Dibujar en el canvas una rueda
      procedure Dibujar_Rueda(Pixmap: Cairo_Context; color_neumatico: Gtkada.Style.Cairo_Color;
                              color_llanta: Gtkada.Style.Cairo_Color; x: Gint; y: Gint;
                              diametro_neumatico:Gint; diametro_llanta: Gint);

      -- Dibujar en el canvas el chasis de un coche
      procedure Dibujar_Chasis(Pixmap: Cairo_Context; color: Gtkada.Style.Cairo_Color;
                               posX: Gint; posY: Gint);

      -- Dibujar en el canvas las ventanas de un coche
      procedure Dibujar_Ventanas_Coche(Pixmap: Cairo_Context; color: Gtkada.Style.Cairo_Color;
                                       posX: Gint; posY: Gint);

      -- Procedimientos que determinan cuándo se genera una avería en un
      -- carril determinado, al pulsar el correspondiente botón en la ventana
      procedure Averia1_Generada(Win : access Gtk_Window_Record'Class);
      procedure Averia2_Generada(Win : access Gtk_Window_Record'Class);

end PKG_graficos;
