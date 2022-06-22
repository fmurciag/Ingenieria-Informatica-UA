//francisco joaquin murcia gomez 48734281H
#include "tavlcom.h"
#include "tvectorcom.h"
#include <iostream>
#include <queue>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            

using namespace std;

// Constructor por defecto
TNodoAVL::TNodoAVL ():item(),iz(),de(){
    fe=0;
}
// Constructor de copia
TNodoAVL::TNodoAVL (TNodoAVL &nodo):item(nodo.item),iz(nodo.iz),de(nodo.de){
    fe=nodo.fe;
}
// Destructor
TNodoAVL::~TNodoAVL (){

}
// Sobrecarga del operador asignación
TNodoAVL & TNodoAVL::operator=( TNodoAVL &nodo){
    fe=nodo.fe;
    this->item=nodo.item;
    if(!(this->iz).EsVacio())this->iz=nodo.iz;
    if(!(this->de).EsVacio())this->de=nodo.de;
    return *this;
}


void TAVLCom::InordenAux(TVectorCom &v, int &pos)const{
    if(!EsVacio()){
        (raiz->iz).InordenAux(v,pos);
        v[pos]=Raiz();
        pos++;
        (raiz->de).InordenAux(v,pos);
    }
}

void TAVLCom::PreordenAux(TVectorCom & v,  int &pos){
    if(!EsVacio()){
        v[pos]=Raiz();
        pos++;
        (raiz->iz).PreordenAux(v,pos);
        (raiz->de).PreordenAux(v,pos);
    }

}

void TAVLCom::PostordenAux(TVectorCom &v, int &pos){
    if(!EsVacio()){
        (raiz->iz).PostordenAux(v,pos);
        (raiz->de).PostordenAux(v,pos);
        v[pos]=Raiz();
        pos++;
    }

}
    


void TAVLCom::Copiar (const TAVLCom & origen){
    if(origen.raiz!=NULL){
        TNodoAVL *aux=new TNodoAVL();
        aux->item=origen.raiz->item;
        aux->fe=origen.raiz->fe;
        raiz=aux;
        (raiz->iz).Copiar ( origen.raiz->iz);
        (raiz->de).Copiar ( origen.raiz->de);
    }
    else{
        raiz=NULL;
    }
}




// Constructor por defecto
TAVLCom::TAVLCom (){
    raiz=NULL;
}
// Constructor de copia
TAVLCom::TAVLCom (TAVLCom &arbol){
    Copiar(arbol);
}
// Destructor
TAVLCom::~TAVLCom (){
    if (raiz != NULL){
        delete raiz;
        raiz = NULL;
    }
}

// Sobrecarga del operador asignación
TAVLCom & TAVLCom::operator=(TAVLCom &a){
    this -> ~TAVLCom();
    Copiar (a);
    return *this;
}

bool TAVLCom::igual( TAVLCom arbol){
    if((raiz->iz).EsVacio()){
        return true;
    }else if((raiz->de).EsVacio()){
        return true;
    }else{
        return arbol.Buscar(raiz->item) && igual(raiz->iz) && igual(raiz->de);
    } 
}

// Sobrecarga del operador igualdad
bool TAVLCom::operator==( TAVLCom &arbol){
    if(Nodos()==arbol.Nodos()){
    return igual(arbol);
    }else{
        return false;
    }
}
// Sobrecarga del operador desigualdad
bool TAVLCom::operator!=( TAVLCom &arbol){
    TVectorCom v1=Inorden();
    TVectorCom v2=arbol.Inorden();
    if(v1==v2){
        return false;
    }else{
        return true;
    }
}
// Devuelve TRUE si el árbol está vacío, FALSE en caso contrario
bool  TAVLCom::EsVacio()const{
    if(raiz==NULL)return true;
    return false;
}

