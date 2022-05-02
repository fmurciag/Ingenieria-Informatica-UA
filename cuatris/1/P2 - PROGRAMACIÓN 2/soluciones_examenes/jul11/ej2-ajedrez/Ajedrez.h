#ifndef _AJEDREZ_H_
#define _AJEDREZ_H_

#include "Pieza.h"

class Ajedrez
{
 private:
  Pieza tablero[8][8];
  string nombre;

 public:

  Ajedrez(string nombre);
  Ajedrez(string nombre, string nombrefichero);
  void InicializarTablero();
  bool LeerFichero(string nombre);
  bool AnyadirPieza(Pieza p, int x, int y);
  bool MoverPieza(int ox, int oy, int dx, int dy);
  bool BorrarPieza(int x, int y);
  void Mostrar();
};

#endif
