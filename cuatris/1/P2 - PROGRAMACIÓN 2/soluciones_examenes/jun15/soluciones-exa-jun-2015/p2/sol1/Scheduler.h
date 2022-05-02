#ifndef _SCHEDULER_H_
#define _SCHEDULER_H_

#include <iostream>
#include <vector>

#include "Hoguera.h"

using namespace std;


class Scheduler
{
  friend ostream &operator<<(ostream &os,const Scheduler &sched);
  
  private:
	vector<Hoguera> hogueras;
	vector<string> teams;
	int getMinor() const;
  
  public:
	Scheduler(string fileName);
	void addTeam(string team) { teams.push_back(team); }
	void distributeTeams();
};



#endif // _SCHEDULER_H_