void TAVLCom::EquilibrarIzquierdaBorrar(){//

    TNodoAVL* j;
    TNodoAVL* k;
    int E2=0;
    if (raiz->iz.raiz->fe == 0 || raiz->iz.raiz->fe == -1){//ROTACIÓN II

        //cout<<"  II"<<endl;
        //cout <<Preorden()<<endl;
        //Mover (J, HijoIzq (I));
        j=raiz->iz.raiz;
        raiz->iz.raiz=NULL;
        //Mover (HijoIzq (I), HijoDer(J));
        raiz->iz.raiz=j->de.raiz;
        j->de.raiz=NULL;
        // Mover (HijoDer (J), I);
        j->de.raiz=raiz;
        raiz=NULL;
        //FE (J) = 0; FE (HijoDer (J))=0;
        j->fe=0;
        j->de.raiz->fe=0;
        //Mover (I,J);
        raiz=j;
        j=NULL;
        //cout <<Preorden()<<endl;
    }else{//ROTACIÓN ID

        //cout<<"  ID"<<endl;
        //Mover (J, HijoIzq (I));
        j=raiz->iz.raiz;
        raiz->iz.raiz=NULL;
        //Mover (K, HijoDer (J));//
        k=j->de.raiz;
        j->de.raiz=NULL;
        //E2 = FE (K);
        E2=k->fe;
        //Mover (HijoIzq (I), HijoDer(K));
        raiz->iz.raiz=k->de.raiz;
        k->de.raiz=NULL;
        //Mover (HijoDer (J), HijoIzq(K));//
        j->de.raiz=k->iz.raiz;
        k->iz.raiz=NULL;
        //Mover (HijoIzq (K), J);
        k->iz.raiz=j;
        j=NULL;
        //Mover (HijoDer (K), I);
        k->de.raiz=raiz;
        raiz=NULL;
        //FE (K) = 0;
        k->fe=0;

        switch (E2)
        {
        case -1:
            // FE (HijoIzq (K)) = 0; FE (HijoDer (K)) = 1;
            k->iz.raiz->fe=0;
            k->de.raiz->fe=1;
            break;
        case 1:
            // FE (HijoIzq (K)) = -1; FE (HijoDer (K)) = 0;
            k->iz.raiz->fe=-1;
            k->de.raiz->fe=0;
            break;
        case 0:
            // FE (HijoIzq (K)) = 0; FE (HijoDer (K)) = 0;
            k->iz.raiz->fe=0;
            k->de.raiz->fe=0;
            break;
        }
        //Mover (I, K);
        raiz=k;
        k=NULL;
    }


}

void TAVLCom::EquilibrarIzquierdaInsertar(){//

    TNodoAVL* j;
    TNodoAVL* k;
    int E2=0;
    if (raiz->iz.raiz->fe == -1){//ROTACIÓN II
    //cout<<"II"<<endl;

        //Mover (J, HijoIzq (I));
        j=raiz->iz.raiz;
        raiz->iz.raiz=NULL;
        //Mover (HijoIzq (I), HijoDer(J));
        raiz->iz.raiz=j->de.raiz;
        j->de.raiz=NULL;
        // Mover (HijoDer (J), I);
        j->de.raiz=raiz;
        raiz=NULL;
        //FE (J) = 0; FE (HijoDer (J))=0;
        j->fe=0; 
        j->de.raiz->fe=0;
        //Mover (I,J);
        raiz=j;
        j=NULL;
    }else{//ROTACIÓN ID
    //cout<<"ID"<<endl;

        //Mover (J, HijoIzq (I));
        j=raiz->iz.raiz;
        raiz->iz.raiz=NULL;
        //Mover (K, HijoDer (J));//
        k=j->de.raiz;
        j->de.raiz=NULL;
        //E2 = FE (K);
        E2=k->fe;
        //Mover (HijoIzq (I), HijoDer(K));
        raiz->iz.raiz=k->de.raiz;
        k->de.raiz=NULL;
        //Mover (HijoDer (J), HijoIzq(K));//
        j->de.raiz=k->iz.raiz;
        k->iz.raiz=NULL;
        //Mover (HijoIzq (K), J);
        k->iz.raiz=j;
        j=NULL;
        //Mover (HijoDer (K), I);
        k->de.raiz=raiz;
        raiz=NULL;
        //FE (K) = 0;
        k->fe=0;

        switch (E2)
        {
        case -1:
            // FE (HijoIzq (K)) = 0; FE (HijoDer (K)) = 1;
            k->iz.raiz->fe=0;
            k->de.raiz->fe=1;
            break;
        case 1:
            // FE (HijoIzq (K)) = -1; FE (HijoDer (K)) = 0;
            k->iz.raiz->fe=-1;
            k->de.raiz->fe=0;
            break;
        case 0:
            // FE (HijoIzq (K)) = 0; FE (HijoDer (K)) = 0;
            k->iz.raiz->fe=0;
            k->de.raiz->fe=0;
            break;
        }
        //Mover (I, K);
        raiz=k;
        k=NULL;
    }
}


