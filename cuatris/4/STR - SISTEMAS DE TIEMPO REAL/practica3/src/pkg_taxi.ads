--******************* PKG_TAXI.ADS *************************************
-- Paquete que implementa la generación de taxis y su comportamiento
--***********************************************************************

WITH PKG_tipos, Ada.Calendar;USE PKG_tipos;

PACKAGE PKG_Taxi IS

   -- tarea encargada de la generación de taxis
   task Tarea_GeneraTaxis;

   -- Tipo tarea encargada del comportamiento de un taxi
   TASK TYPE T_Tarea_Taxi(ptr_taxi : Ptr_T_RecordTaxi);
   type Ptr_Tarea_Taxi is access T_Tarea_Taxi;

end PKG_taxi;

