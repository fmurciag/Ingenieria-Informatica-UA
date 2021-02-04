
#ifndef _Startup_
#define _Startup_

#include <iostream>
#include <vector>

using namespace std;

#include "Project.h"

class Startup {

   private:

    vector<Project> projects;
    vector<string> teams;
    string name;  
    
   public:
   
    Startup(string filename);
    void addTeam(string team);
    void distributeTeams();
    int getMinor() const;

  friend ostream& operator<<(ostream &os,const Startup &startup);
};

#endif
