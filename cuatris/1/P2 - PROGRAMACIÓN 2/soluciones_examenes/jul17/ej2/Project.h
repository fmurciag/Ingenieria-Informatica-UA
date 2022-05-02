
#ifndef _Project_
#define _Project_

#include <iostream>

using namespace std;

class Project {
  private:
     static int nextId;
     int id;
     string team;
     int time;
     string name;
     
 public:
     Project(string line);
     int getTime() const { return time; }
     string getTeam() const { return team; }
     void setTime(int time) { this->time = time; }
     void setTeam(string team) { this->team = team; }
     
     
 friend ostream & operator<<(ostream &os,const Project &project);

};

#endif
