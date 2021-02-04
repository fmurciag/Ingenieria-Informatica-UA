#ifndef _SCHEDULER_
#define _SCHEDULER_

#include <vector>

using namespace std;

#include "Hoguera.h"

class Scheduler {

private:
	std::vector<Hoguera> hogueras;
	std::vector<string> teams;

public:
	Scheduler(string filename);
	void addTeam(string team);
	void distributeTeams();
	int getMinor() const;
	friend ostream & operator<< (ostream &os,const Scheduler &s);

};

#endif
