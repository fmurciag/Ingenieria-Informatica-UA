#ifndef _CONTAINER_
#define _CONTAINER_


#include<iostream>
using namespace std;

class Container
{
    friend ostream& operator<< (ostream &o,const Container &c);

    protected:
        unsigned int id,value,weight;
        static unsigned int nextId;

    public:
        static const unsigned int kMINWEIGHT=100;
        static const unsigned int  kMINVALUE=100;

        Container(unsigned int weight=0, unsigned int value=0);
        static void resetNextId();

        unsigned int getId() const{return id;}
        unsigned int getWeight() const{return weight;}
        unsigned int getValue() const{return value;}

        void setWeight(unsigned int weight);
        void setValue(unsigned int value);
  

   
};
#endif 
