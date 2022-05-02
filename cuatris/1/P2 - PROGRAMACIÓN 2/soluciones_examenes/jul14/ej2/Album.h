#ifndef _ALBUM_H_
#define _ALBUM_H_

#include <iostream>
#include <vector>

using namespace std;

class Album
{
  friend ostream& operator<<(ostream &os, const Album &album);

  private:
    string artist;
    string title;
    vector<string> songs;
    
    string getCampo(string linea, int &pos) const;

  public:
    Album(string artist, string title, string songsLine); // Line in format name,name,name,name
    bool findSong(string song) const;
    bool isEqualTo(const Album &album) const { return (title==album.title && artist==album.artist); }
    string getTitle() const { return title; }
    string getArtist() const { return artist; }
    void setTitle(string title) { this->title=title; }
    void setArtist(string artist) { this->artist=artist; }
};

#endif
