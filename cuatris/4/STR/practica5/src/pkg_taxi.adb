--******************* PKG_TAXI.ADB **************************************
-- Paquete que implementa la generación de taxis y su comportamiento
--***********************************************************************

with PKG_graficos;
with Ada.Calendar;
with PKG_debug,PKG_Semaforo;
with Ada.exceptions; use Ada.exceptions, Ada.Calendar;





PACKAGE BODY PKG_Taxi IS

     protected body OP_luzTaxi is

     entry paradaTaxi when libre is
     begin
          addPasajero;
     end paradaTaxi;

     entry completo when not libre is
     begin
          null;
     end completo;

     procedure cambiar_luz(estado: Boolean) is
          begin
          libre:=estado;
     end cambiar_luz;

          function getLuzTaxi return Boolean is
               begin
               return libre;
          end getLuzTaxi;

          procedure addPasajero is
               begin
          if numPasajeros < pkg_tipos.CAPACIDAD_TAXI-1 and libre then
               numPasajeros:=numPasajeros+1;
          else
               libre:=false;
          end if;
     end addPasajero;
          procedure resetearTaxi is
               begin
          numPasajeros:=0;
          libre:=false;
     end resetearTaxi;

end OP_luzTaxi;


   --*************************************************************
   -- TAREA ENCARGADA DE LA GENERACIÓN DE TAXIS PERIÓDICAMENTE
   --*************************************************************
   TASK BODY Tarea_GeneraTaxis IS
      Tarea_taxi : Ptr_Tarea_Taxi;
          ptr_taxi   : Ptr_T_RecordTaxi;
          siguiente : Time;
    inicio : Time;
     BEGIN
            inicio:=Ada.Calendar.Clock;--enchufamos el crono
          siguiente := Ada.Calendar.Clock + PERIODO_TAXI;
      PKG_debug.Escribir("======================INICIO TASK GeneraTaxis");

      LOOP
         -- Crear un nuevo taxi solo si no hay ninguno presente
         IF NOT PKG_graficos.Hay_Taxi THEN

            Ptr_Taxi := NEW T_RecordTaxi;

            -- Crear una tarea para el comportamiento del taxi
            Tarea_Taxi := NEW T_Tarea_Taxi(ptr_taxi);
         END IF;

         DELAY until(siguiente);
               siguiente := siguiente + PERIODO_TAXI;

      END LOOP;

      exception
       when event: others =>
        PKG_debug.Escribir("ERROR en TASK GeneraTaxis: " & Exception_Name(Exception_Identity(event)));

   END Tarea_GeneraTaxis;



   --*************************************************************
   -- TIPO TAREA ENCARGADA DEL COMPORTAMIENTO DE UN TAXI
   --*************************************************************
   TASK BODY T_Tarea_Taxi IS
          Taxi  : T_RecordTaxi;

   begin
      PKG_debug.Escribir("======================INICIO TASK Taxi");

      -- Inicializar los datos del taxi pasados como parámetros del tipo tarea
      taxi:= ptr_taxi.all;

      -- Visualizar el taxi
          PKG_graficos.Aparece(Taxi);
          pkg_graficos.Actualiza_Indicador_Taxi(True,Taxi);

      -- el taxi se mueve hasta llegar al final de la carretera
      WHILE NOT PKG_graficos.Pos_Final(Taxi) LOOP

          if PKG_graficos.Posicion_Stop_Semaforo(Taxi) and PKG_Semaforo.OP_Semaforo.Semaforo_Rojo then
                    pkg_graficos.Actualiza_Atributo(T_RangoVelocidad(0), taxi);
                    PKG_Semaforo.OP_Semaforo.controlSemaforo;
                    pkg_graficos.Actualiza_Atributo(T_RangoVelocidad(VELOCIDAD_TAXI), taxi);
          end if;

         IF pkg_graficos.Pos_Parada_Taxi(taxi) THEN
            -- Detener el taxi en la parada un tiempo fijo
                    pkg_graficos.Actualiza_Atributo(T_RangoVelocidad(0), taxi);


                    delay (TIEMPO_PARADA_OBLIGATORIA);
            --COdigo de la recogida de peatones
                    OP_luzTaxi.cambiar_luz(true);


            -- reanudar la marcha
            pkg_graficos.Actualiza_Atributo(T_RangoVelocidad(VELOCIDAD_TAXI), taxi);

                    select
                         OP_luzTaxi.completo;

                    or
                           delay pkg_tipos.TIEMPO_RECOGIDA_PASAJEROS;

                    end select;
                    OP_luzTaxi.cambiar_luz(false);
                    pkg_graficos.Actualiza_Indicador_Taxi(false,Taxi);


         END IF;

         -- actualiza la posicion del taxi
         PKG_graficos.Actualiza_Movimiento(taxi);

         delay (RETARDO_MOVIMIENTO);

      END LOOP;

          -- El taxi deja de visualizarse

          PKG_graficos.Desaparece(taxi);

          OP_luzTaxi.resetearTaxi;

      PKG_debug.Escribir("======================FIN TASK Taxi");

     exception
       when pkg_tipos.DETECTADA_COLISION_TREN => PKG_graficos.Desaparece(taxi);
       when event: others =>
        PKG_debug.Escribir("ERROR en TASK Taxi: " & Exception_Name(Exception_Identity(event)));

   end T_Tarea_Taxi;


END PKG_taxi;
