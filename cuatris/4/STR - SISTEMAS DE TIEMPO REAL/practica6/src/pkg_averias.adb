--******************* PKG_AVERIAS.ADB ***********************************
-- Paquete que implementa la aparici�n de aver�as en los coches
-- En los taxis no se producen aver�as
--***********************************************************************

PACKAGE BODY Pkg_Averias IS

   -----------------------------------------------------------------
   -- TIPO OBJETO PROTEGIDO PARA EL INDICADOR DE AVER�AS
   -----------------------------------------------------------------
     PROTECTED body T_OP_Indicador_Averia IS

           entry carrilCortado when not (Indicador_Averia=NO_AVERIA)is
          begin
               null;
          end carrilCortado;

          entry reaunadrCarril when Indicador_Averia=NO_AVERIA is
          begin
               null;
          end reaunadrCarril;

      -- Actualiza el indicador del estado de una aver�a del carril correspondiente
      PROCEDURE Actualiza_Indicador(estado: T_Estado_Averia) IS
      BEGIN
         -- Se deben tener en cuenta las pulsaciones de los botones para
         -- generar una nueva aver�a cuando no se est� atendiendo otra
         -- aver�a en el carril
         IF NOT (estado = COMUNICADA_AVERIA AND Indicador_Averia /= NO_AVERIA) THEN
            Indicador_Averia := Estado;
         END IF;
      END Actualiza_Indicador;


      -- Consulta el indicador del estado de una aver�a del carril correspondiente
      FUNCTION Consulta_Indicador RETURN T_Estado_Averia IS
      BEGIN
         RETURN Indicador_Averia;
      END Consulta_Indicador;



   END T_OP_Indicador_Averia;


   ------------------------------------------------------------------------
   -- Funci�n que devuelve la duraci�n (en segundos) del tipo
   -- de aver�a especificado
   ------------------------------------------------------------------------
   FUNCTION Duracion_Tipo_Averia(tipo_averia : T_Tipo_Averia) return Duration is
   BEGIN
      RETURN Dur_Tipo_Averia(Tipo_Averia);
   END Duracion_Tipo_Averia;

END PKG_Averias;
