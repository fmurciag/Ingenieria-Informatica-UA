//DNI 48734281H Murcia Gomez, Francisco Joaquin
//practica 2 programacion 2 

#include <iostream>
#include <vector>
#include<fstream>
#include<cstdlib>
#include<cstring>


using namespace std;

unsigned int id_contenedor=0;//identificador de los contenedores

const int MAXNAME=20;
//constantes de peso, valor y contenedores minimos
const int MINPESO=100;
const int MINVALOR=100;
const int MINCONT=5;
const int MINPESOSHIP=500;

struct Container
{
  unsigned int id;
  unsigned int weight;
  unsigned int value;
};

struct Ship
{
  string name;
  unsigned int weight;
  unsigned int value;
  unsigned int maxWeight;
  unsigned int maxContainers;
  vector<Container> containers;
};

struct Base
{
  string name;
  vector<Container> containers;
  vector<Ship> ships;
};

struct BinShip
{
char name[MAXNAME];
unsigned int weight;
unsigned int value;
unsigned int maxWeight;
unsigned int maxContainers;
unsigned int numContainers;
};

struct BinBase
{
char name[MAXNAME];
unsigned int numContainers;
unsigned int nextId;
};

enum Error
{
  ERR_MENU_OPTION, 
  ERR_CONTAINER_WEIGHT,
  ERR_CONTAINER_VALUE,
  ERR_CONTAINER_ID,
  ERR_SHIP_NAME,
  ERR_SHIP_MAXCONTAINERS,
  ERR_SHIP_MAXWEIGHT,
  ERR_SHIP_NO_MORE_CONTAINERS,
  ERR_SHIP_NO_MORE_WEIGHT,
  ERR_CANT_OPEN_FILE,
  ERR_ARGS
}; 



void error(Error n)
{
  switch (n)
  {
    case ERR_MENU_OPTION:
      cout << "Error: unknown menu option" << endl;
      break;

    case ERR_CONTAINER_WEIGHT:
      cout << "Error: container weight is below minimum" << endl;
      break;
      
    case ERR_CONTAINER_VALUE:
      cout << "Error: container value is below minimum" << endl;
      break;
    
    case ERR_CONTAINER_ID:
      cout << "Error: wrong container id" << endl;
      break;
      
    case ERR_SHIP_NAME:
      cout << "Error: wrong ship name" << endl;
      break;
      
    case ERR_SHIP_MAXCONTAINERS:
      cout << "Error: wrong maximum number of containers" << endl;
      break;
      
    case ERR_SHIP_MAXWEIGHT:
      cout << "Error: wrong maximum weight" << endl;
      break;
      
    case ERR_SHIP_NO_MORE_CONTAINERS:
      cout << "Error: ship cannot carry more containers" << endl;
      break;

    case ERR_SHIP_NO_MORE_WEIGHT:
      cout << "Error: ship cannot not hold more containers" << endl;
      break;

    case ERR_CANT_OPEN_FILE:
      cout << "Error: can't open file" << endl;
      break;

    case ERR_ARGS:
      cout << "Error: wrong arguments" << endl;
  }
}

void printContainer(const Container &c)
{
    cout<<"["<<c.id<<" "<<c.weight<<":"<<c.value<<"]";
}

void printShip(const Ship &s)
{
    cout<<"{"<<s.name<<":"<<" "<<s.weight<<" "<<"("<<s.maxWeight<<")"<<","<<" "<<s.containers.size()<<" "<<"("<<s.maxContainers<<")"<<","<<" "<<s.value<<endl;
    if(s.containers.size()!=0){
        for(int unsigned i=0;i<s.containers.size();i++){//contenedores de las naves
            printContainer(s.containers[i]);
        }
        cout<<endl;
    }
    cout<<"}";
}

void printBase(const Base &b)
{
    
    cout<<"Name: "<<b.name<<endl<<"Containers: "<<endl;

    for(int unsigned i=0;i<b.containers.size();i++){//contenedores
        printContainer(b.containers[i]);
        cout<<endl;
    }
    cout<<"Ships: "<<endl;
    for(int unsigned i=0;i<b.ships.size();i++){//naves
        printShip(b.ships[i]);
        cout<<endl;
    }
    
}

