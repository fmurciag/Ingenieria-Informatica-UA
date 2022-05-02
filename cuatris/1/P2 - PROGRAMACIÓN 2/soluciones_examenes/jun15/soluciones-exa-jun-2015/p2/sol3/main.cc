#include <iostream>
using namespace std;
#include "Scheduler.h"
int main()
{
Scheduler f("hogueras2015.txt");
f.addTeam("Equipo A");
f.addTeam("Equipo Benalua");
f.addTeam("Equipo San Juan");
f.distributeTeams();
cout << f << endl;
}
