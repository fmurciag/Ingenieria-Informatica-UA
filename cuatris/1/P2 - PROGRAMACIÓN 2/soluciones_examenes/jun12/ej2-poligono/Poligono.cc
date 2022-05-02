#include "Poligono.h"

Poligono::Poligono(const Poligono &p)
{
      nombre=p.nombre;
      coordenadas=p.coordenadas;
}

bool Poligono::anyadirCoordenada(const Coordenada &c)
{ 
      bool ok=true;
      
      int tam=coordenadas.size();

      if (tam<maxCoordenadas) {
       	coordenadas.push_back(c);
      }
      else ok=false;

      return ok;
}

double Poligono::calcularPerimetro() const
{
     double sum=0;
     if (coordenadas.size()>1)
     {
        for (unsigned int i=1; i<coordenadas.size(); i++)
           sum+=coordenadas[i-1].distancia(coordenadas[i]);
           
        sum+=coordenadas[0].distancia(coordenadas[coordenadas.size()-1]);
     }
     else cout << "Error, insuficientes datos" << endl;

     return sum;
}
