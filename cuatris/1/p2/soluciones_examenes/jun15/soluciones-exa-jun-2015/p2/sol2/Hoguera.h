#ifndef _HOGUERA_H_
#define _HOGUERA_H_

#include <iostream>

using namespace std;

class Hoguera
{
  friend ostream &operator<<(ostream &os, const Hoguera &hoguera);

  public:
    Hoguera(string line); 

    int getTime() const { return time; }    
    string getTeam() const { return team; }
    void setTime(int time) { this->time=time; }
    void setTeam(string team) { this->team=team; }

  private:
    int time, id;
    string name, team;
    static int idNextHoguera;
};

#endif
