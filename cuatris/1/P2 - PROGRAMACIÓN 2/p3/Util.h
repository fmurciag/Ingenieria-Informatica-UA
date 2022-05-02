#ifndef _UTIL_
#define _UTIL_

#include <iostream>

using namespace std;

enum Error
{
  ERR_MENU_OPTION, 
  ERR_CONTAINER_WEIGHT,
  ERR_CONTAINER_VALUE,
  ERR_CONTAINER_ID,
  ERR_SHIP_NAME,
  ERR_SHIP_MAXCONTAINERS,
  ERR_SHIP_MAXWEIGHT,
  ERR_SHIP_NO_MORE_CONTAINERS,
  ERR_SHIP_NO_MORE_WEIGHT
}; 

class Util
{
  public:
    static void error(Error e);
};

#endif