void addContainer(Base &b)
{
    Container c;
    int peso=0;
    int valor=0;
   cout<<"Container weight: ";
   cin>>peso;
    if(peso<MINPESO)//compruebas peso
       error(ERR_CONTAINER_WEIGHT);
    else{
        cout<<"Container value: ";
        cin>>valor;
        if(valor<MINVALOR)//comprueba valor
            error(ERR_CONTAINER_VALUE);
        else{
            id_contenedor++;//para que empieze en 1..2..3 y no en 0
            //meter al struct contenedor
            c.id=id_contenedor;
            c.weight=peso;
            c.value=valor;
        
            b.containers.push_back(c);//meter a base
        }
    }
}

void removeContainer(Base &b)
{
    int unsigned i=0;
    bool encontrado=false;//contro error id
    int unsigned identi;
    cout<<"Container id: ";
    cin>>identi;
    for(i=0;i<b.containers.size();i++){//busca el contenedor 
        if(identi == b.containers[i].id){
            b.containers.erase(b.containers.begin()+i);//lo borra
            encontrado=true;
        }
    }
    if(!encontrado){//contro error id
        error(ERR_CONTAINER_ID);
    }
}

void addShip(Base &b)
{
    
    unsigned int numcontenedor=0;
    unsigned int peso=0;
    Ship s;
    bool encontrado=false;//contro error nombre
    string nombre;
    cout<<"Ship name: ";
    getline(cin,nombre);
    for(unsigned int i=0;i<b.ships.size();i++){//mira si el nombre esta repetido
        if(b.ships[i].name==nombre){
            encontrado=true;
        }
    }
    //pide y comprueva las caracteristicas de la nave, si no esta repe el nombre
    if(!encontrado){//contro error nombre
       cout<<"Ship max. containers: ";
       cin>>numcontenedor;
       if(numcontenedor>=MINCONT){
           cout<<"Ship max. weight: ";
           cin>>peso;
           if(peso>=MINPESOSHIP){
               //mete valores al struct ships 
               s.name=nombre;
               s.maxWeight=peso;
               s.maxContainers=numcontenedor;
               s.weight=0;
               s.value=0;
               
               b.ships.push_back(s);//mete a base
           }
           else
               error(ERR_SHIP_MAXWEIGHT);
       }
       else
           error(ERR_SHIP_MAXCONTAINERS);
    }
    else
        error(ERR_SHIP_NAME);
}

void removeShip(Base &b)
{
    bool encontrado=false;//contro error
    
    string nombre;
    cout<<"Ship name: ";
    getline(cin,nombre);
    for(unsigned int i=0;i<b.ships.size();i++){//busca el nombre que introduces
        if(b.ships[i].name==nombre){
            encontrado=true;
            for(unsigned int j=0;j<b.ships[i].containers.size();j++){//meter los contenedores a base
                b.containers.push_back(b.ships[i].containers[j]);
            }
            b.ships.erase(b.ships.begin()+i);
        }
    }
    if(!encontrado){//contro error
        error(ERR_SHIP_NAME);
    }
}

void manualDistribution(Base &b)
{
    bool id_encontrado=false/*contro error*/,nave_encontrado=false;//contro error
    unsigned int pos_nave=0;
    unsigned int identificador=0;
    string nombre;
    cout<<"Container id: ";
    cin>>identificador;
    
    for(unsigned int i=0;i<b.containers.size();i++){//buscar id de contenedor
        if(identificador == b.containers[i].id){
            identificador=i;
            id_encontrado=true;
        }
    }
    if(!id_encontrado){//contro error contenedor
        error(ERR_CONTAINER_ID);
    }
    else{//si encuentra el contenedor
        cout<<"Ship name: ";
        cin.ignore();
        getline(cin,nombre);
    
        for(unsigned int i=0;i<b.ships.size();i++){//busca la nave
            if(b.ships[i].name==nombre){
                pos_nave=i;
                nave_encontrado=true;
            }
        }
        if(!nave_encontrado){//contro error nave
            error(ERR_SHIP_NAME);
            
        }
        else{//si encuentra la nave
            if(b.ships[pos_nave].containers.size()<b.ships[pos_nave].maxContainers){//mira que le cojan contenedores a la nave
                if((b.ships[pos_nave].weight+b.containers[identificador].weight)<=b.ships[pos_nave].maxWeight){//mira que soporteel peso  
                    //actualiza peso y valor de la nave 
                    b.ships[pos_nave].weight=b.ships[pos_nave].weight+b.containers[identificador].weight;
                    b.ships[pos_nave].value=b.ships[pos_nave].value+b.containers[identificador].value;
                    //borra y mueve de la base a la nave el contenedor
                    b.ships[pos_nave].containers.push_back(b.containers[identificador]);
                    b.containers.erase(b.containers.begin()+identificador);

                }
                else{
                    error(ERR_SHIP_NO_MORE_WEIGHT);
                }   
            }
            else{
                error(ERR_SHIP_NO_MORE_CONTAINERS);
            }
        }
    }
}

