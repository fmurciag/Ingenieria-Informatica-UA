WITH PKG_tipos; USE PKG_tipos;
package pkg_coches is

      
   task type Tarea_GeneraCoches(carril:T_Carril);
     carril1:Tarea_GeneraCoches(1);
     carril2:Tarea_GeneraCoches(2);
   
   TASK TYPE T_Tarea_Coche(ptr_Coche : Ptr_T_RecordCoche);
   type Ptr_Tarea_Coche is access T_Tarea_Coche;

end pkg_coches;
