
#include <cstdlib>
#include "Project.h"

int Project::nextId=1;

Project::Project(string line)
{
  unsigned i=0;
  string num;
  
  while (i<line.length() && line[i] != '=')
  {
    name += line[i];
    i++;
  }
  i++;
  while (i<line.length())
  {
    num += line[i];
    i++;
  }
  time = atoi(num.c_str());
  id = nextId;
  nextId++;
  team="";
}

ostream & operator<<(ostream &os,const Project &project)
{
  os << project.name << " (" << project.id << ")= " << project.time;

  return os;
}
