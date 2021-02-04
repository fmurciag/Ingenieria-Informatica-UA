#ifndef _BIBLIOTECA_H_
#define _BIBLIOTECA_H_

#include "Libro.h"

class Biblioteca {
  friend ostream& operator<< (ostream &os, const Biblioteca &biblioteca);

  private:
    string nombre;
    vector<Libro> libros;
    int buscar(const Libro &l) const;

  public:
    Biblioteca(string nombre) { this->nombre=nombre; }
    void anyadir(const Libro &l);
    bool prestar(const Libro &l); 
};

#endif
