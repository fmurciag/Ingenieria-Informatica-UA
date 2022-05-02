with PKG_graficos;
with PKG_debug,Ada.Text_IO;
with Ada.exceptions; use Ada.exceptions;
WITH Ada.Numerics.Discrete_Random, PKG_Semaforo,PKG_Averias,pkg_teleoperador;
package body pkg_coches is

     --*************************************************************
     -- TAREA ENCARGADA DE LA GENERACIÓN DE TAXIS PERIÓDICAMENTE
     --*************************************************************
     TASK BODY Tarea_GeneraCoches IS
          Tarea_Coche                                        : Ptr_Tarea_Coche;
          ptr_Coche                                          : Ptr_T_RecordCoche;
          
          PACKAGE Pkg_Velocidad_Aleatorio
          is new Ada.Numerics.Discrete_Random(T_RangoVelocidadAparicion);
          Generador_Velocidad                                : Pkg_Velocidad_Aleatorio.Generator;
          velocidadCoche                                     : T_RangoVelocidadAparicion;
          
          package pkg_Retardo_Coche is new ada.Numerics.Discrete_Random(T_RetardoCoches);
          Generador_Retardo                                  : pkg_Retardo_Coche.Generator;
          retardoCoche                                       : T_RetardoCoches;


          PACKAGE Pkg_ColorCoche_Aleatorio
          is new Ada.Numerics.Discrete_Random(T_ColorCoche);
          Generador_ColorCoche                               : Pkg_ColorCoche_Aleatorio.Generator;
          colorCoche                                         : T_ColorCoche;
        
          
        
          idCoche:T_IdCoche;
          idCarril:T_Carril;
          
          
          
     BEGIN
          PKG_debug.Escribir("======================INICIO TASK GeneraCoche");
          Pkg_Velocidad_Aleatorio.Reset(Generador_Velocidad);
          pkg_Retardo_Coche.Reset(Generador_Retardo);
          Pkg_ColorCoche_Aleatorio.Reset(Generador_ColorCoche);
          idCoche:=1;
                              --1 ID de carril
                    idCarril:=T_Carril(carril);
          
          
          

          LOOP
                                   -- mira averias
               
               select

                    PKG_Averias.OP_Indicador_Averia(carril).carrilCortado;--comunicamos averia
                    PKG_graficos.Dibujar_Carril_Cerrado( idCarril); --pintamos
                    
                    
               then abort
                    -- Crear un nuevo cocje
                    
                    loop
                    Ptr_Coche := NEW T_RecordCoche;
               
               
                    retardoCoche:=pkg_Retardo_Coche.Random(Generador_Retardo);
               

                    PKG_graficos.Actualiza_Atributo(idCarril, ptr_Coche.all);
                    -- 2 id del coche
                    PKG_graficos.Actualiza_Atributo(idCoche, ptr_Coche.all);
                    idCoche:=idCoche+1;
                    --3 color
                    colorCoche:=Pkg_ColorCoche_Aleatorio.Random(Generador_ColorCoche);
                    PKG_graficos.Actualiza_Atributo(colorCoche, ptr_Coche.all);
                    -- 4 velocidad
                    velocidadCoche:=Pkg_Velocidad_Aleatorio.Random(Generador_Velocidad);
                    PKG_graficos.Actualiza_Atributo(velocidadCoche, Ptr_Coche.all);
                    --5 posicion
                    pkg_graficos.Actualiza_Atributo(pkg_graficos.Pos_Inicio(ptr_Coche.all), ptr_Coche.all);
               
               
               

                    -- Crear una tarea para el comportamiento del taxi
                    Tarea_Coche:= NEW T_Tarea_Coche(Ptr_Coche);
         

                         DELAY(Standard.Duration(retardoCoche));
                         END LOOP;
               end select;
               PKG_Averias.OP_Indicador_Averia(carril).reaunadrCarril;
               PKG_graficos.Dibujar_Carril_Abierto( idCarril); --pintamos
               
          END LOOP;

     exception
          when event                                         : others =>
               PKG_debug.Escribir("ERROR en TASK GeneraCoche : " & Exception_Name(Exception_Identity(event)));

     END Tarea_GeneraCoches;



     --*************************************************************
     -- TIPO TAREA ENCARGADA DEL COMPORTAMIENTO DE UN cohe
     --*************************************************************
     TASK BODY T_Tarea_Coche IS
          Coche                                              : T_RecordCoche;
          
          PACKAGE Pkg_Averia_Aleatoria is new Ada.Numerics.Discrete_Random(T_Tipo_Averia);
          Generador_Averia                              : Pkg_Averia_Aleatoria.Generator;
          Averia                                         : T_Tipo_Averia;
     begin
          PKG_debug.Escribir("======================INICIO TASK coche");
          Pkg_Averia_Aleatoria.Reset(Generador_Averia);
          Averia:=Pkg_Averia_Aleatoria.Random(Generador_Averia);
          -- Inicializar los datos del taxi pasados como parámetros del tipo tarea
          Coche:= ptr_Coche.all;

          -- Visualizar el cohe
          PKG_graficos.Aparece(Coche);

          -- el taxi se mueve hasta llegar al final de la carretera
          WHILE NOT PKG_graficos.Pos_Final(Coche) LOOP
               
               --Control averia
               if pkg_graficos.Tiene_Averia(coche => Coche) then
                     pkg_graficos.Actualiza_Atributo(T_RangoVelocidad(0), Coche);
                                           PKG_graficos.Actualiza_Movimiento(Coche);
                                           Averia:=Pkg_Averia_Aleatoria.Random(Generador_Averia);
                    pkg_teleoperador.Tarea_operador.msg_tele(Coche.Carril, Averia);
                    delay(5.0);
                    pkg_teleoperador.Tarea_operador.msg_AveriaReparada(Coche.Carril); --comunico reparacion
                    PKG_Averias.OP_Indicador_Averia(Coche.Carril).Actualiza_Indicador(PKG_tipos.T_Estado_Averia(NO_AVERIA));
                    pkg_graficos.Actualiza_Atributo(T_RangoVelocidad(VELOCIDAD_TAXI), Coche);
                    PKG_graficos.Actualiza_Movimiento(Coche);
                    
                end if;
               
               
               --control de semaforo
               if PKG_graficos.Posicion_Stop_Semaforo(Coche) and PKG_Semaforo.OP_Semaforo.Semaforo_Rojo then
                    
                    pkg_graficos.Actualiza_Atributo(T_RangoVelocidad(0), Coche);
                    PKG_graficos.Actualiza_Movimiento(Coche);
                    PKG_Semaforo.OP_Semaforo.controlSemaforo; 
                    pkg_graficos.Actualiza_Atributo(T_RangoVelocidad(VELOCIDAD_TAXI), Coche);
                    
               elsif PKG_graficos.Coche_Parado(Coche) then
                    PKG_Semaforo.OP_Semaforo.controlSemaforo; 
                    pkg_graficos.Actualiza_Atributo(T_RangoVelocidad(VELOCIDAD_TAXI), Coche);
               end if;
              
         

         
               -- actualiza la posicion del taxi
               
               PKG_graficos.Actualiza_Movimiento(Coche);
               
               
               delay(RETARDO_MOVIMIENTO);

          END LOOP;

          -- El taxi deja de visualizarse
          PKG_graficos.Desaparece(Coche);

          PKG_debug.Escribir("======================FIN TASK coche");

     exception
          when pkg_tipos.DETECTADA_COLISION_TREN => PKG_graficos.Desaparece(Coche);
          when event                                         : others =>
               PKG_debug.Escribir("ERROR en TASK coche        : " & Exception_Name(Exception_Identity(event)));

     end T_Tarea_Coche;

end pkg_coches;
