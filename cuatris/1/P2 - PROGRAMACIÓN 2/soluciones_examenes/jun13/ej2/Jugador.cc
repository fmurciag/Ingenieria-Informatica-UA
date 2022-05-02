#include "Jugador.h"

bool Jugador::mover(string palabra, string rack)
{
  bool salir=false, ok=true;

  // Buscar letras en rack
  for (unsigned int i=0; i<palabra.length() && !salir; i++)
  {
    bool encontrado=false;
    for (unsigned int j=0; j<rack.length() && !encontrado; j++)
    {
      if (palabra[i]==rack[j]) {
        encontrado=true;
        rack.erase(j,1);  
      }
    }
    if (!encontrado) {
      cout << "Palabra incorrecta" << endl;
      salir=true;
      ok=false;
    }
  }
  if (!salir) 
    puntuacion+=palabra.length();  

  return ok;
}
