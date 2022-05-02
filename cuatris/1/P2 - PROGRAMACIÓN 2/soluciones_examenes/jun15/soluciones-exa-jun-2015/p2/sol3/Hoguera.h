#ifndef _HOGUERA_
#define _HOGUERA_

#include <string>
using namespace std;

class Hoguera {

private:
	static int idNextHoguera;
	int id;
	string team;
	int time;
	string name;

public:
	Hoguera(string line);
	int getTime() const { return time;}
	string getTeam() const { return team;}
	void setTime(int time) { this->time = time;}
	void setTeam(string team) {this->team = team;}

friend ostream & operator<<(ostream &os,const Hoguera &h);

};

#endif
