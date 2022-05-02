--******************* PKG_AVERIAS.ADB ***********************************
-- Paquete que implementa la aparición de averías en los coches
-- En los taxis no se producen averías
--***********************************************************************

PACKAGE BODY Pkg_Averias IS

   -----------------------------------------------------------------
   -- TIPO OBJETO PROTEGIDO PARA EL INDICADOR DE AVERÍAS
   -----------------------------------------------------------------
   PROTECTED body T_OP_Indicador_Averia IS

      -- Actualiza el indicador del estado de una avería del carril correspondiente
      PROCEDURE Actualiza_Indicador(estado: T_Estado_Averia) IS
      BEGIN
         -- Se deben tener en cuenta las pulsaciones de los botones para
         -- generar una nueva avería cuando no se está atendiendo otra
         -- avería en el carril
         IF NOT (estado = COMUNICADA_AVERIA AND Indicador_Averia /= NO_AVERIA) THEN
            Indicador_Averia := Estado;
         END IF;
      END Actualiza_Indicador;


      -- Consulta el indicador del estado de una avería del carril correspondiente
      FUNCTION Consulta_Indicador RETURN T_Estado_Averia IS
      BEGIN
         RETURN Indicador_Averia;
      END Consulta_Indicador;


   END T_OP_Indicador_Averia;


   ------------------------------------------------------------------------
   -- Función que devuelve la duración (en segundos) del tipo
   -- de avería especificado
   ------------------------------------------------------------------------
   FUNCTION Duracion_Tipo_Averia(tipo_averia : T_Tipo_Averia) return Duration is
   BEGIN
      RETURN Dur_Tipo_Averia(Tipo_Averia);
   END Duracion_Tipo_Averia;

END PKG_Averias;
