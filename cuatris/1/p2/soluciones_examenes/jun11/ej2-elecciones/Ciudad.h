#ifndef _CIUDAD_H_
#define _CIUDAD_H_

#include <vector>

#include "Partido.h"

class Ciudad
{
  private:
    string nombre;
    vector<Partido> partidos;
    static const int maxpartidos=10;
  
  public:
    Ciudad(string nom):nombre(nom) {  } 
    Ciudad(const Ciudad& c);
  
    void setNombre(string nombre) { this->nombre=nombre; }
    string getNombre() const { return nombre; }
    void anyadirVoto(string nompartido);
    void leerVotos(string nombrefichero);
    void imprimirResultados() const;
    string getGanador() const;
};

#endif
