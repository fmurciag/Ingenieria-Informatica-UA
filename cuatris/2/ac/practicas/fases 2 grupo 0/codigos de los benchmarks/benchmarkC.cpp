#include<cstdlib>
#include <stdlib.h> 
#include <time.h>
#include <iostream>
#include <windows.h>

using namespace std;

const unsigned int M = 100;//Cambiar para modificar el tamaño de la matriz


double PCFreq = 0.0;
__int64 CounterStart = 0;

void StartCounter()
{
    LARGE_INTEGER li;
    if (!QueryPerformanceFrequency(&li))
        printf("QueryPerformanceFrequency failed!\n");

    PCFreq = double(li.QuadPart);

    QueryPerformanceCounter(&li);
    CounterStart = li.QuadPart;
}

// Obtenemos el tiempo del contador.
double GetCounter()
{
    LARGE_INTEGER li;
    QueryPerformanceCounter(&li);
    return double(li.QuadPart - CounterStart) / PCFreq;
}


int main() {
    long double suma = 0;
    int a = 0;
    int b = 0;
    for (int g = 0; g < 10;g++)
    {


        long double tiempo = 0;


        for (int l = 0; l < 500; l++) {

            int vector[M][M];
            int cursor = 0;

            srand(time(NULL));//Semilla para randomizar

            for (unsigned int i = 0; i < M; i++)
                for (unsigned int j = 0; j < M; j++)
                    vector[i][j] = rand() % 20 + 1;

            StartCounter();
            int aux[M][M];
            for (int i = 0; i < M; i++)
            {
                for (int j = 0; j < M; j++)
                {
                    aux[i][j] = 0;
                    for (int k = 0; k < M - 1; k++)
                    {
                        aux[i][j] = aux[i][j] + vector[i][k] * vector[k][j];
                    }
                }
            }
          
            
            tiempo += GetCounter();

            

        }


        cout << "Tiempo total: " << tiempo << endl;
        suma += tiempo;
    }
    cout << "media: " << suma / 10<<endl;
    system("pause");

    return 0;
}