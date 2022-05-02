#ifndef _LIBRO_H_
#define _LIBRO_H_

#include <iostream>
#include <vector>

using namespace std;

class Libro
{
  friend ostream& operator<<(ostream &os, const Libro &libro);

  private:
    string titulo;
    string autor;
    int numEjemplares;
    int numPrestados;
    
  public:
    Libro(string titulo, string autor, int numEjemplares=1);
    bool prestar();
    bool devolver();
    int getNumDisponibles() const { return numEjemplares-numPrestados; }
    void anyadirEjemplares(int numEjemplaresNuevos); 
    int getNumEjemplares() const { return numEjemplares; }
    bool isEqualTo(const Libro &l) const;
};

#endif
