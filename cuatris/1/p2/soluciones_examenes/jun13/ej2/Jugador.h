#ifndef _JUGADOR_H_
#define _JUGADOR_H_

#include <iostream>

using namespace std;

class Jugador
{
  private:
    int puntuacion;

  public:
    Jugador() { puntuacion=0; }
    int getPuntuacion() const { return puntuacion; }
    bool mover(string palabra, string rack);
};

#endif
