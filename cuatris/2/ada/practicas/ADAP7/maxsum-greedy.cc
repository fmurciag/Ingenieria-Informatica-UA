//Francisco Juaquin Murcia Gomez 48734281H
#include<iostream>
#include<fstream>
#include <vector>
#include<algorithm>
using namespace std;


int greedy(vector<int>&SELECION,vector<int>&v, int T){
    vector<size_t> idx( v.size());
    for( size_t i = 0; i < idx.size(); i++) idx[i] = i;
    sort( idx.begin(), idx.end(), [&v]( size_t x, size_t y ){
        return v[x] > v[y];
    });
    int acc_v = 0.0;
    for( auto i : idx ) {
        if( v[i] < T ) {
            acc_v += v[i];
            T -= v[i];
            SELECION[i]=v[i];
            
        }
    }
    return acc_v;


}

int sumaVector(vector<int>v){
    int suma=0;
    for(unsigned i=0;i<v.size();i++){
        suma+=v[i];
    }
    return suma;
}



void error(int codigo, string fallo){//contiene los mensajes de error
    /*
    codigo 1: fallo en el fichero
    codigo 2: argumentos erroneos
    */
    switch (codigo)
    {
    case 1:
        cout<<"ERROR: can’t open file: "<<fallo<<"."<<endl<<"Usage: " << endl<< "maxsum-greedy -f file" << endl;
        exit(-1);
        break;
    case 2:
        cout<<"ERROR: unknown option "<<fallo<<"."<<endl<<"Usage: " << endl<< "maxsum-greedy -f file" << endl;
        exit(-1);
        break;
    }
}
int main(int argc,char*argv[]){
   

    vector<int>v;//vector valores
    int n=0,T=0;
    ifstream fichero;
    string nombre_fichero="",f="-f";//nombre fichero, y string de los args
    

    //leo y valido los argumentos
    for(int i=1;i<argc;i++){
        if(argv[i]==f){


            if(i+1==argc){
                error(1,"");
            }

            i++;
            nombre_fichero=argv[i];
            
                
            
        }else{
            error(2,argv[i]);
        }
    }

    //extracion de los datos del fichero
    fichero.open(nombre_fichero.c_str(),ios::in);//abrir el fichero
    if(fichero.is_open()){
        fichero>>n;
        fichero>>T;
        int a=0;
        while (fichero>>a){
            v.push_back(a);
        }
        fichero.close();
    }
    else{
        error(1,nombre_fichero);
       
    }
    vector<int>SELECION(n);//vector que contiene los elementos seleccionados
    //mostrado por pantalla
    cout<<"Greedy: "<<greedy(SELECION,v,T)<<endl;
    cout<<"Selection: ";
    for(unsigned i=0;i<v.size();i++){
        if(v[i]!=0)cout<<v[i]<<" ";
    }
    cout<<endl;
    cout<<"Selection value: "<<sumaVector(SELECION)<<endl;
    
    return 0;
}