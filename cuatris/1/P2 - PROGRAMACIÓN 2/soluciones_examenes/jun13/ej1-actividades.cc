#include <iostream>
#include <fstream>
#include <vector>

using namespace std;

struct Actividad
{
  string nombre;
  vector<string> socios;
};


string extraerCampo(string s, int &i, char simbolo)
{
  string salida;
  while (i<s.length() && s[i]!=simbolo)
  {
    salida+=s[i];
    i++;
  }
  i++;
  return salida;  
}


void anyadirActividad(string act, string socio, vector<Actividad> &actividades)
{
  bool encontrado=false;
  
  for (int i=0; i<actividades.size() && !encontrado; i++)
  {
    if (act==actividades[i].nombre)
    {
      encontrado=true;
      actividades[i].socios.push_back(socio);
    }
  }
  
  if (!encontrado)
  {
      Actividad a;
      a.nombre=act;
      a.socios.push_back(socio);
      actividades.push_back(a);
  }
}

vector<Actividad> leerFicheroActividades(char factividades[])
{
  vector<Actividad> actividades;

  bool ok=false;
  ifstream f(factividades);
  if (f.is_open())
  {
    string s;
    getline(f,s);
    
    while (!f.eof())
    {
      int i=0;
      string socio=extraerCampo(s,i,',');
      
      while (i<s.length())
      {
        string act=extraerCampo(s,i,',');
        anyadirActividad(act,socio,actividades);
      }
    
      getline(f,s);
    }  
    f.close();
  }

  return actividades;
}

void imprimirActividades(vector<Actividad> actividades)
{
    for (int i=0; i<actividades.size(); i++)
    {
        cout << actividades[i].nombre << ": ";
        for (int j=0; j<actividades[i].socios.size(); j++)
        {
           if (j!=actividades[i].socios.size()-1) cout << actividades[i].socios[j] << ", ";
           else cout << actividades[i].socios[j];
        }
        cout << endl;
    }

}

int main(int argc, char *argv[])
{
  if (argc!=2)
    cout << "Error de parametros" << endl;
    
  else
  {
    vector<Actividad> actividades=leerFicheroActividades(argv[1]);
    imprimirActividades(actividades);
  }
}