void TAVLCom::EquilibrarDerechaInsertar(){//
    TNodoAVL* j;
    TNodoAVL* k;
    int E2=0;
    if (raiz->de.raiz->fe == 1){//ROTACIÓN DD
    //cout<<"DD"<<endl;
        //Mover (J, HijoDe(I));
        j=raiz->de.raiz;
        raiz->de.raiz=NULL;
        //Mover (HijoDe(I), HijoIz(J));
        raiz->de.raiz=j->iz.raiz;
        j->iz.raiz=NULL;
        // Mover (HijoIz (J), I);
        j->iz.raiz=raiz;
        raiz=NULL;
        //FE (J) = 0; FE (HijoIz (J))=0;
        j->fe=0; 
        j->iz.raiz->fe=0;
        //Mover (I,J);
        raiz=j;
        j=NULL;
        
    }else{//ROTACIÓN DI
    //cout<<"DI"<<endl;
        //Mover (J, HijoDe (I));
        j=raiz->de.raiz;
        raiz->de.raiz=NULL;
        //Mover (K, HijoIZ (J));
        k=j->iz.raiz;
        j->iz.raiz=NULL;
        //E2 = FE (K);
        E2=k->fe;
        //Mover (HijoDE (I), HijoIz(K));
        raiz->de.raiz=k->iz.raiz;
        k->iz.raiz=NULL;
        //Mover (HijoIz (J), HijoDe(K));
        j->iz.raiz=k->de.raiz;
        k->de.raiz=NULL;
        //Mover (HijoDe (K), J);
        k->de.raiz=j;
        j=NULL;
        //Mover (HijoIz (K), I);
        k->iz.raiz=raiz;
        raiz=NULL;
        //FE (K) = 0;
        k->fe=0;

        switch (E2)
        {
        case -1:
            // FE (HijoIz (K)) = 0; FE (HijoDe (K)) = 1;
            k->de.raiz->fe=1;
            k->iz.raiz->fe=0;
            break;
        case 1:
            // FE (HijoIzq (K)) = -1; FE (HijoDer (K)) = 0;
            k->de.raiz->fe=0;
            k->iz.raiz->fe=-1;
            break;
        case 0:
            // FE (HijoIzq (K)) = 0; FE (HijoDer (K)) = 0;
            k->de.raiz->fe=0;
            k->iz.raiz->fe=0;
            break;
        }
        //Mover (I, K);
        raiz=k;
        k=NULL;
    }

}

