#include <iostream>
#include <fstream>

#include "Ajedrez.h"

using namespace std;

Ajedrez::Ajedrez(string nombre)
{
  this->nombre=nombre;
}

Ajedrez::Ajedrez(string nombre, string nombreFichero)
{
  this->nombre=nombre;
  LeerFichero(nombreFichero);
}

// Asume sintaxis correcta y solo un espacio entre cada dato  
// Fichero tablero: 
// p b 2 2
// r b 1 3
//

void Ajedrez::InicializarTablero()
{
  for (int i=0; i<8; i++)
    for (int j=0; j<8; j++)
      tablero[i][j].setVacia();
}
  
bool Ajedrez::LeerFichero(string nombre) // Asume sintaxis correcta y solo un espacio entre cada dato
{
    bool ok=true;
    
    InicializarTablero();

    ifstream f(nombre.c_str());
    if (f.is_open())
    {
      char nombre, color;
      int nx, ny;

      f >> nombre;
      while (!f.eof())
      {
            f >> color >> nx >> ny;

            bool c=false;
            if (color=='n') c=true;
             
            Pieza p(nombre,c);
            AnyadirPieza(p,nx,ny);
              
            f >> nombre;
       }
    }
    else {
      cout << "Error de apertura del fichero" << endl;
      ok=false;  
    }
    return ok;
}

// Supone xy correctos
bool Ajedrez::AnyadirPieza(Pieza p, int x, int y)
{
      bool ok=true;
      if (!tablero[x][y].isVacia() && p.getColor()==tablero[x][y].getColor() ) { 
        cout << "Error: casilla ocupada por una pieza del mismo color" << endl;
        ok=false;
      }
      else 
      {
        tablero[x][y]=p;
      }
      return ok;
}

bool Ajedrez::MoverPieza(int ox, int oy, int dx, int dy) 
{
    bool ok=false;
    if (!tablero[ox][oy].isVacia())
    {
      if (!tablero[dx][dy].isVacia() && tablero[dx][dy].getColor()==tablero[ox][oy].getColor())
      {
        cout << "No se puede mover a un sitio ocupado por una pieza del mismo color" << endl;  
      }
      else 
      {
        ok=true;
        AnyadirPieza(tablero[ox][oy],dx,dy);
        BorrarPieza(ox,oy);
      }
    }
    return ok;
}

bool Ajedrez::BorrarPieza(int x, int y)
{
     bool ok=true;
     if (!tablero[x][y].isVacia())
        tablero[x][y].setVacia();

     else {
       cout << "Error al borrar la pieza" << endl;
       ok=false;
     }
     return ok;
}

void Ajedrez::Mostrar()
{
    cout << nombre << endl;
    for (int y=0; y<8; y++) 
    {
        cout << "|";
        for (int x=0; x<8; x++) 
        {
          if (!tablero[x][y].isVacia())
            cout << tablero[x][y].getTipo() << tablero[x][y].getColor() << "|";
          else cout << "  |";
        }
        cout << endl;
    }
}
