#include "Cancion.h"
#include "Playlist.h"

using namespace std;

int main()
{
  Cancion c("Cadenza","Dutch Uncles",5);
  Cancion c2("The ink", "Dutch Uncles",3);
  Cancion c3("Amor imposible", "Camela");
  Cancion c4("Trainwrecks","Weezer");
  c4.setPuntuacion(5);
 
  Playlist p("Grandes exitos");
  p.anyadirCancion(c);
  p.anyadirCancion(c2);
  p.anyadirCancion(c3);  
  p.anyadirCancion(c4);

  cout << p << endl;  
}
