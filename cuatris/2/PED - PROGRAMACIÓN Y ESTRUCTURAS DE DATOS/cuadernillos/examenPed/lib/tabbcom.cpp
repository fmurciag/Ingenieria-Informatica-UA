//francisco joaquin murcia gomez 48734281H
#include "tabbcom.h"
#include "tvectorcom.h"
#include "tlistacom.h"
#include <iostream>
#include <queue>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            

using namespace std;

TNodoABB::TNodoABB ():item(),iz(),de(){
}
TNodoABB::TNodoABB (const TNodoABB &nodo):item(nodo.item),iz(nodo.iz),de(nodo.de){                                                                                                                                                                                                                                                                                                              
}
TNodoABB::~TNodoABB (){
}                                                                                                                                                   
TNodoABB & TNodoABB::operator=( const TNodoABB &nodo){
    this->item=nodo.item;
    if(!(this->iz).EsVacio())this->iz=nodo.iz;
    if(!(this->de).EsVacio())this->de=nodo.de;
    return *this;
}






                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            




void TABBCom::InordenAux(TVectorCom &v, int &pos){
    if(!EsVacio()){
        (nodo->iz).InordenAux(v,pos);
        v[pos]=Raiz();
        pos++;
        (nodo->de).InordenAux(v,pos);
    }
}

void TABBCom::PreordenAux(TVectorCom & v,  int &pos){
    if(!EsVacio()){
        v[pos]=Raiz();
        pos++;
        (nodo->iz).PreordenAux(v,pos);
        (nodo->de).PreordenAux(v,pos);
    }

}

void TABBCom::PostordenAux(TVectorCom &v, int &pos){
    if(!EsVacio()){
        (nodo->iz).PostordenAux(v,pos);
        (nodo->de).PostordenAux(v,pos);
        v[pos]=Raiz();
        pos++;
    }

}
    


void TABBCom::Copiar (const TABBCom & origen){
    if(origen.nodo!=NULL){
        TNodoABB *aux=new TNodoABB();
        aux->item=origen.nodo->item;
        nodo=aux;
        (nodo->iz).Copiar ( origen.nodo->iz);
        (nodo->de).Copiar ( origen.nodo->de);
    }
    else{
        nodo=NULL;
    }
}


TABBCom::TABBCom (){
    this->nodo=NULL;
}

TABBCom::TABBCom (const TABBCom &arbol){
    Copiar(arbol);
}
TABBCom::~TABBCom (){
    if (nodo != NULL){
        delete nodo;
        nodo = NULL;
    }
}

TABBCom & TABBCom::operator=( const TABBCom &a){
    this -> ~TABBCom();
    Copiar (a);
    return *this;
}

bool TABBCom::igual( TABBCom arbol){


    if((nodo->iz).EsVacio()){
        return true;
    }else if((nodo->de).EsVacio()){
        return true;
    }else{
        return arbol.Buscar(nodo->item) && igual(nodo->iz) && igual(nodo->de);
    }
    
}


bool TABBCom::operator==( TABBCom &arbol){
    if(Nodos()==arbol.Nodos()){
        return igual(arbol);
    }else{
        return false;
    }
}
bool  TABBCom::EsVacio()const{
    if(nodo==NULL)return true;
    return false;
}

bool TABBCom::Insertar( TComplejo &c){
    if(Buscar(c))return false;
    
    if(EsVacio()){
        
        TNodoABB *nodoAux= new TNodoABB();
        nodoAux->item=c;
        nodo=nodoAux;
        
    }else{
        //casos de < y > modulo
        if(c.Mod()>(nodo->item).Mod()){
            (nodo->de).Insertar(c);
        }else if(c.Mod()<(nodo->item).Mod()){
            (nodo->iz).Insertar(c);
        }else{//caso == modulo
            //casos de < y > real
            if(c.Re()>(nodo->item).Re()){
                (nodo->de).Insertar(c);
            }else if(c.Re()<(nodo->item).Re()){
                (nodo->iz).Insertar(c);
            }else{//caso == real
                //casos de < y > imaginario
                if(c.Im()>(nodo->item).Im()){
                    (nodo->de).Insertar(c);
                }else if(c.Im()<(nodo->item).Im()){
                    (nodo->iz).Insertar(c);
                }
            }
            
        }   
    }
    return true;
    
}

int TABBCom::numHijos(){
    
    int hijos=2;
    if((nodo->de).EsVacio()){
        
        hijos--;
    }
    if((nodo->iz).EsVacio()){
       
        hijos--;
    }
    return hijos;
}




// Borra el elemento en el árbol
TComplejo TABBCom::mayorIz(){
    if((nodo->de).EsVacio()){
        TComplejo mayor(nodo->item);
        return mayor;
    }else{
        TComplejo aux((nodo->de).mayorIz());
        return aux;
    }
}


