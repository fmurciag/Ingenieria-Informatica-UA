

#include <iostream>
#include <fstream>
#include <vector>
#include <cstdlib>

using namespace std;

typedef struct {

  char titulo[50];
  char url[50];
  int longitud;

} Documento;

void obtenerDocumento(ifstream &fb,int ndoc,Documento &doc)
{
  fb.seekg(ndoc*sizeof(doc));
  fb.read((char *)&doc,sizeof(doc));
}

void buscaDocumentos(string linea,unsigned i)
{
  vector<Documento> vdocs;
  vector<int> vapa;
  string numdoc,numapa;
  int ndoc,napa;
  Documento doc;
  ifstream fb;

  fb.open("documents.bin",ios::in|ios::binary);
  if (fb.is_open())
  {
   do
   {
    numdoc="";
    while (i<linea.length() && linea[i] != ':')
    {
      numdoc += linea[i];
      i++;
    }
    
    ndoc = atoi(numdoc.c_str());
    i++;
    
    numapa="";
    while (i<linea.length() && linea[i] != '|')
    {
      numapa += linea[i];
      i++;
    }
    
    napa = atoi(numapa.c_str());
    i++;

    cout << "ndoc=" << ndoc << ", napa=" << napa << endl;
    
    obtenerDocumento(fb,ndoc,doc);
    
    cout << "doc.titulo=" << doc.titulo << endl;
    
    vdocs.push_back(doc);
    vapa.push_back(napa);
    
   } while (i<linea.length());
   
   float maxFreq=0;
   int docMaxFreq=-1;
   for (unsigned j=0;j<vdocs.size();j++) 
   {
     float freq=((float)vapa[j])/vdocs[j].longitud;

     cout << "freq=" << freq << " maxFreq=" << maxFreq << endl;     
     if (freq > maxFreq)
     {
       maxFreq = freq;
       docMaxFreq = j;
     }
   }
   
   if (docMaxFreq != -1)
   {
     cout << vdocs[docMaxFreq].titulo << "(" 
          << vdocs[docMaxFreq].url << ") frecuencia=" << maxFreq << endl; 
   }
  }
  else
     cout << "Error, no puedo abrir 'documentos.bin'" << endl;
}

bool buscaPalabra(string linea,string palabra)
{
  bool found=false;
  
  string pallinea="";
  unsigned i=0;
  
  while (i<linea.length() && linea[i]!='|')
  {
    pallinea += linea[i];
    i++;
  }
  i++;
  
  if (pallinea == palabra)
  {
    found = true;
    buscaDocumentos(linea,i);  
  }
  return found;
}


int main()
{
  string palabra,linea;
  ifstream indice;

  cout << "Introduce la palabra: " ; getline(cin,palabra);
  
  indice.open("index.txt",ios::in);
  if (indice.is_open())
  {
    bool encontrada=false;
  
    while (!encontrada && getline(indice,linea))
    {
       encontrada = buscaPalabra(linea,palabra);   
    }
    
    indice.close();
    
    if (!encontrada)
       cout << "Error, la palabra '"<< palabra <<"'no se ha encontrado" << endl;
  }
  else
     cout << "Error, no puedo abrir 'indice.txt'" << endl;
}
