
#include <iostream>
#include <fstream>
#include <cstdlib>
#include <vector>

using namespace std;


typedef struct {
  int cuatrimestre;
  char codAsignatura[6];
  int franjaInicio;
  int franjaFin;
} GRUPO;

const char *nfbin="asignaturas.bin";
const char *nftxt="horario.txt";


void obtenerDatosGrupo(int ng,ifstream &f,vector<GRUPO> &vg)
{
  GRUPO g;

  f.seekg(ng*sizeof(GRUPO));
  f.read((char *)&g,sizeof(g));
  vg.push_back(g);
}

bool haySolapamiento(const GRUPO &g1,const GRUPO &g2)
{
  bool hay=true;
  if (g1.cuatrimestre != g2.cuatrimestre ||
     (g1.franjaInicio > g2.franjaFin || g1.franjaFin < g2.franjaInicio))
     hay=false;

  return hay;
}

void procesarLinea(string s,ifstream &f)
{
  string nom,num;
  vector<GRUPO> vg;
  vector<int> vn;
  unsigned i=0;
  bool solapa=false;
  
  while (i<s.length() && s[i] != ':')
  {
    nom += s[i];
    i++;
  }
  i++; // saltar ':'
  
  while (i<s.length())
  {
    num="";
    while (i<s.length() && s[i]!='|')
    {
      num += s[i];
      i++;
    }
    i++; // saltar '|'
    
    int numgr = atoi(num.c_str());
    obtenerDatosGrupo(numgr,f,vg);
    vn.push_back(numgr);
    
//    cout << "grupo: " << numgr << endl;
  }
  
  
  for (unsigned i=0;i<vg.size();i++)
  {
    for (unsigned j=i+1;j<vg.size();j++)
      if (haySolapamiento(vg[i],vg[j]))
      {
        solapa=true;
        cout << "Solapamiento: " << nom << endl;
        cout << vn[i] << " " << vg[i].cuatrimestre << " " << vg[i].codAsignatura << " " 
                             << vg[i].franjaInicio << "-" << vg[i].franjaFin << endl;
                             
        cout << vn[j] << " " << vg[j].cuatrimestre << " " << vg[j].codAsignatura << " " 
                             << vg[j].franjaInicio << "-" << vg[j].franjaFin << endl;
      }
  }
  
  if (!solapa)
    cout << nom << ": ok" << endl;
}


int main()
{
 ifstream fb,ft;
 
 fb.open(nfbin,ios::in|ios::binary);
 if (fb.is_open())
 {
   ft.open(nftxt,ios::in);
   if (ft.is_open())
   {
      string linea;
      
      while (getline(ft,linea))
      {
        procesarLinea(linea,fb);
      }
   }
   else
     cout << "No puedo abrir " << nftxt << endl;
 
   fb.close();
 }
 else
     cout << "No puedo abrir " << nfbin << endl;

 return 0;
}
