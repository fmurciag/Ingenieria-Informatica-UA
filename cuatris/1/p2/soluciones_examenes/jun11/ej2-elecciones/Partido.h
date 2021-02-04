#ifndef _PARTIDO_
#define _PARTIDO_

#include <iostream>
#include <fstream>

using namespace std;

class Partido {
  private:
    string nombre;
    int numvotos;
  public:
    Partido(string nom=""):nombre(nom) { numvotos=0;}
    string getNombre() const { return nombre; }
    void setNombre(string nom) { nombre=nom; }
    int getNumvotos() const { return numvotos; }
    void anyadirVoto();
};

#endif
