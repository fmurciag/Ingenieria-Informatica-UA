#include "Biblioteca.h"

int main()
{
  Biblioteca b("General");
  Libro l1("La guerra del fin del mundo","Mario Vargas Llosa");
  Libro l2("La tapadera","John Grisham", 4);
  Libro l3("A game of thrones","George R.R. Martin", 2);
        
  b.anyadir(l1);
  b.anyadir(l2);
  b.anyadir(l3);
                
  b.prestar(l1);
  b.prestar(l2);
                    
  cout << b << endl;
}

