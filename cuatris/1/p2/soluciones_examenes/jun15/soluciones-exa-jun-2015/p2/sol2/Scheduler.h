#ifndef _SCHEDULER_H_
#define _SCHEDULER_H_

#include "Hoguera.h"
#include <vector>

class Scheduler
{
  friend ostream &operator<<(ostream &os, const Scheduler &s);

  public:
    Scheduler(string filename);
    void distributeTeams();
    void addTeam(string team);
    
  private:
    vector<Hoguera> hogueras;
    vector<string> teams;
    
    int getMinor() const;
};

#endif
