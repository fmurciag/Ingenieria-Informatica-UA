
#include <iostream>
#include <cstdlib>  // OJO

using namespace std;

#include "List.h"

List::List(string name)
{
  this->name = name;
}

bool List::searchMovie(string title) const
{
    bool found=false;
  
    for (unsigned j=0;j<movies.size() && !found;j++)
      if (movies[j].getTitle() == title) found=true;
     
    return found;
}


void List::addMovie(string desc,Genre genre)
{
  string movieName="",number="";
  unsigned int i=0;
  int score;
  
  while (i<desc.length() && desc[i] != ',')
  {
    movieName += desc[i];
    i++;
  }
  
  i++;
  while (i<desc.length() && desc[i] == ' ') i++; // saltar blancos
  
  while (i<desc.length()) {
    number += desc[i];
    i++;
  }
  
  score = atoi(number.c_str());
  
  try {
    Movie movie(movieName,genre,score);  // OJO, orden de las comprobaciones
      
    if (!searchMovie(movieName))
      movies.push_back(movie);
      
  } catch (...) {   // OJO, no hay que hacer nada en el catch
  }
}

float List::getMeanScore() const
{
  float mean=0.0;
  
  for (unsigned i=0;i<movies.size();i++) mean += movies[i].getScore();
  
  return mean/movies.size();
}


ostream& operator<<(ostream &os,const List &list)
{
  os << list.name << endl 
     << list.getMeanScore() << endl;

  os << list.movies.size() << endl;
  
  for (unsigned i=0;i<list.movies.size();i++)
     os << list.movies[i];
     
  return os;
}
