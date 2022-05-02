with Ada.Float_Text_IO, Ada.Numerics.Float_Random, Ada.Text_IO;

procedure numerosAleatorios is
     subtype T_Digito is Float range 0.0..1.0;	
     generador:Ada.Numerics.Float_Random.Generator;
     digito:T_Digito;
begin
     Ada.Numerics.Float_Random.Reset(generador);
     for i in 1..10 loop
          digito:=Ada.Numerics.Float_Random.Random(generador);
          Ada.Float_Text_IO.Put(digito, Aft => 4, Exp => 0);
          Ada.Text_IO.Skip_Line;
     end loop;
     

end numerosAleatorios;
