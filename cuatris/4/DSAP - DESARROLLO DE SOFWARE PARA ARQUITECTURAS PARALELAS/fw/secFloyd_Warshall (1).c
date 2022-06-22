#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>
#include <malloc.h>

#define nmax 1000

int main(int argc, char *argv[]) {
  float **Crear_matriz_pesos_consecutivo(int, int);
  int **Crear_matriz_caminos_consecutivo(int, int);
  double ctimer(void);
  void printMatrizCaminos(int **, int, int);
  void printMatrizPesos(float **, int, int);
  void calcula_camino(float **, int **, int);
  void Definir_Grafo(int, float **, int **);
  int i, j, k, n=10;
  double t1,t2;
  float **dist; 
  int **caminos;
  
  if (argc > 1) sscanf(argv[1], "%i", &n);
  printf("Numero de vertices en el grafo: %d\n",n); 
  if (n > nmax) {
    printf("El numero de vertices (%d) sobrepasa el maximo (%d)\n",n,nmax);
    return 0;
  }
  dist = Crear_matriz_pesos_consecutivo(n,n);
  caminos = Crear_matriz_caminos_consecutivo(n,n);
  Definir_Grafo(n,dist,caminos); 
  if (n <= 10) {
    printMatrizPesos(dist,n,n);
    printMatrizCaminos(caminos,n,n);
  }
  t1 = ctimer();
  for (k=0; k<n; k++) {
      for (i = 0; i < n; i++)
          for (j = 0; j < n; j++)
              if ((dist[i][k] * dist[k][j] != 0) ) {
                 if ((dist[i][k] + dist[k][j] < dist[i][j]) || (dist[i][j] == 0)){
                    dist[i][j] = dist[i][k] + dist[k][j];
                    caminos[i][j] = caminos[k][j];
                 }
              }
  }
  t2 = ctimer();
  printf("Tiempo total %f \n",t2-t1);	
  if (n <= 10) {
    printMatrizPesos(dist,n,n);
    printMatrizCaminos(caminos,n,n);
  }
  calcula_camino(dist, caminos, n);
  free(caminos);
  free(dist);
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

#include <sys/time.h>
#include <sys/types.h>
#include <sys/times.h>
#include <unistd.h>

static int nclock;
double ctimer(void)
{
   struct timeval tp;
   struct timezone tzp;
   double diff;
   nclock=sysconf(_SC_CLK_TCK);
   gettimeofday(&tp, &tzp);
   diff=(double)tp.tv_sec+(double)tp.tv_usec/1.0e6;
   return diff;
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

