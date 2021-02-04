#include <fstream>
#include <iostream>
#include "Scheduler.h"

Scheduler::Scheduler(string filename)
{
	ifstream f;

	f.open(filename.c_str(), ios::in);
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

}
	
void Scheduler::addTeam(string team)
{
	teams.push_back(team);
}
	
void Scheduler::distributeTeams()
{
	int it=0;
	int pos;

	do
	{
		pos = getMinor();
		if (pos != -1)
		{
			hogueras[pos].setTeam(teams[it]);
			it++;
			if (it >= teams.size()) it=0;
		}
	} while (pos != -1);
}
	
int Scheduler::getMinor() const
{
	int pos=-1;
	int minTime=10000;

	for (unsigned int i=0;i<hogueras.size();i++)
	{
 		if (hogueras[i].getTeam().length() == 0)  // sin equipo asignado
 		{
			if (hogueras[i].getTime() < minTime)
			{
				pos=i;
				minTime=hogueras[i].getTime();
			}
		}
	}
	return pos;
}
	
ostream & operator<< (ostream &os,const Scheduler &s)
{
	for (unsigned int i=0;i<s.teams.size();i++)
	{
		os << "[" << s.teams[i] << "] :";
		for (unsigned int j=0;j<s.hogueras.size();j++)
		{
			if (s.hogueras[j].getTeam() == s.teams[i])
				os << " " << s.hogueras[j];
		}
		os << endl;
	}
	return os;
}