void TAVLCom::EquilibrarDerechaBorrar(){//
    TNodoAVL* j;
    TNodoAVL* k;
    int E2=0;
    if (raiz->de.raiz->fe == 0 || raiz->de.raiz->fe == 1){//ROTACIÓN DD
            //cout<<"  DD"<<endl;
        //Mover (J, HijoDe(I));
        j=raiz->de.raiz;
        raiz->de.raiz=NULL;
        //Mover (HijoDe(I), HijoIz(J));
        raiz->de.raiz=j->iz.raiz;
        j->iz.raiz=NULL;
        // Mover (HijoIz (J), I);
        j->iz.raiz=raiz;
        raiz=NULL;
        //FE (J) = 0; FE (HijoIz (J))=0;
        j->fe=0; 
        j->iz.raiz->fe=0;
        //Mover (I,J);
        raiz=j;
        j=NULL;
        
    }else{//ROTACIÓN DI
        //cout<<"  DI"<<endl;
        //Mover (J, HijoDe (I));
        j=raiz->de.raiz;
        raiz->de.raiz=NULL;
        //Mover (K, HijoIZ (J));
        k=j->iz.raiz;
        j->iz.raiz=NULL;
        //E2 = FE (K);
        E2=k->fe;
        //Mover (HijoDE (I), HijoIz(K));
        raiz->de.raiz=k->iz.raiz;
        k->iz.raiz=NULL;
        //Mover (HijoIz (J), HijoDe(K));
        j->iz.raiz=k->de.raiz;
        k->de.raiz=NULL;
        //Mover (HijoDe (K), J);
        k->de.raiz=j;
        j=NULL;
        //Mover (HijoIz (K), I);
        k->iz.raiz=raiz;
        raiz=NULL;
        //FE (K) = 0;
        k->fe=0;

        switch (E2)
        {
        case -1:
            // FE (HijoIz (K)) = 0; FE (HijoDe (K)) = 1;
            k->de.raiz->fe=1;
            k->iz.raiz->fe=0;
            break;
        case 1:
            // FE (HijoIzq (K)) = -1; FE (HijoDer (K)) = 0;
            k->de.raiz->fe=0;
            k->iz.raiz->fe=-1;
            break;
        case 0:
            // FE (HijoIzq (K)) = 0; FE (HijoDer (K)) = 0;
            k->de.raiz->fe=0;
            k->iz.raiz->fe=0;
            break;
        }
        //Mover (I, K);
        raiz=k;
        k=NULL;
    }

}



// Inserta el elemento TComplejo en el árbol
bool TAVLCom::InsertarAux(const TComplejo &c,bool &crece){
    bool creceHijoIz=false, creceHijoDe=false, insercionOK=false;
    if(Buscar(c))return false;
    
    if(EsVacio()){ 
        TNodoAVL *nodoAux= new TNodoAVL();
        nodoAux->item=c;
        raiz=nodoAux;
        crece=true;
        return true;
        
    }else{
        //casos de < y > modulo
        if(c.Mod()>(raiz->item).Mod()){
            insercionOK=(raiz->de).InsertarAux(c,creceHijoDe);
        }else if(c.Mod()<(raiz->item).Mod()){
            insercionOK=(raiz->iz).InsertarAux(c,creceHijoIz);
        }else{//caso == modulo
            //casos de < y > real
            if(c.Re()>(raiz->item).Re()){
                insercionOK=(raiz->de).InsertarAux(c,creceHijoDe);
            }else if(c.Re()<(raiz->item).Re()){
                insercionOK=(raiz->iz).InsertarAux(c,creceHijoIz);
            }else{//caso == real
                //casos de < y > imaginario
                if(c.Im()>(raiz->item).Im()){
                    insercionOK=(raiz->de).InsertarAux(c,creceHijoDe);
                }else if(c.Im()<(raiz->item).Im()){
                    insercionOK=(raiz->iz).InsertarAux(c,creceHijoIz);
                }
            }
            
        }   
    }
    
    
        //actualización de factores de equilibrio
    // solo es necesario actualizar si uno de los hijos ha crecido
    if (creceHijoIz || creceHijoDe){

        if(creceHijoIz && raiz->fe ==-1){
            
            EquilibrarIzquierdaInsertar();
            crece=false;
        }else if(creceHijoDe && raiz->fe ==1){
            
            EquilibrarDerechaInsertar();
            crece=false;
        }else if ((creceHijoIz && raiz->fe ==1) || (creceHijoDe && raiz->fe ==-1) ) {
            //Le indicamos al padre que este subárbol no ha crecido
            
            crece=false;
            raiz->fe=0;
        }else if((creceHijoIz && raiz->fe ==0) ){
            
            raiz->fe=-1;
            crece=true;
        }else if(creceHijoDe && raiz->fe ==0){
            
            raiz->fe=1;
            crece=true;
        }
        //..
    }else{
        
        //Le indicamos al padre que este subárbol no ha crecido
        crece=false;
    }

    return true;
}

