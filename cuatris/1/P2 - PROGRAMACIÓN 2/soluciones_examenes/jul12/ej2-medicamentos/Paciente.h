#ifndef _PACIENTE_H_
#define _PACIENTE_H_

#include <iostream>
#include "Medicamento.h"

using namespace std;

class Paciente
{
   private:
     Medicamento medicamentos[30];
     int nummedicamentos;
     string nombre;

     bool buscarIncompatibilidades(string nombre); // Metodo privado

   public:
     Paciente(string nombre) { this->nombre=nombre; nummedicamentos=0; }
     Paciente(const Paciente &p);
     string getNombre() const { return nombre; }
     void setNombre(string nombre) {this->nombre=nombre; }
     bool recetarMedicamento(const Medicamento &m);
     bool guardar(string filename) const;
};

#endif
