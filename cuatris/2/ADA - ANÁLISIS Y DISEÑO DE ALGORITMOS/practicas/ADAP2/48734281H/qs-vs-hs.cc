//Francisco Joaquin Murcia Gomez 48734281H

/*
ADA. 2019-20
Pràctica 2 de laboratori: Empirical analysis by means of program-steps account of two sorting algorithms: Middle-Quicksort and Heapsort.

Objectiu de la pràctica (a més dels especificats en la pràctica anterior):
- Conèixer els algorismes d'ordenació quicksort i heapsort
- Utilitzar el mètode del compte de passos de manera empírica per a obtenir la complexitat temporal d'un algorisme.
- Comprender la necesidad de generar un número (significativo desde el punto de vista estadístico) de instancias aleatorias para el cálculo de la complejidad temporal promedio cuando el algoritmo presenta caso mejor y peor.
- Entender que la complejidad temporal (y espacial) de un algoritmo puede depender del tipo de instancias del problema que se le presentan.
*/
#include <unistd.h>
#include <iostream>
#include <math.h>
#include <chrono>

using namespace std;
using namespace chrono;

double STEP=0;

//--------------------------------------------------------------
// Middle Quick Sort
void 
middle_QuickSort(int * v, long left, long right){
long i,j;
int pivot;
    STEP++;
	if (left<right){
		i=left; j=right;
		pivot=v[(i+j)/2];
		do{
			while (v[i]<pivot){
                STEP++;
                i++;
            } 
			while (v[j]>pivot){
                STEP++;
                 j--;
            } 
           
			if (i<=j){
                STEP++; 
				swap(v[i], v[j]);
				i++; j--;
			}
            
		}while (i<=j);
		if (left < j)  middle_QuickSort(v,left,j);
		if (i < right) middle_QuickSort(v,i,right);
	}
}

//--------------------------------------------------------------                
//HEAPSORT
// Procedure sink used by Heapsort algorith
// Sink an element (indexed by i) in a tree to keep the heap property. n is the size of the heap. 

void sink(int *v, size_t n, size_t i)
{
size_t largest;
size_t l, r; //left and right child

do{
    STEP++;

   	largest = i;  // Initialize largest as root
    l = 2*i + 1;  // left = 2*i + 1
    r = 2*i + 2;  // right = 2*i + 2
 
    // Is left child larger than root?
    if (l < n && v[l] > v[largest])
        largest = l;
 
    // Is right child larger than largest so far?
    if (r < n && v[r] > v[largest])
        largest = r;
 
    // If largest is still root then the process is done
    if (largest == i) break;
    
	// If not, swap new largest with current node i and repeat the process with childs. 
    swap(v[i], v[largest]);
 	i=largest;  
   } while (true);
}
 
//--------------------------------------------------------------                
// Heapsort algorithm (ascending sorting)
void heapSort(int *v, size_t n)
{
    // Build a MAX-HEAP with the input array
    for (size_t i = n / 2 - 1; ; i--){
        sink(v, n, i);
		if (i==0) break; //as size_t is unsigned type
	}	

	// A this point we have a HEAP on the input array, let's take advantage of this to sort the array:
    // One by one swap the first element, which is the largest (max-heap), with the last element of the vector and rebuild heap by sinking the new placed element on the begin of vector.  
    for (size_t i=n-1; i>0; i--)
    {
		// Move current root to the end.
        swap(v[0], v[i]);
 		// Now largest element is at the end but do not know if the element just moved to the beginning is well placed, so a sink process is required.
		// Note that, at the same time, the HEAP size is reduced one element by one.
        sink(v, i, 0);
		// The procedure ends when HEAP has only one element, the lower of the input array
    }
}



int
main(void){

//    srand(getpid());
    srand(0);

    cout << "# QUICKSORT VERSUS HEAPSORT."
        << endl
        << "# Average processing Msteps (millions of program steps)"
        << endl
        <<"# Number of samples (arrays of integer): 30"
        <<endl
        <<"#                RANDOM ARRAYS       SORTED ARRAYS     REVERSE SORTED ARRAYS"
        <<endl
        << "#            -------------------  -------------------  ---------------------"
        <<endl
        <<"#   Size     QuickSort  HeapSort  QuickSort  HeapSort  QuickSort   HeapSort"
        <<endl
        <<"#==========================================================================="
        << endl;

    

    for (int exp = 15; exp <= 21; exp++){
        size_t size = size_t( pow(2,exp) );
        int* v = new int [size];
        if (!v){
            cerr << "Error, not enough memory!" << endl;
            exit (EXIT_FAILURE);  
        }

        double sumaTiempo=0;
    

        cout << size << "\t     " << std::flush;

        for(int veces=0; veces<30;veces++){//quickSort

            for (size_t j = 0; j < size; j++) 
            v[j] = rand(); 

            STEP=0;
            middle_QuickSort(v,0,size-1);
            double tiempo= (STEP/1000000) ;
            
            sumaTiempo+=tiempo;
            
        }
        cout << round(1000*sumaTiempo/30)/1000  << "\t"<<std::flush;
        sumaTiempo=0;

        for(int veces=0; veces<30;veces++){//heapSort

            for (size_t j = 0; j < size; j++) 
            v[j] = rand(); 

            STEP=0;
            heapSort(v ,size);
            

            double tiempo= (STEP/1000000) ;
            
            sumaTiempo+=tiempo;
            
        }
        cout << round(1000*sumaTiempo/30)/1000  << "\t  "<<std::flush;
        sumaTiempo=0;

        for(int veces=0; veces<30;veces++){//quickSort ordenado

            for (size_t j = 0; j < size; j++) 
            v[j] = j; 

            STEP=0;
            middle_QuickSort(v,0,size-1);
            double tiempo= (STEP/1000000) ;
            
            sumaTiempo+=tiempo;
            
        }
        cout << round(1000*sumaTiempo/30)/1000  << "      "<<std::flush;
        sumaTiempo=0;

        for(int veces=0; veces<30;veces++){//heapSort ordenado

            for (size_t j = 0; j < size; j++) 
            v[j] = j; 

            STEP=0;
            heapSort(v ,size);
            

            double tiempo= (STEP/1000000) ;
            
            sumaTiempo+=tiempo;
            
        }
        cout << round(1000*sumaTiempo/30)/1000  << "\t"<<std::flush;
        sumaTiempo=0;

        for(int veces=0; veces<30;veces++){//quickSort inverso

            for (size_t j = 0; j < size; j++) 
            v[j] = size-j; 

            STEP=0;
            middle_QuickSort(v,0,size-1);
            double tiempo= (STEP/1000000) ;
            
            sumaTiempo+=tiempo;
            
        }
        cout << round(1000*sumaTiempo/30)/1000 << " \t    "<<std::flush;
        sumaTiempo=0;

        for(int veces=0; veces<30;veces++){//heapSort inverso

            for (size_t j = 0; j < size; j++) 
            v[j] = size-j; 

            STEP=0;
            heapSort(v ,size);
            

            double tiempo= (STEP/1000000)  ;
            
            sumaTiempo+=tiempo;
            
        }
        cout << round(1000*sumaTiempo/30)/1000  << endl<<std::flush;
        sumaTiempo=0;

        for (size_t i = 1; i < size; i++)
            if (v[i] < v[i-1]){ 
                cerr << "Panic, array not sorted! " << i << endl; 
                exit(EXIT_FAILURE);            
            }

        delete[] v; 
    }
     
   
}