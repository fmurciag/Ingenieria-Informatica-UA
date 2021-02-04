#ifndef _CANCION_H_
#define _CANCION_H_

#include <iostream>
#include <vector>

using namespace std;

class Cancion
{
  friend ostream &operator<< (ostream &os, const Cancion &c); 

  private:
    string nombre;
    string artista;
    int puntuacion;

  public:
  
    static const int SP=-1;
    
    Cancion(string nombre, string artista, int puntuacion=SP);  
    string getNombre() const { return nombre; }
    string getArtista() const { return artista; }
    int getPuntuacion() const { return puntuacion; }
    void setPuntuacion(int puntuacion);
};

#endif
