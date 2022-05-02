#include "Medicamento.h"

Medicamento::Medicamento(const Medicamento& m) 
{ 
      nombre=m.nombre;
      numincompatibilidades=m.numincompatibilidades; 
      for (int i=0; i<numincompatibilidades; i++) 
        incompatibilidades[i]=m.incompatibilidades[i];
}

bool Medicamento::nuevaIncompatibilidad(string nombre) {
      // Comprobar que exista?
      bool ok=true;
      if (numincompatibilidades<20)
       incompatibilidades[numincompatibilidades++]=nombre;      
      else ok=false;
      
      return ok;
}


bool Medicamento::esIncompatibleCon(string nombre)
{
      bool found=false;
      for (int i=0; i<numincompatibilidades && !found; i++)
      {
        if (incompatibilidades[i]==nombre) 
          found=true;
      }
      return found;
}
