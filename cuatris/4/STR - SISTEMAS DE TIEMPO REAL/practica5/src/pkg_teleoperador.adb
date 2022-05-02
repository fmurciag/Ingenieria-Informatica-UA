package body pkg_teleoperador is

     task body Tarea_operador is
     begin
          loop
               select
               accept msg_tele(carril:in PKG_tipos.T_Carril;averia:in pKG_tipos.T_Tipo_Averia) do
                    PKG_graficos.Actualiza_Tipo_Averia_Carril(carril,averia);
                    PKG_Averias.OP_Indicador_Averia(carril).Actualiza_Indicador(PKG_tipos.T_Estado_Averia(REPARANDO_AVERIA));
                    end msg_tele;
               or
                    accept msg_AveriaReparada (carril : in T_Carril) do
                         PKG_Averias.OP_Indicador_Averia(carril).Actualiza_Indicador(PKG_tipos.T_Estado_Averia(NO_AVERIA));
                    end msg_AveriaReparada;
                    end select;
          end loop;
     end Tarea_operador;

end pkg_teleoperador;



