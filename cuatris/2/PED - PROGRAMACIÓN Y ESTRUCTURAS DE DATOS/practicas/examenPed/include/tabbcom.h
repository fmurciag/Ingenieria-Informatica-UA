//francisco joaquin murcia gomez 48734281H
#ifndef _TABBCom_
#define _TABBCom_

#include <iostream>
#include <queue>
#include "tvectorcom.h"
#include "tlistacom.h"
using namespace std;
class TNodoABB;
class TABBCom{
    
    private:
        // Puntero al nodo
        TNodoABB *nodo;
        // AUXILIAR : Devuelve el recorrido en inorden
        void InordenAux(TVectorCom &,  int &);
        // AUXILIAR : Devuelve el recorrido en preorden
        void PreordenAux(TVectorCom &,  int &);
        // AUXILIAR : Devuelve el recorrido en postorden
        void PostordenAux(TVectorCom &, int &);

        void Copiar (const TABBCom & );

        bool igual( TABBCom );

        int numHijos();

        void situarse(TComplejo &c);

        TComplejo mayorIz();

        //examen   quita los pares
        TListaCom quitarPares(TListaCom  );


    public:

        
        // Constructor por defecto
        TABBCom ();
        // Constructor de copia
        TABBCom (const TABBCom &);
        // Destructor
        ~TABBCom ();
        // Sobrecarga del operador asignación
        TABBCom & operator=( const TABBCom &);
        // Sobrecarga del operador igualdad
        bool operator==( TABBCom &);
        // Devuelve TRUE si el árbol está vacío, FALSE en caso contrario
        bool EsVacio()const;
        // Inserta el elemento en el árbol
        bool Insertar( TComplejo &);
        // Borra el elemento en el árbol
        bool Borrar( TComplejo &);
        // Devuelve TRUE si el elemento está en el árbol, FALSE en caso contrario
        bool Buscar(TComplejo &);
        // Devuelve el elemento en la raíz del árbol
        TComplejo Raiz();
        // Devuelve la altura del árbol (la altura de un árbol vacío es 0)
        int Altura();
        // Devuelve el número de nodos del árbol (un árbol vacío posee 0 nodos)
        int Nodos()const;
        // Devuelve el número de nodos hoja en el árbol (la raíz puede ser nodo hoja)
        int NodosHoja();
        // Devuelve el recorrido en inorden
        TVectorCom Inorden();
        // Devuelve el recorrido en preorden
        TVectorCom Preorden();
        // Devuelve el recorrido en postorden
        TVectorCom Postorden();
        // Devuelve el recorrido en niveles
        TVectorCom Niveles()const;
        // Sobrecarga del operador salida

        //examen
        TComplejo* MostrarNiveles (TListaCom & );


        friend ostream & operator<<(ostream &, const TABBCom &);
};

class TNodoABB{
    friend class TABBCom;
    private: 
        // El elemento del nodo
        TComplejo item;
        // Subárbol izquierdo y derecho
        TABBCom iz, de;
    public:
        // Constructor por defecto
        TNodoABB ();
        // Constructor de copia
        TNodoABB (const TNodoABB &);
        // Destructor
        ~TNodoABB ();
        // Sobrecarga del operador asignación
        TNodoABB & operator=( const TNodoABB &);
};



#endif
