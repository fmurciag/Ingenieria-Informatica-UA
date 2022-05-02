#include "Album.h"

bool Album::findSong(string song) const
{
  bool encontrado=false;
  for (int i=0; i<songs.size() && !encontrado; i++)
  {
    if (songs[i]==song)
      encontrado=true;
  }
  return encontrado;
}

string Album::getCampo(string linea, int &pos) const
{
  string salida;
  
  while (pos<linea.length() && linea[pos]!=',')
    salida+=linea[pos++];

  pos++;
  return salida;
}


Album::Album(string artist, string title, string songsline)
{
  this->artist=artist;
  this->title=title;

  int i=0;  
  while (i<songsline.length())
  {
    string song=getCampo(songsline,i);
          
    if (!findSong(song))  
      songs.push_back(song);
    else 
      cout << "La cancion " << song << " ya estaba introducida" << endl;
  }
}

ostream& operator<<(ostream &os, const Album &album)
{
  os << album.title << " (" << album.artist << ") : ";
  for (int i=0; i<album.songs.size(); i++)
  {
    os << album.songs[i];
    if (i!=album.songs.size()-1)
      os << "-";
  }
  return os;
}

