#include "Scheduler.h"
#include <fstream>

Scheduler::Scheduler(string filename)
{
   ifstream f(filename.c_str());
   
   if (f.is_open())
   {
     string line;
     while (getline(f,line))
     {
       Hoguera h(line);
       hogueras.push_back(h);
     }
     f.close();
   }
   else cout << "Error de apertura del fichero" << endl;      
}

void Scheduler::addTeam(string team)
{
  teams.push_back(team);
}

void Scheduler::distributeTeams()
{
  int pos,nteam=0;
  
  while ((pos=getMinor())!=-1)
  {
    hogueras[pos].setTeam(teams[nteam++]);

    if (nteam==teams.size()) 
      nteam=0;
  }
}

int Scheduler::getMinor() const
{
  int pos=-1;
  int minTime;
  for (unsigned i=0; i<hogueras.size(); i++)
  {
     if (hogueras[i].getTeam()=="" && (pos==-1 || hogueras[i].getTime()<minTime))
     {
       minTime=hogueras[i].getTime();
       pos=i;
     }
  }
  return pos;
}

ostream &operator<<(ostream &os, const Scheduler &scheduler)
{
  for (unsigned team=0; team<scheduler.teams.size(); team++)
  {
    os << "[ " << scheduler.teams[team] << " ] : ";
    for (unsigned i=0; i<scheduler.hogueras.size(); i++)
    {
      if (scheduler.hogueras[i].getTeam()==scheduler.teams[team])
        os << scheduler.hogueras[i] << " ";
    }
    os << endl;
  }
  return os;
}
