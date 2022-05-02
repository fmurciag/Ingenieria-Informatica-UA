
#include <iostream>

using namespace std;

#include "Shelter.h"

// OJO   "... o si ya tiene más de MAXADOPTED animales."


Shelter::Shelter(string name)
{
 this->name=name;
}

bool Shelter::add(const Animal &a)
{
  if (search(a)==-1) animals.push_back(a);
}


bool Shelter::adopt(const Animal &a,string owner)
{
  unsigned pos=search(a);
  if (pos!=-1 && !animals[pos].isAdopted() && ownerIsValid(owner))
  {
    animals[pos].adopt(owner);
  }
  else
    cout << a.getName() << " cannot be adopted" << endl;
}

int Shelter::search(const Animal &a) const
{
  for (unsigned i=0;i<animals.size();i++)
    if (animals[i].getName() == a.getName() && animals[i].getAnimalType() == a.getAnimalType())
       return i;
  return -1;
}

bool Shelter::ownerIsValid(string owner) const
{
  unsigned count=0;
  for (unsigned i=0;i<animals.size();i++)
    if (animals[i].getOwner()==owner) count++;
      
  if (count < MAXADOPTED)
    return true;
  else
    return false;
}

ostream &operator<<(ostream &os,const Shelter &s)
{
  os << "---- Adopted -----" << endl;
  for (unsigned i=0;i<s.animals.size();i++)
    if (s.animals[i].isAdopted())
       os << s.animals[i];
  
  os << "---- Not adopted -----" << endl;
  for (unsigned i=0;i<s.animals.size();i++)
    if (!s.animals[i].isAdopted())
       os << s.animals[i];
  
  return os;
}
