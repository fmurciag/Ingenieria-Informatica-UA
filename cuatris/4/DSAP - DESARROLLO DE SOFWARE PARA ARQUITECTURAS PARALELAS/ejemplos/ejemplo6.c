#include <stdio.h>
#include <mpi.h>
#include <stdlib.h>

main(int argc, char **argv)
{
   int myrank, numprocs, i, m;
   MPI_Status estado;
   double *x, *y;

   MPI_Init(&argc,&argv);
   MPI_Comm_rank(MPI_COMM_WORLD, &myrank);
   MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
   if (myrank == 0)
   {
        printf("Longitud de los vectores: "); 
        scanf("%i",&m); 
   }
   MPI_Bcast(&m            // Referencia al vector donde se almacena/envia
         ,1                // numero de elementos maximo a recibir
         ,MPI_INT          // Tipo de dato
         ,0                // numero del proceso root
         ,MPI_COMM_WORLD); // Comunicador por el que se recibe
   x = (double *)malloc(m * sizeof(double));
   y = (double *)malloc(m * sizeof(double));
 
   for (i=0; i<m-1; i++)
   {
         x[i]=(myrank+1) * i;
         y[i]=0.0;
   }
   printf("Soy %d. Antes de recibir el valor de x es:  ",myrank);
   for (i=0; i<9; i++) 
	printf("%4.1f ",x[i]);
   printf("\n");
   MPI_Allreduce(x,y,m,MPI_DOUBLE,MPI_SUM,MPI_COMM_WORLD);
   printf("Soy %d. Despues de recibir el valor de y es:",myrank);
   for (i=0; i<9; i++) 
	printf("%4.1f ",y[i]);
   printf("\n");
   free(x);
   free(y);
   MPI_Finalize();
}
