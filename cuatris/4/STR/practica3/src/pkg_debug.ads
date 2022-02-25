--************************ PAQUETE PKG_DEBUG ****************************
-- Este paquete se encarga de imprimir mensajes en un fichero de texto
-- así como mostrarlos por pantalla
--*************************************************************************

with Ada.Text_IO;

package PKG_debug is

   -- Procedure que abre el fichero y escribe su cabecera
   procedure Crear_Fichero;

   -- Procedure que se encarga de cerrar el fichero
   procedure Cerrar_Fichero;

   -- Procedure que escribe una cadena de caracteres en el fichero y
   -- en pantalla
   procedure Escribir(cadena:String);


   private
      nomb_fich: String:= "debug.txt";  -- nombre del fichero de salida
      fich: Ada.Text_IO.File_Type;


      -- Procedure que se encarga de abrir el fichero o de crearlo si no existe.
      procedure Abrir_Fichero;

      -- Procedure que escribe una cabecera en el fichero y en pantalla
      procedure Escribir_Cabecera;

      -- Procedure que escribe una cadena de caracteres en el fichero
      procedure Escribir_Fichero(cadena:String);

      -- Procedure que escribe una cadena de caracteres en pantalla
      procedure Escribir_Pantalla(cadena:String);

end PKG_debug;
