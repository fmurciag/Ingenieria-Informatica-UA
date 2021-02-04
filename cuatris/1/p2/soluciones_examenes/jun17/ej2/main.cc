
#include <iostream>

using namespace std;

#include "List.h"

int main() {
    List l("The best movies");

    l.addMovie("Brian's life, 4", Action);
    l.addMovie("Interstellar, 5", SciFi);
    l.addMovie("Sharknado, -10", SciFi);  // Excepcion 
    l.addMovie("Interstellar, 5", SciFi); // Ya existe
    l.addMovie("Monster's Ball, 4",Drama);

    cout << l << endl;
}