bool dividir(Base &b,unsigned int &pos_contenedor){// divide contenedores
    Container c;
    unsigned int peso=0,valor=0,new_peso1=0,new_valor1=0,new_peso2=0,new_valor2=0;
    bool exito=false;

    peso=b.containers[pos_contenedor].weight;
    valor=b.containers[pos_contenedor].value;
    //valores de los nuevos contenedores
    new_peso1=peso/2;
    new_valor1=valor/2;
    new_peso2=peso/2;
    new_valor2=valor/2;
    if(new_peso1>=MINPESO && new_valor1>=MINPESO && new_peso2>=MINPESO && new_valor2>=MINPESO){//superacion de los valores minimos de los nuevos contenedores
        if(peso%2!=0){//arreglo de peso impar 
            new_peso2++;
        }
        if(valor%2!=0){//arreglo de valor impar
            new_valor2++;
        }
        id_contenedor++;
        c.id=id_contenedor;
        c.weight=new_peso1;
        c.value=new_valor1;
        b.containers.push_back(c);
        //contenedor 2
        id_contenedor++;
        c.id=id_contenedor;
        c.weight=new_peso2;
        c.value=new_valor2;
        b.containers.push_back(c);
        //eliminacion
        b.containers.erase(b.containers.begin()+pos_contenedor);

        exito=true;
    }
    return(exito);
}   
void automaticDistribution(Base &b)
{ 
    bool control=false;
    unsigned int iteraciones=0;//las veces que te lo repite
    while(!control){
        bool nave_encontrada=false;
        if(b.containers.size()!=0){//mira si hay contenedores

            unsigned int valor=0;
            unsigned int pos_contenedor=0;

            for(unsigned int i=0;i<b.containers.size();i++){//1º buscar el contenedor que cumple las condiciones
                if(b.containers[i].value>valor){//condicion de valor
                    pos_contenedor=i;
                    valor=b.containers[i].value;
                }
                else{
                    if(b.containers[i].value==valor){
                        if(b.containers[i].weight<b.containers[pos_contenedor].weight){//condicion de peso
                            pos_contenedor=i;
                        }
                    }//si el peso del candidato es igual o menor que el actual se coge el primero, "pos_contenedor" siempre va a ser menor a "i"
                }
            }
            if(b.ships.size()!=0){

                unsigned int pos_nave=0;
                unsigned int valor=999999999;//valor muy grande para que te coja siempre el menor
                for(unsigned int i=0;i< b.ships.size();i++){//2º buscar la nave idonea para ese contenedor
                    if(b.ships[i].containers.size()<b.ships[i].maxContainers && (b.ships[i].weight+b.containers[pos_contenedor].weight<=b.ships[i].maxWeight)){//si le cogen y soporta el peso 
                        nave_encontrada=true;
                        if(valor>b.ships[i].value){//mira si el valor del candidato es mayor al actual
                            pos_nave=i;
                            valor=b.ships[i].value;
                            

                        }
                    //si el peso del valor es igual o mayor que el actual se coge el primero, "pos_nave" siempre va a ser menor a "i" 
                    }
                }
                if(nave_encontrada){//si le cogen y soporta el peso lo mete
                    //actualiza peso y valor de la nave 
                    b.ships[pos_nave].weight=b.ships[pos_nave].weight+b.containers[pos_contenedor].weight;
                    b.ships[pos_nave].value=b.ships[pos_nave].value+b.containers[pos_contenedor].value;            
                    //borra y mueve de la base a la nave el contenedor
                    b.ships[pos_nave].containers.push_back(b.containers[pos_contenedor]);
                    b.containers.erase(b.containers.begin()+pos_contenedor);
                    iteraciones=0;
                }
                else{
                    bool peso=false;
                   // bool cantidad=false;
                    for(unsigned int i=0;i< b.ships.size();i++){//si le coge peso en alguna nave intenta dividir
                        if(b.ships[i].weight<b.ships[i].maxWeight){
                            peso=true; 
                        }
                        
                    }
                    if(peso){//
                        if(dividir(b,pos_contenedor))
                        iteraciones=0;
                    }
                }
                iteraciones++;
                if(iteraciones==4){//si te entra en bucle 4 veces sin meter nada sale
                    control=true;
                }
            }
            else{
                control=true;
            }
        }
        else{
            control=true;
        }
    }
}

