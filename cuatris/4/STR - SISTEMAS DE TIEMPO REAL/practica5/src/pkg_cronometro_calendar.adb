
package body pkg_cronometro_calendar is
     task body activacion is

          periodo   : constant Duration := 1.0;
          siguiente : Time;
          inicio : Time;
     begin
          inicio:=Clock;--enchufamos el crono
          siguiente := Clock + periodo;
          loop
               --accion
               pkg_graficos.Actualiza_Cronometro((Clock - inicio));
               delay until siguiente;
               siguiente := siguiente + periodo;
               
          end loop;
     end activacion;

end pkg_cronometro_calendar;
--Las medidas de tiempo que estamos empleando no requieren de tanta precision
