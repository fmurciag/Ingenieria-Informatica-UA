with  Ada.Text_Io, Ada.Float_Text_IO; use Ada.Text_Io;

package pkg_ejercicio2c is
 
   procedure OtroProcedimiento;
     type TdiasSemana is (Lunes, Martes, Miercoles, Jueves, Viernes, Sabado, Domingo);
   numAlumnos : Integer := 7;
   function getNotaMedia return Float ;
   
      
   package pkg_diasSemana is new Ada.Text_IO.Enumeration_Io(Enum => TdiasSemana);
   
   
   
   
   
   private 
   notaMedia : Float := 3.19;
end pkg_ejercicio2c;
