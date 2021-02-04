#include <iostream>
#include <fstream>
#include <vector>

using namespace std;

const string kE1="Error de sintaxis";
const string kE2="Error de apertura del fichero ";

bool EsLetra(char c)
{
  return (c>='a' && c<='z' || c>='A' && c<='Z');
}

char AMinusculas(char c)
{
  char salida=c;

  if (c>='A' && c<='Z')
    salida = c-'A'+'a';

  return salida;
}

string ExtraerPalabra(string s, int &i)
{
  string pal;
  
  while (i<s.length() && !EsLetra(s[i]))
    i++;
  
  while (i<s.length() && EsLetra(s[i]))
  {
    pal+=AMinusculas(s[i]); 
    i++;
  }
  return pal;
}

void AnyadirPalabra(string pal, vector<string> &palabras)
{
  bool encontrada=false;
  
  for (int i=0; i<palabras.size() && !encontrada; i++)
  {
    if (palabras[i]==pal) {
      encontrada=true;
    }
  }
  if (!encontrada && pal!="")
  {
    palabras.push_back(pal);
  }
}

void ProcesarLinea(string s)
{
  vector<string> palabras;
  int i=0;
  
  while (i<s.length()) 
  {
    string pal=ExtraerPalabra(s,i);
    AnyadirPalabra(pal,palabras);
  }
  cout << palabras.size() << " " << s << endl;
} 

void LeerFichero(char nombre[])
{
  ifstream f(nombre);
  if (f.is_open())
  {
    string s;
    getline(f,s);
    while (!f.eof())
    {
      ProcesarLinea(s);
      getline(f,s);  
    }
    f.close();
  }
  else cout << kE2 << nombre << endl;
}

int main(int argc, char* argv[])
{
  if (argc!=2)
    cout << kE1 << endl;
    
  else LeerFichero(argv[1]);
}
