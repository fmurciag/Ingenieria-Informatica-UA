
//francisco joaquin murcia gomez 48734281H

#include"tlistacom.h"
#include <iostream>

using namespace std;

TListaNodo::TListaNodo():error(){
    TComplejo com;
    this->e=com;
    this->anterior=NULL;
    this->siguiente=NULL;

}
TListaNodo::TListaNodo (const TListaNodo &n):error(){
    this->e=n.e;
    this->anterior=NULL;
    this->siguiente=NULL;
}
TListaNodo::~TListaNodo (){
    anterior=NULL;
    siguiente=NULL;
}
TListaNodo & TListaNodo::operator=(const TListaNodo &n){
    
    if(this!=&n){
        
        (*this).~TListaNodo();
        this->e=n.e;
        this->anterior=n.anterior;
        this->siguiente=n.siguiente;  
        
    }
    
    return *this; 
    
}








//pos

TListaPos::TListaPos (){
    this->pos=NULL;
}
TListaPos::TListaPos (const TListaPos & p){
    this->pos=p.pos;
}
TListaPos::~TListaPos(){
    pos=NULL;
}
TListaPos& TListaPos::operator=(const TListaPos & p){
    
    if(this!=&p){
        (*this).~TListaPos();
        this->pos=p.pos;
        
    }
    return *this;
    
}
bool TListaPos::operator==(const TListaPos &p )const{
    if(this->pos==p.pos)return true;
    return false;
}
bool TListaPos::operator!=(const TListaPos &p )const{
    return !(*this==p);
}
TListaPos TListaPos::Anterior()const{
    TListaPos p;
    if(this->EsVacia())return p;
    p.pos=pos->anterior;

    return p;
}
TListaPos TListaPos::Siguiente()const{
    TListaPos p;
    if(this->EsVacia())return p;
    p.pos=pos->siguiente;
    return p;
}
bool TListaPos::EsVacia()const{
    if(this->pos==NULL)return true;
    return false;
}

//listaCom








TListaCom & TListaCom::operator=(const TListaCom &l){
    this->primero=l.primero;
    this->ultimo=l.ultimo;
    return *this;
}
bool TListaCom::operator==(const TListaCom  &l)const{
    TListaPos pos2=l.Primera();
    TListaPos pos1=Primera();
    if((EsVacia()==true)&&(l.EsVacia()==false)||(EsVacia()==false)&&(l.EsVacia()==true)){//si una es vacia y la otra no
        return false;
    }else if((EsVacia()==true)&&(l.EsVacia()==true)){//dos vacias
        return true;
    }else if(Longitud()!=l.Longitud()){//mismo tamaño
        return false;
    }else{
        while(!pos1.EsVacia()){
            if(pos1!=pos2) return false;
            pos1=pos1.Siguiente();
            pos2=pos2.Siguiente();
        }
        return true;
    }
    
    
}


bool TListaCom::operator!=(const TListaCom  &l)const{
    return !(*this==l);
}

TListaPos TListaCom::Primera()const{
    TListaPos pri;
    
    pri.pos=primero;
    
    return pri;
    
    
}
TListaPos TListaCom::Ultima()const{
    TListaPos ulti;
    ulti.pos=ultimo;
    return ulti;
}
bool TListaCom::InsCabeza(const TComplejo & i){
    TListaNodo* aux = new TListaNodo;
    if(aux==NULL)return false;
    aux->e = i;
    if(this->primero == NULL) {
        aux->siguiente = NULL;
        aux->anterior=NULL;
        this->primero = aux;
        this->ultimo = aux;
       
    }
    else {
        
        aux->siguiente = this->primero;
        this->primero = aux;
        this->primero->siguiente->anterior=aux;
        
        
        
        
        
    }
    return true;
}
TComplejo TListaCom::Obtener(const TListaPos &posicion)const{
    TComplejo err;
    if(posicion.EsVacia())return err;
    if(!EsVacia()){
        
        return posicion.pos->e;
    }
    
    return err;
    
    
}

int TListaCom::Longitud()const{
    TListaPos p;
    int longitud=0;
    p=Primera();
    while(p.pos!=NULL){
        longitud++;
        p=p.Siguiente();
    }
    return longitud;
}


ostream & operator<<(ostream & o, const TListaCom &lista){
    TListaPos posicion=lista.Primera();
    o<<"{";
    int longitud=lista.Longitud();
    for(int i=0;i<longitud;i++){
        
        o<<lista.Obtener(posicion);
        if(!posicion.Siguiente().EsVacia())o<<" ";
        posicion=posicion.Siguiente();
           
    }
    
    o<<"}";
    return o;
}
bool TListaCom::InsertarI(const TComplejo & com,const TListaPos &posicion){
    if(posicion.EsVacia()) return false;

    TListaNodo *meter=new TListaNodo;//reservar memoria
    if(meter==NULL){
        return false;
    }
    if(this->primero==posicion.pos){//miro si es la primera pos
        return InsCabeza(com);
    }
  
    
    //enlazo el nodo
    meter->e=com;
    meter->anterior=posicion.Anterior().pos;
    meter->siguiente=posicion.pos;

    //cambio los punteros de los otros
    posicion.pos->anterior->siguiente=meter;
    posicion.pos->anterior=meter;
    
    return true;
}

