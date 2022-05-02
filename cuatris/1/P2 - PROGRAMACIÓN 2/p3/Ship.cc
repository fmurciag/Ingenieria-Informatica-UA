#include<iostream>
#include<vector>
#include"Container.h"
#include"Ship.h"
#include"Util.h"
using namespace std;

Ship::Ship(){
    cout<<"Ship name: ";
    getline(cin,name);
    
    //pedida y comprobacion de contenedores
    cout<<"Ship max. containers: ";
    cin>>maxContainers;
    if(maxContainers>=kMINCONTAINERS){
        //pedida y compribacion del peso
        cout<<"Ship max. weight: ";
        cin>>maxWeight;
        if(maxWeight>=kMINWEIGHT){
            //valores ya comprobados
            this->maxWeight=maxWeight;
            this->maxContainers=maxContainers;
            this->name=name;
            value=0;
            weight=0;
        }else{
           throw ERR_SHIP_MAXWEIGHT;
        }
    }else{
        throw ERR_SHIP_MAXCONTAINERS;
    }
}

Ship::Ship(string name,unsigned int maxContainers,unsigned int maxWeight){
    //comprobacion de contenedores
    if(maxContainers>=kMINCONTAINERS){
        //comprobacion del peso
        if(maxWeight>=kMINWEIGHT){
            this->maxWeight=maxWeight;
            this->maxContainers=maxContainers;
            this->name=name;
            value=0;
            weight=0;
          
        }else{
            throw ERR_SHIP_MAXWEIGHT;

        }
    }else{
        throw ERR_SHIP_MAXCONTAINERS;
    }
}



int Ship::searchContainer(unsigned int id)const{
    int posicion=-1;
    for(unsigned int i=0;i<getNumContainers();i++){
        if(containers[i].getId()==id){
            posicion=i;
        }
    }
    return(posicion);
}

bool Ship::admitsContainer(const Container &c){
    bool correcto=false;
    unsigned int pesoNave=getWeight();
    if(getNumContainers()<getMaxContainers()){
        if((c.getWeight()+pesoNave)<=getMaxWeight()){
            correcto=true;
        }
    }
    return(correcto);
}

Container Ship::getContainer(unsigned int id) const{
    bool encontrado=false;
    unsigned int posicion=0;
    for(unsigned int i=0;i<getNumContainers();i++){
        if(containers[i].getId()==id){
            posicion=i;
            encontrado=true;
            
        }
    }
    cout<<encontrado<<"***";
    if(encontrado==false){
        cout<<"*******";
        throw ERR_CONTAINER_ID;
    }
    return(containers[posicion]);
}

bool Ship::addContainer(Container &c){
    bool correcto=false;
    if(getNumContainers()<getMaxContainers()){//¿le cogen?
        if((c.getWeight()+getWeight())<=getMaxWeight()){//¿soporta peso?
            
            weight=weight+c.getWeight();
            value=value+c.getValue();
            containers.push_back(c);

            correcto=true;


        }else{
            Util::error(ERR_SHIP_NO_MORE_WEIGHT);
        }
    }else{
        Util::error(ERR_SHIP_NO_MORE_CONTAINERS);
    }
    
    return(correcto);
}

bool Ship::removeContainer(unsigned int id){
    bool correcto=false;
    int posicion=0;
    posicion=searchContainer(id);
    if(posicion!=-1){
        weight=weight-containers[posicion].getWeight();
        value=value-containers[posicion].getValue();
        containers.erase(containers.begin()+posicion);
        correcto=true;
    }else{
        Util::error(ERR_CONTAINER_ID);
    }
    return(correcto);
}



vector<Container>Ship::releaseContainers(){
    vector<Container> contenedores;
    for(unsigned int i=0;i<getNumContainers();i++){
        contenedores.push_back(containers[i]);
    }
    weight=0;
    value=0;
    containers.clear();
    return(contenedores);
}

ostream& operator<<(ostream	&so,const Ship &s){
    so<<"{"<<s.name<<":"<<" "<<s.weight<<" "<<"("<<s.maxWeight<<")"<<","<<" "<<s.containers.size()<<" "<<"("<<s.maxContainers<<")"<<","<<" "<<s.value<<endl;
    for(unsigned int i=0;i<s.getNumContainers();i++){
        so<<s.containers[i];
    }
    so<<endl<<"}";
    return(so);
}

