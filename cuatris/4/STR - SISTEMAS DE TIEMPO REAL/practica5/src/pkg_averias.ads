--******************* PKG_AVERIAS.ADS ***********************************
-- Paquete que implementa la aparición de averías en los coches
-- En los taxis no se producen averías
--***********************************************************************

WITH Gtk.Window;   USE Gtk.Window;
with PKG_tipos; use PKG_tipos;

PACKAGE PKG_Averias IS

     -----------------------------------------------------------------
     -- DECLARACIÓN DEL TIPO OBJETO PROTEGIDO PARA EL INDICADOR DE AVERÍAS
     -----------------------------------------------------------------
     PROTECTED TYPE T_OP_Indicador_Averia IS

          entry carrilCortado;
          entry reaunadrCarril;

          -- Actualiza el indicador del estado de una avería del carril correspondiente
          procedure Actualiza_Indicador(estado : T_Estado_Averia);

          -- Consulta el indicador del estado de una avería del carril correspondiente
          FUNCTION Consulta_Indicador RETURN T_Estado_Averia;


     private
          -- estado de la avería en un carril
          Indicador_Averia                     : T_Estado_Averia := NO_AVERIA;

     END T_OP_Indicador_Averia;


     -- Declaración del array de objetos protegidos con los indicadores
     -- de avería para cada carril
     OP_Indicador_Averia                       : ARRAY (T_Carril) OF T_OP_Indicador_Averia;


     -- Función que devuelve la duración (en segundos) del tipo
     -- de avería especificado
     FUNCTION Duracion_Tipo_Averia(tipo_averia : T_Tipo_Averia) return Duration;



PRIVATE
     -----------------------------------------------------------------
     -- DECLARACIONES DE CONSTANTES
     -----------------------------------------------------------------
     -- Duración en segundos de cada tipo de avería
     DUR_AVERIA_GASOLINA                       : constant := 10.0;
     DUR_AVERIA_PINCHAZO                       : constant := 15.0;

     -- Declaración del array de duraciones (en segundos) de cada tipo de avería
     Dur_Tipo_Averia                           : ARRAY(T_Tipo_Averia) OF Duration := (DUR_AVERIA_GASOLINA,
                                                                                      DUR_AVERIA_PINCHAZO);

end PKG_Averias;

