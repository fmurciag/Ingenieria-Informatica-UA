package body pkg_cronometro_realtime is

        task body activacion is

          periodo   :  Time_Span := To_Time_Span(1.0);
          siguiente : Time;
          inicio : Time;
     begin
          inicio:=Ada.Real_Time.Clock;--enchufamos el crono
          siguiente := Ada.Real_Time.Clock + periodo;
          loop
               --accion
               pkg_graficos.Actualiza_Cronometro(To_Duration(Ada.Real_Time.Clock - inicio));
               delay until siguiente;
               siguiente := siguiente + periodo;
               
          end loop;
     end activacion;

end pkg_cronometro_realtime;




