--******************* PKG_peaton.ADS ************************************
-- Paquete que implementa la generaci�n de peatones y su comportamiento
--***********************************************************************

with PKG_tipos,PKG_Taxi; USE PKG_tipos;


package PKG_peaton is
   -- tarea encargada de la generaci�n de peatones
   TASK Tarea_GeneraPeatones;

   -- Tipo tarea encargada del comportamiento de un peat�n
   TASK TYPE T_Tarea_Peaton(ptr_peaton : Ptr_T_RecordPeaton);
   type Ptr_Tarea_Peaton is access T_Tarea_Peaton;

end PKG_peaton;
