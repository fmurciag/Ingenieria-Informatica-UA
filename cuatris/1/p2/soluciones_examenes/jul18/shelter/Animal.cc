

#include <iostream>
#include <cstdlib>

// OJO 
//  - atoi en el enunciado


using namespace std;

#include "Animal.h"

Animal::Animal(string s)
{
  unsigned i=0;
  string nametype="",agestr="";;
  
  name="";
  while (i<s.length() && s[i] != ',')
  {
    name += s[i];
    i++;
  }
  i++;
  while (i<s.length() && s[i] != ',')
  {
    nametype += s[i];
    i++;
  }
  i++;
  while (i<s.length())
  {
    agestr += s[i];
    i++;
  }
  age = atoi(agestr.c_str());
  if (nametype=="Dog") type=Dog;
  else if (nametype=="Cat") type=Cat;
  else if (nametype=="Jerbo") type=Jerbo;
  else throw nametype;
  owner="";
}

void Animal::adopt(string owner)
{
  this->owner = owner;
}

ostream &operator<<(ostream &os,const Animal &a)
{
  os << a.name << ", age=" << a.age;
  if (a.owner != "") os << ", owner=" << a.owner;
  os << endl;
  
  return os;
}
