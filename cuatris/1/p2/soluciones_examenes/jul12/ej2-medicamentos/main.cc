#include <iostream>

#include "Paciente.h"
#include "Medicamento.h"

using namespace std;

int main()
{
  Paciente p("Juan Lopez");
  
  Medicamento m("Diazepam");
  m.nuevaIncompatibilidad("Lanoxin");
  m.nuevaIncompatibilidad("Prozac");
  p.recetarMedicamento(m);
          
  Medicamento m2("Prozac");
  p.recetarMedicamento(m2);    // Incompatible, no se debe anyadir
              
  Medicamento m3("Salbutamol");
  p.recetarMedicamento(m3); 
  
  p.guardar("prueba.txt");

}
