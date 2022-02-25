with PKG_graficos;
with Ada.Calendar;
with PKG_debug;
with Ada.exceptions; use Ada.exceptions, Ada.Calendar;
WITH Ada.Numerics.Discrete_Random;
package body pkg_tren is

    --*************************************************************
   -- TAREA ENCARGADA DE LA GENERACIÓN DE TAXIS PERIÓDICAMENTE
   --*************************************************************
   TASK BODY Tarea_GeneraTrenes IS
      Tarea_tren : Ptr_Tarea_Tren;
      ptr_tren  : Ptr_T_RecordTren;
          
       PACKAGE Pkg_NumVagones_Aleatorio
                is new Ada.Numerics.Discrete_Random(T_NumVagones);
      Generador_NumVagones: Pkg_NumVagones_Aleatorio.Generator;
      numVagones   : T_NumVagones;

      -- Creación de paquete para generar un color aleatorio
      PACKAGE Pkg_ColorTren_Aleatorio
                is new Ada.Numerics.Discrete_Random(T_ColorTren);
      Generador_ColorTren: Pkg_ColorTren_Aleatorio.Generator;
          colorTren   : T_ColorTren;
          
          
          
    siguiente : Time;
    inicio : Time;
          
        
          
     BEGIN
          
          inicio:=Ada.Calendar.Clock;--enchufamos el crono
          siguiente := Ada.Calendar.Clock + PERIODO_TREN;
          
          
          PKG_debug.Escribir("======================INICIO TASK GeneraTrens");
      Pkg_NumVagones_Aleatorio.Reset(Generador_NumVagones);
      Pkg_ColorTren_Aleatorio.Reset(Generador_ColorTren);

      LOOP
         -- Crear un nuevo taxi solo si no hay ninguno presente
         IF NOT PKG_graficos.Hay_Tren THEN

            Ptr_Tren := NEW T_RecordTren;
                    colorTren:=Pkg_ColorTren_Aleatorio.Random(Generador_ColorTren);
                    numVagones:=Pkg_NumVagones_Aleatorio.Random(Generador_NumVagones);
                    PKG_graficos.Actualiza_Atributo(colorTren, Ptr_tren.all);
                    PKG_graficos.Actualiza_Atributo(numVagones, ptr_tren.all);
                    
            
            -- Crear una tarea para el comportamiento del taxi
            Tarea_Tren:= NEW T_Tarea_Tren(ptr_tren);
         END IF;

               DELAY until(siguiente);
               siguiente := siguiente + PERIODO_TREN;

      END LOOP;

      exception
       when event: others =>
        PKG_debug.Escribir("ERROR en TASK GeneraTaxis: " & Exception_Name(Exception_Identity(event)));

   END Tarea_GeneraTrenes;



   --*************************************************************
   -- TIPO TAREA ENCARGADA DEL COMPORTAMIENTO DE UN Tren
   --*************************************************************
   TASK BODY T_Tarea_Tren IS
      Tren  : T_RecordTren;
   begin
      PKG_debug.Escribir("======================INICIO TASK Tren");

      -- Inicializar los datos del taxi pasados como parámetros del tipo tarea
      tren:= ptr_tren.all;

      -- Visualizar el taxi
      PKG_graficos.Aparece(Tren);

      -- el taxi se mueve hasta llegar al final de la carretera
      WHILE NOT PKG_graficos.Pos_Final(Tren) LOOP

         

         
-- actualiza la posicion del taxi
         PKG_graficos.Actualiza_Movimiento(tren);
         delay(RETARDO_MOVIMIENTO);

      END LOOP;

      -- El taxi deja de visualizarse
      PKG_graficos.Desaparece(tren);

      PKG_debug.Escribir("======================FIN TASK Tren");

      exception
       when event: others =>
        PKG_debug.Escribir("ERROR en TASK Tren: " & Exception_Name(Exception_Identity(event)));

   end T_Tarea_Tren;

end pkg_tren;
