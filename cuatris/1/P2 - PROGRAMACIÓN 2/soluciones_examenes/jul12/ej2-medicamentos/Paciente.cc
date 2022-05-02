#include "Paciente.h"
#include <fstream>

Paciente::Paciente(const Paciente &p)
{ 
       nummedicamentos=p.nummedicamentos;
       nombre=p.nombre;
       for (int i=0; i<p.nummedicamentos; i++)
         medicamentos[i]=p.medicamentos[i];
}

bool Paciente::buscarIncompatibilidades(string nombre)
{
  bool compatible=true;
  for (int i=0; i<nummedicamentos && compatible ; i++)
  {
    if (medicamentos[i].esIncompatibleCon(nombre))
     compatible=false;
  }
  return !compatible;
}

bool Paciente::recetarMedicamento(const Medicamento &m)
{
  bool ok=true;
 
 // Buscar que no exista el medicamento? 
  if (nummedicamentos < 30 && !buscarIncompatibilidades(m.getNombre()))
  {
    medicamentos[nummedicamentos++]=m;
  }
  else {
    cout << "Error!" << endl;
    ok=false;
  }
  return ok;
}

bool Paciente::guardar(string filename) const
{
   ofstream f;
   bool ok=true;
   f.open(filename.c_str());
   if (f.is_open())
   {
     f << "Paciente: " << nombre << endl;
     for (int i=0; i<nummedicamentos; i++)
       f << i+1 << " " << medicamentos[i].getNombre() << endl;
     f.close();
   }
   else {
     cout << "Error de apertura del fichero " << filename;
     ok=false;
   }
   return ok;
}
