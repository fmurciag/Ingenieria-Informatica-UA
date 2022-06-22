#include <mpi.h>
#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <malloc.h>

main(int argc, char **argv)
{
   double **Crear_matriz(int fila, int col);
   void Liberar_matriz(double **matriz, int fila);
   void vervector(double a[], int m, char nombre[]);

   int myrank, numprocs;
   MPI_Status estado, status;
   int m=10, n=10, fila;         
   int i, j, k, error;
   double *vector;
   double **matriz, *sol, *sol_sec;

   MPI_Init(&argc,&argv);
   MPI_Comm_rank(MPI_COMM_WORLD, &myrank);
   MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
   printf("Soy el proceso %d de un total de %d.\n",myrank,numprocs);
   if (myrank == 0){
     switch (argc)
        {
                case 3: sscanf(argv[2], "%i", &n);
                case 2: sscanf(argv[1], "%i", &m);
                case 1: break;
                default: printf("Demasiados parametros\n");
                         MPI_Finalize();
                         return 0;
        }
     printf("\n  *************** DATOS DE LA EJECUCIÓN ****************************\n");
     printf("  * Filas = %4d, Columnas = %4d                                  *\n", m, n);
     printf("  ******************************************************************\n\n");
   }

   MPI_Bcast(&m,1,MPI_INT,0,MPI_COMM_WORLD);
   MPI_Bcast(&n,1,MPI_INT,0,MPI_COMM_WORLD);

   if ((numprocs > m+1) || (numprocs ==1)) {
      if (myrank == 0) printf("  *********  Numero de procesos inadecuado  *********\n\n");
      MPI_Finalize();
      return 0;
   }

   vector = (double *)malloc(sizeof(double) * n); 

   if (myrank == 0){
      matriz = Crear_matriz(m, n);
      for (i=0; i<m; i++){
          for (j=0; j<n; j++){
                matriz[i][j] = i+j;
          }
      }
      for (j=0; j<n; j++) vector[j]=j+1;
      sol_sec = (double *)malloc(sizeof(double) * m);
      for (i=0;i<m;i++){
         sol_sec[i] = 0;
         for (j=0;j<n;j++) 
             sol_sec[i] = sol_sec[i]  + matriz[i][j]*vector[j];
      }
   }

   MPI_Bcast(vector,n,MPI_DOUBLE,0,MPI_COMM_WORLD);

   if (myrank == 0){
      sol = (double *)malloc(sizeof(double) * m);
      for(fila=0; fila<numprocs-1;fila++){
         MPI_Send(&fila, 1, MPI_INT, fila+1, 1, MPI_COMM_WORLD);   
         MPI_Send(&matriz[fila][0], n, MPI_DOUBLE, fila+1, 1, MPI_COMM_WORLD);
         printf("Fila %d asignada al proceso %d.\n",fila,fila+1);
      }
      for(fila=numprocs-1; fila<m;fila++){
         MPI_Recv(&k, 1, MPI_INT, MPI_ANY_SOURCE, MPI_ANY_TAG, MPI_COMM_WORLD, &estado);
         MPI_Recv(&sol[k], 1, MPI_DOUBLE, estado.MPI_SOURCE, k, MPI_COMM_WORLD, &status);
         MPI_Send(&fila, 1, MPI_INT, estado.MPI_SOURCE, 1, MPI_COMM_WORLD);
         MPI_Send(&matriz[fila][0], n, MPI_DOUBLE, estado.MPI_SOURCE, 1, MPI_COMM_WORLD);
         printf("Fila %d asignada al proceso %d.\n",fila,estado.MPI_SOURCE);
      }
      for (i=1; i<numprocs; i++) {
         MPI_Recv(&k, 1, MPI_INT, MPI_ANY_SOURCE, 2, MPI_COMM_WORLD, &estado);
         MPI_Recv(&sol[k], 1, MPI_DOUBLE, estado.MPI_SOURCE, k, MPI_COMM_WORLD, &estado);
      }
      for (i=1; i<numprocs; i++) {
         MPI_Send(&i, 1, MPI_INT, i, 99, MPI_COMM_WORLD);
      }
      if (m <= 10) vervector(sol, m, "sol");
      error=0;
      for (i=0;i<m;i++) 
          if (abs(sol[i]-sol_sec[i]) > 0.000001) error += 1;
      printf("  *********  Errores cometidos: %d  *********\n\n\n", error);
      
      Liberar_matriz(matriz,m);
      free(sol);
      free(sol_sec);
   }
   else {
     double *aux; 
     double sol;
     aux = (double *)malloc(sizeof(double) * n);
     while (1) {
      MPI_Recv(&fila, 1, MPI_INT, 0, MPI_ANY_TAG, MPI_COMM_WORLD, &estado); 
      if (estado.MPI_TAG == 99) {
         MPI_Finalize();
         return 0;
      }
//      printf("Fila %d calculandose en proceso %d.\n",fila,myrank);
      MPI_Recv(aux, n, MPI_DOUBLE, 0, MPI_ANY_TAG, MPI_COMM_WORLD, &estado); 
      sol=0;
      for (j=0; j<n; j++) 
          sol = sol + aux[j]*vector[j];
      MPI_Send(&fila, 1, MPI_INT, 0, 2, MPI_COMM_WORLD);
      MPI_Send(&sol, 1, MPI_DOUBLE, 0, fila, MPI_COMM_WORLD);
     }
     free(aux);
   } 
   MPI_Finalize();
}

double **Crear_matriz(int fila, int col)
{
    double **ret_val;
    int i;

    ret_val = (double **)malloc(sizeof(double *) * fila);
    if (ret_val == NULL) {
        perror("Problemas al dimensionar");
        exit(EXIT_FAILURE);
    }

    for (i = 0; i < fila; ++i) {
        ret_val[i] = (double *)malloc(sizeof(double) * col);
        if (ret_val[i] == NULL) {
            perror("Problemas al dimensionar");
            exit(EXIT_FAILURE);
        }
    }
    return ret_val;
}

void Liberar_matriz(double **matriz, int fila)
{
    int i;

    for (i = 0; i < fila; ++i)
        free(matriz[i]);
    free(matriz);
}

void vervector(double a[], int m, char nombre[])
{
 int i;
 printf("%6s = ",nombre);
 for (i=0;i<m;i++){
     printf("%7.3f ",a[i]);
     if ((i+1)%10  == 0) printf("\n         ");
 }
 printf("\n");
}
