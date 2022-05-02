#ifndef _PIEZA_H_
#define _PIEZA_H_

#include <iostream>

using namespace std;

class Pieza {
  private:
    char tipo;
    bool color;

  public:
    Pieza(char tipo=' ', bool color=false);
    char getTipo() const;
    bool getColor() const;
    bool isVacia() const { return (tipo==' '); }
    void setVacia() { tipo=' '; }
};

#endif
