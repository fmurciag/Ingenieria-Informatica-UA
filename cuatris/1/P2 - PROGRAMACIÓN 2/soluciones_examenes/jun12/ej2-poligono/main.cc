#include <iostream>
#include "Poligono.h"

using namespace std;

int main()
{
   Poligono p("mi triangulo");
   
   p.anyadirCoordenada(Coordenada(0,0));
   p.anyadirCoordenada(Coordenada(3,3));
   p.anyadirCoordenada(Coordenada(3,0));
   
   cout << "Perimetro de " << p.getNombre() << " = " << p.calcularPerimetro() << endl; 
}