void unassignContainer(Base &b)
{
    string nombre;
    unsigned int identificador=0,pos_contenedor=0,pos_nave=0;
    bool nave_encontrada=false,id_encontrado=false;
    cout<<"Ship name: ";
    getline(cin,nombre);

    for(unsigned int i=0;i<b.ships.size();i++){// busca nave
        if(nombre==b.ships[i].name){
            nave_encontrada=true;
            pos_nave=i;
        }
    }
    if(!nave_encontrada){//si no la encuentra
        error(ERR_SHIP_NAME);
    }
    else{
        cout<<"Container id: ";
        cin>>identificador;
        for(unsigned int i=0;i<b.ships[pos_nave].containers.size();i++){//busca id
            if(identificador==b.ships[pos_nave].containers[i].id){
                id_encontrado=true;
                pos_contenedor=i;
            }
        }
        if(!id_encontrado){//si no lo encuentra
            error(ERR_CONTAINER_ID);
        }
        else{
            //actualiza los valores de la nave
            b.ships[pos_nave].weight=b.ships[pos_nave].weight-b.ships[pos_nave].containers[pos_contenedor].weight;
            b.ships[pos_nave].value=b.ships[pos_nave].value-b.ships[pos_nave].containers[pos_contenedor].value;
            //borra y mueve de la nave a la base el contenedor
            b.containers.push_back(b.ships[pos_nave].containers[pos_contenedor]);
            b.ships[pos_nave].containers.erase(b.ships[pos_nave].containers.begin()+pos_contenedor);
        }
    }

}
void clearAssignations(Base &b)
{

    for(unsigned int i=0;i<b.ships.size();i++){// recorre naves
        while(b.ships[i].containers.size()!=0){// borra hasta que no tengas contenedores
            //borra y mueve de la nave a la base el contenedor
            b.containers.push_back(b.ships[i].containers[0]);
            b.ships[i].containers.erase(b.ships[i].containers.begin()+0);
        }
        //actualiza los valores de la nave
        b.ships[i].weight=0;
        b.ships[i].value=0;
    }
}

