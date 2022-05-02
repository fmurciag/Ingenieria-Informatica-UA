#include "Ciudad.h"

int main()
{
  Ciudad c("Villa arriba");
  c.leerVotos("elecciones.txt");
  c.imprimirResultados();
  cout << "--------" << endl << "Ganador: " << c.getGanador() << endl;
}
