#include "Playlist.h"

vector<string> Playlist::getArtistas() const
{
  vector<string> artistas;
  for (int i=0; i<canciones.size(); i++)
  {
    string artista=canciones[i].getArtista();
    // buscar artista y anyadir el nuevo si no estaba
    bool encontrado=false;
    for (int j=0; j<artistas.size() && !encontrado; j++)    
    {
      if (artistas[j]==artista)
        encontrado=true;
    }
    if (!encontrado) artistas.push_back(artista);
  }
  return artistas;
}

float Playlist::getPuntuacionMedia() const
{
  float punt=0;
  int n=0;
  for (int i=0; i<canciones.size(); i++)
  {
    if (canciones[i].getPuntuacion()!=Cancion::SP)
    {
      punt+=canciones[i].getPuntuacion();
      n++;
    }
  }
  return (punt/n);
}

bool Playlist::buscarCancion(const Cancion &c)
{
  bool encontrado=false;
  for (int i=0; i<canciones.size() && !encontrado; i++)
  {
    if (canciones[i].getNombre()==c.getNombre() && canciones[i].getArtista() == c.getArtista())
    {
      encontrado=true;
    }
  }  
  return encontrado;
}

bool Playlist::anyadirCancion(const Cancion &c)
{
  bool ok=true;

  if (!buscarCancion(c))
    canciones.push_back(c);

  else {
    cout << "Error, la cancion ya estaba en la lista" << endl;
    ok=false;
  }
  return ok;
}

ostream& operator<<(ostream &os, const Playlist &p)
{
  os << "Titulo: " << p.nombre << endl;
  os << "Artistas: ";
  for (int i=0; i<p.getArtistas().size(); i++)
  {
   os << p.getArtistas()[i];
   if (i!=p.getArtistas().size()-1) os << ",";
  }
  os << endl << "-----------" << endl;
  for (int i=0; i<p.canciones.size(); i++)
  {
    Cancion c=p.canciones[i];
    cout << c;
  }
  os << "-----------" << endl;
  os << "Puntuacion media= " << p.getPuntuacionMedia();
  
  return os;
}
