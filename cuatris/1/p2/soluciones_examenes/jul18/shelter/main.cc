#include "Shelter.h"

int main()
{
  try {
    Animal a1("Bobby,Dog,3");
    Animal a2("Jerry Harry,Jerbo,2");
    Animal a3("Thanos,Dog,5");

    Shelter s;
    s.add(a1);
    s.add(a2);
    s.add(a3);

    s.adopt(a1, "Juan");
    s.adopt(a1, "Juan"); // No se permite (ya adoptado)
    s.adopt(a2, "Pepe");

    cout << s;
    Animal a5("Jerry,Mouse,10");
  } 
  catch(string message) {
    cout << "Animal type " << message 
         << " not supported" << endl;
  }
}