void meterContenedor(Base &b,unsigned int peso,unsigned int valor){//mete los contenedores a la base
    if(peso<MINPESO)//compruebas peso
        error(ERR_CONTAINER_WEIGHT);
    else{
        if(valor<MINVALOR)//comprueba valor
            error(ERR_CONTAINER_VALUE);

        else{
            Container c;
            id_contenedor++;//para que empieze en 1..2..3 y no en 0
            //meter al struct contenedor
            c.id=id_contenedor;
            c.weight=peso;
            c.value=valor;
        
            b.containers.push_back(c);//meter a base
        }
    }

}
void meterNave(Base &b,unsigned int peso,unsigned int contenedores,string nombre){//mete las naves a la base
    Ship s;
    bool encontrado=false;
    for(unsigned int i=0;i<b.ships.size();i++){//mira si el nombre esta repetido
        if(b.ships[i].name==nombre){
            encontrado=true;
        }
    }
    //pide y comprueva las caracteristicas de la nave, si no esta repe el nombre
    if(!encontrado){//contro error nombre
       if(contenedores>=MINCONT){
           if(peso>=MINPESOSHIP){
               //mete valores al struct ships 
               s.name=nombre;
               s.maxWeight=peso;
               s.maxContainers=contenedores;
               s.weight=0;
               s.value=0;
               
               b.ships.push_back(s);//mete a base
           }
           else
               error(ERR_SHIP_MAXWEIGHT);
       }
       else
           error(ERR_SHIP_MAXCONTAINERS);
    }
    else
        error(ERR_SHIP_NAME);
}
void lectura(Base &b,string texto){//lee y seleciona los datos
    string peso_c,valor_c;//guardo el peso y el valor para hacerles el atoy      contenedores
    string nombre,peso_s,contenedores_s;
    for(unsigned int i = 0; i < texto.length(); i++){

        //contenedor
        peso_c="";
        valor_c="";
        if(texto[i] == 'C'){ 
            i++;
            unsigned int peso=0,valor=0;
            while(texto[i]=='('||texto[i]==' '){//colocacion en el peso
                i++;
            }
            while(texto[i]!=' '&&texto[i]!=','){//asignacion del peso
                peso_c=peso_c+texto[i];
                i++;
            }
            while(texto[i]==' '||texto[i]==','){//colocacion en valor
                i++;
            }
            while(texto[i]!=' '&& texto[i]!=')'){//asignacion del valor
                valor_c=valor_c+texto[i];
                i++;
            }
            //asignacion de valores y metida en base
            peso=atoi(peso_c.c_str());
            valor=atoi(valor_c.c_str());
            meterContenedor(b,peso,valor);// comprueba y te mete el contenedor
        }
        //nave
        nombre="";
        peso_s="";
        contenedores_s="";
        if(texto[i] == 'S'){ 
            i++;
            unsigned int peso=0,contenedores=0;
            while(texto[i]=='['||texto[i]==' '||texto[i]=='"'){//colocacion en el nombre
                i++;
            }
            while(texto[i]!='"'){//asignacion del nombre
                nombre=nombre+texto[i];
                i++;
            }
            while(texto[i]==' '||texto[i]=='"'){//colocacion en posicion peso
                i++;
            }
            while(texto[i]!=':'&&texto[i]!=' '){//asignacion del peso
                peso_s=peso_s+texto[i];
                i++;
            }
            while(texto[i]==' '||texto[i]==':'){//colocacion en posicion contenedores
                i++;
            }
            while(texto[i]!=']'&&texto[i]!=' '){//asignacion de contenedores
                contenedores_s=contenedores_s+texto[i];
                i++;
            }
            //asignacion de valores y metida en base
            peso=atoi(peso_s.c_str());
            contenedores=atoi(contenedores_s.c_str());
            meterNave(b,peso,contenedores,nombre);// comprueva y te mete la nave
        }
    }
}
void importData(Base &b)
{
    ifstream fichero;
    string nombre_fichero,texto/*donde copio el fichero*/;
    string peso_c,valor_c/*guardo el peso y el valor para hacerles el atoy*/;
    
    cout<<"Enter filename: ";
    getline(cin,nombre_fichero);
    fichero.open(nombre_fichero.c_str(),ios::in);//abrir el fichero
    if(fichero.is_open()){
        while(getline(fichero, texto)/*te lo copia en un string*/){
            lectura(b, texto);//lee el string que pone lo mismo que el fichero de texto
        }
        fichero.close();
    }
    else{
        error(ERR_CANT_OPEN_FILE);
    }
}
 
void exportData(Base &b)
{
    ofstream fichero;
    string nombre_fichero;
    cout<<"Enter filename: ";
    getline(cin,nombre_fichero);
    fichero.open(nombre_fichero.c_str(),ios::out);
    if(fichero.is_open()){
        for(unsigned int i=0;i<b.containers.size();i++){//contenedores de base
            fichero<<"C("<<b.containers[i].weight<<","<<b.containers[i].value<<")\n";// meter C(peso,valor)
        }
       for(unsigned int i=0;i<b.ships.size();i++){//  naves
            fichero<<"S["<<'"'<<b.ships[i].name<<'"'<<" "<<b.ships[i].maxWeight<<":"<<b.ships[i].maxContainers<<"]\n";// meter S["nombre" pesoMAX:contenedoresMax]
            for(unsigned int j=0;j<b.ships[i].containers.size();j++){//contenedores de nave
                fichero<<"C("<<b.ships[i].containers[j].weight<<","<<b.ships[i].containers[j].value<<")\n";// meter C(peso,valor)
            }
        }
        fichero.close();
    }
    else{
        error(ERR_CANT_OPEN_FILE);
    }

}

