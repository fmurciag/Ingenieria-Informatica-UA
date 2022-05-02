with  Ada.Text_Io, Ada.Integer_Text_IO; use Ada.Text_Io;
with pkg_ejercicio2c;
 procedure ejercicio1 is
   s:String:="Comenzamos las practicas de STR";
   mes:Natural:=0;
begin
   Put("Hola mundo");
   Put_Line(s);
   pkg_ejercicio2c.OtroProcedimiento;
   
   begin
      Put("introduce el mes: ");
      Ada.Integer_Text_IO.Get(mes);
      case mes is
      when 12 | 1 | 2 => Put_Line("invierno");
      when 3 | 4 | 5 => Put_Line("primavera");
      when 6 | 7 | 8 => Put_Line("verano");
      when 9 | 10 | 11 => Put_Line("otoño");
      when others => Put_Line("mes incorrecto");
      end case;
      pkg_ejercicio2c.fin;
   
   		exception
      		when event: others => Put_Line("El número de mes debe se > 0");
   end;
     Ada.Text_IO.Put_Line("fin del progarma");
  
end ejercicio1;


---al usar " use " no es necesaio especificar el paquete a la hora de llamr a las funciones


--Ejercicio 2

--procedure OtroProcedimiento is
--begin
  --     Put_Line("Soy OtroProcedimiento");
--end OtroProcedimiento;  

--no compila porque solo puede haber una unidad de complacion, si se crea un paquete se soluciona el problema