bool TAVLCom::Insertar( const TComplejo &c){
    bool crece=false;
    return InsertarAux(c,crece);
}

// Devuelve TRUE si el elemento está en el árbol, FALSE en caso contrario
bool TAVLCom::Buscar(const TComplejo & com){
    if(raiz==NULL)return false;
    if(raiz->item==com){
        return true;
    }else{
        return (raiz->iz).Buscar(com) || (raiz->de).Buscar(com);
    }
}

int TAVLCom::numHijos(){
    
    int hijos=2;
    if((raiz->de).EsVacio()){
        
        hijos--;
    }
    if((raiz->iz).EsVacio()){
       
        hijos--;
    }
    return hijos;
}




// Borra el elemento en el árbol
TComplejo TAVLCom::mayorIz(){
    if((raiz->de).EsVacio()){
        TComplejo mayor(raiz->item);
        return mayor;
    }else{
        TComplejo aux((raiz->de).mayorIz());
        return aux;
    }
}


bool TAVLCom::BorrarAux(TComplejo &c,bool &decrece){
    bool decreceHijoIz=false, decreceHijoDe=false, borradoOK=false;
    if(EsVacio())return false;
    if(this->raiz->item==c){
        int hijos=-1;
        hijos=numHijos();
        if(hijos==0){
            
            raiz->~TNodoAVL();
            this->raiz=NULL;
            decrece=true;
            borradoOK=true;
            //cout<<"borrado"<<endl;
            return true;

        }else if(hijos==1){
            if(!(this->raiz->iz).EsVacio()){
                //cout<<"unoIz"<<endl;
                TNodoAVL* aux=raiz;
                raiz=raiz->iz.raiz;
                aux->iz.raiz=NULL;
                delete aux;
                aux=NULL;
                borradoOK=true;
                decrece=true;

                /*TComplejo aux((this->raiz->iz).raiz->item);
                this->raiz->item=aux;
                borradoOK=raiz->iz.BorrarAux((raiz->iz).raiz->item,decreceHijoIz );
                */
                /*
                this->nodo->de=(this->nodo->iz).nodo->de;
                this->nodo->iz=(this->nodo->iz).nodo->iz;
                 */
            }
            if(!(this->raiz->de).EsVacio()){
                //cout<<"unoDe"<<endl;
                TNodoAVL* aux=raiz;
                raiz=raiz->de.raiz;
                aux->de.raiz=NULL;
                delete aux;
                aux=NULL;
                borradoOK=true;
                decrece=true;

                /*
                TComplejo aux((this->raiz->de).raiz->item);
                this->raiz->item=aux;
                borradoOK=this->raiz->de.BorrarAux((this->raiz->de).raiz->item,decreceHijoDe);
                */
                /*
                this->nodo->iz=(this->nodo->de).nodo->iz;
                this->nodo->de=(this->nodo->de).nodo->de; 
                */
            }
        }else if(hijos==2){
            //cout<<"3"<<endl;

            TComplejo aux(raiz->iz.mayorIz());
            //cout<<raiz->item<<endl;
            borradoOK=raiz->iz.BorrarAux(aux,decreceHijoIz);
            
            raiz->item=aux;
        
        }
    }else{
        //cout<<"busca"<<endl;
        if((raiz->de).Buscar(c)){
            borradoOK=raiz->de.BorrarAux(c,decreceHijoDe);
            //cout<<"estaDe"<<decreceHijoDe<<" "<<raiz->item<<raiz->fe<<endl;
        }
        if((raiz->iz).Buscar(c)){
            //cout<<raiz->fe<<endl;
            borradoOK=raiz->iz.BorrarAux(c,decreceHijoIz);
            
            //cout<<"estaIz "<<decreceHijoIz<<" "<<raiz->fe<<endl;
        }
    }
    /*
    }else if((this->raiz->iz).Buscar(c)){
        cout<<"4"<<endl;
        (this->raiz->iz).BorrarAux(c,decreceHijoIz);
    }else if((this->raiz->de).Buscar(c)){
        (this->raiz->de).BorrarAux(c,decreceHijoDe);
    }*/
        

    //cout<<"rota"<<endl;
    //cout<<raiz->item<<raiz->fe<<endl;
    if (decreceHijoIz || decreceHijoDe){
        //cout <<"entra "<<raiz->fe<<endl;
        if(decreceHijoIz && raiz->fe ==1){

            if(raiz->de.raiz->fe==0 ){
                decrece=false;
            }else{
                decrece=true;
            }
            //cout<<"de"<<endl;
            EquilibrarDerechaBorrar();
            
        }else if(decreceHijoDe && raiz->fe ==-1){//
            
          
            if(raiz->iz.raiz->fe==0){
                decrece=false;
            }else{
                decrece=true;
            }
            //cout<<"iz"<<endl;
            EquilibrarIzquierdaBorrar();

            
        }else if ((decreceHijoDe && raiz->fe ==1) || (decreceHijoIz && raiz->fe ==-1) ) {//
            //cout<<"fe=0"<<endl;
            //Le indicamos al padre que este subárbol no ha crecido
            decrece=true;
            raiz->fe=0;
        }else if((decreceHijoDe && raiz->fe ==0) ){//
            raiz->fe=-1;
            decrece=false;
        }else if(decreceHijoIz && raiz->fe ==0){//
            raiz->fe=1;
            decrece=false;
        }
        //..
    }else{
        //Le indicamos al padre que este subárbol no ha crecido
        decrece=false;
    }
    return borradoOK;
}

