//francisco joaquin murcia gomez 48734281H
#ifndef _TVectorCom_
#define _TVectorCom_ 
#include <iostream>
#include "tcomplejo.h"
using namespace std;
class TVectorCom{
    private:
	TComplejo error;
        TComplejo *c;
        int tamano;
    public:
        // Constructor por defecto
        TVectorCom();
        // Constructor a partir de un tamaño
        TVectorCom (int);
        // Constructor de copia
        TVectorCom (const TVectorCom &);
        // Destructor
        ~TVectorCom ();
        // Sobrecarga del operador asignación
        TVectorCom & operator=( const TVectorCom &);


        // Sobrecarga del operador igualdad
        bool operator==( const TVectorCom &)const;
        // Sobrecarga del operador desigualdad
        bool operator!=( const TVectorCom &)const;
        // Sobrecarga del operador corchete (parte IZQUIERDA)
        TComplejo & operator[](int);
        // Sobrecarga del operador corchete (parte DERECHA)
        TComplejo operator[](int) const;
        // Tamaño del vector (posiciones TOTALES)
        int Tamano();
        // Cantidad de posiciones OCUPADAS (TComplejo NO VACIO) en el vector
        int Ocupadas();
        // Devuelve TRUE si existe el TComplejo en el vector
        bool ExisteCom(const TComplejo &);
        // Mostrar por pantalla los elementos TComplejo del vector con PARTE REAL IGUAL O POSTERIOR al argumento
        void MostrarComplejos( double);
        // REDIMENSIONAR el vector de TComplejo
        bool Redimensionar( int);


        // Sobrecarga del operador salida
        friend ostream & operator<<(ostream &, const TVectorCom &);
};

#endif
