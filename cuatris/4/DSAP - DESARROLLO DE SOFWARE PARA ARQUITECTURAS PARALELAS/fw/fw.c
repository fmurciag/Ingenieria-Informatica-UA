// FRANCISCO JOAQUIN MURCIA GOMEZ

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <mpi.h>
#include <malloc.h>

#define nmax 1000
#define maxnumprocs 8

int main(int argc, char **argv) {
   float **Crear_matriz_pesos_consecutivo(int, int);
   int **Crear_matriz_caminos_consecutivo(int, int);
   void printMatrizCaminos(int **, int, int);
   void printMatrizPesos(float **, int, int);
   void calcula_camino(float **, int **, int);
   void Definir_Grafo(int, float **, int **);

   int myrank, numprocs, nfilas, resto, nlocal, sender;
   int i, j, k, n;
   double t1,t2;
   float **dist, *aux; 
   int **caminos, *auxC;
   int fila_k;

   MPI_Status estado;

   MPI_Init(&argc, &argv);
   MPI_Comm_rank(MPI_COMM_WORLD, &myrank);
   MPI_Comm_size(MPI_COMM_WORLD, &numprocs);

   if(myrank == 0){

      if (numprocs>maxnumprocs){
         printf("El numero de procesos no puede ser mayor que 8\n");
         MPI_Finalize();
         return 0;
      }
      printf("Indique el tamaño de la matriz: ");
      scanf("%d",&n);
      printf("\n");
      if(n > nmax){
         printf("El número de vertices sobrepasa el maximo \n");
         MPI_Finalize();
         return 0;
      }
   }

   // Mandar n al resto de procesos mediante Broadcast
   MPI_Bcast(&n, 1, MPI_INT, 0, MPI_COMM_WORLD);

   if (myrank == 0) {
      dist = Crear_matriz_pesos_consecutivo(n,n); // dimensionar matriz de pesos
      caminos = Crear_matriz_caminos_consecutivo(n,n); // dimensionar matriz de caminos
      Definir_Grafo(n,dist,caminos); // crear el grafo

      // calcular el reparto de filas entre procesos
      nfilas = n / numprocs;
      resto = n % numprocs;
      nlocal = nfilas + resto;
   }
   else {
      // calcular el reparto de filas
      nfilas = n / numprocs;
      resto = n % numprocs;
      nlocal = nfilas;

      dist = Crear_matriz_pesos_consecutivo(nlocal,n); // dimensionar matriz de pesos
      caminos = Crear_matriz_caminos_consecutivo(nlocal,n); // dimensionar matriz de caminos
   }

   // MPI_Scatter para repartir las matrices de peso y de caminos.
   if (myrank != 0) {
      MPI_Scatter(&dist[0][0], nfilas*n, MPI_FLOAT, &dist[0][0], nfilas*n, MPI_FLOAT, 0, MPI_COMM_WORLD);
      MPI_Scatter(&caminos[0][0], nfilas*n, MPI_INT, &caminos[0][0], nfilas*n, MPI_INT, 0, MPI_COMM_WORLD);
   }
   else {
      MPI_Scatter(&dist[resto][0], nfilas*n, MPI_FLOAT, MPI_IN_PLACE, nfilas*n, MPI_FLOAT, 0, MPI_COMM_WORLD);
      MPI_Scatter(&caminos[resto][0], nfilas*n, MPI_INT, MPI_IN_PLACE, nfilas*n, MPI_INT, 0, MPI_COMM_WORLD);
   }

   // Reservar memoria para auxiliares de las matrices (float e int)
   aux = malloc(n * sizeof(float));
   auxC = malloc(n * sizeof(int));

   for (int k = 0; k < n; k++) {
      // Calcular el número de proceso que almacena la fila k: sender
      for (int i = 0; i < numprocs; i++) {
         if (k < resto + nfilas * (i + 1)) {
            sender = i;
            break;
         }
      }
      // buscar la fila k correcta
      if (sender == 0) {
         fila_k = k;
      }
      else {
         fila_k = k - (nfilas * sender) - resto;
      }
      
      if (myrank == sender) {
         for (int m = 0; m < n; m++) {
            // Hacer aux igual a la fila k de la matriz de pesos, dist.
            aux[m] = dist[fila_k][m];
            // Hacer auxC igual a la fila k de la matriz de caminos, caminos.
            auxC[m] = caminos[fila_k][m];
         }
      }

      // Broadcast de aux y auxC
      MPI_Bcast(&aux[0], n, MPI_FLOAT, sender, MPI_COMM_WORLD);
      MPI_Bcast(&auxC[0], n, MPI_INT, sender, MPI_COMM_WORLD);

      //acctualizamos la matriz
      for (int i = 0; i < nlocal; i++) {
         for (int j = 0; j < n; j++) {
            if ((dist[i][k] * aux[j] != 0)) {
               if ((dist[i][k] + aux[j] < dist[i][j]) || (dist[i][j] == 0)) {
                  dist[i][j] = dist[i][k] + aux[j];
                  caminos[i][j] = auxC[j];
               }
            }
         }
      }
   }
   
   // MPI_Gather para recoger las matrices de peso y de caminos.
   if (myrank != 0) {
      MPI_Gather(&dist[0][0], nfilas*n, MPI_FLOAT, &dist[0][0], nfilas*n, MPI_FLOAT, 0, MPI_COMM_WORLD);
      MPI_Gather(&caminos[0][0], nfilas*n, MPI_INT, &caminos[0][0], nfilas*n, MPI_INT, 0, MPI_COMM_WORLD);
   }
   else {
      MPI_Gather(MPI_IN_PLACE, nfilas*n, MPI_FLOAT, &dist[resto][0], nfilas*n, MPI_FLOAT, 0, MPI_COMM_WORLD);
      MPI_Gather(MPI_IN_PLACE, nfilas*n, MPI_INT, &caminos[resto][0], nfilas*n, MPI_INT, 0, MPI_COMM_WORLD);
   }

   // calcula camino
   if (myrank == 0) {
      // sólo cuando el nº de vértices sea menor o igual que 10 se mostrará
      // la matriz de pesos y la de distancias final
      if (n <= 10) {
         printMatrizPesos(dist,n,n);
         printMatrizCaminos(caminos,n,n);
      }
      calcula_camino(dist, caminos, n);
   }

   free(aux);
   free(auxC);
   free(dist);
   free(caminos);

   MPI_Finalize();
   return 0;
}

