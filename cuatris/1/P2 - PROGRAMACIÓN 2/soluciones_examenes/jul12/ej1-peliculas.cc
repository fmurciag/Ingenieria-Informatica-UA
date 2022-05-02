#include <iostream>
#include <fstream>
#include <cstdlib> // Para atoi
#include <vector>

using namespace std;

typedef struct
{
  string title;
  string director;
  int year;
} Pelicula;

string getCampo(ifstream &f, string campo)
{
   string output, s;

   getline(f,s); // Se puede hacer buscando y eliminando el campo o borrando los n primeros caracteres con algun metodo de string
   
   if (s.length()>=campo.length()) // No es necesario ponerlo asumiendo sintaxis correcta
   {
     for (int i=campo.length(); i<s.length(); i++)
       output+=s[i];
   }
   return output;
}

void imprimirPelicula(Pelicula a)
{
  cout << a.year << ", " <<  a.title << ", " << a.director << endl;
}


void imprimirListado(vector<Pelicula> listado)
{
  for (int i=0; i<listado.size(); i++)
     imprimirPelicula(listado[i]);
}

void insertarOrdenado(vector<Pelicula> &listado, Pelicula a)
{
      int pos;
      bool esmenor=true;

      for (pos=0; pos<listado.size() && a.year<listado[pos].year; pos++);
      
      // Añadimos una nueva película al final para ampliar el tamaño del vector
      listado.push_back(a);
      
      for (int i=listado.size()-2; i>=pos; i--)
         listado[i+1]=listado[i];

      listado[pos]=a;
}

bool leerFichero(char *nombre, vector<Pelicula> &listado)
{
    bool ok;

    ifstream f(nombre);
    string s;
    
    if (ok=f.is_open())
    {
      Pelicula a;

      a.title=getCampo(f,"title: ");
      while (!f.eof())
      {
          a.director=getCampo(f,"director: ");   
          a.year=atoi(getCampo(f,"year: ").c_str());

          insertarOrdenado(listado,a);
          
          a.title=getCampo(f,"title: ");
      }    
    }
    else ok=false;
  
    return ok;
}

int main(int argc, char* argv[])
{
  vector<Pelicula> listado;
  
  if (argc==2)
  {
    if (leerFichero(argv[1],listado))
      imprimirListado(listado);
  }
  else cout << "Error de parametros. Sintaxis: " << argv[0] << " <fichero.txt>" << endl;  
}
