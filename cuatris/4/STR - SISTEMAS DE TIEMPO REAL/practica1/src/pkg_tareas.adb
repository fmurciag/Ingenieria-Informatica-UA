with Ada.Numerics.Discrete_Random;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;
with Ada.Text_IO; use Ada.Text_IO;
package body pkg_tareas is
     task body T_Tarea is
          subtype T_Num is Integer range a..b;
          package pkg_NumAleatorio is new Ada.Numerics.Discrete_Random (T_Num);
          use pkg_NumAleatorio;
          generador_num : Generator;
          num
          : T_Num;
     begin
          Reset (generador_num);
          for i in 1..20 loop
               num:= Random(generador_num);
               Put(num);
               New_Line;
               delay(0.01); -- La tarea queda suspendida 0.01 segundos
          end loop;
     end T_Tarea;
begin
put_line("Sentencias de inicialización del paquete");
end pkg_tareas;

--B: con delay se van intercalando las tareas
--C: se podrian utilizar sentecia de extraccion de datos como lectura de ficheros o un get para escribir por teclado: ej: "Open(File, In_File, "input.txt");"
