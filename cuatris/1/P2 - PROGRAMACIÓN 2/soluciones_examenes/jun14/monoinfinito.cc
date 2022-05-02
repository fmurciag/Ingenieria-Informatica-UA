#include <iostream>
#include <fstream>
#include <cstdlib>
#include <vector>

using namespace std;

const int kMAX=10000;
const char kDICT[]="diccionario.txt";
const string kERRSINTAXIS="Error de sintaxis";
const string kERRFICH="Error de apertura del fichero";
const string kLONG="- Longitud ";

string generarPalabra(int tam)
{
  string salida;
  for (int i=0; i<tam; i++)
      salida+=(rand()%26)+'a';
  return salida;
}

bool estaEnDiccionario(string palabra, const vector<string> &diccionario)
{
   bool encontrado=false;
   
   for (int i=0; i<diccionario.size() && !encontrado; i++)
   {
    if (palabra==diccionario[i])
     encontrado=true;
   }
   return encontrado;
}

void generarPalabras(int n1, int n2, const vector<string> &diccionario)
{
   for (int tam=n1; tam<=n2; tam++)
   {
     cout << kLONG << tam << ":" << endl;
     for (int i=0; i<kMAX; i++)
     {
       string palabra=generarPalabra(tam);
       if (estaEnDiccionario(palabra,diccionario))
         cout << palabra << endl;
     }
   } 
}

vector<string> getDiccionario(int n1, int n2)
{
   vector<string> diccionario;
   ifstream fi(kDICT);
   bool encontrado=false;
   
   if (fi.is_open())
   {   
     string palabra;
     
     getline(fi,palabra);  
     while (!fi.eof())
     {
       if (palabra.length()>=n1 && palabra.length()<=n2)
         diccionario.push_back(palabra);
   
       getline(fi,palabra);
     }
     fi.close();
   }
   else cout << kERRFICH << kDICT << endl;    
  
   return diccionario;
}

int main(int argc, char *argv[])
{
  if (argc==3)
  {
    int n1=atoi(argv[1]);
    int n2=atoi(argv[2]);
    
    if (n1>=1 && n1<=10 && n2>=1 && n2<=10 && n1<=n2) // Se podia haber hecho tambien una funcion para procesar los argumentos
    {
      vector<string> diccionario=getDiccionario(n1,n2);
      if (diccionario.size()!=0)
       generarPalabras(n1,n2,diccionario);
    }
    else cout << kERRSINTAXIS << endl;  
  }
  else cout << kERRSINTAXIS << endl;  

  return 0;
}
