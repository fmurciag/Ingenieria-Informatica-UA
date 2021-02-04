#ifndef _COLLECTION_H_
#define _COLLECTION_H_

#include "Album.h"

class Collection
{
  friend ostream& operator<<(ostream &os, const Collection &c);

  private:
    string name;
    vector<Album> albums;
    bool findAlbum(const Album &album) const;
    
  public:
    Collection(string name="") { this->name=name; }
    bool addAlbum(const Album& album);
    void findSong (const string &song) const;
};

#endif
