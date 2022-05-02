#include <iostream>
#include <fstream>
#include <vector>

using namespace std;

void extraerDatos(string linea, string fichero, vector<string> &datos, char separador)
{
  int pos;
  string resultado;
  bool enEntidad=false;
  
  for (int i=0; i<linea.length(); i++)
  {
    if (linea[i]==separador)
    {
       if (!enEntidad) {
          enEntidad=true;
       }
       else {
        enEntidad=false;
        datos.push_back(resultado);
        resultado="";
       }
    }
    else if (enEntidad)
        resultado+=linea[i];
  }
}

void extraerEntidades(char filename[], vector<string> &fechas, 
vector<string> &personas, vector<string> &empresas, vector<string> &lugares)
{
   ifstream f(filename);
   if (f.is_open())
   {
       string linea;
       getline(f,linea); // Tambien se puede leer caracter a caracter
       while (!f.eof())
       {
         extraerDatos(linea,filename,fechas,'@');
         extraerDatos(linea,filename,personas,'$');
         extraerDatos(linea,filename,empresas,'#');
         extraerDatos(linea,filename,lugares,'*');

         getline(f,linea);
       }
       f.close();   
   }
   else cout << "Error de apertura del fichero " << filename << endl;   
}

void imprimirDatos(vector<string> fechas, vector<string> personas, vector<string> empresas, vector<string> lugares)
{
  cout << "Fechas: ";
  for (int i=0; i<fechas.size(); i++)
    cout << fechas[i] << " | ";
  cout << endl << "Personas: ";
  for (int i=0; i<personas.size(); i++)
    cout << personas[i] << " | ";
  cout << endl << "Empresas: ";
  for (int i=0; i<empresas.size(); i++)
    cout << empresas[i] << " | ";
  cout << endl << "Lugares: ";
  for (int i=0; i<lugares.size(); i++)
    cout << lugares[i] << " | ";
    
  cout << endl;
}

int main(int argc, char *argv[])
{
  vector<string> fechas, personas, empresas, lugares;

  if (argc!=1)
  {  
    for (int i=1; i<argc; i++)
      extraerEntidades(argv[i],fechas,personas,empresas,lugares);
    
    imprimirDatos(fechas,personas,empresas,lugares);
  }
  else cout << "Error: El programa necesita argumentos" << endl;

}
