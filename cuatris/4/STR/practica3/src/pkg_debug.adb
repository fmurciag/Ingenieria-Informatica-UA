--************************ PAQUETE PKG_DEBUG ****************************
-- Este paquete se encarga de imprimir mensajes en un fichero de texto
-- Además de imprimirlos en el fichero, se muestran por pantalla
--*************************************************************************

with Ada.exceptions; use Ada.exceptions;

package body PKG_debug is


   --------------------------------------------------------------------
   -- Procedure que abre el fichero y escribe su cabecera
   --------------------------------------------------------------------
   procedure Crear_Fichero is
   BEGIN
      Abrir_Fichero;
      Escribir_Cabecera;
   end Crear_Fichero;


   --------------------------------------------------------------------
   -- Procedimiento que se encarga de cerrar el fichero
   --------------------------------------------------------------------
   procedure Cerrar_Fichero is
   begin
      Ada.Text_IO.Close(fich);

      exception
       when event: others =>
        Ada.Text_IO.Put_Line("ERROR al Cerrar el Fichero: " & exception_message(event));
   end Cerrar_Fichero;


   --------------------------------------------------------------------
   -- Procedure que escribe una cadena de caracteres en el fichero y
   -- en pantalla
   --------------------------------------------------------------------
   procedure Escribir(cadena:String) is
   begin
      Escribir_Fichero(cadena);
      Escribir_Pantalla(cadena);
   end Escribir;


   --------------------------------------------------------------------
   -- Procedure que se encarga de crear y abrir el fichero
   --------------------------------------------------------------------
   procedure Abrir_Fichero is
   begin
       Ada.Text_IO.Create(fich, Ada.Text_IO.Append_File, nomb_fich);
      exception
       when others =>
          -- si el fichero ya está creado, lo abrimos
          Ada.Text_IO.Open(fich, Ada.Text_IO.Append_File, nomb_fich);

   end Abrir_Fichero;


   --------------------------------------------------------------------
   -- Procedure que escribe una cabecera en el fichero y en pantalla
   --------------------------------------------------------------------
   procedure Escribir_Cabecera is
   begin
      Escribir(" ");
      Escribir("**************************************************************************");
      Escribir("**************************************************************************");
      Escribir("          PRACTICA STR: CONTROL DE TRAFICO");
      Escribir("**************************************************************************");
      Escribir("**************************************************************************");
      Escribir(" ");
   end Escribir_Cabecera;


   --------------------------------------------------------------------
   -- Procedure que escribe una cadena de caracteres en el fichero
   --------------------------------------------------------------------
   procedure Escribir_Fichero(cadena:String) is
   begin
      Ada.Text_IO.Put_Line(fich,cadena);

      exception
       when event: others =>
        Ada.Text_IO.Put_Line("ERROR en Escritura en Fichero: " & exception_message(event));
   end Escribir_Fichero;


   --------------------------------------------------------------------
   -- Procedure que escribe una cadena de caracteres en pantalla
   --------------------------------------------------------------------
   procedure Escribir_Pantalla(cadena:String) is
   begin
      Ada.Text_IO.Put_Line(cadena);
   end Escribir_Pantalla;


BEGIN
   Crear_Fichero;

end PKG_debug;
