#include "Ajedrez.h"

int main()
{
  Ajedrez j("Joaquim Travesset","ajedrez.txt");
  j.MoverPieza(5,2,5,1);
  j.Mostrar();
}