void TABBCom::situarse(TComplejo &c){
    
    if(this->nodo->item==c){
        int hijos=-1;
        hijos=numHijos();
        if(hijos==0){
            nodo->~TNodoABB();
            this->nodo=NULL;


        }else if(hijos==1){
            if(!(this->nodo->iz).EsVacio()){
                /*TComplejo aux((this->nodo->iz).nodo->item);
                this->nodo->item=aux;
                this->nodo->iz.Borrar((this->nodo->iz).nodo->item);*/

                TNodoABB* aux=nodo;
                nodo=nodo->iz.nodo;
                aux->iz.nodo=NULL;
                delete aux;
                aux=NULL;
                /*
                this->nodo->de=(this->nodo->iz).nodo->de;
                this->nodo->iz=(this->nodo->iz).nodo->iz;
                 */
            }
            if(!(this->nodo->de).EsVacio()){
                /*TComplejo aux((this->nodo->de).nodo->item);
                this->nodo->item=aux;
                this->nodo->de.Borrar((this->nodo->de).nodo->item);*/

                TNodoABB* aux=nodo;
                nodo=nodo->de.nodo;
                aux->de.nodo=NULL;
                delete aux;
                aux=NULL;



                /*
                this->nodo->iz=(this->nodo->de).nodo->iz;
                this->nodo->de=(this->nodo->de).nodo->de; 
                */
            }
        }else if(hijos==2){
            TComplejo aux(nodo->iz.mayorIz());
            Borrar(aux);
            nodo->item=aux;
        }

    }else if((this->nodo->iz).Buscar(c)){
        (this->nodo->iz).situarse(c);
    }else if((this->nodo->de).Buscar(c)){
        (this->nodo->de).situarse(c);
    }
}

bool TABBCom::Borrar(TComplejo &c){
    if(Buscar(c)){
        situarse(c);
        return true;
    }else{
        return false;
    }
}
// Devuelve TRUE si el elemento está en el árbol, FALSE en caso contrario

bool TABBCom::Buscar( TComplejo & com){
    if(nodo==NULL)return false;
    if(nodo->item==com){
        return true;
    }else{
        return (nodo->iz).Buscar(com) || (nodo->de).Buscar(com);
    }
}


TComplejo TABBCom::Raiz(){
    if (nodo != NULL) return nodo -> item;
        else{
            TComplejo vacio;
            return vacio;
        } 
}

int TABBCom::Altura(){
    int a=0,b=0;

    if(nodo!=NULL){
        a=(nodo->iz).Altura();
        b=(nodo->de).Altura();
        return (1 + (a < b ? b : a));
    }
    return 0;

}

int TABBCom::Nodos()const{
    if(EsVacio()){
        return 0;
    }else{
        return (nodo->iz).Nodos()+(nodo->de).Nodos()+1;
    }
}

int TABBCom::NodosHoja(){
    if(EsVacio())return 0;
    if((nodo->iz).EsVacio()&&(nodo->de).EsVacio() ){
        return 1;
    }else{
        return (nodo->iz).NodosHoja()+(nodo->de).NodosHoja();
    }
}
TVectorCom TABBCom::Inorden(){
    int pos=1;
    TVectorCom v(Nodos());
    InordenAux(v,pos);
    return v;

}

TVectorCom TABBCom::Preorden(){
    int pos=1;
    TVectorCom v(Nodos());
    PreordenAux(v,pos);
    return v;
}

TVectorCom TABBCom::Postorden(){
    int pos=1;
    TVectorCom v(Nodos());
    PostordenAux(v,pos);
    return v;
}

TVectorCom TABBCom::Niveles()const{
    TVectorCom v(Nodos());
    if(EsVacio())return v;
    int pos=1;
    queue<TABBCom> c;
    TABBCom aux;
    aux=(*this);
    c.push(aux);
    while(!c.empty()){
        aux=c.front();
        v[pos]=aux.Raiz();
        pos++;
        c.pop();
        if(!(aux.nodo->iz).EsVacio())c.push(aux.nodo->iz);
        if(!(aux.nodo->de).EsVacio())c.push(aux.nodo->de);
    }
    return v;

}

TListaCom TABBCom::quitarPares(TListaCom lista ){//quitamos los pares de la lista

    TListaCom recortada;
    TListaPos posicion=lista.Primera();
    recortada.InsCabeza(lista.Obtener(posicion));
    int longitud=lista.Longitud();
    posicion=posicion.Siguiente();
    for(int i=0;i<longitud;i++){ 
        if(!posicion.EsVacia()){
            
            if(i%2!=0){
                recortada.InsertarD(lista.Obtener(posicion),recortada.Ultima());
            }
            posicion=posicion.Siguiente();
            
        }
        
        
           
    }
    return recortada;

}


TComplejo* TABBCom::MostrarNiveles (TListaCom &lista ){

    lista=quitarPares(lista);
    int longitud=lista.Longitud();
    TComplejo *complejos=new TComplejo[longitud];
    TListaPos posicion=lista.Primera();

    for(int i=0;i<longitud;i++){ 
        if(!posicion.EsVacia()){
            
            complejos[i]=lista.Obtener(posicion);

            
            posicion=posicion.Siguiente();
            
        }     
    }
    this->~TABBCom ();
    for(int i=0;i<longitud;i++){
        Insertar(complejos[i]);
    }












    return complejos;



}









ostream & operator<<(ostream &o, const TABBCom &arbol){
    o<<arbol.Niveles();
    return o;
}