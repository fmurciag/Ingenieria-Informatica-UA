#include "Startup.h"

int main() {
  Startup s("data.txt");  
  s.addTeam("A");
  s.addTeam("B");
  s.addTeam("C");
        
  s.distributeTeams();
          
  cout << s << endl;
}
