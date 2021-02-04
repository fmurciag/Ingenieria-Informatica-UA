#include <iostream>
#include <fstream>
#include <vector>

using namespace std;

vector<string> getLines(const char *filename)
{
  ifstream f(filename);
  vector<string> output;
  
  if (f.is_open())
  {
    string line;
    while (getline(f,line))
      output.push_back(line);

    f.close();
  }
  else cout << "Error opening file" << endl;
 
  return output;
}

void analyze(const vector<string> &tweets, const vector<string> &politicians, const string &keyword)
{
  for (unsigned i=0; i<politicians.size(); i++)
  {	
    int total=0, found=0;    
    for (unsigned j=0; j<tweets.size(); j++)
    {
      if (tweets[j].find(politicians[i])!=string::npos)
      {
        total++;
        if (tweets[i].find(keyword)!=string::npos)
          found++;
      }
    }
    float ratio= found==0? 0 : 100*found/(float)total;
    
    cout << politicians[i] << " (Total: " << total << "; Encontrados: " << found << "; Ratio:" << ratio << "\%)" << endl;
  }
}

int main(int argc, char *argv[])
{
  if (argc==4)
  {
    vector<string> tweets=getLines(argv[1]);
    if (tweets.size()!=0)
    {
      vector<string> politicians=getLines(argv[2]);

      if (politicians.size()!=0) 
        analyze(tweets,politicians,argv[3]);
    }
  }
  else cout << "Syntax error" << endl;
}
