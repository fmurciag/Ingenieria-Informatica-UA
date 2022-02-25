with PKG_tipos; use PKG_tipos;
WITH PKG_graficos;
WITH PKG_Averias;

package pkg_teleoperador is

   Task Tarea_operador is 
          entry msg_tele(carril : T_Carril; averia : T_Tipo_Averia);
          entry msg_AveriaReparada(carril : T_Carril);
   end Tarea_operador;

end pkg_teleoperador;
