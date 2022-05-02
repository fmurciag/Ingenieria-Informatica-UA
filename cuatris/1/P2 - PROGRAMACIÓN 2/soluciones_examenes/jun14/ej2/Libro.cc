#include "Libro.h"

Libro::Libro(string titulo, string autor, int numEjemplares)
{
      this->titulo=titulo;
      this->autor=autor;
      this->numEjemplares=numEjemplares;
      numPrestados=0;
}
    
bool Libro::prestar() {
    bool ok=false;

    if (getNumDisponibles()>0)
    {
      numPrestados++;
      ok=true;
    }
    else
        cout << "Error, no se puede prestar el libro" << endl;

    return ok;
}
    
bool Libro::devolver()
{
      bool ok=false;

      if (numPrestados>0)
      {
        ok=true;
        numPrestados--;
      }
      else
        cout << "Error, este libro no estaba prestado" << endl;
        
      return ok;
}

void Libro::anyadirEjemplares(int numEjemplaresNuevos) 
{
    numEjemplares+=numEjemplaresNuevos; 
}

bool Libro::isEqualTo(const Libro &l) const 
{
    return (titulo==l.titulo && autor==l.autor);
}

ostream& operator<<(ostream &os, const Libro &libro)
{
  os << libro.getNumDisponibles() << ": " << libro.titulo << " ( " << libro.autor << " ) ";
  return os;
}
