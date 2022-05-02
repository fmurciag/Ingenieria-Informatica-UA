#include <iostream>
#include <stdlib.h>
#include "Partida.h"

using namespace std;

int main()
{
   srand(2);

   Partida p(2);
   p.jugar();
   cout << p << endl;
}
