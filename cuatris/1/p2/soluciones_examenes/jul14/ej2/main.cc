#include "Collection.h"

int main()
{
  Album album("Weezer","Hurley","Memories,Ruling Me,Trainwrecks,Unspoken");
  Album album2("Camela","Lo mejor de Camela","Cuando zarpa el amor,Nunca debi enamorarme,Nadie como tu");

  Collection c("My collection");
  c.addAlbum(album);
  c.addAlbum(album2);
  c.addAlbum(album2); // Ya estaba, no se anyade y se muestra un error

  c.findSong("Trainwrecks"); // Busca esta cancion en todos los albumes e imprime sus datos
  
  cout << c << endl; // Mostramos la coleccion completa
}
