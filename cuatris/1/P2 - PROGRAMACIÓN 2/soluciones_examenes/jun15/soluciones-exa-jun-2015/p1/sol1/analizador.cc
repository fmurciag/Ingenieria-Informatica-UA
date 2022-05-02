#include <iostream>
#include <vector>
#include <fstream>
#include <sstream>

using namespace std;

typedef struct
{
	string cuenta;
	int total;
	int encontrados;
} Politico;

void leerPoliticos(ifstream &f, vector<Politico> &polis)
{
	string cuenta;
	getline(f,cuenta);
	while(!f.eof())
	{
		Politico poli;
		poli.cuenta = cuenta;
		poli.total = 0;
		poli.encontrados = 0;
		polis.push_back(poli);
		getline(f,cuenta);
	}
}

bool buscar(const string &tweet, const string &s)
{
	istringstream iss(tweet);
	string cadena;
	bool encontrado = false;
	while( !encontrado && (iss >> cadena))
	{
		if(cadena == s)
			encontrado = true;
	}
	return encontrado;
} 

void procesarTweets(ifstream &f, vector<Politico> &polis, const string &s)
{
	string tweet;
	getline(f,tweet);
	while(!f.eof())
	{
		for(unsigned i = 0; i < polis.size(); i++)
		{
			if(buscar(tweet,polis[i].cuenta))
			{
				polis[i].total++;
				if(buscar(tweet,s))
					polis[i].encontrados++;
			}
		}
		getline(f,tweet);
	}
}

void emitirSalida(const vector<Politico> &polis)
{
	for(unsigned i = 0; i < polis.size(); i++)
	{
		int ratio = (polis[i].total == 0)? 0 
					: 100*polis[i].encontrados/polis[i].total;
		cout << polis[i].cuenta << " (Total: " << polis[i].total
			<< "; Encontrados: " << polis[i].encontrados
			<< "; Ratio: " << ratio << "%)" << endl;
	}	
}



int main(int argc, char *argv[])
{
	if(argc != 4)
	{
		cerr << "Usage: " << argv[0] << "<tweets> <politicos> <cadena>\n";
		return 1;	
	}
	else
	{
		ifstream f1(argv[1]), f2(argv[2]);
		if( f1.is_open() && f2.is_open() )
		{
			vector<Politico> politicos;
			leerPoliticos(f2,politicos);
			f2.close();
			procesarTweets(f1,politicos,string(argv[3]));
			f1.close();
			emitirSalida(politicos);
		}
		return 0;
	}
}
