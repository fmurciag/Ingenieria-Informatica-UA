#include "Util.h"

void Util::error(Error e)
{
  switch (e)
  {
    case ERR_MENU_OPTION:
      cout << "Error: unknown menu option" << endl;
      break;

    case ERR_CONTAINER_WEIGHT:
      cout << "Error: container weight is below minimum" << endl;
      break;
      
    case ERR_CONTAINER_VALUE:
      cout << "Error: container value is below minimum" << endl;
      break;
    
    case ERR_CONTAINER_ID:
      cout << "Error: wrong container id" << endl;
      break;
      
    case ERR_SHIP_NAME:
      cout << "Error: wrong ship name" << endl;
      break;
      
    case ERR_SHIP_MAXCONTAINERS:
      cout << "Error: wrong maximum number of containers" << endl;
      break;
      
    case ERR_SHIP_MAXWEIGHT:
      cout << "Error: wrong maximum weight" << endl;
      break;
      
    case ERR_SHIP_NO_MORE_CONTAINERS:
      cout << "Error: ship cannot carry more containers" << endl;
      break;

    case ERR_SHIP_NO_MORE_WEIGHT:
      cout << "Error: ship cannot not hold more containers" << endl;
      break;
  }
}
