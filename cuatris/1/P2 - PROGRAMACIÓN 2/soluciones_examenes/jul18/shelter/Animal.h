
#ifndef _Animal_
#define _Animal_

#include <iostream>

using namespace std;

enum AnimalType {Cat, Dog, Jerbo};

class Animal {

  private:
  
    string name;
    unsigned age;
    AnimalType type;
    string owner;
  
  public:
  
    Animal(string s);
    string getName() const           { return name;  }
    unsigned getAge() const          { return age;   }
    AnimalType getAnimalType() const { return type;  }
    string getOwner() const          { return owner; }
    void adopt(string owner);
    bool isAdopted() const           { return owner.length()>0; }

  friend ostream &operator<<(ostream &os,const Animal &a);
};

#endif
