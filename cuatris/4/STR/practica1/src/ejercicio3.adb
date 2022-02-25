with Ada.Text_IO, Ada.Integer_Text_IO, Ada.Float_Text_IO, pkg_ejercicio2c;

procedure ejercicio3 is
   d:pkg_ejercicio2c.TdiasSemana;
   pos:Natural;
   value:pkg_ejercicio2c.TdiasSemana;
   begin
   Ada.Text_IO.Put("Alumnos: ");
   		Ada.Integer_Text_IO.Put(pkg_ejercicio2c.numAlumnos, 0);

   Ada.Text_IO.Put("nota media: ");
   Ada.Float_Text_IO.Put(pkg_ejercicio2c.getNotaMedia, 0 ,1, 0);

   for dia in pkg_ejercicio2c.TdiasSemana loop

      pkg_ejercicio2c.pkg_diasSemana.Put(dia);
    	Ada.Text_IO.New_Line;
	end loop;
	Ada.Text_IO.Put("introduce un dia: ");
   pkg_ejercicio2c.pkg_diasSemana.Get(d);

   pos:=pkg_ejercicio2c.TdiasSemana'Pos(d);
   value:=pkg_ejercicio2c.TdiasSemana'Val(pos);

     Ada.Text_IO.Put("su dia es:  ");
   pkg_ejercicio2c.pkg_diasSemana.Put(value);--


   ---salta una excepcion (ADA.IO_EXCEPTIONS.DATA_ERROR) al porque el valor no pertenece al tipo enumerado






 end ejercicio3;
