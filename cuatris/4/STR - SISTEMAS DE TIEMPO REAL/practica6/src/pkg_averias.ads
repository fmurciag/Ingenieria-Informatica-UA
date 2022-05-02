--******************* PKG_AVERIAS.ADS ***********************************
-- Paquete que implementa la aparici�n de aver�as en los coches
-- En los taxis no se producen aver�as
--***********************************************************************

WITH Gtk.Window;   USE Gtk.Window;
with PKG_tipos; use PKG_tipos;

PACKAGE PKG_Averias IS

     -----------------------------------------------------------------
     -- DECLARACI�N DEL TIPO OBJETO PROTEGIDO PARA EL INDICADOR DE AVER�AS
     -----------------------------------------------------------------
     PROTECTED TYPE T_OP_Indicador_Averia IS

          entry carrilCortado;
          entry reaunadrCarril;

          -- Actualiza el indicador del estado de una aver�a del carril correspondiente
          procedure Actualiza_Indicador(estado : T_Estado_Averia);

          -- Consulta el indicador del estado de una aver�a del carril correspondiente
          FUNCTION Consulta_Indicador RETURN T_Estado_Averia;


     private
          -- estado de la aver�a en un carril
          Indicador_Averia                     : T_Estado_Averia := NO_AVERIA;

     END T_OP_Indicador_Averia;


     -- Declaraci�n del array de objetos protegidos con los indicadores
     -- de aver�a para cada carril
     OP_Indicador_Averia                       : ARRAY (T_Carril) OF T_OP_Indicador_Averia;


     -- Funci�n que devuelve la duraci�n (en segundos) del tipo
     -- de aver�a especificado
     FUNCTION Duracion_Tipo_Averia(tipo_averia : T_Tipo_Averia) return Duration;



PRIVATE
     -----------------------------------------------------------------
     -- DECLARACIONES DE CONSTANTES
     -----------------------------------------------------------------
     -- Duraci�n en segundos de cada tipo de aver�a
     DUR_AVERIA_GASOLINA                       : constant := 10.0;
     DUR_AVERIA_PINCHAZO                       : constant := 15.0;

     -- Declaraci�n del array de duraciones (en segundos) de cada tipo de aver�a
     Dur_Tipo_Averia                           : ARRAY(T_Tipo_Averia) OF Duration := (DUR_AVERIA_GASOLINA,
                                                                                      DUR_AVERIA_PINCHAZO);

end PKG_Averias;

