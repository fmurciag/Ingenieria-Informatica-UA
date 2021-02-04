#include "Coordenada.h"

Coordenada::Coordenada(const Coordenada &c) 
{ 
	x=c.x; 
	y=c.y; 
}

bool Coordenada::isError() const { 
	return (x==-1 || y==-1); 
}

double Coordenada::distancia(const Coordenada &b) const
{
     return (sqrt(pow(x-b.x,2.0)+pow(y-b.y,2.0)));
}
