
#include <iostream>

using namespace std;

#include "Movie.h"

Movie::Movie(string title,Genre genre,int score)
{
  if (score < -1 || score > 5) throw 1;
  
  this->title = title;
  this->genre = genre;
  this->score = score;
}

string Movie::genreToString(Genre genre)
{
  string s="";

  switch (genre) {
    case Action: s="Action";
      break;
    case SciFi: s="SciFi";
      break;
    case Drama: s="Drama";
      break;
    case Comedy: s="Comedy";
      break;
  }
  return s;
}

ostream& operator<<(ostream &os,const Movie &movie)
{
  os << movie.title << " (" << movie.score << ") " 
     << Movie::genreToString(movie.genre) << endl;
     
  return os;
}

