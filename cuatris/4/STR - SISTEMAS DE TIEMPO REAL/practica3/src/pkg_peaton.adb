--******************* PKG_peaton.ADB ************************************
-- Paquete que implementa la generación de peatones y su comportamiento
--***********************************************************************

with PKG_graficos;
with PKG_debug;
with Ada.exceptions; use Ada.exceptions;
WITH Ada.Numerics.Discrete_Random;

package body PKG_peaton is
     --*************************************************************
     -- TAREA ENCARGADA DE LA GENERACIÓN DE PEATONES PERIÓDICAMENTE
     --*************************************************************
     TASK BODY Tarea_GeneraPeatones IS
          Tarea_peaton                                          : Ptr_Tarea_Peaton;
          ptr_peaton                                            : Ptr_T_RecordPeaton;

          -- Creación de paquete para generar un número aleatorio de peatones
          PACKAGE Pkg_NumPeatones_Aleatorio
          is new Ada.Numerics.Discrete_Random(T_NumPeatones);
          Generador_NumPeatones                                 : Pkg_NumPeatones_Aleatorio.Generator;
          numPeatones                                           : T_NumPeatones;

          -- Creación de paquete para generar un color aleatorio
          PACKAGE Pkg_ColorPeaton_Aleatorio
          is new Ada.Numerics.Discrete_Random(T_ColorAparicionPeaton);
          Generador_ColorPeaton                                 : Pkg_ColorPeaton_Aleatorio.Generator;
          colorPeaton                                           : T_ColorAparicionPeaton;

          idPeaton                                              : T_IdPeaton;


     BEGIN
          PKG_debug.Escribir("======================INICIO TASK GeneraPeatones");

          -- inicializar generadores de valores aletaorios
          pkg_numPeatones_aleatorio.Reset(Generador_NumPeatones);
          pkg_ColorPeaton_aleatorio.Reset(Generador_ColorPeaton);

          LOOP
               -- Crear un nuevo grupo de peatones si la parada está vacía
               IF PKG_graficos.Parada_Taxi_Vacia THEN

                    -- generar número aleatorio de peatones
                    NumPeatones := Pkg_NumPeatones_Aleatorio.Random(Generador_NumPeatones);

                    for i in 0..NumPeatones-1 loop
                         ptr_peaton := new T_RecordPeaton;

                         -- actualizar identificador del peatón
                         idPeaton := T_IdPeaton(i);
                         pkg_graficos.Actualiza_Atributo(idPeaton, Ptr_peaton.all);

                         -- generar color aleatorio
                         colorPeaton := Pkg_ColorPeaton_Aleatorio.Random(Generador_ColorPeaton);
                         pkg_graficos.Actualiza_Atributo(colorPeaton, Ptr_peaton.all);

                         -- actualizar posición del peatón
                         pkg_graficos.Actualiza_Atributo(pkg_graficos.Pos_Inicio(ptr_peaton.all), ptr_peaton.all);

                         -- Crear una tarea para el comportamiento del peaton
                         Tarea_Peaton := NEW T_Tarea_Peaton(ptr_peaton);
                    end loop;

               END IF;

               DELAY(PERIODO_PEATONES);

          END LOOP;

     exception
          when event                                            : others =>
               PKG_debug.Escribir("ERROR en TASK GeneraPeatones : " & Exception_Name(Exception_Identity(event)));

     END Tarea_GeneraPeatones;



     --*************************************************************
     -- TIPO TAREA ENCARGADA DEL COMPORTAMIENTO DE UN PEATON
     --*************************************************************
     TASK BODY T_Tarea_Peaton IS
          peaton                                                : T_RecordPeaton;
     begin
          PKG_debug.Escribir("======================INICIO TASK Peaton");

          -- Inicializar los datos del peatón pasados como parámetros del tipo tarea
          peaton:= ptr_peaton.all;

          -- Visualizar el peatón
          PKG_graficos.Aparece(peaton);

          delay(pkg_tipos.TIEMPO_ESPERA_PEATON);

          -- El peatón deja de visualizarse
          PKG_graficos.Desaparece(peaton);

          PKG_debug.Escribir("======================FIN TASK Peatón");

     exception
          when event                                            : others =>
               PKG_debug.Escribir("ERROR en TASK Peatón        : " & Exception_Name(Exception_Identity(event)));

     end T_Tarea_Peaton;

end PKG_peaton;