void leerBinario(Base &b, ifstream &fichero){//lee un fichero binario
    BinBase binbase;
    BinShip binship;
    Container c;
    Ship s;

    //leo base
    fichero.read((char*)&binbase,sizeof(binbase));
    b.name=binbase.name;
    id_contenedor=binbase.nextId;
    
    //leo contenedores en base
    for(unsigned int i=0;i<binbase.numContainers;i++){
        fichero.read((char*)&c,sizeof(c));
        b.containers.push_back(c);
    }

    if(!fichero.eof()){//si es el fin del fichero, salgo 

        unsigned int pos_nave=0;
        while(fichero.read((char*)&binship,sizeof(binship))){//naves
            s.name=binship.name;
            s.weight=binship.weight;
            s.maxWeight=binship.maxWeight;
            s.value=binship.value;
            s.maxContainers=binship.maxContainers;
            b.ships.push_back(s);
            
            for(unsigned int i=0;i<binship.numContainers;i++){//contenedores de las naves
                fichero.read((char*)&c,sizeof(c));
                b.ships[pos_nave].containers.push_back(c); 
            }
            pos_nave++;//cambio la posicion de la nave
        }
    }
}
void loadBase(Base &b)
{
    char confirmacion;
    ifstream fichero;
    string nombre_fichero;
    cout<<"Enter filename: ";
    getline(cin,nombre_fichero);
    
   fichero.open(nombre_fichero.c_str(),ios::in | ios::binary);
    if(fichero.is_open()){
        do{
            cout<<"Confirm? (y/n): ";
            cin>>confirmacion;

        }while(confirmacion!='y'&&confirmacion!='n');// repeticion del menu de si o no
        if(confirmacion=='y'){
            //borrado de todo
            while(b.containers.size()!=0){
                b.containers.erase(b.containers.begin()+0);
            }
            while(b.ships.size()!=0){
                b.ships.erase(b.ships.begin()+0);
            }
            
            leerBinario(b, fichero);//lectura del fichero

            fichero.close();
        }
    }
    else{
        error(ERR_CANT_OPEN_FILE);
    }
}



void escribirBinario(Base &b, ofstream &fichero){// escribe un fichero binario
    BinBase binbase;
    BinShip binship;
    Container c;
   

    //valores de base
    binbase.numContainers=b.containers.size();
    binbase.nextId=id_contenedor;
    strncpy(binbase.name,b.name.c_str(),MAXNAME-1);//copia el nombre de la base a la estructura binaria
    binbase.name[MAXNAME-1]='\0';

    //meto base
    fichero.write((char*)&binbase,sizeof(binbase));
    
    for(unsigned int i=0;i<b.containers.size();i++){//contenedores base
        //de base a struct contenedor
        c.weight=b.containers[i].weight;
        c.value=b.containers[i].value;
        c.id=b.containers[i].id;
        //de struct al fichero
        fichero.write((char*)&c,sizeof(c));
    }
   //naves
    for(unsigned int i=0;i<b.ships.size();i++){
        //se inicializan a 0
        binship.value=0;
        binship.weight=0;
        binship.maxWeight=0;
        binship.numContainers=0;
        binship.maxContainers=0;
        
        //asignacion de valores al binShip
        strncpy(binship.name,b.ships[i].name.c_str(),MAXNAME-1);//copia el nombre de la nave a la estructura binaria
        binship.name[MAXNAME-1]='\0';
         
        binship.value=b.ships[i].value;
        binship.weight=b.ships[i].weight;
        binship.maxWeight=b.ships[i].maxWeight;
        binship.numContainers=b.ships[i].containers.size();
        binship.maxContainers=b.ships[i].maxContainers;
        //metemos nave
        fichero.write((char*)&binship,sizeof(binship));
        //contenedores de nave
        for(unsigned int j=0;j<b.ships[i].containers.size();j++){//contenedores base
            //de base a struct contenedor
            c.weight=b.ships[i].containers[j].weight;
            c.value=b.ships[i].containers[j].value;
            c.id=b.ships[i].containers[j].id;
            //de struct al fichero
            fichero.write((char*)&c,sizeof(c));
        }
    }
}

void saveBase(Base &b)
{
    ofstream fichero;
    string nombre_fichero;
    cout<<"Enter filename: ";
    getline(cin,nombre_fichero);
    fichero.open(nombre_fichero.c_str(),ios::out | ios::binary);
    if(fichero.is_open()){
        escribirBinario(b, fichero);//escribe en un fichero binario
        fichero.close();
    }
    else{
        error(ERR_CANT_OPEN_FILE);
    }
}

