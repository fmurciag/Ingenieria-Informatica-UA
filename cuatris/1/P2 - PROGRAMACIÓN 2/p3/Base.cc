#include<iostream>
#include<string>
#include"Container.h"
#include "Util.h"
#include "Ship.h"
#include "Base.h"
using namespace std;

Base::Base(string name){
    this->name=name;
}

Container Base::getContainer(unsigned int id) const{
    Container c;
    bool encontrado=false;
    for(unsigned int i=0;i<getNumContainers();i++){
        if(containers[i].getId()==id){
            c=containers[i];
            encontrado=true;
        }
    }
    if(encontrado==false){
        throw ERR_CONTAINER_ID;
    }
    return(c);
}

Ship * Base::getShip(string name) const{
    unsigned int posicion=0;
    bool encontrado=false;
    for(unsigned int i=0;i<getNumShips();i++){
        if((*ships[i]).getName()==name){
            posicion=i;
            encontrado=true;
        }
    }
    if(encontrado==false){
        throw ERR_CONTAINER_ID;
    }
    return(ships[posicion]);
}

int Base::searchContainer(unsigned int id) const{
    int posicion=-1;
    for(unsigned int i=0;i<getNumContainers();i++){
        if(containers[i].getId()==id){
            posicion=i;
        }
    }
    return(posicion);
}

int Base::searchShip(string name) const{
    int posicion=-1;
    for(unsigned int i=0;i<getNumShips();i++){
        if((*ships[i]).getName()==name){
            posicion=i;
        }
    }
    return(posicion);

}

bool Base::addContainer(unsigned int weight,unsigned int value){
    bool control=false;
    try{
        Container nuevo(weight,value);
        containers.push_back(nuevo);
        control=true;
    }
    catch(Error e){
        Util::error(e);
    }

    return (control);
}

bool Base::removeContainer(unsigned int id){
    bool control=false;
    int posicion=0;
    cout<<"Container id: ";
    cin>>id;
    posicion=searchContainer(id);
    if(posicion!=-1){
        containers.erase(containers.begin()+posicion);
        control=true;
    }else{
        Util::error(ERR_CONTAINER_ID);
    }
    
    return(control);
}

bool  Base::addShip(Ship * s){
    bool control=false;
    int posicion=0;
    
 
    posicion=searchShip(s->getName());
    if(posicion==-1){
        ships.push_back(s);
        control=true;
    }else{
        Util::error(ERR_SHIP_NAME);
    }

    return (control);

}

bool  Base::removeShip(string name){
    bool control=false;
    int posicion=0;
    vector<Container> auxVector;//vector temporal
    if(name==""){
        cout<<"Ship name: ";
        getline(cin,name);
    }
    posicion=searchShip(name);
    if(posicion!=-1){
        auxVector=ships[posicion]->releaseContainers();
        containers.insert(containers.end(),auxVector.begin(),auxVector.end());
        ships.erase(ships.begin()+posicion);
        
        control=true;
    }else{
        Util::error(ERR_SHIP_NAME);
    }

    return control;
}

bool  Base::manualDistribution(unsigned int id,string name){
    bool control=false;
    int posicionC=0;
    int posicionS=0;
    if(id==0){
        cout<<"Container id: ";
        cin>>id;
    }
    posicionC=searchContainer(id);
    if(posicionC!=-1){
        if(name==""){
            cout<<"Ship name: ";
            cin.ignore();
            getline(cin,name);
        }
        posicionS=searchShip(name);
        if(posicionS!=-1){
            control=(*ships[posicionS]).addContainer(containers[posicionC]);
            if(control){
                containers.erase(containers.begin()+posicionC);
            }
            
        }else{
            Util::error(ERR_SHIP_NAME);
        }
    }else{
        Util::error(ERR_CONTAINER_ID);
    }

    return control;
}

bool  Base::unassignContainer(unsigned id,string name){
    bool control=false;
    int posicionC=0;
    int posicionS=0;

    if(name==""){
        cout<<"Ship name: ";
        getline(cin,name);
    }
    posicionS=searchShip(name); 
    if(posicionS!=-1){
        if(id==0){
            cout<<"Container id: ";
            cin>>id;
        }
        posicionC=(*ships[posicionS]).searchContainer(id);
        if(posicionC!=-1){

            try{
                containers.push_back((*ships[posicionS]).getContainer(id));
                control=(*ships[posicionS]).removeContainer(id);
            }
            catch(Error e){
                Util::error(e);
            }

        }else{
            Util::error(ERR_CONTAINER_ID);

        } 
        
    }else{
        Util::error(ERR_SHIP_NAME);
        
    }
    
    return control;
}

