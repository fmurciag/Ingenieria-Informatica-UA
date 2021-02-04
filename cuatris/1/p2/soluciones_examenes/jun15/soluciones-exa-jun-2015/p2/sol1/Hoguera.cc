#include <iostream>
#include <cstdlib>
#include "Hoguera.h"

using namespace std;

int Hoguera::idNextHoguera = 1;

ostream &operator<<(ostream &os,const Hoguera &h)
{
	os << h.name << " (" << h.id << ")=" << h.time;
	return os;
}


Hoguera::Hoguera(string line)
{
	int i = 0;
	while( line[i] != ':')
		name += line[i++];
	i++;
	string s;
	while( i < (int)line.length() )
		s+=line[i++];
	time = atoi(s.c_str());
	id = idNextHoguera++;
}
