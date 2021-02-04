#include <iostream>
#include <stdio.h>
#include <stdlib.h>
using namespace std;

int main()
{
	int vector[20] = {2,30,45,2,7,4,8,1,4,5,7,9,5,4,0,6,7,6,1,3};
	
	__asm
	{
			mov esi, 0//contador posicion inicial del vector; controla la cantidad de veces que ejecuta el algoritmo
			mov edi, 19//contador posicion final del vector, le llamaremos n; controla la posicion de los accesos al vector

		recorrido:
			sub edi, 1//le resta 1 a n
			mov edx, [vector+4*edi]//accede a la posicion n-1 del vector y lo guarda en el registro edx
			add edi, 1//restable n
			mov eax, [vector+4*edi]//accede a la posicion n del vector y lo guarda en el registro eax
			cmp edx, eax//comparamos  los registros correspondientes a las posiciones n y n-1 del vector
			jl intercambio //si "lo que hay en n del vector" > "lo que hay en n-1 del vector" intercambia los valores en la etiqueta "intercambio"
			jmp decrementar // si no, decrementa n en la etiqueta "decrementar"

		incremento:	
			inc esi// aumentamos el contador de inicio de vector 
			mov edi, 19 //restablecemos el contador de final en 19
			cmp esi, 20//compara 20 con el contador de inicio del vector
			jl recorrido//si esi < 20 salta a la etiqueta "recorrido" y sigue ordenando el vector
			jmp fin//si no, salta a la etiqueta "fin"

		intercambio:
			mov [vector+4*edi], edx// guarda el numero menor (edx) en la posicion que ocupaba eax en el vector
			sub edi, 1//le resta 1 a n
			mov [vector + 4 * edi], eax// guarda el numero mayor (eax) en la posicion que ocupaba edx en el vector
			add edi, 1//restable n
			jmp decrementar // salta a la etiqueta "decrementar"

		decrementar:
			dec edi// decrementa en 1 el contador de final de vlector
			cmp edi, esi// comparamos los contadores
			jg recorrido// si edi>esi sigue recorriendo el vector saltando a la etiqueta "recorrido"
			jmp incremento// si no, salta a "incremento"

		fin://etiqueta para finalizar el programa

	}
	for (int i = 0; i < 20; i++) {
		printf_s(" %d ", vector[i]);
	}
}

