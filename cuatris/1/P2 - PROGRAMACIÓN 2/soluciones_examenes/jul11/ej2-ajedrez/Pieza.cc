#include "Pieza.h"

Pieza::Pieza(char tipo, bool color);
{
      this->tipo=tipo;
      this->color=color;
}

char Pieza::getTipo() const
{
      return tipo;
}
    
bool Pieza::getColor() const
{
      return color;
}

