//Francisco Juaquin Murcia Gomez 48734281H
#include<iostream>
#include<fstream>
#include <vector>
#include<limits>
using namespace std;

const int SENTINEL = -1;
vector< vector< int >>TABLA;//tabla para mostrar, representa el almacen


int naive(const vector<int>& v, int n, int T){
    if(n==0)return 0;
    int s1= naive(v,n-1,T);
    int s2 = numeric_limits<int>::lowest();
    if(v[n-1]<=T){
        s2=v[n-1]+naive(v,n-1,T-v[n-1]);
    }
    return max( s1, s2 );

}

int memoizacion(vector< vector< int >> &almacen,const vector<int>& v, int n, int T){
    
    if(almacen[n][T] != SENTINEL ) return almacen[n][T];
    if( n == 0 ) return almacen[n][T] = 0;
    int s1= memoizacion(almacen,v,n-1,T);
    int s2 = numeric_limits<int>::lowest();
    if(v[n-1]<=T){
        s2=v[n-1]+memoizacion(almacen,v,n-1,T-v[n-1]);
    }
    return almacen[n][T] = max( s1, s2 ); 

}

int memoizacion(const vector<int>& v, int n, int T){
    vector< vector< int >> almacen( n+1, vector<int>( T+1, SENTINEL));
    return memoizacion(almacen,v,n,T);
}

int iterativeTabla(const vector<int>& v, int ultima_n, int ultima_T, bool tabla){//obtiene el valor para "Iterative (table)" y si hay que mostrar el almacen lo asigna a la variable global
    vector< vector< int >> almacen( ultima_n+1, vector<int>( ultima_T+1));

    for( int T = 0; T <= ultima_T; T++ ) almacen[0][T] = 0;
        for( int n = 1; n <= ultima_n; n++ )
            for( int T = 1; T <= ultima_T; T++ ) {
                int S1 = almacen[n-1][T];
                int S2 = numeric_limits<int>::lowest();
                if(T >= v[n-1])
                    S2 = v[n-1] + almacen[n-1][T-v[n-1]];
                almacen[n][T] = max( S1, S2 ); 
            }
    if(tabla){
        TABLA=almacen;
        return almacen[ultima_n][ultima_T];
    }else{
        return almacen[ultima_n][ultima_T];
    }        
    

}

int iterativeVector(const vector<int>& v, int ultima_n, int ultima_T){//obtiene el valor para "Iterative (vector)"
    vector<int> v0(ultima_T+1);
    vector<int> v1(ultima_T+1);
    for( int T = 0; T <= ultima_T; T++ ) v0[T] = 0; 

    for( int n = 1; n <= ultima_n; n++ ) {
        for( int T = 0; T <= ultima_T; T++ ){
            int S1 = v0[T];
            int S2 = numeric_limits<int>::lowest();
            if( T >= v[n-1] )
                S2 = v[n-1] + v0[T-v[n-1]];
            v1[T] = max( S1, S2 ); 

        }
        swap(v0,v1);
    }
    return v0[ultima_T];

}

void seleccion(const vector<int>& v, vector<bool>&solucion, vector<vector<bool>>traza){// genera el vector SOLUCION 
    int ultima_n = traza.size()-1;
    int T = traza[0].size()-1;
    for( int n = ultima_n; n > 0; n-- ) {
        if( traza[n][T] ) {
            solucion[n-1] = true;
            T -= v[n-1];

        }else{
            solucion[n-1] = false;

        }
    }

}

int valorSeleccion(const vector<int>& v, int ultima_n, int ultima_T,vector<vector<bool>>&traza){// obtiene el valor total para la parte "Selection value"
    vector< vector< int >> almacen( ultima_n+1, vector<int>(ultima_T+1));
    traza = vector<vector<bool>>( ultima_n+1, vector<bool>(ultima_T+1));

    for( int T = 0; T <= ultima_T; T++ ) {
        almacen[0][T] = 0;
        traza[0][T] = false; 
    }
    for( int n = 1; n <= ultima_n; n++ ){
        for( int T = 1; T <= ultima_T; T++ ) {
            double S1 = almacen[n-1][T];
            double S2 = numeric_limits<int>::lowest();
            if( T >= v[n-1] )
                S2 = v[n-1] + almacen[n-1][T-v[n-1]];
            almacen[n][T] = max( S1, S2 ); 
            traza[n][T] = S2 > S1;
            
        }
    }
    return almacen[ultima_n][ultima_T];

}

void filtrar( vector<bool>solucion,const vector<int>&v,int n){// filtra el vector de valores y muestra los valores que en la solucion estan a "true"
    for(unsigned i=0;i<solucion.size();i++){
        if(solucion[i]){
            cout<<v[i]<<" ";
        }
    }
}



void mostrarTabla(int T,int n){//muestra la tabla del "Iterative (table)"
    for(int i=0;i<=n;i++){
        for(int j=0;j<=T;j++){
            cout<<TABLA[i][j]<<" ";
        }
        cout<<endl;
    }
}

void error(int codigo, string fallo){//contiene los mensajes de error
    /*
    codigo 1: fallo en el fichero
    codigo 2: argumentos erroneos
    */
    switch (codigo)
    {
    case 1:
        cout<<"ERROR: can’t open file: "<<fallo<<"."<<endl<<"Usage: " << endl<< "maxsum [-t] [--ignore_naive] -f file" << endl;
        exit(-1);
        break;
    case 2:
        cout<<"ERROR: unknown option "<<fallo<<"."<<endl<<"Usage: " << endl<< "maxsum [-t] [--ignore_naive] -f file" << endl;
        exit(-1);
        break;
    }
}

int main(int argc,char*argv[]){
   

    vector<int>v;//vector valores
    int n=0,T=0;
    ifstream fichero;
    string nombre_fichero="",f="-f",t="-t",ignore="--ignore_naive";//nombre fichero, y string de los args
    bool navie=true, matriz=false;// activadores de navie y de "Selection value"

    //leo y valido los argumentos
    for(int i=1;i<argc;i++){
        if(argv[i]==f||argv[i]==t||argv[i]==ignore){

            if(argv[i]==f){//arg -f

                if(i+1==argc){
                    error(1,"");
                }

                i++;
                nombre_fichero=argv[i];
                if(argv[i]==t){
                    error(1,nombre_fichero);
                    
                }
                
            }else if(argv[i]==ignore){// arg -ignore_naive
                navie=false;
            }else if(argv[i]==t){//arg -t
                matriz=true;
            }
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
    vector<vector<bool>>TRAZA;
    vector<bool>SOLUCION(n);//vector que contiene que valores son validos y cuales no
    int sValue=valorSeleccion(v,n,T,TRAZA);
    //mostrado por pantalla
    if(navie)cout<< "Naive: "<<naive(v,n,T)<<endl;
    cout<<"Memoization: "<<memoizacion(v,n,T)<<endl;
    cout<<"Iterative (table): "<<iterativeTabla(v,n,T,matriz)<<endl;
    cout<<"Iterative (vector): "<<iterativeVector(v,n,T)<<endl;
    cout<<"Selection: ";
        seleccion(v,SOLUCION,TRAZA);
        filtrar(SOLUCION,v,n);
    cout<<endl;
    cout<<"Selection value: "<<sValue<<endl;
    if(matriz){
        cout<<"Iterative table:"<<endl;
        mostrarTabla(T,n);
    }
    return 0;
}