
#include <fstream>
#include <climits>
#include "Startup.h"



Startup::Startup(string filename)
{
  ifstream f;
  
  f.open(filename.c_str(),ios::in);
  
  if (f.is_open())
  {
    string line;
    bool primera=true;
    
    while (getline(f,line))
    {
      if (primera)
      {
        name = line;
        primera=false;
      }
      else
      {
        Project p(line);
        
        projects.push_back(p);
      }
    }
  }
  else
    cout << "Error, no puedo abrir " << filename << endl;
}

void Startup::addTeam(string team)
{
  teams.push_back(team);
}

void Startup::distributeTeams()
{
   bool salir=false;

   while (!salir)
    for (unsigned i=0;i<teams.size() && !salir;i++)
    {
      int minor=getMinor();
      
      if (minor != -1)
        projects[minor].setTeam(teams[i]);
      else
        salir = true;
    }
}

int Startup::getMinor() const 
{
  int minTime=INT_MAX;
  int p=-1;
  
  for (unsigned i=0;i<projects.size();i++)
  {
//    cout << "P[" << i << "]=" << projects[i] << endl;
    if (projects[i].getTeam() == "" && projects[i].getTime() < minTime)
    {
      p = i;
      minTime = projects[i].getTime();
    }
  }
  
//  cout << "p=" << p << endl;
  
  return p;
}

ostream& operator<<(ostream &os,const Startup &startup)
{
  os << startup.name << endl << "-----" << endl;
  for (unsigned i=0;i<startup.teams.size();i++)
  {
     os << startup.teams[i] << ":";
     for (unsigned j=0;j<startup.projects.size();j++)
       if (startup.projects[j].getTeam() == startup.teams[i])
         os << " " << startup.projects[j];
     os << endl;
  }
  
  return os;
}


