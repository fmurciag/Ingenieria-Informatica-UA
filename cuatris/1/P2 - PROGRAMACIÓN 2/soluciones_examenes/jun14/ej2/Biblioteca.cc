#include "biblioteca.h"

int Biblioteca::buscar(const Libro &l) const
{
  int posicion=-1;

  for (int i=0; i<libros.size() && posicion==-1; i++)
  {
    if (libros[i].isEqualTo(l))
      posicion=i;
  }
  return posicion;
}

void Biblioteca::anyadir(const Libro &l)
{
  int posicion=buscar(l);
  
  if (posicion==-1)
    libros.push_back(l);
  else
    libros[posicion].anyadirEjemplares(l.getNumEjemplares());
}

bool Biblioteca::prestar(const Libro &l)
{
  bool ok=false;

  int posicion=buscar(l);

  if (posicion!=-1) 
    ok=libros[posicion].prestar();

  return ok;
}

ostream& operator<< (ostream &os, const Biblioteca &biblioteca)
{
  os << biblioteca.nombre << endl;
  os << "--------" << endl;
  os << "Libros disponibles" << endl;
  for (int i=0; i<biblioteca.libros.size(); i++)
  {
    if (biblioteca.libros[i].getNumDisponibles() > 0)
      os << biblioteca.libros[i] << endl;
  }
  return os;
}
