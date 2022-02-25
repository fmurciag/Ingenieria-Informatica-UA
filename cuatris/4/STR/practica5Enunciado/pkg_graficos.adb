--************************ PAQUETE PKG_GRAFICOS ***************************
-- Este paquete se encarga de implementar la interfaz gr�fica
--*************************************************************************

with Double_Buffer;     use Double_Buffer;
with Gdk.Color;         use Gdk.Color;
with Gtk.Enums;         use Gtk.Enums;
with Gtk.Box;           use Gtk.Box;
with Gtk.Button;        use Gtk.Button;
with Gtk.Widget;        use Gtk.Widget;
with Ada.Float_Text_IO;
with pango.layout; use pango.layout;
with Pango.Font;
with PKG_debug;
with Ada.exceptions; use Ada.exceptions;
with Gdk.Pixbuf;
with Gdk.Window; use Gdk.Window;
with Gdk; use Gdk;
with Gtk.Grid;
with Gtk.Alignment;
with gnat.OS_Lib;
with Gtk.Check_Button;  use Gtk.Check_Button;
with PKG_Averias;

package body PKG_graficos is

   ------------------------------------------------------------------------
   -- Procedure que se encarga de redibujar y capturar eventos en la
   -- interfaz gr�fica
   ------------------------------------------------------------------------
   procedure Simular_Sistema IS
   BEGIN
      Gtk.Main.Main;
   END Simular_Sistema;


   ------------------------------------------------------------------------
   -- Procedure que se encarga de inicializar la interfaz gr�fica
   ------------------------------------------------------------------------
   procedure Inicializar_Interfaz_Grafica is
      Win        : Gtk_Window;
      Buffer     : Gtk_Double_Buffer;
      Vbox, Hbox : Gtk_Box;
      Id         : G_Source_Id;--Timeout_Handler_Id;
      Button     : Gtk_Button;
      inbuilt_font : Pango.Font.Pango_Font_Description;
      --ventana : Gdk_Window;
      --TAM_BUTTON_EXIT : constant GLib.Gint := 20;
      alinea_button_exit: Gtk.Alignment.Gtk_Alignment;
      A_Check_Button1, A_Check_Button2 : Gtk_Check_Button;
   begin
      -- ventana principal
      Gtk.Window.Gtk_New (Win, Window_Toplevel);
      Set_Title (Win, "PRACTICA STR: CONTROL DE TRAFICO");
      Win.Set_Default_Size(CANVAS_WIDTH, CANVAS_HEIGHT);--+TAM_BUTTON_EXIT);
      Void_Cb.Connect (Win, "destroy", Void_Cb.To_Marshaller (Quit'Access));


      -- bot�n Exit
      Gtk_New (Button, "EXIT");
      Destroyed.Object_Connect (Button,
                                "clicked",
                                Destroyed.To_Marshaller (Quit'Access),
                                Slot_Object => Win);

      Button.Set_Size_Request(100, 20);--TAM_BUTTON_EXIT);
      Gtk.Alignment.Gtk_New(alinea_button_exit, 0.5, 0.5, 0.0, 0.0);
      alinea_button_exit.Add(Button);


      --  Double buffer
      Double_Buffer.Gtk_New(Buffer);
      Buffer.Set_Size_Request(CANVAS_WIDTH, CANVAS_HEIGHT);-- -TAM_BUTTON_EXIT);


      -- Botones para la generaci�n de aver�as
      Gtk_New (A_Check_Button1, "Averia en Carril 1");
      Widget_Handler.Object_Connect(A_Check_Button1, "clicked",
                                    Widget_Handler.To_Marshaller (Averia1_Generada'ACCESS),
                                    Slot_Object => Win);

      Gtk_New (A_Check_Button2, "Averia en Carril 2");
      Widget_Handler.Object_Connect(A_Check_Button2, "clicked",
                                    Widget_Handler.To_Marshaller (Averia2_Generada'ACCESS),
                                    Slot_Object => Win);



      -- Asociar widgets a la ventana principal y mostrarla
      Gtk_New_Hbox (Hbox, Homogeneous =>  True, Spacing => 10);
      Gtk_New_Vbox (Vbox, Homogeneous => False, Spacing => 0);

      Pack_Start (Vbox, Buffer);
      --Pack_Start (Vbox, Button, Expand => False, Fill => False);
      Pack_Start (Vbox, alinea_button_exit);--, Expand => False, Fill => False);

      Pack_Start (Vbox, A_Check_Button1);
      Pack_Start (Vbox, A_Check_Button2);

      Pack_Start (Hbox, Vbox);






      Win.Add(Hbox);

      Show_All (Win);

      --ventana := Get_Window(Gtk_Widget(Buffer));
      --Gdk.Window.Move(ventana, 0, 0);


      Id := Main_Context_Sources.Timeout_Add (PERIODICIDAD_REDIBUJAR_MILISEGUNDOS,
                                              Draw_Complex_Buffer'Access,
                                              Gtk_Drawing_Area (Buffer));


      Gdk_New(pangoLayout, Get_Pango_Context(Buffer));
      inbuilt_font := Pango.Font.From_String("Courier");
      Pango.Layout.Set_Font_Description(pangoLayout, inbuilt_font);


      -- INICIALIZACI�N DEL ESTADO DE TODOS LOS OBJETOS VISUALIZADOS
      Inicializar_Buffer_Coches;

   end Inicializar_Interfaz_Grafica;


   ------------------
   -- Draw_Complex --
   ------------------
   procedure Draw_Complex (Pixmap : Cairo_Context) is
   begin

      Draw_Rectangle (Pixmap, Black_Gc, Filled => True,
                      X     => 0,   Y      => 0,
                      Width => CANVAS_WIDTH, Height => CANVAS_HEIGHT);


      -- DIBUJAR EN EL CANVAS EL ESTADO ACTUAL DE TODOS LOS OBJETOS
      Dibujar_Canvas_Calle(Pixmap);

   end Draw_Complex;


   -------------------------
   -- Draw_Complex_Buffer --
   -------------------------
   function Draw_Complex_Buffer (Area : Gtk_Drawing_Area) return Boolean is
      Buffer : Gtk_Double_Buffer := Gtk_Double_Buffer (Area);

   begin
      Draw_Complex (Get_Pixmap (Buffer));

      Double_Buffer.Thaw(Buffer);

      --Double_Buffer.Draw (Buffer);

      return True;
   end Draw_Complex_Buffer;



   ----------
   -- Quit --
   ----------
   procedure Quit (Win : access Gtk_Window_Record'Class) is
      pragma Warnings (Off, Win);
   begin
      Gnat.OS_Lib.OS_Exit(0);
      --Gtk.Main.Main_Quit;
      --Ada.Task_Identification.Abort_Task(Ada.Task_Identification.Current_Task);
   end Quit;



   ------------------------------------------------------------------------
   -- Procedure que inicializa el buffer de coches
   ------------------------------------------------------------------------
   procedure Inicializar_Buffer_Coches is
      coche : T_RecordCoche;
   BEGIN
      for carril IN T_Carril loop
         FOR I IN T_RangoBufferCoches LOOP
            Coche.Id := 0;
            Coche.Pos.X := 0;
            Coche.Pos.Y := y_carril(carril);
            coche.velocidad := (VELOCIDAD_COCHE, 0);
            Coche.Color:= T_ColorCoche'First;
            coche.carril := carril;
            Pkg_Buffer_Coches.Actualiza_Item(i, Coche, Buffer_Coches(carril));

            -- Inicializamos tambi�n el buffer de coches accidentados
            Pkg_Buffer_Accidentes.Actualiza_Item(i, Coche, Buffer_Accidentes(carril));
         END LOOP;
      END LOOP;

      exception
       when event: others =>
        PKG_debug.Escribir("ERROR en Inicializar_Buffer_Coches: " & Exception_Name(Exception_Identity(event)));

   end Inicializar_Buffer_Coches;



   ------------------------------------------------------------------------
   -- Funci�n que devuelve la posici�n de un coche dentro del buffer
   -----------------------------------------------------------------------
   FUNCTION Posicion_Buffer(Id : T_IdCoche;
                            Buffer: Pkg_Buffer_Coches.T_Buffer) RETURN T_RangoBufferCoches is
      coche_aux     : T_RecordCoche;
      pos           : T_RangoBufferCoches;
   BEGIN
      BEGIN
          Pos := Pkg_Buffer_Coches.Posicion_Primer_Item(Buffer);
          Coche_Aux := Pkg_Buffer_Coches.Consulta_Item(Pos, Buffer);
          WHILE Coche_Aux.Id /= Id LOOP
             Pos := Pos+1;
             Coche_Aux := Pkg_Buffer_Coches.Consulta_Item(Pos, Buffer);
          END LOOP;
      exception
          when event: others =>
            PKG_debug.Escribir("ERROR en Posicion_Buffer: " & Exception_Name(Exception_Identity(event)));
      end;

      RETURN(Pos);
   END Posicion_Buffer;


   ------------------------------------------------------------------------
   -- Funci�n que devuelve true si el veh�culo ha llegado al sem�foro (funci�n sobrecargada)
   ------------------------------------------------------------------------
   FUNCTION Posicion_Stop_Semaforo(Coche: IN T_RecordCoche) RETURN Boolean IS
   BEGIN
      RETURN Coche.Pos.X  <= X_TREN+75
             AND (coche.pos.x + Integer(coche.velocidad.x)) >= X_TREN+75;

   END Posicion_Stop_Semaforo;

   FUNCTION Posicion_Stop_Semaforo(Taxi: IN T_RecordTaxi) RETURN Boolean IS
   BEGIN
      RETURN taxi.Pos.X  <= X_TREN+75
             AND (taxi.pos.x + Integer(taxi.velocidad.x)) >= X_TREN+75;

   END Posicion_Stop_Semaforo;




   ------------------------------------------------------------------------
   -- Hacer aparecer el objeto especificado en el sistema (procedimiento sobrecargado)
   ------------------------------------------------------------------------
   PROCEDURE Aparece(taxi: in T_RecordTaxi) is
   BEGIN
      Taxi_Dibujar := taxi;
   END Aparece;

   PROCEDURE Aparece(tren: in T_RecordTren) is
   BEGIN
      Tren_Dibujar := Tren;
   END Aparece;

   PROCEDURE Aparece(peaton : in T_RecordPeaton) is
   BEGIN
      Pkg_Buffer_Peatones.Insertar_Ultimo_Item(peaton, Buffer_Peatones);

   END Aparece;

   PROCEDURE Aparece(coche : in T_RecordCoche) is
   BEGIN
      Pkg_Buffer_Coches.Insertar_Ultimo_Item(Coche, Buffer_Coches(coche.carril));
   END Aparece;


   ------------------------------------------------------------------------
   -- Eliminar el objeto especificado (procedimiento sobrecargado)
   ------------------------------------------------------------------------
   PROCEDURE Desaparece(taxi : T_RecordTaxi) IS
   BEGIN
      Taxi_Dibujar := ((0,0),(0,0), true, false);
   end Desaparece;

   PROCEDURE Desaparece(tren : T_RecordTren) IS
   BEGIN
      Tren_Dibujar := ((0,0),(0,0),0, Blue, false);
   end Desaparece;

   PROCEDURE Desaparece(Peaton : IN T_RecordPeaton) IS
      Peaton_Aux : T_RecordPeaton;
      pos        : T_IdPeaton;
   BEGIN
      -- Buscar posici�n del peat�n dentro del buffer
      Pos := Posicion_Buffer(peaton.Id, Buffer_Peatones);
      Pkg_Buffer_Peatones.Extraer_Posicion_Item(Pos, peaton_aux, buffer_peatones);
      exception
       when event: others =>
        PKG_debug.Escribir("ERROR en Desaparece peaton: " & Exception_Name(Exception_Identity(event)));

   end Desaparece;

   PROCEDURE Desaparece(coche : in T_RecordCoche) IS
      coche_aux : T_RecordCoche;
      pos       : T_RangoBufferCoches;
   BEGIN
      -- Buscar posici�n del coche dentro del buffer
      Pos := Posicion_Buffer(coche.Id, Buffer_Coches(coche.Carril));
      Pkg_Buffer_Coches.Extraer_Posicion_Item(pos, coche_aux, buffer_coches(coche.carril));
      exception
       when event: others =>
        PKG_debug.Escribir("ERROR en Desaparece coche: " & Exception_Name(Exception_Identity(event)));

   end Desaparece;


   ------------------------------------------------------------------------
   -- Funci�n que devuelve true si el objeto ha alcanzado su posici�n final (funci�n sobrecargada)
   ------------------------------------------------------------------------
   FUNCTION Pos_Final(taxi: in T_RecordTaxi) RETURN Boolean IS
   BEGIN
      RETURN Taxi.Pos.X <= X_FINAL_COCHE;
   END Pos_Final;

   FUNCTION Pos_Final(tren: in T_RecordTren) RETURN Boolean IS
   BEGIN
      RETURN (Tren.Pos.Y-tren.n_vagones*TAM_VAGON) > CANVAS_HEIGHT;
   END Pos_Final;

   FUNCTION Pos_Final(coche : in T_RecordCoche) RETURN Boolean IS
   BEGIN
      RETURN Coche.Pos.X <= X_FINAL_COCHE;
   END Pos_Final;


   ------------------------------------------------------------------------
   -- Funci�n que devuelve las coordenadas de la posici�n inicial de un objeto (funci�n sobrecargada)
   ------------------------------------------------------------------------
   FUNCTION Pos_Inicio(taxi : T_RecordTaxi) RETURN T_Punto IS
   BEGIN
      RETURN (X=>X_INICIO_COCHE, Y=>Y_CARRIL_TAXI);
   END Pos_Inicio;

   FUNCTION Pos_Inicio(tren: T_RecordTren) RETURN T_Punto IS
   BEGIN
      RETURN (X=>X_TREN, Y=>0);
   END Pos_Inicio;

   FUNCTION Pos_Inicio(peaton : T_RecordPeaton) RETURN T_Punto IS
   BEGIN
      RETURN (X_PARADA_TAXI+Integer(peaton.Id)*10, Y_PARADA_TAXI);
   END Pos_Inicio;

   FUNCTION Pos_Inicio(coche : T_RecordCoche) RETURN T_Punto IS
   BEGIN
      RETURN (X=>X_INICIO_COCHE, Y=>Y_Carril(coche.Carril));
   END Pos_Inicio;




   ------------------------------------------------------------------------
   -- Actualiza la posici�n de un objeto dependiendo de su velocidad (procedimiento sobrecargado)
   ------------------------------------------------------------------------
   PROCEDURE Actualiza_Movimiento(taxi: IN OUT T_RecordTaxi) IS
      Nueva_Pos_X   : Integer;
   BEGIN
      -- calcula la nueva posici�n, comprobando que no nos salimos
      -- fuera de las dimensiones de la ventana
      nueva_pos_x := taxi.pos.x - Integer(taxi.velocidad.x);
      IF nueva_pos_x < 0 THEN
         nueva_pos_x := 0;
      ELSIF Nueva_Pos_X > CANVAS_WIDTH THEN
         Nueva_Pos_X := CANVAS_WIDTH;
      END IF;

      taxi.Pos.X:= Nueva_Pos_X;

      taxi_dibujar := taxi;

      IF taxi.pos.Y >= (Tren_Dibujar.Pos.Y - (Tren_Dibujar.n_vagones+1)* TAM_VAGON)
        AND taxi.Pos.Y <= Tren_Dibujar.Pos.Y + 20
        and taxi.Pos.x - 20 <= X_TREN and taxi.Pos.X + 20 >= X_TREN then
           RAISE DETECTADA_COLISION_TREN;
      END IF;

   end Actualiza_Movimiento;


   PROCEDURE Actualiza_Movimiento(tren: IN OUT T_RecordTren) IS
   nueva_pos_y : Integer;
   BEGIN
      -- calcula la nueva posici�n: podemos salirnos fuera de las dimensiones
      -- de la ventana para que vayan desapareciendo todos los vagones
      nueva_pos_y := tren.pos.y + Integer(tren.velocidad.y);
      tren.pos.y := nueva_pos_y;

      tren_dibujar := tren;
   end Actualiza_Movimiento;



   PROCEDURE Actualiza_Movimiento(Coche : IN OUT T_RecordCoche) IS
      Nueva_Pos_X   : Integer;
      Coche_Delante : T_RecordCoche;
      vel_delante    : T_RangoVelocidad;
      dist_obstaculo : Integer;
      pos           : T_RangoBufferCoches;
   BEGIN
      -- Evitar colisiones s�lo si el coche dispone de SIF
      IF (PKG_graficos.Tiene_SIF(Coche)) THEN
         -- Obtener la velocidad del coche de delante y la distancia al mismo
         Detectar_Coche_Delante(Coche, vel_Delante, Dist_Obstaculo);
         IF dist_obstaculo < DIST_SEGURIDAD THEN
            Actualiza_Atributo(T_RangoVelocidad'Min(vel_delante, coche.velocidad.x), coche);
         END IF;
      END IF;

      -- calcula la nueva posici�n, comprobando que no nos salimos
      -- fuera de las dimensiones de la ventana
      nueva_pos_x := coche.pos.x - Integer(coche.velocidad.x);
      IF nueva_pos_x < 0 THEN
         nueva_pos_x := 0;
      ELSIF Nueva_Pos_X > CANVAS_WIDTH THEN
         Nueva_Pos_X := CANVAS_WIDTH;
      END IF;

      Coche.Pos.X:= Nueva_Pos_X;

      begin
         -- Buscar posici�n del coche dentro del buffer
         Pos := Posicion_Buffer(Coche.Id, Buffer_Coches(Coche.Carril));
         Pkg_Buffer_Coches.Actualiza_Item(pos, coche, buffer_coches(coche.carril));

         Coche_Delante := PKG_graficos.Consulta_Coche_Delante(coche);

      exception
          when event: others =>
           PKG_debug.Escribir("ERROR en Actualiza_Movimiento de un coche: " & Exception_Name(Exception_Identity(event)));
      end;

      IF coche.id /= coche_delante.id then
         IF Coche.Pos.X < Coche_Delante.Pos.X + TAM_COCHE-10 THEN
           RAISE DETECTADA_COLISION;
         END IF;
      END IF;

      IF coche.pos.Y >= (Tren_Dibujar.Pos.Y - (Tren_Dibujar.n_vagones+1)* TAM_VAGON)
        AND coche.Pos.Y <= Tren_Dibujar.Pos.Y + 20
        and coche.Pos.x - 20 <= X_TREN and coche.Pos.X + 20 >= X_TREN then
           RAISE DETECTADA_COLISION_TREN;
      END IF;


   end Actualiza_Movimiento;


   ------------------------------------------------------------------------
   -- Actualiza un determinado campo (atributo) del tipo record que caracteriza un objeto (procedimiento sobrecargado)
   ------------------------------------------------------------------------
   PROCEDURE Actualiza_Atributo(posicion : IN T_Punto; taxi : IN OUT T_RecordTaxi) is
   BEGIN
      taxi.pos:= posicion;
   END Actualiza_Atributo;


   PROCEDURE Actualiza_Atributo(posicion : IN T_Punto; tren: IN OUT T_RecordTren) is
   BEGIN
      tren.pos:= posicion;
   END Actualiza_Atributo;


   PROCEDURE Actualiza_Atributo(posicion : IN T_Punto; peaton: IN OUT T_RecordPeaton) is
   BEGIN
      peaton.pos:= posicion;
   END Actualiza_Atributo;


   PROCEDURE Actualiza_Atributo(posicion : IN T_Punto; coche: IN OUT T_RecordCoche) is
   BEGIN
      coche.pos:= posicion;
   END Actualiza_Atributo;


   PROCEDURE Actualiza_Atributo(color : IN T_Color; Peaton : IN OUT T_RecordPeaton) IS
   BEGIN
      peaton.color := color;
   END Actualiza_Atributo;


   PROCEDURE Actualiza_Atributo(color : IN T_Color; Tren : IN OUT T_RecordTren) IS
   BEGIN
      tren.color := color;
   END Actualiza_Atributo;

   PROCEDURE Actualiza_Atributo(color : IN T_Color; coche: IN OUT T_RecordCoche) IS
   BEGIN
      coche.color := color;
   END Actualiza_Atributo;


   PROCEDURE Actualiza_Atributo(Velocidad : IN T_RangoVelocidad; taxi : IN OUT T_RecordTaxi) is
   BEGIN
      taxi.Velocidad := (velocidad,0);
   END Actualiza_Atributo;


   PROCEDURE Actualiza_Atributo(Velocidad : IN T_RangoVelocidad; coche: IN OUT T_RecordCoche) is
   BEGIN
      coche.Velocidad := (velocidad,0);
   END Actualiza_Atributo;


   PROCEDURE Actualiza_Atributo(Velocidad : IN T_RangoVelocidad; tren: IN OUT T_RecordTren) is
   BEGIN
      tren.Velocidad := (0, velocidad);
   END Actualiza_Atributo;


   PROCEDURE Actualiza_Atributo(id: IN T_IdPeaton; Peaton : IN OUT T_RecordPeaton) IS
   BEGIN
      peaton.id:= id;
   END Actualiza_Atributo;


   PROCEDURE Actualiza_Atributo(id: IN T_IdCoche; coche: IN OUT T_RecordCoche) IS
   BEGIN
      coche.id:= id;
   END Actualiza_Atributo;


   PROCEDURE Actualiza_Atributo(carril: IN T_Carril; Coche: IN OUT T_RecordCoche) IS
   BEGIN
      coche.carril:= carril;
   END Actualiza_Atributo;


   PROCEDURE Actualiza_Atributo(Num_Vagones : T_NumVagones; Tren: IN OUT T_RecordTren) IS
   BEGIN
      Tren.N_Vagones := Num_Vagones;
   end Actualiza_Atributo;



   ------------------------------------------------------------------------
   -- Funci�n que devuelve true si el coche tiene velocidad cero
   ------------------------------------------------------------------------
   FUNCTION Coche_Parado(coche: in T_RecordCoche) RETURN Boolean IS
   BEGIN
      RETURN Coche.Velocidad =  (0,0);
   END Coche_Parado;


   ------------------------------------------------------------------------
   -- Funci�n que devuelve true si hay un tren presente en el entorno
   ------------------------------------------------------------------------
   function Hay_Tren return boolean IS
   BEGIN
      RETURN Tren_Dibujar.Presente;
   end Hay_Tren;


   ------------------------------------------------------------------------
   -- Funci�n que devuelve true si el tren ent� dentro de la zona
   -- de cruce con la carretera
   ------------------------------------------------------------------------
   FUNCTION Dentro_Zona_Cruce(Tren: IN T_RecordTren) RETURN Boolean IS
   BEGIN
      RETURN Tren.Pos.Y >= Y_SUPERIOR_ZONA_TREN
         and (Tren.Pos.Y-tren.n_vagones*TAM_VAGON) <= Y_INFERIOR_ZONA_TREN;
   END Dentro_Zona_Cruce;



   ------------------------------------------------------------------------
   -- Actualiza el color del sem�foro que se visualiza
   ------------------------------------------------------------------------
   PROCEDURE Actualiza_Color_Semaforo(color: IN T_ColorSemaforo) IS
   BEGIN
      CASE Color IS
         WHEN Red    => color_semaforo := (Red, Black);
         WHEN Green  => Color_semaforo := (Black, Green);
         WHEN Black  => Color_semaforo := (Black, Black);

      END CASE;
   end Actualiza_Color_Semaforo;


   ------------------------------------------------------------------------
   -- Funci�n que devuelve true si hay un taxi presente en el entorno
   ------------------------------------------------------------------------
   function Hay_Taxi return boolean IS
   BEGIN
      RETURN Taxi_Dibujar.Presente;
   end Hay_Taxi;



   ------------------------------------------------------------------------
   -- Funci�n que devuelve true si el taxi ha alcanzado la parada de taxis
   ------------------------------------------------------------------------
   FUNCTION Pos_Parada_Taxi(taxi: in T_RecordTaxi) RETURN Boolean IS
   BEGIN
      RETURN Taxi.Pos.X <= X_PARADA_TAXI + VELOCIDAD_TAXI/2
             and taxi.pos.x >= X_PARADA_TAXI - VELOCIDAD_TAXI/2;

   END Pos_Parada_Taxi;


   ------------------------------------------------------------------------
   -- Procedimiento que cambia el estado que se visualiza (libre o no) del taxi
   ------------------------------------------------------------------------
   PROCEDURE Actualiza_Indicador_Taxi(Esta_Libre: IN Boolean; taxi : in out T_RecordTaxi) is
   BEGIN
      Taxi.Libre := Esta_Libre;
      taxi_dibujar := taxi;

   END Actualiza_Indicador_Taxi;



   ------------------------------------------------------------------------
   -- Funci�n que devuelve true si no hay peatones en la parada
   ------------------------------------------------------------------------
   FUNCTION Parada_Taxi_Vacia RETURN Boolean IS
   BEGIN
      RETURN Pkg_Buffer_Peatones.Vacio(Buffer_Peatones);
   END Parada_Taxi_Vacia;


   ------------------------------------------------------------------------
   -- Procedure que actualiza el cron�metro que se visualiza en el sistema
   ------------------------------------------------------------------------
   PROCEDURE Actualiza_Cronometro(tiempo_transcurrido: Duration) IS
   BEGIN
      Ada.Float_Text_IO.put(Hora_segundos, Float(tiempo_transcurrido), 3, 0);
   END Actualiza_Cronometro;


   -------------------------------------------------
   -- Actualiza el color que se visualiza del peat�n
   -------------------------------------------------
   PROCEDURE Actualiza_Color_Peaton(Color: IN T_ColorPeaton; peaton: in out T_RecordPeaton) is
   pos : T_IdPeaton;
   begin
      Actualiza_Atributo(color, peaton);

      -- buscar posici�n del peat�n dentro del buffer
      pos := Posicion_Buffer(peaton.id, buffer_peatones);
      pkg_buffer_peatones.Actualiza_Item(pos, peaton, buffer_peatones);
      exception
       when event: others =>
        PKG_debug.Escribir("ERROR en Actualiza_Color_Peaton: " & Exception_Name(Exception_Identity(event)));

   end Actualiza_Color_Peaton;


   ------------------------------------------------------------------------
   -- Procedimiento que actualiza el tipo de aver�a de coche en el carril
   -- especificado
   ------------------------------------------------------------------------
   PROCEDURE Actualiza_Tipo_Averia_Carril(Carril : IN T_Carril;
                                          tipo_averia:T_Tipo_Averia) is
   BEGIN
      Tipo_Averia_Carril(Carril) := Tipo_Averia;
   END Actualiza_Tipo_Averia_Carril;


   ------------------------------------------------------------------------
   -- Funci�n que devuelve true si no hay coches en el carril especificado
   ------------------------------------------------------------------------
   FUNCTION Carril_Vacio(Carril: IN T_Carril) RETURN Boolean IS
   BEGIN
      RETURN Pkg_Buffer_Coches.Vacio(Buffer_Coches(Carril));
   END Carril_Vacio;



   ------------------------------------------------------------------------
   -- Dibuja la se�al de prohibido para el carril especificado
   ------------------------------------------------------------------------
   PROCEDURE Dibujar_Carril_Cerrado(Carril : IN T_Carril) IS
   BEGIN
      Carril_Cerrado(Carril) := True;
   END Dibujar_Carril_Cerrado;



   ------------------------------------------------------------------------
   -- NO dibuja la se�al de prohibido para el carril especificado
   ------------------------------------------------------------------------
   PROCEDURE Dibujar_Carril_Abierto(Carril : IN T_Carril) IS
   BEGIN
      Carril_Cerrado(Carril) := false;
   END Dibujar_Carril_Abierto;



   ------------------------------------------------------------------------
   -- Devuelve true si el coche especificado est� averiado
   ------------------------------------------------------------------------
   FUNCTION Tiene_Averia(Coche: IN T_RecordCoche) RETURN Boolean IS
   BEGIN
      -- Comprobar si se ha comunicado aver�a en el carril y el coche
      -- es el �ltimo dentro del mismo
      IF coche.id = Id_Ultimo_Coche(coche.carril)
         AND PKG_averias.OP_Indicador_Averia(coche.carril).Consulta_Indicador /= NO_AVERIA THEN
         RETURN True;
      ELSE
         RETURN False;
      END IF;
   END Tiene_Averia;


   -------------------------------------------------------------------
   -- Actualiza el contador de reloj de aver�a del carril especificado
   -------------------------------------------------------------------
   PROCEDURE Actualiza_Reloj_Averia(Carril: IN T_Carril; Valor_Reloj: IN Integer) IS
   BEGIN
      Contador_Reloj_Averia(Carril) := Valor_Reloj;
   end Actualiza_Reloj_Averia;




   ----------------------------------------
   -- PROCEDIMIENTOS Y FUNCIONES PRIVADAS :
   ----------------------------------------

   ------------------------------------------------------------------------
   -- Funci�n que devuelve la posici�n de un peat�n dentro del buffer
   -----------------------------------------------------------------------
   FUNCTION Posicion_Buffer(Id : T_IdPeaton;
                            Buffer: Pkg_Buffer_Peatones.T_Buffer) RETURN T_IdPeaton is
      peaton_aux : T_RecordPeaton;
      pos        : T_IdPeaton;
   BEGIN
      begin
          Pos := Pkg_Buffer_Peatones.Posicion_Primer_Item(Buffer);
          peaton_Aux := Pkg_Buffer_Peatones.Consulta_Item(Pos, Buffer);
          WHILE peaton_Aux.Id /= Id LOOP
             Pos := Pos+1;
             peaton_Aux := Pkg_Buffer_Peatones.Consulta_Item(Pos, Buffer);
          END LOOP;
      exception
         when event: others =>
            PKG_debug.Escribir("ERROR en Posicion_Buffer: " & Exception_Name(Exception_Identity(event)));
      end;

      RETURN(Pos);
   END Posicion_Buffer;



   ------------------------------------------------------------------------
   -- Procedimiento que devuelve la velocidad del coche de delante y la distancia
   -- al mismo
   -- Si no hay coche delante, devuelve la velocidad del propio coche y distancia = -1
   ------------------------------------------------------------------------
   PROCEDURE Detectar_Coche_Delante(Coche : IN T_RecordCoche;
                                    vel_Delante: OUT T_RangoVelocidad;
                                    Distancia : OUT Integer) IS
   Coche_Delante: T_RecordCoche;

   BEGIN
      Coche_Delante := PKG_graficos.Consulta_Coche_Delante(coche);

      IF Coche.Id /= Coche_Delante.Id THEN
         Distancia := Coche.Pos.X - Coche_Delante.Pos.X - TAM_COCHE;
         vel_delante := coche_delante.velocidad.x;
      ELSE
         Distancia := -1;
         vel_delante := coche.velocidad.x;
      END IF;
      exception
       when event: others =>
        PKG_debug.Escribir("ERROR en Detectar_Coche_Delante: " & Exception_Name(Exception_Identity(event)));

   END Detectar_Coche_Delante;



   ------------------------------------------------------------------------
   -- Funci�n que devuelve los datos del coche de delante del especificado
   -- en el mismo carril
   -- Si no tiene ninguno delante, devuelve los datos del propio coche
   ------------------------------------------------------------------------
   FUNCTION Consulta_Coche_Delante(coche : in T_RecordCoche) return T_RecordCoche is
      Coche_Delante    : T_RecordCoche;
      Pos_Delante, Pos : T_RangoBufferCoches;
   BEGIN
      begin
          -- Buscar posici�n del coche dentro del buffer
          Pos := Posicion_Buffer(coche.Id, Buffer_Coches(coche.Carril));

          -- si es el primer coche en el carril
          if pos = Pkg_Buffer_coches.Posicion_Primer_Item(Buffer_Coches(coche.carril)) then
             pos_Delante := pos;  -- entonces no tiene ninguno delante
          ELSE
             --Id_Delante := Id-1;
             pos_Delante := pos-1;
          end if;
          Coche_delante := Pkg_Buffer_Coches.Consulta_Item(pos_delante, Buffer_Coches(coche.carril));
      exception
       when event: others =>
        PKG_debug.Escribir("ERROR en Consulta_Coche_Delante: " & Exception_Name(Exception_Identity(event)));
      end;

      return coche_delante;
   end Consulta_Coche_Delante;


   ------------------------------------------------------------------------
   -- Funci�n que devuelve el id del �ltimo coche del carril especificado
   ------------------------------------------------------------------------
   FUNCTION Id_Ultimo_Coche(Carril : T_Carril) RETURN T_IdCoche IS
      Pos  : PKG_tipos.T_RangoBufferCoches;
      coche: T_RecordCoche;
   BEGIN
      Pos := Pkg_Buffer_Coches.Posicion_Ultimo_Item(Buffer_Coches(Carril));
      coche := Pkg_Buffer_Coches.Consulta_Item(Pos, Buffer_Coches(Carril));
      RETURN Coche.Id;
   END Id_Ultimo_Coche;


   ------------------------------------------------------------------------
   -- A�ade un nuevo coche accidentado en el sistema para dibujarlo
   -- en la cuneta
   ------------------------------------------------------------------------
   PROCEDURE Coche_Accidentado(Coche : IN T_RecordCoche) IS
   BEGIN
      Pkg_Buffer_Accidentes.Insertar_Ultimo_Item(Coche, Buffer_Accidentes(Coche.Carril));
   end Coche_Accidentado;


   ------------------------------------------------------------------------
   -- Funci�n que devuelve true si el coche dispone de sif (Sistema Inteligente de Frenado)
   ------------------------------------------------------------------------
   FUNCTION Tiene_SIF(coche: in T_RecordCoche) return boolean is
   BEGIN
      return true;  -- Todos los coches tienen SIF
      -- RETURN (Coche.Color = Green);
   end Tiene_SIF;



   ------------------------------------------------------------------------
   -- Procedure que se encarga de dibujar en el canvas la calle, con la
   -- l�nea cont�nua y el paso de peatones y los sem�foros
   -- (parte est�tica y din�mica de la interfaz)
   ------------------------------------------------------------------------
   procedure Dibujar_Canvas_Calle(Pixmap : Cairo_Context) is
      pos_primer_coche : T_RangoBufferCoches;
      pos_ultimo_coche : T_RangoBufferCoches;
      pos_Coche        : T_RangoBufferCoches;
      Item_Coche       : T_RecordCoche;
      Y_Accidente      : T_CoordenadaY;
      Id_primer_peaton: T_IdPeaton;
      Id_ultimo_peaton: T_IdPeaton;
      Id_Peaton       : T_IdPeaton;
      Item_Peaton     : T_RecordPeaton;

      Rotulo_Parada_Taxi : String := "Parada Taxi";

      Rotulo1_Averia  : String := "CONTROL DE AVERIAS";
      Rotulo2_Averia  : String := "descripcion       estado           duracion";
      Rotulo_Carril1  : String := "CARRIL 1";
      Rotulo_Carril2  : String := "CARRIL 2";
      averia_carril   : T_Estado_Averia;
      x, y            : Gint; -- coordenadas


   begin
      -- Dibujar el asfalto de la calle
      Draw_Rectangle (Pixmap, Grey_Gc, Filled => True,
                      X     => 1,  Y      => Y_CARRIL1-70,
                      Width => CANVAS_WIDTH, Height => 150);

      -- Dibujar la l�nea discont�nua de la calle
      Cairo.Set_Line_Width(Pixmap, 5.0);
      FOR I IN 1..CANVAS_WIDTH / 5 LOOP
         if i rem 2 /= 0 then
            Draw_Line(Pixmap, White_Gc, Gint((i-1)* 5), Y_SUPERIOR_CARRETERA + 50, Gint((i-1)* 5)+5, Y_SUPERIOR_CARRETERA + 50);
         end if;
      end loop;
      Cairo.Set_Line_Width(Pixmap, 2.0); -- restaurar valor por defecto

      -- Dibujar la l�nea cont�nua de la calle
      Draw_Line(Pixmap, White_Gc, 1, Y_SUPERIOR_CARRETERA + 100, CANVAS_WIDTH, Y_SUPERIOR_CARRETERA + 100);

      -- dibujar las flechas en las calles
      flechas1 := Crear_Flechas(Y_SUPERIOR_CARRETERA+25);
      flechas2 := Crear_Flechas(Y_SUPERIOR_CARRETERA+75);
      flechas3 := Crear_Flechas(Y_SUPERIOR_CARRETERA+125);

      for i in 1..NUM_FLECHAS loop
         Dibujar_Lineas(Pixmap, White_Gc, flechas1(i), 5);
         Dibujar_Lineas(Pixmap, White_Gc, flechas2(i), 5);
         Dibujar_Lineas(Pixmap, White_Gc, flechas3(i), 5);
      end loop;


      -- Dibujar la v�a del tren
      Draw_Rectangle (Pixmap, Black_Gc, Filled => True,
                      X     => X_TREN+20,  Y      => 0,
                      Width => 10, Height => CANVAS_HEIGHT);

      FOR i IN 1..CANVAS_HEIGHT/10 LOOP
         Draw_Line(Pixmap, Yellow_Gc, X_TREN+20, Gint((i-1)*10), X_TREN+20+8, Gint((i-1)*10));
      end loop;


      -- Dibujar todos los COCHES
      FOR Carril IN T_Carril LOOP
         if not Pkg_Buffer_coches.Vacio(Buffer_Coches(carril)) then
            pos_primer_coche := Pkg_Buffer_coches.Posicion_Primer_Item(Buffer_Coches(carril));
            pos_ultimo_coche := Pkg_Buffer_coches.Posicion_Ultimo_Item(Buffer_Coches(carril));
            pos_coche := pos_primer_coche;
            loop
               item_coche := Pkg_Buffer_coches.Consulta_Item(pos_coche, buffer_coches(carril));

               Dibujar_Canvas_Coche(Pixmap, item_coche.pos.x, item_coche.pos.y, Convertir_Color_GC(item_coche.color));

               exit when pos_coche = pos_ultimo_coche;

               pos_Coche := pos_coche+1;
            END LOOP; -- fin de los coches de un carril
         end if; -- fin si el carril no est� vacio
      end loop; -- fin de los carriles

      -- Tambi�n dibujamos los coches ACCIDENTADOS
      FOR Carril IN T_Carril LOOP
         if not Pkg_Buffer_accidentes.Vacio(Buffer_accidentes(carril)) then
            pos_primer_coche := Pkg_Buffer_accidentes.Posicion_Primer_Item(Buffer_accidentes(carril));
            pos_ultimo_coche := Pkg_Buffer_accidentes.Posicion_Ultimo_Item(Buffer_accidentes(carril));
            pos_coche := pos_primer_coche;
            loop
               item_coche := Pkg_Buffer_accidentes.Consulta_Item(pos_coche, buffer_accidentes(carril));

               IF (Carril = 1) THEN
                  Y_Accidente := Y_Carril(2) + 50;
               ELSE
                  Y_Accidente := Y_Carril(Carril) + 100;
               END IF;

               Dibujar_Canvas_Coche(Pixmap, item_coche.pos.x, y_accidente, Convertir_Color_GC(item_coche.color));

               exit when pos_coche = pos_ultimo_coche;

               pos_Coche := pos_coche+1;
            END LOOP; -- fin de los coches accidentados de un carril
         end if; -- fin si el carril de accidentes no est� vacio
      end loop; -- fin de los carriles con accidentes


      -- Dibujar el sem�foro
      Dibujar_Canvas_Semaforo(Pixmap);

      -- Dibujar el tren si est� actualmente presente
      IF Tren_Dibujar.Presente THEN
         Dibujar_Canvas_Tren(Pixmap);
      END IF;

      -- Dibujar el taxi si est� actualmente presente
      IF Taxi_Dibujar.Presente THEN
         Dibujar_Canvas_Taxi(Pixmap);
      END IF;

      -- Dibujar parada del taxi
      Draw_Rectangle (Pixmap, Grey_Gc, Filled => True,
                      X     => X_PARADA_TAXI-10,  Y => Y_PARADA_TAXI-15,
                      Width => N_MAX_PEATONES*10+10, Height => 33);
      Draw_Line(Pixmap, Green_Gc, X_PARADA_TAXI-12, Y_PARADA_TAXI-16, X_PARADA_TAXI-12, Y_PARADA_TAXI-16 +32);
      Draw_Line(Pixmap, Green_Gc, X_PARADA_TAXI+N_MAX_PEATONES*10, Y_PARADA_TAXI-16, X_PARADA_TAXI+N_MAX_PEATONES*10, Y_PARADA_TAXI-16 +32);
      Draw_Line(Pixmap, Green_Gc, X_PARADA_TAXI-16, Y_PARADA_TAXI-17, X_PARADA_TAXI-16+N_MAX_PEATONES*10+20, Y_PARADA_TAXI-17);

      set_text(pangoLayout, rotulo_parada_taxi);
      Draw_Layout(pixmap, Green_Gc, X_PARADA_TAXI-30, Y_PARADA_TAXI-35, pangoLayout);

      -- Dibujar todos los PEATONES
      if not Pkg_Buffer_Peatones.Vacio(Buffer_Peatones) then
         Id_Primer_Peaton:= Pkg_Buffer_Peatones.Posicion_Primer_Item(Buffer_Peatones);
         Id_ultimo_Peaton:= Pkg_Buffer_Peatones.Posicion_Ultimo_Item(Buffer_Peatones);
         Id_Peaton := Id_Primer_Peaton;
         loop
            item_peaton:= Pkg_Buffer_Peatones.Consulta_Item(id_peaton, Buffer_Peatones);

            Dibujar_Canvas_Peaton(Pixmap, item_peaton.pos.x, item_peaton.pos.y, Convertir_Color_GC(item_peaton.color));

            exit when id_peaton = id_ultimo_peaton;

            Id_peaton := id_peaton+1;
         END LOOP; -- fin de los peatones
      end if; -- fin si no hay peatones

      -- Dibujar en el cronometro el tiempo transcurrido
      Dibujar_Canvas_Cronometro(Pixmap);


      -- Dibujar el r�tulo de indicaci�n de aver�a en un coche
      x := 380;
      set_text(pangoLayout, rotulo1_averia);
      Draw_Layout(Pixmap, Yellow_Gc, 480, 15, pangoLayout);
      set_text(pangoLayout, rotulo2_averia);
      Draw_Layout(Pixmap, Yellow_Gc, x, 40, pangoLayout);
      set_text(pangoLayout, rotulo_carril1);
      Draw_Layout(Pixmap, Yellow_Gc, 300, 70, pangoLayout);
      set_text(pangoLayout, rotulo_carril2);
      Draw_Layout(Pixmap, Yellow_Gc, 300, 100, pangoLayout);

      -- dibujar fondo para la descripci�n
      Draw_Rectangle (Pixmap, Red_Gc, Filled => True,
                      X     => x,  Y => 60,
                      Width => 150, Height => 60);
      Draw_Line(Pixmap, White_Gc, x, 90, x+150, 90);

      -- dibujar fondo para el estado
      x := 540;
      Draw_Rectangle (Pixmap, Green_Gc, Filled => True,
                      X     => x,  Y => 60,
                      Width => 150, Height => 60);
      Draw_Line(Pixmap, White_Gc, x, 90, x+150, 90);

      -- dibujar fondo para los contadores de reloj
      x := 700;
      Draw_Rectangle (Pixmap, White_Gc, Filled => True,
                      X     => 700,  Y => 60,
                      Width => 30, Height => 60);
      Draw_Line(Pixmap, White_Gc, x, 90, x+30, 90);

      FOR i IN T_Carril LOOP
         averia_carril := Pkg_Averias.OP_indicador_averia(i).Consulta_Indicador;
         y := 65 + Gint(i-1)*30;

         IF Averia_Carril /= NO_AVERIA THEN
            -- Dibujar el r�tulo del tipo de aver�a s�lo cuando �sta exista
            set_text(pangoLayout, T_String_20.To_String(Rotulo_Tipo_Averia_Carril(tipo_averia_carril(i))));
            Draw_Layout(Pixmap, Black_Gc, 385, y, pangoLayout);
         END IF;

         -- Dibujar el r�tulo del estado de la aver�a
         set_text(pangoLayout, T_String_20.To_String(Rotulo_Averia_Carril(Averia_Carril)));
         Draw_Layout(Pixmap, Black_Gc, 545, y, pangoLayout);

         -- Dibujar el contador del reloj de la cuenta atr�s de la duraci�n de una reparaci�n de aver�a
         set_text(pangoLayout, Integer'Image(contador_reloj_averia(i)));
         Draw_Layout(Pixmap, Black_Gc, 700, y, pangoLayout);
      END LOOP;

      -- Dibujar la se�al de prohibido si un carril est� cerrado
      FOR I IN T_Carril LOOP
         IF carril_cerrado(i) THEN
            Draw_Rectangle(Pixmap, Red_Gc, Filled => True,
                           X => CANVAS_WIDTH-30, Y => Gint(Y_Carril(I))-10,
                           Width => 30, Height => 30,
                           Corner_Radius => 30.0/2.0);
            Draw_Rectangle (Pixmap, White_Gc, Filled => True,
                            X => CANVAS_WIDTH-25,  Y => Gint(Y_Carril(I)+2),
                            Width => 18, Height => 5);
         end if;
      END LOOP;


   end Dibujar_Canvas_Calle;


   ------------------------------------------------------------------------
   -- Procedure que se encarga de dibujar en el canvas el sem�foro
   ------------------------------------------------------------------------
   procedure Dibujar_Canvas_Semaforo(Pixmap : Cairo_Context) is
   begin
      -- Dibujar el sem�foro de coches
      Draw_Rectangle (Pixmap, Blue_Gc, Filled => True,
                      X => X_TREN+50,  Y => Y_SUPERIOR_CARRETERA-30,
                      Width => 16, Height => 24);
      for i in 1..2 loop
         Draw_Rectangle(Pixmap, Convertir_Color_GC(color_semaforo(i)), Filled => True,
                  X => X_TREN+53 , Y => Y_SUPERIOR_CARRETERA-28 + Gint(I-1)*10,
                  Width => 10, Height => 10,
                  Corner_Radius => 10.0/2.0);
      end loop;
   end Dibujar_Canvas_Semaforo;


   ------------------------------------------------------------------------
   -- Procedure que se encarga de dibujar en el canvas un peat�n
   -- en una posici�n y color especificados
   ------------------------------------------------------------------------
   PROCEDURE Dibujar_Canvas_Peaton(Pixmap : Cairo_Context;
                                   posX, posY: in integer;
                                   color_peaton: in Gtkada.Style.Cairo_Color) is
   begin
      -- Dibujar la cabeza
      Draw_Rectangle(Pixmap, color_peaton, Filled => True,
               X => Gint(posX-3), Y => Gint(posY-8), Width => 8, Height => 8,
               Corner_Radius => 8.0/2.0);

      -- Dibujar el tronco y los brazos
      Draw_Line(Pixmap, color_peaton, Gint(posX), Gint(posY-2), Gint(posX), Gint(posY+3));
      Draw_Line(Pixmap, color_peaton, Gint(posX-3), Gint(posY), Gint(posX+3), Gint(posY));

      -- Dibujar las piernas
      Draw_Line(Pixmap, color_peaton, Gint(posX), Gint(posY+3), Gint(posX-3), Gint(posY+8));
      Draw_Line(Pixmap, color_peaton, Gint(posX), Gint(posY+3), Gint(posX+3), Gint(posY+8));
   end Dibujar_Canvas_Peaton;



    ------------------------------------------------------------------------
   -- Procedure que se encarga de dibujar en el canvas un coche
   -- en una posici�n y color especificados
   ------------------------------------------------------------------------
   PROCEDURE Dibujar_Canvas_Coche(Pixmap : Cairo_Context;
                                   posX, posY: in integer;
                                   color_coche: in Gtkada.Style.Cairo_Color) is
   accidentado : boolean;
   humo        : Gdk_Points_Array:=(1..6 =>(X =>0, Y=>0));
   BEGIN
      -- comprobar si el coche est� accidentado
      Accidentado := PosY /= Y_Carril(1) AND PosY /= Y_Carril(2);

      -- dibujar el chasis del coche
      IF Accidentado THEN
         --chasis(1) := (Gint(posX-20), Gint(posY+8));
         --chasis(2) := (Gint(posX-10), Gint(posY+5));
         for i in 0..3 loop
            humo(1) := (Gint(posX-12-i*3), Gint(posY+7));
            humo(2) := (Gint(posX-15-i*3), Gint(posY+3));
            humo(3) := (Gint(posX-13-i*3), Gint(posY+5));
            humo(4) := (Gint(posX-17-i*3), Gint(posY-1));
            humo(5) := (Gint(posX-13-i*3), Gint(posY+1));
            humo(6) := (Gint(posX-18-i*3), Gint(posY-4));
            Dibujar_Lineas(Pixmap, Black_Gc, humo, 6);
         END LOOP;
      END IF;

      Dibujar_Chasis(Pixmap, color_coche, Gint(posX), Gint(posY));

      -- dibujar las ventanas
      Dibujar_Ventanas_Coche(Pixmap, Grey_Gc, Gint(posX), Gint(posY));

      -- dibujar la rueda delantera
      Dibujar_Rueda(Pixmap, Black_Gc, Grey_Gc, Gint(posX-12),  Gint(posY+6),  9, 5);

      -- dibujar la rueda trasera
      Dibujar_Rueda(Pixmap, Black_Gc, Grey_Gc, Gint(posX+9),  Gint(posY+6),  9, 5);
   end Dibujar_Canvas_Coche;



   ------------------------------------------------------------------------
   -- Procedure que se encarga de dibujar en el canvas el tren
   ------------------------------------------------------------------------
   procedure Dibujar_Canvas_Tren(Pixmap : Cairo_Context) is
      PosX, PosY : Integer;
      color : Gtkada.Style.Cairo_Color := Convertir_Color_GC(tren_dibujar.Color);
   BEGIN
      posX := tren_dibujar.pos.x;
      posY := tren_dibujar.pos.y;

      -- DIBUJAR LA MAQUINA DEL TREN
      Draw_Rectangle (Pixmap, color, Filled => True,
                      X => Gint(posX+10),  Y => Gint(posY)+15,
                      Width => 10, Height => 10);
      Draw_Rectangle (Pixmap, color, Filled => True,
                      X => Gint(posX),  Y => Gint(posY)-10,
                      Width => 20, Height => 25);

      -- dibujar la chimenea
      Draw_Rectangle (Pixmap, Red_Gc, Filled => True,
                      X => Gint(posX+2),  Y => Gint(posY)+18,
                      Width => 8, Height => 2);

      -- dibujar una ventana
      Draw_Rectangle (Pixmap, Grey_Gc, Filled => True,
                      X => Gint(posX+5),  Y => Gint(posY)+5,
                      Width => 5, Height => 5);

      -- dibujar ruedas
      FOR I IN 1..2 LOOP
         Dibujar_Rueda(Pixmap, Black_Gc, Grey_Gc, Gint(posX+17),  Gint(posY+10-(i-1)*15),  9, 5);
      END LOOP;

      -- DIBUJAR VAGONES
      FOR I IN 1..tren_dibujar.n_vagones LOOP
         -- dibujar enganche
         Draw_Rectangle (Pixmap, color, Filled => True,
                         X => Gint(posX+15),  Y => Gint(posY-15-(i-1)*TAM_VAGON),
                         Width => 4, Height => 5);
         -- dibujar vag�n
         Draw_Rectangle (Pixmap, color, Filled => True,
                         X => Gint(posX),  Y => Gint(posY-10-i*TAM_VAGON),
                         Width => 20, Height => 35);

         -- dibujar ventanas
         FOR J IN 1..3 LOOP
            Draw_Rectangle (Pixmap, Grey_Gc, Filled => True,
                            X => Gint(posX+5),  Y => Gint(posY-20-(i-1)*TAM_VAGON-j*8),
               Width => 5, Height => 5);
         END LOOP;

         -- dibujar ruedas
         FOR k IN 1..2 LOOP
            Dibujar_Rueda(Pixmap, Black_Gc, Grey_Gc, Gint(posX+17),  Gint(posY+10-i*TAM_VAGON-(k-1)*15),  9, 5);
         END LOOP;

      END LOOP;
   end Dibujar_Canvas_Tren;


   ------------------------------------------------------------------------
   -- Procedure que se encarga de dibujar en el canvas un coche
   -- en una posici�n y color especificados
   ------------------------------------------------------------------------
   PROCEDURE Dibujar_Canvas_Taxi(Pixmap : Cairo_Context) IS
      --chasis      : Gdk_Points_Array:=(1..7 =>(X =>0, Y=>0));
      Ventana     : Gdk_Points_Array:=(1..4 =>(X =>0, Y=>0));
      PosX, PosY  : Integer;
      color       : Gtkada.Style.Cairo_Color := White_Gc;
      color_indicador : Gtkada.Style.Cairo_Color;
   BEGIN
      posX := taxi_dibujar.pos.x;
      PosY := Taxi_Dibujar.Pos.Y;

      -- dibujar el chasis del coche
      Dibujar_Chasis(Pixmap, color, Gint(posX), Gint(posY));

      -- dibujar la l�nea en el chasis
      Draw_Rectangle (Pixmap, Green_Gc, Filled => True,
                      X => Gint(posX-10),  Y => Gint(posY+4),
                      Width => 28, Height => 1);

      -- dibujar el indicador del techo
      IF Taxi_Dibujar.Libre THEN
         Color_Indicador := Green_Gc;
      ELSE
         Color_Indicador := Red_Gc;
      END IF;
      Draw_Rectangle (Pixmap, color_indicador, Filled => True,
                      X => Gint(posX-3),  Y => Gint(posY-8),
                      Width => 3, Height => 3);

      -- dibujar las ventanas
      Dibujar_Ventanas_Coche(Pixmap, Grey_Gc, Gint(posX), Gint(posY));

      -- dibujar la rueda delantera
      Dibujar_Rueda(Pixmap, Black_Gc, Grey_Gc, Gint(posX-12),  Gint(posY+6),  9, 5);

      -- dibujar la rueda trasera
      Dibujar_Rueda(Pixmap, Black_Gc, Grey_Gc, Gint(posX+9),  Gint(posY+6),  9, 5);
   end Dibujar_Canvas_Taxi;


   ------------------------------------------------------------------------
   -- Procedure que se encarga de dibujar el cron�metro
   ------------------------------------------------------------------------
   PROCEDURE Dibujar_Canvas_Cronometro(Pixmap : Cairo_Context) IS
   BEGIN
      -- Dibujar el cron�metro
      Draw_Rectangle (Pixmap, White_Gc, Filled => True,
                      X => Gint(X_CRONOMETRO),  Y => Gint(Y_CRONOMETRO-15),
                      Width => 65, Height => 20);

      -- Imprimir el valor del cron�metro como texto
      set_text(pangoLayout, hora_segundos);
      Draw_Layout(Pixmap, Black_Gc, Gint(X_CRONOMETRO), Gint(Y_CRONOMETRO-15), pangoLayout);
   END Dibujar_Canvas_Cronometro;


   ------------------------------------------------------------------------
   -- Funci�n que convierte un color a su correspondiente del
   -- contexto gr�fico
   ------------------------------------------------------------------------
   FUNCTION Convertir_Color_GC(Color : T_Color) RETURN Gtkada.Style.Cairo_Color IS
   BEGIN
      CASE Color IS
         WHEN Red    => RETURN(Red_Gc);
         WHEN Green  => RETURN(Green_Gc);
         WHEN Blue   => RETURN(Blue_Gc);
         WHEN Yellow => RETURN(Yellow_Gc);
         WHEN Black  => RETURN(Black_Gc);
         WHEN White  => RETURN(White_Gc);
         WHEN Grey   => RETURN(Grey_Gc);
      END CASE;
   end Convertir_Color_GC;


   ------------------------------------------------------------------------
   -- Crear flechas de las calles
   ------------------------------------------------------------------------
   function Crear_Flechas(y : Integer) return T_Flechas is
      flechas : T_Flechas;
      flecha : Gdk_Points_Array(1..5);
      x: integer;
   begin
      x := 25;
      for i in 1..NUM_FLECHAS loop
         flecha(1) := (Gint(x+10), Gint(y));
      	  flecha(2) := (Gint(x), Gint(y));
         flecha(3) := (Gint(x+2), Gint(y-2));
         flecha(4) := (Gint(x), Gint(y));
         flecha(5) := (Gint(x+2), Gint(y+2));
         flechas(i) := flecha;
         x := x+180;
      end loop;
      return flechas;
   end Crear_Flechas;


   ------------------------------------------------------------------------
   -- Dibujar en el canvas las l�neas determinadas por un conjunto de puntos
   ------------------------------------------------------------------------
   procedure Dibujar_Lineas(Pixmap : Cairo_Context; color : Gtkada.Style.Cairo_Color; flecha : Gdk_Points_Array; n_puntos : Integer) is
   begin
      for i in 1..n_puntos-1 loop
         Draw_Line(Pixmap, color, flecha(i).x, flecha(i).y, flecha(i+1).x, flecha(i+1).y);
      end loop;
   end Dibujar_Lineas;


   ------------------------------------------------------------------------
   -- Dibujar en el canvas una rueda
   ------------------------------------------------------------------------
   procedure Dibujar_Rueda(Pixmap: Cairo_Context; color_neumatico: Gtkada.Style.Cairo_Color;
                           color_llanta: Gtkada.Style.Cairo_Color; x: Gint; y: Gint;
                           diametro_neumatico:Gint; diametro_llanta: Gint) is
   begin
         Draw_Rectangle(Pixmap, color_neumatico, Filled => True,
               X => x, Y => y, Width => diametro_neumatico, Height => diametro_neumatico,
                        Corner_Radius => Gdouble(diametro_neumatico)/2.0);
         Draw_Rectangle(Pixmap, color_llanta, Filled => True,
               X => x+2, Y => y+2, Width => diametro_llanta, Height => diametro_llanta,
                        Corner_Radius => Gdouble(diametro_llanta)/2.0);
   end Dibujar_Rueda;


   ------------------------------------------------------------------------
   -- Dibujar en el canvas el chasis de un coche
   ------------------------------------------------------------------------
   procedure Dibujar_Chasis(Pixmap: Cairo_Context; color: Gtkada.Style.Cairo_Color;
                             posX: Gint; posY: Gint) is
   begin
      Cairo.Move_To(Pixmap, Gdouble(posX-20), Gdouble(posY+8));
      Cairo.Line_To(Pixmap, Gdouble(posX-20), Gdouble(posY+5));
      Cairo.Line_To(Pixmap, Gdouble(posX-12), Gdouble(posY+2));
      Cairo.Line_To(Pixmap, Gdouble(posX-5), Gdouble(posY-5));
      Cairo.Line_To(Pixmap, Gdouble(posX+15), Gdouble(posY-5));
      Cairo.Line_To(Pixmap, Gdouble(posX+20), Gdouble(posY+3));
      Cairo.Line_To(Pixmap, Gdouble(posX+20), Gdouble(posY+8));
      Cairo.Close_Path(Pixmap);
      Cairo.Set_Source_Rgb(Pixmap, color.Red, color.Green, color.Blue);
      Cairo.Fill(Pixmap);
    end Dibujar_Chasis;


   ------------------------------------------------------------------------
   -- Dibujar en el canvas las ventanas de un coche
   ------------------------------------------------------------------------
   procedure Dibujar_Ventanas_Coche(Pixmap: Cairo_Context; color: Gtkada.Style.Cairo_Color;
                                   posX: Gint; posY: Gint) is
   begin
      -- ventana delantera
      Cairo.Move_To(Pixmap, GDouble(posX-9), GDouble(posY+2));
      Cairo.Line_To(Pixmap, GDouble(posX-5), GDouble(posY-3));
      Cairo.Line_To(Pixmap, GDouble(posX+2), GDouble(posY-3));
      Cairo.Line_To(Pixmap, GDouble(posX+2), GDouble(posY+2));
      Cairo.Close_Path(Pixmap);
      Cairo.Set_Source_Rgb(Pixmap, color.Red, color.Green, color.Blue);
      Cairo.Fill(Pixmap);

      -- ventana trasera
      Cairo.Move_To(Pixmap, GDouble(posX+5), GDouble(posY-3));
      Cairo.Line_To(Pixmap, GDouble(posX+12), GDouble(posY-3));
      Cairo.Line_To(Pixmap, GDouble(posX+16), GDouble(posY+2));
      Cairo.Line_To(Pixmap, GDouble(posX+5), GDouble(posY+2));
      Cairo.Close_Path(Pixmap);
      Cairo.Fill(Pixmap);
   end Dibujar_Ventanas_Coche;


   ------------------------------------------------------------------------
   -- Seleccionado Bot�n 1 para provocar aver�a en carril 1
   ------------------------------------------------------------------------
   procedure Averia1_Generada (Win : access Gtk_Window_Record'Class) is
      pragma Warnings (Off, Win);
   begin
      pkg_averias.OP_Indicador_Averia(1).Actualiza_Indicador(COMUNICADA_AVERIA);
   end Averia1_Generada;


   ------------------------------------------------------------------------
   -- Seleccionado Bot�n 2 para provocar aver�a en carril 2
   ------------------------------------------------------------------------
   procedure Averia2_Generada (Win : access Gtk_Window_Record'Class) is
      pragma Warnings (Off, Win);
   begin
      pkg_averias.OP_Indicador_Averia(2).Actualiza_Indicador(COMUNICADA_AVERIA);
   end Averia2_Generada;




--***************************************************************
-- SENTENCIAS DE INICIALIZACI�N DEL PAQUETE
--***************************************************************
BEGIN
   PKG_debug.Escribir("Inicializando Entorno Gr�fico...");

   -- Inicializar el entorno gr�fico
   --Gtk.Main.Set_Locale;
   Gtk.Main.Init;
   Inicializar_Interfaz_Grafica;


   PKG_debug.Escribir("Entorno Gr�fico Inicializado.");
end PKG_graficos;