// Borra un TComplejo del árbol AVL
bool TAVLCom::Borrar(TComplejo &c){
    bool decrece=false;
    return BorrarAux(c,decrece);
}
// Devuelve la altura del árbol (la altura de un árbol vacío es 0)
int TAVLCom::Altura(){
    int a=0,b=0;

    if(raiz!=NULL){
        a=(raiz->iz).Altura();
        b=(raiz->de).Altura();
        return (1 + (a < b ? b : a));
    }
    return 0;

}
// Devuelve el elemento TComplejo raíz del árbol AVL
TComplejo TAVLCom::Raiz()const{
    if (raiz != NULL) return raiz -> item;
        else{
            TComplejo vacio;
            return vacio;
        } 
}
// Devuelve el número de nodos del árbol (un árbol vacío posee 0 nodos)
int TAVLCom::Nodos()const{
    if(EsVacio()){
        return 0;
    }else{
        return (raiz->iz).Nodos()+(raiz->de).Nodos()+1;
    }
}
// Devuelve el número de nodos hoja en el árbol (la raíz puede ser nodo hoja)
int TAVLCom::NodosHoja(){
    if(EsVacio())return 0;
    if((raiz->iz).EsVacio()&&(raiz->de).EsVacio() ){
        return 1;
    }else{
        return (raiz->iz).NodosHoja()+(raiz->de).NodosHoja();
    }
}
TVectorCom TAVLCom::Inorden()const{
    int pos=1;
    TVectorCom v(Nodos());
    InordenAux(v,pos);
    return v;

}

TVectorCom TAVLCom::Preorden(){
    int pos=1;
    TVectorCom v(Nodos());
    PreordenAux(v,pos);
    return v;
}

TVectorCom TAVLCom::Postorden(){
    int pos=1;
    TVectorCom v(Nodos());
    PostordenAux(v,pos);
    return v;
}


ostream & operator<<(ostream &o, const TAVLCom &arbol){
    TVectorCom v(arbol.Inorden());

    o<<v;
    return o;
}