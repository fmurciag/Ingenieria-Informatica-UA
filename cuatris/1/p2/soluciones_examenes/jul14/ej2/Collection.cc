#include "Collection.h"
#include <vector>

bool Collection::addAlbum(const Album& album)
{
  bool encontrado=findAlbum(album);

  if (encontrado)
      cout << "Ya existe un album igual" << endl;
  else albums.push_back(album);
  
  return (!encontrado);
}

bool Collection::findAlbum(const Album &album) const
{
   bool encontrado=false;
   for (int i=0; i<albums.size() && !encontrado; i++)
   {
     if (albums[i].isEqualTo(album))
      encontrado=true;
   }   
   return encontrado;
}

void Collection::findSong(const string &song) const // Si se encuentra en varios albumes, se muestran todos
{
  for (int i=0; i<albums.size(); i++)
  {
    if (albums[i].findSong(song))
     cout << "La cancion " << song << " esta en el album" << albums[i].getTitle() << " (" << albums[i].getArtist() << ")"  << endl;
  }
}

ostream& operator<<(ostream &os, const Collection &c)
{
  os << "------" <<  c.name << "------" << endl;
  for (int i=0; i<c.albums.size(); i++)
    os << c.albums[i] << endl; 
  return os;
}
