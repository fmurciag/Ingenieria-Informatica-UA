#include "Ciudad.h"

Ciudad::Ciudad(const Ciudad& c) 
{ 
      nombre=c.nombre; 
      partidos=c.partidos;
}

void Ciudad::anyadirVoto(string nompartido)
{
      unsigned int i=0;
      // Buscamos el partido en el vector
      while (i<partidos.size() &&  partidos[i].getNombre() != nompartido)
         i++;
      // Si ya existe, sumamos el voto
      if (i<partidos.size())
        partidos[i].anyadirVoto();
      // Si no existe, comprobamos que cabe y lo añadimos
      else if ((int)partidos.size()<Ciudad::maxpartidos)
      {
        Partido p(nompartido);
        p.anyadirVoto();
        
        partidos.push_back(p);
      } 
      else cout << "Error: se sobrepasa el número de partidos permitidos" << endl; 
}

void Ciudad::leerVotos(string nombrefichero)
{
       ifstream f(nombrefichero.c_str());
       if (f.is_open())
       {
         string s;
         getline(f,s);
         while (!f.eof())
         {
           anyadirVoto(s);
           getline(f,s);
         }
         f.close();
       }
       else cout << "Error de apertura del fichero" << endl;
}

void Ciudad::imprimirResultados() const {
      cout << nombre << endl;
      cout << "--------" << endl;
      for (unsigned int i=0;i<partidos.size();i++)
       cout << partidos[i].getNombre() << ":" << partidos[i].getNumvotos() << endl;       
}

string Ciudad::getGanador() const {
      int maxvotos=0;
      int index=0;

      for (unsigned int i=0;i<partidos.size();i++)
      {
        if (partidos[i].getNumvotos() > maxvotos)
        {
          index=i;
          maxvotos=partidos[i].getNumvotos();
        }
      }
      return partidos[index].getNombre();
}
