#include "Partida.h"

ostream &operator<<(ostream &os, const Partida &p)
{
  for (int i=0; i<p.numJugadores; i++)
      os << "Puntuacion jugador "  <<  i+1 << "= " << p.jugadores[i].getPuntuacion() << endl;
  return os;
}

Partida::Partida(int numJugadores) 
{
      this->numJugadores=numJugadores;
      jugadores.resize(numJugadores);
      turno=0;
}

void Partida::generarRack()
{
  rack="";

  for (int i=0; i<Partida::letrasRack; i++)
    rack+='A'+rand()%26;  
}

void Partida::jugar()
{
  string palabra;
  bool salir=false;
  
  do
  {
    generarRack();
    cout << "Rack= " << rack << endl;
    
    for (int i=0; i<numJugadores && !salir; i++)
    {
      cout << "Jugador " << turno+1 << "= ";
      getline(cin,palabra);
    
      if (palabra=="q") salir=true;
      else if (jugadores[turno].mover(palabra,rack))
      {
        turno++;
        if (turno==numJugadores)
          turno=0;
      }
      else i--;
    } 
  } while (!salir);  
}
