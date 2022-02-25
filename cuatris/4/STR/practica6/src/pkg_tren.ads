WITH PKG_tipos, Ada.Calendar;USE PKG_tipos;
package pkg_tren is


   -- tarea encargada de la generación de tren
   task Tarea_GeneraTrenes;

   -- Tipo tarea encargada del comportamiento de un taxi
   TASK TYPE T_Tarea_Tren(ptr_tren : Ptr_T_RecordTren);
   type Ptr_Tarea_Tren is access T_Tarea_Tren;

end pkg_tren;
