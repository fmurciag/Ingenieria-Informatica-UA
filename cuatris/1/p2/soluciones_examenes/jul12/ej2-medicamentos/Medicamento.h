#ifndef _MEDICAMENTO_H_
#define _MEDICAMENTO_H_

#include <iostream>

using namespace std;

class Medicamento
{
   private:
     string nombre;
     string incompatibilidades[20];
     int numincompatibilidades;

   public:
     Medicamento(string nom=""):nombre(nom) { numincompatibilidades=0; }
     Medicamento(const Medicamento& m); 

     string getNombre() const { return nombre; }
     void setNombre(string nombre) {this->nombre=nombre; }
     bool nuevaIncompatibilidad(string nombre);
     bool esIncompatibleCon(string nombre);
};

#endif
