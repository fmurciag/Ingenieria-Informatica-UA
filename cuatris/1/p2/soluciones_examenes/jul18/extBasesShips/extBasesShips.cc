
#include <fstream>
#include <iostream>
#include <cstdlib>

using namespace std;

struct RegBin {
  char name[15];
  int p;
  int e;
  double x;
  double y;
};

RegBin leerReg(ifstream &fb,int pos)
{
  RegBin rb;

  fb.seekg(pos*sizeof(rb));
  fb.read((char *)&rb,sizeof(rb));
  
  return rb;
}

void processLine(int nl,string l,ifstream &fb,ofstream &bases,ofstream &ships)
{
  string type="";
  unsigned i=0;
  
  while (i<l.length() && l[i]==' ') i++;  // blancos antes
  
  while (i<l.length() && l[i] != ':' && l[i] != ' ')
  {
    type += l[i];
    i++;
  }
  
  if (type != "base" && type != "ship")
    cout << "Error en linea " << nl << ": " << l << endl;
  else
  {
    string name="",num="";
    int pos;
    
    while (i<l.length() && l[i] != '"') i++;
    i++; // saltar primeras comillas
    while (i<l.length() && l[i] != '"')
    {
      name += l[i];
      i++;
    }
    
    while (i<l.length() && !isdigit(l[i])) i++; // buscar numero
    while (i<l.length() && isdigit(l[i]))
    {
      num += l[i];
      i++;
    }
    pos = atoi(num.c_str());
    
    RegBin rb=leerReg(fb,pos);
    
    if (name == rb.name)
    {
      if (type == "base")
        bases << "\"" << rb.name << "\", " << rb.p << ":" << rb.e << ", (" << rb.x << "," << rb.y << ")" << endl;
      else
        ships << "(" << rb.p << "," << rb.e << ") \"" << rb.name << "\"" << endl;
    }
    else
      cout << "Error en linea " << nl << ": nombre \"" << name << "\" no coincide con fichero binario (nombre=\"" << rb.name << "\")" << endl;
  }
}

int main(int argc,char *argv[])
{
  if (argc == 3)
  {
    ifstream fdr;
    
    fdr.open(argv[2],ios::in);
    if (fdr.is_open())
    {
      ifstream fb;
      
      fb.open(argv[1],ios::in|ios::binary);
      if (fb.is_open())
      {
         ofstream bases,ships;
      
         bases.open("bases.txt",ios::out);
         ships.open("ships.txt",ios::out);
         if (bases.is_open() && ships.is_open())
         {
           string l;
           int nl=1;
           
           while (getline(fdr,l))
           {
             processLine(nl,l,fb,bases,ships);
             nl++;
           }
           bases.close();
           ships.close();
         }
         else
           cout << "Error, no puedo abrir bases.txt o ships.txt" << endl;
         
         fb.close();
      }
      else
        cout << "Error, no puedo abrir " << argv[1] << endl;
    
      fdr.close();
    }
    else
      cout << "Error, no puedo abrir " << argv[2] << endl;
  }
  else
    cout << "Uso: " << argv[0] << " binaryFile.bin datosReales.txt" << endl;

  return 0;
}

