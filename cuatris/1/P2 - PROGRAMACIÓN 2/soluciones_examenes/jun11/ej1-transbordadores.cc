#include <iostream>
#include <fstream>
#include <cstdlib>

using namespace std;

const string kE1="Error al abrir el fichero ";

struct Fecha
{
  int dia, mes, anyo;
};

string getCampo(string s, int &i, char c)
{
  string salida;
  while (i<s.length() && s[i]!=c)
  {
    salida+=s[i];
    i++;
  }
  i++;
  return salida;
}

Fecha stringAFecha(string s)
{
    Fecha f;
    int i=0;
    f.dia=atoi(getCampo(s,i,'/').c_str());
    f.mes=atoi(getCampo(s,i,'/').c_str());
    f.anyo=atoi(getCampo(s,i,'/').c_str());
    return f;
}

bool isMenorIgual(Fecha f1, Fecha f2)
{
  bool salida=true;
  if (f1.anyo>f2.anyo)
    salida=false;
  else if (f1.anyo==f2.anyo)
  {
    if (f1.mes>f2.mes)
      salida=false;
    else if (f1.mes==f2.mes)
    {
      if (f1.dia>f2.dia)
        salida=false;
    }
  }
  return salida;
}

void procesarLinea(string s, Fecha fechausuario, string trans)
{
  int i=0;

  string transbordador=getCampo(s,i,':');
  string pieza=getCampo(s,i,':');
  string fecha=getCampo(s,i,':');
  
  Fecha f=stringAFecha(fecha);

  if (isMenorIgual(f,fechausuario) && trans==transbordador)
    cout << pieza << endl;
}

void procesarFichero(char nombre[], Fecha fechausuario, string trans)
{
    fstream f;
    f.open(nombre,ios::in);
    if (f.is_open())
    {	
      string s;
      getline(f,s);
      while (!f.eof())
      {
        procesarLinea(s,fechausuario,trans);
        getline(f,s);
      }
      f.close();
    }  
    else cout << kE1 << nombre << endl;
}

int main(int argc, char *argv[])
{
  if (argc==4)
  {
    string transbordador=argv[2];
    Fecha fechausuario=stringAFecha(argv[3]);

    procesarFichero(argv[1],fechausuario,transbordador);
  }
  else cout << "Sintaxis: " << argv[0] << " fichero.txt transbordador fecha" << endl;
}

