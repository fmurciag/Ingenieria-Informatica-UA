#include <iostream>
#include <vector>
using namespace std;









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
    
    //mostrado por pantalla
    
    return 0;
}