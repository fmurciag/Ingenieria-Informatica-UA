--******************* PKG_peaton.ADB ************************************
-- Paquete que implementa la generaci�n de peatones y su comportamiento
--***********************************************************************

with PKG_graficos,PKG_Taxi;
with PKG_debug;
with Ada.exceptions; use Ada.exceptions;
WITH Ada.Numerics.Discrete_Random,Ada.Calendar;use Ada.Calendar;

package body PKG_peaton is
     --*************************************************************
     -- TAREA ENCARGADA DE LA GENERACI�N DE PEATONES PERI�DICAMENTE
     --*************************************************************
     TASK BODY Tarea_GeneraPeatones IS
          Tarea_peaton                                          : Ptr_Tarea_Peaton;
          ptr_peaton                                            : Ptr_T_RecordPeaton;

          -- Creaci�n de paquete para generar un n�mero aleatorio de peatones
          PACKAGE Pkg_NumPeatones_Aleatorio
          is new Ada.Numerics.Discrete_Random(T_NumPeatones);
          Generador_NumPeatones                                 : Pkg_NumPeatones_Aleatorio.Generator;
          numPeatones                                           : T_NumPeatones;

          -- Creaci�n de paquete para generar un color aleatorio
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
               -- Crear un nuevo grupo de peatones si la parada est� vac�a
               IF PKG_graficos.Parada_Taxi_Vacia THEN

                    -- generar n�mero aleatorio de peatones
                    NumPeatones := Pkg_NumPeatones_Aleatorio.Random(Generador_NumPeatones);

                    for i in 0..NumPeatones-1 loop
                         ptr_peaton := new T_RecordPeaton;

                         -- actualizar identificador del peat�n
                         idPeaton := T_IdPeaton(i);
                         pkg_graficos.Actualiza_Atributo(idPeaton, Ptr_peaton.all);

                         -- generar color aleatorio
                         colorPeaton := Pkg_ColorPeaton_Aleatorio.Random(Generador_ColorPeaton);
                         pkg_graficos.Actualiza_Atributo(colorPeaton, Ptr_peaton.all);

                         -- actualizar posici�n del peat�n
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
           siguiente : Time;
     begin
          PKG_debug.Escribir("======================INICIO TASK Peaton");

          -- Inicializar los datos del peat�n pasados como par�metros del tipo tarea
          peaton:= ptr_peaton.all;

          -- Visualizar el peat�n
          PKG_graficos.Aparece(peaton);

          siguiente:=pkg_tipos.TIEMPO_ESPERA_PEATON + Clock;
          --subida a taxi
          select
               PKG_Taxi.OP_luzTaxi.paradaTaxi;
               pkg_graficos.Actualiza_Color_Peaton(Green,peaton);
          or

               delay until(siguiente - 2.5);
               pkg_graficos.Actualiza_Color_Peaton(Black,peaton);

          end select;
          delay 2.5;

          -- El peat�n deja de visualizarse
          PKG_graficos.Desaparece(peaton);

          PKG_debug.Escribir("======================FIN TASK Peat�n");

     exception
          when event                                            : others =>
               PKG_debug.Escribir("ERROR en TASK Peat�n        : " & Exception_Name(Exception_Identity(event)));

     end T_Tarea_Peaton;
     end PKG_peaton;