void Definir_Grafo(int n,float **dist,int **caminos)
{
// Inicializamos la matriz de pesos y la de caminos para el algoritmos de Floyd-Warshall. 
// Un 0 en la matriz de pesos indica que no hay arco.
// Para la matriz de caminos se supone que los vertices se numeran de 1 a n.
  int i,j;
  for (i = 0; i < n; ++i) {
      for (j = 0; j < n; ++j) {
          if (i==j)
             dist[i][j]=0;
          else {
             dist[i][j]= (11.0 * rand() / ( RAND_MAX + 1.0 )); // aleatorios 0 <= dist < 11
             dist[i][j] = ((double)((int)(dist[i][j]*10)))/10; // truncamos a 1 decimal
             if (dist[i][j] < 2) dist[i][j]=0; // establecemos algunos a 0 
          }
          if (dist[i][j] != 0)
             caminos[i][j] = i+1;
          else
             caminos[i][j] = 0;
      }
  }
}

void calcula_camino(float **a, int **b, int n)
{
 int i,count=2, count2;
 int anterior; 
 int *camino;
 int inicio=-1, fin=-1;

 while ((inicio < 0) || (inicio >n) || (fin < 0) || (fin > n)) {
    printf("Vertices inicio y final: (0 0 para salir) ");
    scanf("%d %d",&inicio, &fin);
 }
 while ((inicio != 0) && (fin != 0)) {
    anterior = fin;
    while (b[inicio-1][anterior-1] != inicio) {
       anterior = b[inicio-1][anterior-1];
       count = count + 1;
    }
    count2 = count;
    camino = malloc(count * sizeof(int));
    anterior = fin;
    camino[count-1]=fin;
    while (b[inicio-1][anterior-1] != inicio) {
       anterior = b[inicio-1][anterior-1];
       count = count - 1;
       camino[count-1]=anterior;
    }
    camino[0] = inicio;
    printf("\nCamino mas corto de %d a %d:\n", inicio, fin);
    printf("          Peso: %5.1f\n", a[inicio-1][fin-1]);
    printf("        Camino: ");
    for (i=0; i<count2; i++) printf("%d ",camino[i]);
    printf("\n");
    free(camino);
    inicio = -1;
    while ((inicio < 0) || (inicio >n) || (fin < 0) || (fin > n)) {
       printf("Vertices inicio y final: (0 0 para salir) ");
       scanf("%d %d",&inicio, &fin);
    }

 }
}