bool TListaCom::InsertarD(const TComplejo & com,const TListaPos &posicion){
    if(posicion.EsVacia())return false;

    TListaNodo *meter=new TListaNodo;//reservar memoria
    if(meter==NULL){
        return false;
    }
    if(this->ultimo==posicion.pos){//miro si es la ultima pos
    
        meter->e=com;
        meter->siguiente=NULL;
        meter->anterior=posicion.pos;

        //cambio los punteros de los otros
        posicion.pos->siguiente=meter;
        this->ultimo=meter;
        
    }else{
        
        //enlazo el nodo nuevo
        meter->e=com;
        meter->siguiente=posicion.Siguiente().pos;
        meter->anterior=posicion.pos;

        //cambio los punteros de los otros
        posicion.pos->siguiente->anterior=meter;
        posicion.pos->siguiente=meter;
    
      
    }
    return true;


}
bool TListaCom::EsVacia()const{
    if(primero==NULL )return true;
    return false;

}

bool TListaCom::Buscar(TComplejo & com){
    TListaPos posicion=Primera();
    while(!posicion.EsVacia()){
        if(Obtener(posicion)==com){
            return true;
        }
        posicion=posicion.Siguiente();
    }
    return false;
}

bool TListaCom::Borrar( TListaPos &posicion){
    if(posicion.EsVacia())return false;
    if(EsVacia()) return false;
    TListaPos aux=posicion;

    if(aux.Anterior().EsVacia() && aux.Siguiente().EsVacia()){//solo un elemento
        this->primero=NULL;
        this->ultimo=NULL;
    }else if(!aux.Anterior().EsVacia() && aux.Siguiente().EsVacia()){//ultimo elemento
        aux.Anterior().pos->siguiente=NULL;
        this->ultimo=aux.Anterior().pos;
    }else if(aux.Anterior().EsVacia() && !aux.Siguiente().EsVacia()){//primer elemento
        aux.Siguiente().pos->anterior=NULL;
        this->primero=aux.Siguiente().pos;
    }else{                                                          //en medio
        aux.Siguiente().pos->anterior=aux.Anterior().pos;
        aux.Anterior().pos->siguiente=aux.Siguiente().pos;
    }
    delete aux.pos;
    aux.pos=NULL;
    return true;

    
}

bool TListaCom::BorrarTodos( TComplejo &com){
    if(EsVacia()) return false;
    bool ok=false;
    while(Borrar(com)){
        ok=true;
    }
    return ok;

}

bool TListaCom::Borrar( TComplejo & com){
    if(EsVacia()) return false;
    TListaPos posicion=Primera();
    while(!posicion.EsVacia()){
        if(Obtener(posicion)==com){
            return Borrar(posicion);
        }
        posicion=posicion.Siguiente();
    }
    return false;
}

TListaCom TListaCom::operator+(TListaCom &lista){
    TListaCom nuevaLista(*this);
    TListaPos posicion=lista.Primera();
    int tam=lista.Longitud();
    for(int i=0;i<tam;i++){
    //while(!posicion.EsVacia()){
        
        
        nuevaLista.InsertarD(lista.Obtener(posicion),nuevaLista.Ultima());
        
        
        posicion=posicion.Siguiente();
    }
    return nuevaLista;

}

TListaCom TListaCom::operator-(TListaCom &lista){
    TListaCom nuevaLista(*this);
    TListaPos posicion=lista.Primera();
    int tam=lista.Longitud();
    for(int i=0;i<tam;i++){
    //while(!posicion.EsVacia()){
        TComplejo com=lista.Obtener(posicion);

        nuevaLista.BorrarTodos(com);
        
        posicion=posicion.Siguiente();
    }
    return nuevaLista;

}

TListaCom::TListaCom(){
    this->primero=NULL;
    this->ultimo=NULL;
}

TListaCom::TListaCom( TListaCom & lista){
   
    if(!lista.EsVacia()){
        TListaNodo *n=new TListaNodo;
        n->e=lista.Obtener(lista.Primera());
        this->primero=n;
        this->ultimo=n;
        TListaPos posicion=lista.Primera();
        int tam=lista.Longitud();
        
        for(int i=1;i<tam;i++){
        //while(!posicion.EsVacia()){
            posicion=posicion.Siguiente();
            this->InsertarD(lista.Obtener(posicion),this->Ultima());
            
            
            
        }
        





       /*
        TListaPos aux;
        
        aux = lista.Primera();
        
        primero=aux.pos;
        int tam=lista.Longitud();
       
       
        for(int i=1;i<tam;i++){
            TListaPos auxx=aux.Siguiente();
            aux.pos->siguiente=auxx.pos;
            aux=aux.Siguiente();
            
        }
        ultimo=aux.pos;
       
       */
    }else{
        this->primero=NULL;
        this->ultimo=NULL;
    }

}

TListaCom::~ TListaCom (){
    
    this->primero=NULL;
    this->ultimo=NULL;
    

}
