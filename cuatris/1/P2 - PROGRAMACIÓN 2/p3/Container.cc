#include<iostream>
#include"Container.h"
#include "Util.h"
using namespace std;

unsigned int Container::nextId=1;
//#------constructor-----------#
Container::Container(unsigned int weight, unsigned int value){
    if(weight==0){
        cout<<"Container weight: ";
        cin>>weight;
    }
    setWeight(weight);
    if(value==0){
        cout<<"Container value: ";
        cin>>value;
    }
    setValue(value);
    this->id=nextId;
    nextId++;
  
}

void Container::resetNextId(){
    nextId=1;
}

//#----------seters-------------#
void Container::setWeight(unsigned int weight){//peso
    if(weight>=kMINWEIGHT){
        this->weight=weight;
    }
    else{
        throw ERR_CONTAINER_WEIGHT;
    }
}

void Container::setValue(unsigned int value){//valor
    if(value>=kMINVALUE){
        this->value=value;
    }
    else{
        throw ERR_CONTAINER_VALUE;
    }
    
}

ostream& operator<<(ostream	&o,	const Container &c){
    o<<"["<<c.id<<" "<<c.weight<<":"<<c.value<<"]";
    return(o);
}