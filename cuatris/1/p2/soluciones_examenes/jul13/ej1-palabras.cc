#include <iostream>
#include <fstream>
#include <vector>

using namespace std;

typedef struct
{
  string palabra;
  int n;
} Palabra;

bool esLetra(char c)
{
  return ((c>='a' && c<='z') || (c>='A' && c<='Z'));
}

string convertirMin(string palabra)
{
  string salida;
  
  for (int i=0; i<palabra.length(); i++)
  {
    if (palabra[i]>='A' && palabra[i]<='Z') // Tambien se puede hacer con toupper
      salida+=palabra[i]-'A'+'a';
    else salida+=palabra[i];
  }
    
  return salida;
}

void anyadirPalabra(string palabra, vector<Palabra> &palabras)
{
  bool encontrado=false;
  for (int i=0; i<palabras.size() && !encontrado; i++)
  {
    if (palabra==palabras[i].palabra)
    {
      palabras[i].n++;
      encontrado=true;
    }
  }
  if (!encontrado)
  {
     Palabra p;
     p.palabra=palabra;
     p.n=1;
     palabras.push_back(p);
  }
}

void procesarLinea(string linea, vector<Palabra> &palabras)
{
  string pal;
  for (int i=0; i<linea.length(); i++)
  {
    if (esLetra(linea[i]))
    {
      pal+=linea[i];
    }
    else {
       if (pal!="") 
       {
         anyadirPalabra(convertirMin(pal),palabras);
         pal="";
       }
    }
  }
  // Ultima palabra
  if (pal!="")
    anyadirPalabra(convertirMin(pal),palabras);
}

bool leerFichero(char nomfichero[], vector<Palabra> &palabras)
{
  bool ok=true;
  string linea;
  ifstream f(nomfichero);
  
  if (f.is_open())
  {
    getline(f,linea);
    while (!f.eof())
    {
      procesarLinea(linea,palabras);    
      getline(f,linea);
    }
  }
  else ok=false;

  return ok;
}

void imprimir(const vector<Palabra> &palabras)
{
  for (int i=0; i<palabras.size(); i++)
    cout << palabras[i].palabra << ": " << palabras[i].n << endl;
}

int main(int argc, char *argv[])
{
  vector<Palabra> palabras;

  if (argc!=2)
    cout << "Sintaxis: " << argv[0] << " <fichero.txt>" << endl;
  else
  {
    if (leerFichero(argv[1],palabras))
      imprimir(palabras); 
  }
}
