#include <iostream>
#include <cstdlib>

using namespace std;

#include "Hoguera.h"

int Hoguera::idNextHoguera=1;

Hoguera::Hoguera(string line)
{
	string name,timestring;
	unsigned int i=0;
	
	while (i<line.length() && line[i] != ':')
	{
	  name += line[i];
	  i++;
	}
	i++;  // saltar ':'
	while (i<line.length())
	{
	  timestring += line[i];
	  i++;
	}
	this->name = name;
	this->time = atoi(timestring.c_str());
	this->team = "";
	this->id = idNextHoguera++;
}

ostream & operator<<(ostream &os,const Hoguera &h)
{
	os << h.name << " ("<< h.id << ")=" << h.time;

	return os;
}
