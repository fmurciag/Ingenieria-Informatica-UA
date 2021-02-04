#include <iostream>
#include <fstream>

#include "Scheduler.h"

ostream &operator<<(ostream &os,const Scheduler &sched)
{
	for(unsigned i = 0; i < sched.teams.size(); i++)
	{
		os << "[ " << sched.teams[i] << " ] :";
		for(unsigned j = 0; j < sched.hogueras.size(); j++)
			if(sched.hogueras[j].getTeam() == sched.teams[i])
				os << " " << sched.hogueras[j];
		os << endl;
	}
	return os;
}

int Scheduler::getMinor() const
{
	int i = 0;
	int iMin = -1;
	int tMin;
	while ( i < (int)hogueras.size() && hogueras[i].getTeam() != ""   )
		i++;
	if( i < (int)hogueras.size() )
	{
		iMin = i;
		tMin = hogueras[iMin].getTime();
		for( i = iMin + 1; i < (int)hogueras.size(); i++)
			if( hogueras[i].getTime() < tMin && hogueras[i].getTeam() == "")
			{
				iMin = i;
				tMin = hogueras[i].getTime();
			}
	}
	return iMin;
}
  

Scheduler::Scheduler(string fileName)
{
	ifstream f(fileName.c_str());
	if(f.is_open())
	{
		string line;
		getline(f,line);
		while( !f.eof() )
		{
			Hoguera h(line);
			hogueras.push_back(h);
			getline(f,line);
		}
		
		f.close();
	}
	else
	{
		cout << "Error: no se puedo abrir el fichero " << fileName << endl;
	}
}


void Scheduler::distributeTeams()
{
	int iTeam = 0;
	int iHoguera = getMinor();
	while( iHoguera != -1 )
	{
		hogueras[iHoguera].setTeam(teams[iTeam]);
		iHoguera = getMinor();
		iTeam = (iTeam+1)%teams.size();
	}
}
