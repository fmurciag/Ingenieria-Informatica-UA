
#ifndef _List_
#define _List_

#include <iostream>
#include <vector>

using namespace std;

#include "Movie.h"

class List {
  private:
  
     string name;
     vector<Movie> movies;
     
     bool searchMovie(string title) const;
     
  public:
  
    List(string name);
    void addMovie(string desc,Genre genre);
    float getMeanScore() const;
    
    
  friend ostream& operator<<(ostream& os,const List &list);
};

#endif