void menu()
{
  cout << "1- Info base" << endl
       << "2- Add container" << endl
       << "3- Remove container" << endl
       << "4- Add ship" << endl
       << "5- Remove ship" << endl
       << "6- Manual distribution" << endl
       << "7- Automatic distribution" << endl
       <<"8- Unassign container"<<endl
       <<"9- Clear assignations"<<endl
       <<"i- Import data"<<endl
       <<"x- Export data"<<endl
       <<"l- Load base"<<endl
       <<"s- Save base"<<endl
       << "q- Quit" << endl
       << "Option: " ;
}

int main(int argc,char*argv[])
{
   Base base;
   string agL="-l",agI="-i",agA="-a";
   string fichero_I,fichero_L;//nombre fichero import y load
   bool argCorrecto=true;//control para argumento correcto
   bool automatic=false;//activador de automatic
   bool import=false;//activador de import
   bool load=false;//activador de load
   
   base.name = "Logistic Center";
   char option;
   if(argc !=1){
        for (int i=1;i<argc;i++){
            // que no sea un argumento
            if((argv[i]!=agA && argv[i]!=agL) && argv[i]!=agI){
                argCorrecto=false;
            }
            //import
            if(argv[i]==agI && !import ){//si encuentra el -i y no si ha repetido antes
                import=true;
                if(i==argc-1){//mira si esta en la ultima posicion
                    argCorrecto=false; 
                }
                else{// te copia el nombre de lo de despues
                    i++;
                    fichero_I=argv[i];
                }
            }
            else{
                if(argv[i]==agI && import){//la contol para que no se repita
                    argCorrecto=false;
                }
            }
            //load
            if(argv[i]==agL && !load ){//si encuentra el -l y no si ha repetido antes
                load=true;
                if(i==argc-1){//mira si esta en la ultima posicion
                    argCorrecto=false; 
                }
                else{// te copia el nombre de lo de despues
                    i++;
                    fichero_L=argv[i];
                }
            }
            else{
                if(argv[i]==agL && load){//la contol para que no se repita
                    argCorrecto=false;
                }
            }
            //automatic
            if(argv[i]==agA && !automatic ){//si encuentra el -a y no si ha repetido antes
                automatic=true;  
            }
            else{
                if(argv[i]==agA && automatic){//la contol para que no se repita
                    argCorrecto=false;
                }
            }   
        }
        //llamadas a modulos import automatic y load

        if(argCorrecto){//si argumentos correctos se introducen
            if(load){
                ifstream fichero;
                fichero.open(fichero_L.c_str(),ios::in | ios::binary);
                if(fichero.is_open()){

                    leerBinario(base, fichero);//lectura del fichero
                    
                    fichero.close();  
                }
                else{
                    error(ERR_CANT_OPEN_FILE);
                }
            }
            if(import){//activacion del import data
                ifstream fichero;
                fichero.open(fichero_I.c_str(),ios::in);
                if(fichero.is_open()){
                    while(getline(fichero, fichero_I)){
                        lectura(base, fichero_I);//lectura del fichero
                    }
                    fichero.close();
                }
                else{
                    error(ERR_CANT_OPEN_FILE);
                }
            }
            if(automatic){//activacion del automatic
                automaticDistribution(base);
            }
        }
   }
   if(argCorrecto){//si argumentos corectos menu normal
        do
        {
            menu();
            cin >> option; cin.get();
                
            switch (option) { 
                case '1': {
                    printBase(base);
                    break;
                }
                case '2':
                { 
                    addContainer(base);
                    break;
                }
                case '3':
                {
                    removeContainer(base);
                    break;
                }
                case '4':
                {
                    addShip(base);
                    break;
                }
                case '5':
                {
                    removeShip(base);
                    break;
                }
                case '6':
                {
                    manualDistribution(base);
                    break;
                }
                case '7':
                {
                    automaticDistribution(base);
                    break;
                }
                case '8':
                {
                    unassignContainer(base);
                    break;
                }
                case '9':
                {
                    clearAssignations(base);
                    break;
                }
                case 'i':
                {
                    importData(base);
                    break;
                }
                case 'x':
                {
                    exportData(base);
                    break;
                }
                case 'l':
                {
                    loadBase(base);
                    break;
                }
                case 's':
                {
                    saveBase(base);
                    break;
                }
                case 'q': {
                    break;
                }
                default:  {
                    error(ERR_MENU_OPTION); 
                    break;
                }
            }
        } while(option != 'q');
    }
    else{
        error(ERR_ARGS);
    }
   return 0;
}