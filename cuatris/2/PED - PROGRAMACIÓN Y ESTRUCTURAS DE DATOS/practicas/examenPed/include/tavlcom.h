//francisco joaquin murcia gomez 48734281H
#ifndef _TAVLCom_
#define _TAVLCom_

#include <iostream>
#include <queue>
#include "tvectorcom.h"
using namespace std;
class TNodoAVL;
class TAVLCom
{
private:
    // Puntero al nodo
    TNodoAVL *raiz;
    // AUXILIAR : Devuelve el recorrido en inorden
    void InordenAux(TVectorCom &,  int &)const;
    // AUXILIAR : Devuelve el recorrido en preorden
    void PreordenAux(TVectorCom &,  int &);
    // AUXILIAR : Devuelve el recorrido en postorden
    void PostordenAux(TVectorCom &, int &);
    void Copiar (const TAVLCom & );
    void EquilibrarIzquierdaBorrar();
    void EquilibrarDerechaBorrar();
    void EquilibrarIzquierdaInsertar();
    void EquilibrarDerechaInsertar();
    bool igual( TAVLCom );
    bool InsertarAux(const TComplejo &,bool& );
    int numHijos();
    TComplejo mayorIz();
    bool BorrarAux(TComplejo &,bool& );
    
public:
    // Constructor por defecto
    TAVLCom ();
    // Constructor de copia
    TAVLCom (TAVLCom &);
    // Destructor
    ~TAVLCom ();
    // Sobrecarga del operador asignación
    TAVLCom & operator=( TAVLCom &);

    // Sobrecarga del operador igualdad
    bool operator==( TAVLCom &);
    // Sobrecarga del operador desigualdad
    bool operator!=( TAVLCom &);
    // Devuelve TRUE si el árbol está vacío, FALSE en caso contrario
    bool EsVacio()const;
    // Inserta el elemento TComplejo en el árbol
    bool Insertar(const TComplejo &);
    // Devuelve TRUE si el elemento está en el árbol, FALSE en caso contrario
    bool Buscar(const TComplejo &);
    // Borra un TComplejo del árbol AVL
    bool Borrar(TComplejo &);
    // Devuelve la altura del árbol (la altura de un árbol vacío es 0)
    int Altura();
    // Devuelve el elemento TComplejo raíz del árbol AVL
    TComplejo Raiz()const;
    // Devuelve el número de nodos del árbol (un árbol vacío posee 0 nodos)
    int Nodos()const;
    // Devuelve el número de nodos hoja en el árbol (la raíz puede ser nodo hoja)
    int NodosHoja();
    // Devuelve el recorrido en inorden sobre un vector
    TVectorCom Inorden()const;
    // Devuelve el recorrido en preorden sobre un vector
    TVectorCom Preorden();
    // Devuelve el recorrido en postorden sobre un vector
    TVectorCom Postorden();
    // Sobrecarga del operador salida

    friend ostream & operator<<(ostream &, const TAVLCom &);
};




class TNodoAVL
{
    friend class TAVLCom;
private:
    // El elemento del nodo
    TComplejo item;
    // Subárbol izquierdo y derecho
    TAVLCom iz, de;
    // Factor de equilibrio
    int fe ;
public:
    // Constructor por defecto
    TNodoAVL ();
    // Constructor de copia
    TNodoAVL (TNodoAVL &);
    // Destructor
    ~TNodoAVL ();
    // Sobrecarga del operador asignación
    TNodoAVL & operator=( TNodoAVL &);
};

#endif