void  Base::automaticDistribution(){
    bool control=false;
    unsigned int iteraciones=0;//las veces que te lo repite
    while(!control){
        bool nave_encontrada=false;
        if(getNumContainers()!=0){//mira si hay contenedores
            unsigned int valor=0;
            unsigned int pos_contenedor=0;

            for(unsigned int i=0;i<getNumContainers();i++){//1º buscar el contenedor que cumple las condiciones
                if(containers[i].getValue()>valor){//condicion de valor
                    pos_contenedor=i;
                    valor=containers[i].getValue();
                }
                else{
                    if(containers[i].getValue()==valor){
                        if(containers[i].getWeight()<containers[pos_contenedor].getWeight()){//condicion de peso
                            pos_contenedor=i;
                        }
                    }//si el peso del candidato es igual o menor que el actual se coge el primero, "pos_contenedor" siempre va a ser menor a "i"
                }
            }
            if(getNumShips()!=0){
                
                unsigned int pos_nave=0;
                unsigned int valor=999999999;//valor muy grande para que te coja siempre el menor
                for(unsigned int i=0;i< getNumShips();i++){//2º buscar la nave idonea para ese contenedor
                    if((*ships[i]).getNumContainers()<(*ships[i]).getMaxContainers() && ((*ships[i]).getWeight()+containers[pos_contenedor].getWeight()<=(*ships[i]).getMaxWeight())){//si le cogen y soporta el peso 
                        nave_encontrada=true;
                        if(valor>(*ships[i]).getValue()){//mira si el valor del candidato es mayor al actual
                            pos_nave=i;
                            valor=(*ships[i]).getValue();
                            

                        }
                    //si el peso del valor es igual o mayor que el actual se coge el primero, "pos_nave" siempre va a ser menor a "i" 
                    }
                }
                if(nave_encontrada){//si le cogen y soporta el peso lo mete
                    //actualiza peso y valor de la nave 
                    (*ships[pos_nave]).addContainer(containers[pos_contenedor]);
                    containers.erase(containers.begin()+pos_contenedor);
                    iteraciones=0;
                }else{
                    bool peso=false;
                    // bool cantidad=false;
                    for(unsigned int i=0;i<getNumShips();i++){//si le coge peso en alguna nave intenta dividir
                        if((*ships[i]).getWeight()<(*ships[i]).getMaxWeight()){
                            peso=true; 
                        }
                        
                    }
                    if(peso){//
                        if(dividir(pos_contenedor)){
                            iteraciones=0;
                        }
                            
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
bool Base::dividir(unsigned int &pos_contenedor){
    unsigned int peso=0,valor=0,new_peso1=0,new_valor1=0,new_peso2=0,new_valor2=0;
    bool exito=false;

    peso=containers[pos_contenedor].getWeight();
    valor=containers[pos_contenedor].getValue();
    //valores de los nuevos contenedores
    new_peso1=peso/2;
    new_valor1=valor/2;
    new_peso2=peso/2;
    new_valor2=valor/2;
    if(new_peso1>=100 && new_valor1>=100 && new_peso2>=100 && new_valor2>=100){//superacion de los valores minimos de los nuevos contenedores
        if(peso%2!=0){//arreglo de peso impar 
            new_peso2++;
        }
        if(valor%2!=0){//arreglo de valor impar
            new_valor2++;
        }
        addContainer(new_peso1,new_valor1);
        addContainer(new_peso2,new_valor2);

        containers.erase(containers.begin()+pos_contenedor);

        exito=true;
    }
    return(exito);
}
void  Base::clearAssignations(){
   
    vector<Container> auxVector;//vector temporal
    for(unsigned int i=0;i<getNumShips();i++){
        auxVector=(*ships[i]).releaseContainers();
        containers.insert(containers.end(),auxVector.begin(),auxVector.end());
    }

}

ostream& operator<<(ostream	&bo,const Base &b){
    bo<<"Name: "<<b.name<<endl;
    bo<<"Containers: "<<endl;
    for(unsigned int i=0;i<b.getNumContainers();i++){
        bo<<b.containers[i]<<endl;
    }
    bo<<"Ships: "<<endl;
    for(unsigned int i=0;i<b.getNumShips();i++){
        bo<<*b.ships[i]<<endl;
    }
    return(bo);
}