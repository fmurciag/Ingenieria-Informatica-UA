#include "Cancion.h"

Cancion::Cancion(string nombre, string artista, int punt)
{
   this->nombre=nombre;
   this->artista=artista;
   
   if (puntuacion>=0 && puntuacion<=5)
    puntuacion=punt;
   else {
    cout << "Error, puntuacion incorrecta" << endl;
    puntuacion=SP;
   }   
}

void Cancion::setPuntuacion(int puntuacion)
{
    if (puntuacion>=0 && puntuacion<=5)
      this->puntuacion=puntuacion; 
    else cout << "Error, puntuacion incorrecta" << endl;
}

ostream &operator<< (ostream &os, const Cancion &c)
{
   os << c.nombre << " - " << c.artista;
   if (c.puntuacion!=Cancion::SP) os  << " - " << c.puntuacion;
   os << endl;
   return os;
}

