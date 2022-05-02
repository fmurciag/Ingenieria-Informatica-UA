//francisco joaquin murcia gomez 48734281H

#include "tvectorcom.h"
#include <iostream>

using namespace std;


TVectorCom::TVectorCom():error(){
    this->c=NULL;
    this->tamano=0;

}
TVectorCom::TVectorCom(int tam):error(){
    if(tam<=0){
        this->c=NULL;
        this->tamano=0;
    }else{
        c= new TComplejo[tam];
        tamano=tam;
    }

}
TVectorCom::TVectorCom(const TVectorCom & v):error(){
    if(v.tamano==0){
        this->c=NULL;
        this->tamano=0;
    }else{
        c=new TComplejo[v.tamano];
        tamano=v.tamano;
        for(int i=0; i<v.tamano;i++){
            c[i]=v.c[i];
        }
    }
}
TVectorCom::~TVectorCom (){
    tamano=0;
    if(c!=NULL){
        delete[] c;
    }
    c=NULL;
    
}
TVectorCom & TVectorCom::operator=( const TVectorCom &v){
    if(this !=&v){
        (*this).~TVectorCom();
        this->c=new TComplejo[v.tamano];
        this->tamano=v.tamano;
        for(int i=0; i<v.tamano;i++){
            this->c[i]=v.c[i];
        }

    }
    return *this;
}
bool TVectorCom::operator==( const TVectorCom &v)const{
    if(this->tamano!=v.tamano){
        return false;
    }
    for(int i=0; i<v.tamano;i++){
        if(this->c[i]!=v.c[i]){
            return false;
        }
    }
    return true;
}
bool TVectorCom::operator!=( const TVectorCom &v)const{
    return !(*this==v);
}
TComplejo & TVectorCom::operator[](int i){//escritura iz

    if(i <= tamano && i>0){
        return this->c[i-1];
    }else{
        return error;

    }

}

TComplejo  TVectorCom::operator[](int i) const{//lectura dercha
     if(i <= tamano && i>0){
        return c[i-1];
    }else{
        return error;
    }
 }

int  TVectorCom::Tamano(){
     return tamano;
 }
int TVectorCom::Ocupadas(){
    int ocupado=0;
    TComplejo complejo(0,0);
    for(int i=0;i<tamano;i++){
        if(this->c[i]!=complejo) ocupado++;
    }
    return ocupado;
}


bool TVectorCom::ExisteCom(const TComplejo &complejo){
    for(int i=0;i<tamano;i++){
        if(this->c[i]==complejo) return true;
    }
    return false;

}

void TVectorCom::MostrarComplejos( double re){
    double re_1=0;
    bool encontrado=false;
    int contidad=0;
    for(int i=0;i<tamano;i++){
        if(this->c[i].Re()>=re){
            contidad++;
        }
    }

    int veces=0;
    cout<<"[";
    for(int i=0;i<tamano;i++){
        if(this->c[i].Re()>=re){
            cout<<c[i];
            encontrado=true;
            veces++;
            if(veces<contidad)cout<<", ";
            
        }
        
        encontrado=false;
    }
    cout<<"]";
  

}

bool TVectorCom::Redimensionar( int tam){
    if(tam==tamano ||tam<=0){
        return false;
    }else{

        if(tam<tamano){
            TVectorCom v(tam);
            for(int i=0;i<tam;i++){
                v.c[i]=this->c[i]; 
            }
            
            for(int i=tam;i<tamano;i++){
               this->c[i].~TComplejo();
            }
            *this=v;

        }
        else if(tam>tamano){
            TVectorCom v(tam);
            for(int i=0;i<tamano;i++){
                v.c[i]=this->c[i]; 
            }
            *this=v;

        }

       
       
       return true;

    
        
    }

}



ostream& operator<<( ostream & o, const TVectorCom & v){
    o<<"[";
    for(int i=0; i<v.tamano;i++){
        o<<"("<<i+1<<") "<<v.c[i];
        if(i!=v.tamano-1) o<<", ";
    }
    o<<"]";
    return o;
}