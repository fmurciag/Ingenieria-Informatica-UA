--******************* PKG_TAXI.ADS *************************************
-- Paquete que implementa la generaci�n de taxis y su comportamiento
--***********************************************************************

WITH PKG_tipos, Ada.Calendar;USE PKG_tipos;

PACKAGE PKG_Taxi IS

     protected OP_luzTaxi is

          entry paradaTaxi;
          entry completo;

          procedure cambiar_luz(estado: in Boolean);
          function getLuzTaxi return Boolean;
          procedure addPasajero;
          procedure resetearTaxi;

          private
          libre : Boolean:=false;
          numPasajeros:integer:=0;
     end OP_luzTaxi;

   -- tarea encargada de la generaci�n de taxis
   task Tarea_GeneraTaxis;

   -- Tipo tarea encargada del comportamiento de un taxi
   TASK TYPE T_Tarea_Taxi(ptr_taxi : Ptr_T_RecordTaxi);
   type Ptr_Tarea_Taxi is access T_Tarea_Taxi;

end PKG_taxi;

