--******************** PKG_bufferGenerico.ADS ***************************
-- Paquete que implementa un buffer circular genérico
--***********************************************************************

generic
   type Tipo_Dato is private;
   type tam is mod <>;

package PKG_bufferGenerico is
   type T_Buffer is limited private;
   procedure Insertar_Ultimo_Item(item: tipo_dato; buffer: in out T_Buffer);
   procedure Extraer_Primer_Item(item: out tipo_dato; buffer: in out T_Buffer);
   procedure Actualiza_Item(pos: in tam; item: in tipo_dato; buffer: in out T_Buffer);
   FUNCTION Posicion_Primer_Item(buffer: IN T_Buffer) RETURN Tam;
   FUNCTION Posicion_Ultimo_Item(buffer: IN T_Buffer) RETURN Tam;
   function Consulta_Item(pos: IN Tam; buffer: IN T_Buffer) return Tipo_Dato;
   function Vacio(buffer: IN T_Buffer) return boolean;
   function Lleno(buffer: IN T_Buffer) return boolean;
   procedure Extraer_Posicion_Item(pos : in tam; item: out tipo_dato; buffer: in out T_Buffer);

   private
      type T_Vector is array (tam) of Tipo_Dato;

      type T_Buffer is
        record
          vector: t_Vector;
          top   : tam:= tam'first;
          Base  : Tam:= Tam'Last;
          num   : Natural := 0; -- número de elementos que hay almacenados
        end record;

end Pkg_bufferGenerico;



