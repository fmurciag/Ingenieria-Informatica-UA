//Francisco Juaquin Murcia Gomez 48734281H
#include<iostream>
#include<fstream>
#include <vector>
#include<algorithm>
#include <queue> 
#include <chrono>

using namespace std;

int EXPANDED=0,DISCARTED=0;//representan Expanded nodes y Discarded nodes



int add_rest(vector<int> v, size_t k){
 int r = 0;
 for( size_t i = k; i < v.size(); i++){
    r += v[i];
 }
 return r;
 }


void backtracking( vector<int> v, int T, size_t k, vector<int> &x, int acc_v, int &best_v,vector<int>&SELECION){
    if( k == x.size() || best_v>=T ) { 
        best_v = max(best_v,acc_v);
        if(k == x.size()){
            SELECION=x;
        }
        
        return ;
    }
    for (int j = 0; j < 2; j++ ) { 
        x[k]=j;
        int present_v = acc_v + x[k] * v[k]; 
        if( present_v <= T && present_v + add_rest(v, k+1) > best_v ){
            EXPANDED++;
            backtracking(v,T, k+1, x, present_v, best_v,SELECION);
        }else
        {
            DISCARTED++;
        }
        
             
    }
}

int backtracking( vector<int> v, int T,vector<int>&SELECION){
    vector<int>x (v.size());
    
    int best_v=-1;
    backtracking( v, T, 0, x, 0, best_v,SELECION);
    return best_v;
}
/*
int sumaVector(vector<int>v){
    int suma=0;
    for(unsigned i=0;i<v.size();i++){
        suma+=v[i];
    }
    return suma;
}
*/


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
   
    clock_t start, end;
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

   
    sort(v.begin(), v.end()); //ordenar el vector valores

    
    //mostrado por pantalla
    vector<int>SELECION;//vector que contiene los elementos seleccionados
    int backtrack=0;
    start = clock();
    backtrack=backtracking(v,T,SELECION);
    end=clock();
    cout<<"Backtracking: "<<backtrack<<endl;
    cout<<"Selection: ";
    for(unsigned i=0;i<SELECION.size();i++){//mostramos los valores seleccionados
        if(SELECION[i]==1){
            cout<<v[i]<<" ";
        } 
    }
    cout<<endl;
    cout<<"Expanded nodes: "<<EXPANDED<<endl;
    cout<<"Discarded nodes: "<<DISCARTED<<endl;
    cout<<"CPU time (ms): ";
    cout<<1000.0*(end-start)/CLOCKS_PER_SEC<<endl;

    
    
    return 0;
}