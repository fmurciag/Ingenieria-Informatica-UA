#ifndef _COORDENADA_H_
#define _COORDENADA_H_

#include <iostream>
#include <cmath>

using namespace std;

class Coordenada
{
  private:
   double x;
   double y;

  public:
   static const Coordenada coordenadaError;

   Coordenada(double x=-1, double y=-1) { this->x=x; this->y=y; }
   Coordenada(const Coordenada &c);
   double distancia(const Coordenada &b) const;
   bool isError() const;
   double getX() const { return x; }
   double getY() const { return y; }
   void setX(double x) { this->x=x; }
   void setY(double y) { this->y=y; }
};

#endif
