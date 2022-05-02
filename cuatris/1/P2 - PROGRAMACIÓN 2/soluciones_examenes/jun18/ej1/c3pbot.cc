#include <iostream>
#include <cctype>
#include <vector>
#include <fstream>
#include <sstream>

using namespace std;

vector<string> parseQuestion(string question) 
{
    for (unsigned i=0; i<question.length(); i++) {
        if (isalpha(question[i])) {
            question[i]=tolower(question[i]);
        }
        else {
            question[i]=' ';
        }
    }    
    vector<string> words;
    stringstream ss(question);

    string word;
    while (ss>>word)
        words.push_back(word);

    return words;
}

unsigned match(vector<string> query, vector<string> words) 
{
    unsigned common=0;
    for (unsigned i=0; i<query.size(); i++) {
        bool found=false;
        for (unsigned j=0; j<words.size() && !found; j++) {
            if (query[i]==words[j]) {
                found=true;
                common+=query[i].length();
            }
        }
    }
    return common;
}

unsigned totalQueryChars(vector<string> query) 
{
    unsigned totalCharQuery=0;
    
    for (unsigned i=0; i<query.size(); i++)
        totalCharQuery+=query[i].length();

    return totalCharQuery;
}


int main() {

    cout << "Hola, soy C3PBot. Escribe tu pregunta a continuación:" << endl;
    string question;
    getline(cin,question);    

    vector<string> query = parseQuestion(question);
    unsigned totalCharQuery = totalQueryChars(query);
    
    ifstream fi("respuestas.txt");
    if (fi.is_open()) {
        
        int maxScore=-1;
        string bestAnswer, line, tmp, answer;

        while (getline(fi, line)) 
        {
            stringstream ss(line);
            getline(ss,tmp,'|');
            getline(ss,answer);

            vector<string> words = parseQuestion(tmp);
            
            int score = match(query,words);
            
            if (score>maxScore) {
                maxScore=score;
                bestAnswer=answer;
            }
        }        
        fi.close();
        
        cout << bestAnswer << "(" << maxScore*100.0/totalCharQuery << "%)" << endl;
    } 
    else cout << "Error opening file" << endl;    
}
