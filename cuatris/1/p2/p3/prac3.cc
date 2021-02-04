#include <iostream>
#include <string.h>
#include "Container.h"
#include "Ship.h"
#include "Base.h"
#include "Util.h"

using namespace std;

void menu()
{
  cout << "1- Info base" << endl
       << "2- Add container" << endl
       << "3- Remove container" << endl
       << "4- Add ship" << endl
       << "5- Remove ship" << endl
       << "6- Manual distribution" << endl
       << "7- Automatic distribution" << endl
       << "8- Unassign container" << endl
       << "9- Clear assignations" << endl
       << "q- Quit" << endl
       << "Option: " ;
}

int main()
{
  Base base("Logistic Center");
  vector<Ship*> naves;
  
  char option;

  do
  {
    menu();
    cin >> option; cin.get();
        
    switch (option)
    { 
      case '1':
      {
        cout << base;
        break;
      }
      case '2':
      { 
        base.addContainer();
        break;
      }
      case '3':
      {
        base.removeContainer();
        break;
      }
      case '4':
      {
        try
        {
          Ship *nueva=new Ship;
          naves.push_back(nueva);
          base.addShip(nueva);
        }
        catch(Error e)
        {
          Util::error(e);
        }
        break;
      }
      case '5':
      {
        base.removeShip();
        break;
      }
      case '6':
      {
        base.manualDistribution();
        break;
      }
      case '7':
      {
        base.automaticDistribution();
        break;
      }
      case '8':
      {
        base.unassignContainer();
        break;
      }
      case '9':
      {
        base.clearAssignations();
        break;
      }
      case 'q':
      {
        for(int i=0;(unsigned)i<naves.size();i++)
        {
          delete naves[i];
        }
        break;
      }
      default:
      {
        Util::error(ERR_MENU_OPTION); 
        break;
      }
    }
  } while(option != 'q');
  return 0;
}
