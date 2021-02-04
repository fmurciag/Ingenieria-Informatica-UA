#ifndef _POLIGONO_H_
#define _POLIGONO_H_

#include <vector>
#include "Coordenada.h"

class Poligono
{
   private:
    string nombre;
    vector<Coordenada> coordenadas;
    static const int maxCoordenadas=20;

   public:
    Poligono(string nombre) { this->nombre=nombre;  }
    Poligono(const Poligono &p);
    bool anyadirCoordenada(const Coordenada &c);
    double calcularPerimetro() const;
    string getNombre() const { return nombre; }
    void setNombre(string nombre) { this->nombre=nombre; }
};

#endif
