--******************* PKG_SEMAFORO.ADS **********************************
-- Paquete que implementa el funcionamiento del sem�foro para cruzar la v�a del tren
--***********************************************************************
with PKG_tipos; use PKG_tipos;

PACKAGE PKG_Semaforo IS

   -- Objeto protegido para almacenar el estado del sem�foro
   PROTECTED OP_Semaforo IS

      -- Cambia el estado (color) del sem�foro
      PROCEDURE Cambia_Estado_Semaforo(color: IN T_ColorSemaforo);

      -- Funci�n que devuelve true si el sem�foro est� en rojo
          FUNCTION Semaforo_Rojo RETURN Boolean;

          entry controlSemaforo;

      PRIVATE
         estado_semaforo : T_ColorSemaforo  := Green;

   END OP_Semaforo;

end PKG_semaforo;

