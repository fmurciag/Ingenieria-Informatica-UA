#ifndef _PLAYLIST_H_
#define _PLAYLIST_H_

#include <iostream>
#include <vector>
#include "Cancion.h"

using namespace std;

class Playlist
{
  friend ostream& operator<<(ostream &os, const Playlist &p);

  private:
    string nombre;
    vector<Cancion> canciones;
  
    bool buscarCancion(const Cancion &c);

  public:
    Playlist(string nombre) { this->nombre=nombre; }
    float getPuntuacionMedia() const;
    vector<string> getArtistas() const;
    bool anyadirCancion(const Cancion &c);
};

#endif
