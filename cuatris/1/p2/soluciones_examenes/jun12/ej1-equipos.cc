#include <iostream>
#include <fstream>
#include <vector>

using namespace std;

const int kMAXEQUIPOS=20;
const int kMAXJUGADORES=22;

typedef struct
{
  string dorsal;
  string nombre;
  string equipo;
} Jugador;

typedef struct
{
  string nombre;
  vector<Jugador> vc;
} Equipo;

string separarCampo(string s, int &i)
{
  string campo;
  while (i!=s.length() && s[i]!=':')
    campo+=s[i++]; 

  i++;
  return campo;   
}

Jugador procesarJugador(string s)
{
    Jugador c;
    int i=0;
    
    c.dorsal=separarCampo(s,i);  
    c.nombre=separarCampo(s,i);
    c.equipo=separarCampo(s,i);
    
    return c;
}

bool insertarJugador(vector<Equipo> &equipos, Jugador c)
{
  bool ok=true;
  bool salir=false;
  
  for (int i=0; i<equipos.size() && !salir; i++)
  {
    if (equipos[i].nombre==c.equipo)
    {
      if(equipos[i].vc.size()<kMAXJUGADORES)
      {
        equipos[i].vc.push_back(c);
      }
      else
      {
        cout << "ERROR: no se pueden añadir más jugadores al equipo " << c.equipo << endl;
      }
      salir=true;
    }
  } 
  if (!salir) 
  { 
    if (equipos.size()<kMAXEQUIPOS)
    {  
      // Nueva equipo, inicializar a 0 el numero de jugadores
      Equipo equipo;
      equipo.nombre=c.equipo;
      equipo.vc.push_back(c);

      equipos.push_back(equipo);
    }
    else
    {
      cout << "ERROR: no se pueden añadir más equipos" << endl;
    }
  }
}

void imprimirResultados(vector<Equipo> equipos)
{
   for (int i=0; i<equipos.size(); i++)
   {
     cout << "-----" << equipos[i].nombre << "-----" << endl;
     for (int j=0; j<equipos[i].vc.size(); j++)
     {
       cout << equipos[i].vc[j].dorsal << " " << equipos[i].vc[j].nombre << endl;
     }
   }
}

bool procesarFichero(char fichero[], vector<Equipo> &equipos)
{
  bool ok=true;
  ifstream f;
  f.open(fichero);
  
  if (f.is_open())
  {
    string s;
    getline(f,s);
    while (!f.eof())
    {
      Jugador c=procesarJugador(s);
      insertarJugador(equipos,c);
      
      getline(f,s);
    }
    f.close();
  }
  else {
   cout << "Error de apertura del fichero" << endl;
   ok=false;
  }
  return ok;
}

int main(int argc, char *argv[])
{
  vector<Equipo> equipos;
  
  if (argc!=2)
  {
    cout << "Sintaxis: " << argv[0] << " <fichero.txt>" << endl;
  }
  else 
  {
    if (procesarFichero(argv[1],equipos))
      imprimirResultados(equipos);
  } 
}
