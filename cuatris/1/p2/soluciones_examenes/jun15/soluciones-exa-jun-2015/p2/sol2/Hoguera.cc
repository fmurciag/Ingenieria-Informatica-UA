#include "Hoguera.h"
#include <sstream>

int Hoguera::idNextHoguera=1;

Hoguera::Hoguera(string line)
{
  stringstream ss(line);
  
  getline(ss,name,':'); // Read name
  ss >> time; // Read time
  
  id=idNextHoguera++;
}

ostream &operator<<(ostream &os, const Hoguera &hoguera)
{
  os << hoguera.name << " (" << hoguera.id << ")=" << hoguera.time; 
  return os;
}
