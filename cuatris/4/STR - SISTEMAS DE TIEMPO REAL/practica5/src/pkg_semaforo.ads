--******************* PKG_SEMAFORO.ADS **********************************
-- Paquete que implementa el funcionamiento del semáforo para cruzar la vía del tren
--***********************************************************************
with PKG_tipos; use PKG_tipos;

PACKAGE PKG_Semaforo IS

   -- Objeto protegido para almacenar el estado del semáforo
   PROTECTED OP_Semaforo IS

      -- Cambia el estado (color) del semáforo
      PROCEDURE Cambia_Estado_Semaforo(color: IN T_ColorSemaforo);

      -- Función que devuelve true si el semáforo está en rojo
          FUNCTION Semaforo_Rojo RETURN Boolean;

          entry controlSemaforo;

      PRIVATE
         estado_semaforo : T_ColorSemaforo  := Green;

   END OP_Semaforo;

end PKG_semaforo;

