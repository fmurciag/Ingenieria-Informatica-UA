#ifndef _HOGUERA_H_
#define _HOGUERA_H_

#include <iostream>


using namespace std;

class Hoguera
{
  friend ostream &operator<<(ostream &os,const Hoguera &h);
  
  private:
	static int idNextHoguera;
	int id;
	string team;
	int time;
	string name;
  
  public:
	Hoguera(string line);
	int getTime() const {return time;}
	string getTeam() const {return team;}
	void setTime(int time) {this->time=time;}
	void setTeam(string team) {this->team = team;}
};




#endif // _HOGUERA_H_