float **Crear_matriz_pesos_consecutivo(int Filas, int Columnas)
{
// crea un array de 2 dimensiones en posiciones contiguas de memoria
 float *mem_matriz;
 float **matriz;
 int fila, col;
 if (Filas <=0) 
    {
        printf("El numero de filas debe ser mayor que cero\n");
        return;
    }
 if (Columnas <= 0)
    {
        printf("El numero de filas debe ser mayor que cero\n");
        return;
    }
 mem_matriz = malloc(Filas * Columnas * sizeof(float));
 if (mem_matriz == NULL) 
	{
		printf("Insuficiente espacio de memoria\n");
		return;
	}
 matriz = malloc(Filas * sizeof(float *));
 if (matriz == NULL) 
	{
		printf ("Insuficiente espacio de memoria\n");
		return;
	}
 for (fila=0; fila<Filas; fila++)
    matriz[fila] = mem_matriz + (fila*Columnas);
 return matriz;
}

int **Crear_matriz_caminos_consecutivo(int Filas, int Columnas)
{
// crea un array de 2 dimensiones en posiciones contiguas de memoria
 int *mem_matriz;
 int **matriz;
 int fila, col;
 if (Filas <=0) 
    {
        printf("El numero de filas debe ser mayor que cero\n");
        return;
    }
 if (Columnas <= 0)
    {
        printf("El numero de filas debe ser mayor que cero\n");
        return;
    }
 mem_matriz = malloc(Filas * Columnas * sizeof(int));
 if (mem_matriz == NULL) 
	{
		printf("Insuficiente espacio de memoria\n");
		return;
	}
 matriz = malloc(Filas * sizeof(int *));
 if (matriz == NULL) 
	{
		printf ("Insuficiente espacio de memoria\n");
		return;
	}
 for (fila=0; fila<Filas; fila++)
    matriz[fila] = mem_matriz + (fila*Columnas);
 return matriz;
}

void printMatrizCaminos(int **a, int fila, int col) {
        int i, j;
        char buffer[10];
        printf("     ");
        for (i = 0; i < col; ++i){
                j=sprintf(buffer, "%c%d",'V',i+1 );
                printf("%5s", buffer);
       }
        printf("\n");
        for (i = 0; i < fila; ++i) {
                j=sprintf(buffer, "%c%d",'V',i+1 );
                printf("%5s", buffer);
                for (j = 0; j < col; ++j)
                        printf("%5d", a[i][j]);
                printf("\n");
        }
        printf("\n");
}

void printMatrizPesos(float **a, int fila, int col) {
        int i, j;
        char buffer[10];
        printf("     ");
        for (i = 0; i < col; ++i){
                j=sprintf(buffer, "%c%d",'V',i+1 );
                printf("%5s", buffer);
       }
        printf("\n");
        for (i = 0; i < fila; ++i) {
                j=sprintf(buffer, "%c%d",'V',i+1 );
                printf("%5s", buffer);
                for (j = 0; j < col; ++j)
                        printf("%5.1f", a[i][j]);
                printf("\n");
        }
        printf("\n");
}