--******************* PKG_TAXI.ADB **************************************
-- Paquete que implementa la generación de taxis y su comportamiento
--***********************************************************************

with PKG_graficos;
with Ada.Calendar;
with PKG_debug;
with Ada.exceptions; use Ada.exceptions, Ada.Calendar;



PACKAGE BODY PKG_Taxi IS


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

      -- el taxi se mueve hasta llegar al final de la carretera
      WHILE NOT PKG_graficos.Pos_Final(Taxi) LOOP

         IF pkg_graficos.Pos_Parada_Taxi(taxi) THEN
            -- Detener el taxi en la parada un tiempo fijo
            pkg_graficos.Actualiza_Atributo(T_RangoVelocidad(0), taxi);
            delay (TIEMPO_PARADA_OBLIGATORIA);
            -- reanudar la marcha
            pkg_graficos.Actualiza_Atributo(T_RangoVelocidad(VELOCIDAD_TAXI), taxi);
         END IF;

         -- actualiza la posicion del taxi
         PKG_graficos.Actualiza_Movimiento(taxi);

         delay (RETARDO_MOVIMIENTO);

      END LOOP;

      -- El taxi deja de visualizarse
      PKG_graficos.Desaparece(taxi);

      PKG_debug.Escribir("======================FIN TASK Taxi");

     exception
       when pkg_tipos.DETECTADA_COLISION_TREN => PKG_graficos.Desaparece(taxi);
       when event: others =>
        PKG_debug.Escribir("ERROR en TASK Taxi: " & Exception_Name(Exception_Identity(event)));

   end T_Tarea_Taxi;


END PKG_taxi;
