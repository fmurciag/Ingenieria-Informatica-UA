
#ifndef _Movie_
#define _Movie_

#include <iostream>

enum Genre { Action, SciFi, Drama, Comedy };

class Movie {

  private:
   
      string title;
      int score;
      Genre genre;
      
  public:
  
      static const int NS=-1;
      
      Movie(string title,Genre genre,int score=NS);
      string getTitle() const { return title; }
      int getScore() const    { return score; }
      Genre getGenre() const  { return genre; }
      
      static string genreToString(Genre genre);
      
      friend ostream& operator<<(ostream &os,const Movie &movie);


};


#endif
