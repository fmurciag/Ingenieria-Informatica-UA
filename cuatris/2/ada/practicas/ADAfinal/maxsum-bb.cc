//Francisco Juaquin Murcia Gomez 48734281H
#include<iostream>
#include<fstream>
#include <vector>
#include<algorithm>
#include <queue> 
#include <chrono>
#include <tuple>
#include <numeric>

using namespace std;
int VIVOS=0;//nodos a˜nadidos a la lista de nodos vivos
int EXPANSIONES=0;//nodos que han sido expandidos
int DESCARTADOs_FAC=0;//nodos descartados por no ser factibles
int PROMETEDORES=0;//nodos que fueron prometedores pero que finalmente se descartaron
int DESCARTADOS_PRO=0;//e nodos descartados por no ser prometedores
int COMPLETADOS=0;//nodos completados
int ACTUALIZADO_NODO_COMPLETO=0;//veces que la mejor soluci´on hasta el momento se ha actualizado a partir de un nodo completado
int ACTUALIZADO_PESIMISTA=0;//veces que la mejor soluci´on hasta el momento se ha actualizado a partir de la cota pesimista de un nodo sin completar



int optimista(  vector<int> &v,int k, int T ){
    

    int acc_v = 0;
    for( unsigned i=k;i<v.size();i++) {
        if( v[i] > T ) {
            acc_v +=T;
            break;
        }
        acc_v += v[i];
        T -= v[i];
    }
    return acc_v;
}




int pesimista(  vector<int> &v,int k, int T) {
    

    int acc_v = 0;

    for( unsigned i=k;i<v.size();i++ ) {

        if( v[i] < T ) {
            acc_v += v[i];
            T -= v[i];
        }
    }

    return acc_v;
}


double podador(  vector<int> &v,  int T ) {
    typedef vector<int> Sol;
    // opt_bound, value, weight, x, k
    typedef tuple< int, int,  Sol, int > Node;
    priority_queue< Node > pq; // Remind: priority queue is a max-heap

    int best_val = pesimista( v,  0, T);
    int opt_bound = optimista(v,  0, T);

    pq.emplace( opt_bound, 0,  Sol(v.size()), 0 );


    while( !pq.empty() ) {

        int value;
        Sol x;
        unsigned k;
        tie(ignore,value,x, k) = pq.top(); 
        pq.pop();
        if(value>=best_val)PROMETEDORES++;//superando la cota optimosta

        if(T==value) continue;//poda

        if(value<=optimista( v,  k+1, T - value))PROMETEDORES++;

        if( k == v.size() || best_val>=T ) { // base case
            best_val = max( best_val, value ) ;
            if( k == v.size()){
                if(value>=best_val)ACTUALIZADO_NODO_COMPLETO++;
                COMPLETADOS++;
            }else{
                if(opt_bound==value)ACTUALIZADO_PESIMISTA++;
            }
            
            continue;
        }
        EXPANSIONES++;
        
        for (unsigned j = 0; j < 2; j++ ) { // non base
            x[k] = j;

            int new_value = value + x[k] * v[k]; // updating value

            if( new_value <= T ) {
                int valor_mejor=best_val;
                best_val = max( best_val, new_value + pesimista( v,  k+1, T - new_value));
                if(best_val!=valor_mejor)ACTUALIZADO_PESIMISTA++;//si el maximo no es el mejor valor actual te lo actualiza en cota pesimista
                int opt_bound = new_value + optimista( v,  k+1, T - new_value);
                if( opt_bound > best_val) {// is promising
                    VIVOS++;
                    pq.emplace( opt_bound, new_value,  x, k+1 );
                }else{
                    DESCARTADOS_PRO++;
                }
            }else{
                DESCARTADOs_FAC++;
            }
        }


    }
    return best_val;
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
    //ordenamos
    vector<int>vSorted(v.size());
    for(unsigned i=0;i<v.size();i++){
        vSorted[i]=i;
    }
    sort(vSorted.begin(),vSorted.end(),[&v] (size_t x,size_t y){
        return v[x]>v[y];
    });
    vector<int>valores(vSorted.size());
    for(unsigned i=0;i<vSorted.size();i++){
        valores[i]=v[vSorted[i]];
    }


    start = clock();
    int k=podador(valores,T );
    end=clock();
    cout<<k<<endl;
    cout<<EXPANSIONES<<" "<<VIVOS<<" "<<DESCARTADOs_FAC<<" "<<DESCARTADOS_PRO<<" "<<PROMETEDORES<<" "<<COMPLETADOS<<" "<<ACTUALIZADO_NODO_COMPLETO<<" "<<ACTUALIZADO_PESIMISTA<<endl;
    cout<<1000.0*(end-start)/CLOCKS_PER_SEC<<endl;
    
    
    

    
    
    return 0;
}