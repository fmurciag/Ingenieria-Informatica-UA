#ifndef _PARTIDA_H_
#define _PARTIDA_H_

#include <iostream>
#include <vector>
#include "Jugador.h"

class Partida
{
  friend ostream& operator<<(ostream &os, const Partida &p);

  private:
    vector<Jugador> jugadores;
    int turno;
    int numJugadores;
    string rack;

    static const int letrasRack=9;

    void generarRack();
   
  public:
    Partida(int numJugadores=1);
    void jugar();
};

#endif
