
#ifndef _Shelter_
#define _Shelter_

#include <vector>

using namespace std;

#include "Animal.h"

class Shelter {
  
  private:
  
    static const unsigned MAXADOPTED=3;
    string name;
    vector<Animal> animals;
  
  public:
    
    Shelter(string name="Natura Shelter");
    bool add(const Animal &a);
    bool adopt(const Animal &a,string owner);
    int search(const Animal &a) const;
    bool ownerIsValid(string owner) const;

  friend ostream &operator<<(ostream &os,const Shelter &s);
};


#endif
