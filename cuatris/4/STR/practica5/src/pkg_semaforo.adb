--******************* PKG_SEMAFORO.ADB ************************************
-- Paquete que implementa el funcionamiento del semáforo para cruzar la vía del tren
--***********************************************************************

WITH pkg_graficos;
with PKG_tipos; use pkg_tipos;

PACKAGE BODY Pkg_Semaforo IS

   ------------------------------------------------------------------------
   -- OBJETO PROTEGIDO PARA ALMACENAR EL ESTADO DEL SEMAFORO
   ------------------------------------------------------------------------
   PROTECTED BODY OP_Semaforo IS

      ---------------------------------------------------------------------
      -- Cambia el estado (color) del semáforo de coches
      ---------------------------------------------------------------------
      PROCEDURE Cambia_Estado_Semaforo(color: IN T_ColorSemaforo) IS
      BEGIN
         -- cambiamos el estado almacenado en el OP
         Estado_Semaforo := Color;

         -- Dibujamos el nuevo color en la interfaz gráfica
         pkg_graficos.Actualiza_Color_Semaforo(color);
      END Cambia_Estado_Semaforo;


      ---------------------------------------------------------------------
      -- Función que devuelve true si el semáforo está en rojo
      ---------------------------------------------------------------------
      FUNCTION Semaforo_Rojo RETURN Boolean IS
      BEGIN
         RETURN Estado_Semaforo = RED;
          END Semaforo_Rojo;



          entry controlSemaforo when not Semaforo_Rojo is
               begin
               null;
          end;

     end OP_Semaforo;


END PKG_semaforo;
