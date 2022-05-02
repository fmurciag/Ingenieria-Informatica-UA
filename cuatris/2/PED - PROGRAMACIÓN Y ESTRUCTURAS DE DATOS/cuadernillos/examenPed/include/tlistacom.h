//francisco joaquin murcia gomez 48734281H
#ifndef _TListaCom_
#define _TListaCom_

#include <iostream>
#include "tcomplejo.h"
using namespace std;
class TListaNodo
{
    friend class TListaPos;
    friend class TListaCom;
private:
    TComplejo error;
    // El elemento del nodo
    TComplejo e;
    // El nodo anterior
    TListaNodo *anterior;
    // El nodo siguiente
    TListaNodo *siguiente;
public:
    // Constructor por defecto
    TListaNodo ();
    // Constructor de copia
    TListaNodo (const TListaNodo &);
    // Destructor
    ~TListaNodo ();
    // Sobrecarga del operador asignación
    TListaNodo & operator=( const TListaNodo &);

};


class TListaPos
{
    friend class TListaCom;
private:
    // Puntero a un nodo de la lista
    TListaNodo *pos;
public:
    // Constructor por defecto
    TListaPos ();
    // Constructor de copia
    TListaPos (const TListaPos &);
    // Destructor
    ~TListaPos ();
    // Sobrecarga del operador asignación
    TListaPos& operator=( const TListaPos &);
    // Sobrecarga del operador igualdad
    bool operator==( const TListaPos &)const ;
    // Sobrecarga del operador desigualdad
    bool operator!=(const TListaPos &)const;
    // Devuelve la posición anterior
    TListaPos Anterior()const;
    // Devuelve la posición siguiente
    TListaPos Siguiente()const;
    // Devuelve TRUE si la posición no apunta a una lista, FALSE en caso contrario
    bool EsVacia()const;
};


class TListaCom
{
    
    private:
        // Primer elemento de la lista
        TListaNodo *primero;
        // Ultimo elemento de la lista
        TListaNodo *ultimo;
    public:
        // Constructor por defecto
        TListaCom ();
        // Constructor de copia
        TListaCom ( TListaCom &);
        // Destructor
        ~ TListaCom ();
        // Sobrecarga del operador asignación
        TListaCom & operator=(const TListaCom &);
        // Sobrecarga del operador
        bool operator==(const TListaCom &)const;
        // Sobrecarga del operador
        bool operator!=(const TListaCom &)const;
        // Sobrecarga del operador suma
        TListaCom operator+(TListaCom &);
        // Sobrecarga del operador resta
        TListaCom operator-(TListaCom &);
        // Devuelve true si la lista está vacía, false en caso contrario
        bool EsVacia()const;
        // Inserta el elemento en la cabeza de la lista
        bool InsCabeza(const TComplejo &);
        // Inserta el elemento a la izquierda de la posición indicada
        bool InsertarI(const TComplejo &,const TListaPos &);
        // Inserta el elemento a la derecha de la posición indicada
        bool InsertarD(const TComplejo &,const TListaPos &);
        // Busca y borra la primera ocurrencia del elemento
        bool Borrar(TComplejo &);
        // Busca y borra todas las ocurrencias del elemento
        bool BorrarTodos( TComplejo &);
        // Borra el elemento que ocupa la posición indicada
        bool Borrar( TListaPos &);
        // Obtiene el elemento que ocupa la posición indicada
        TComplejo Obtener(const TListaPos &)const;
        // Devuelve true si el elemento está en la lista, false en caso contrario
        bool Buscar(TComplejo &);
        // Devuelve la longitud de la lista
        int Longitud()const;
        // Devuelve la primera posición en la lista
        TListaPos Primera()const;
        // Devuelve la última posición en la lista
        TListaPos Ultima()const;
        // Sobrecarga del operador salida
        friend ostream & operator<<(ostream &, const TListaCom &);

};

#endif
