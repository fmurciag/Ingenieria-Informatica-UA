#ifndef _SHIP_
#define _SHIP_

#include<iostream>
#include<vector>
#include"Container.h"
using namespace std;

class Ship
{
    friend ostream& operator<< (ostream &o,const Ship &s);

    protected:
        string name;
        unsigned int weight,value,maxWeight,maxValue,maxContainers;
        vector<Container> containers;
    
    public:
        static const unsigned int kMINCONTAINERS=5;
        static const unsigned int  kMINWEIGHT=500;

        Ship();
        Ship(string name,unsigned int maxContainers,unsigned int maxWeight);
        
        string getName() const {return name;}
        unsigned int getWeight() const{return weight;}
        unsigned int getValue() const{return value;}
        unsigned int getMaxWeight() const{return maxWeight;}
        unsigned int getMaxContainers() const{return maxContainers;}
        unsigned int getNumContainers() const{return containers.size();}
        Container getContainer(unsigned int id) const;


        bool removeContainer(unsigned int id);
        int searchContainer(unsigned int id) const;
        bool admitsContainer(const Container &c);
        bool addContainer(Container &c);
        vector<Container>releaseContainers();
};
#endif 