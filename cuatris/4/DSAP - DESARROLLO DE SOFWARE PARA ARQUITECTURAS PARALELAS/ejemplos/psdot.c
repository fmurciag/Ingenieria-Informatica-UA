#include <stdio.h>
#include <mpi.h>
#include <malloc.h>
#include <stdlib.h>

#define maxn 100000000
main(int argc, char **argv)
{
   int myrank,numprocs;
   int i,j,n,ln,indice;
   double *x, *y, ldot, gdot;
   MPI_Status status;
   double sdot(int, double *, double *);

   MPI_Init(&argc,&argv);
   MPI_Comm_rank(MPI_COMM_WORLD, &myrank);
   MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
     
   if (myrank == 0) {
         printf("Numero de elementos en los vectores (1-%d): ", maxn);
         scanf("%d", &n);
         if (n > maxn) {
             printf("El valor introducido sobrepasa el maximo: %d\n", maxn);
         }
	 if (n < 1)
		 printf("El valor introducido debe ser > 0\n");
   }
   MPI_Bcast(&n,1,MPI_INT,0,MPI_COMM_WORLD);
   if ((n > maxn) || (n < 1)) {
      MPI_Finalize();
      return 0;
   }
   if (myrank == 0) {
         x = malloc(n * sizeof(double));
         y = malloc(n * sizeof(double));
         for (i=0; i<n; i++) {
                 x[i]=i+1;
                 y[i] = (double)1/(i+1);
         }
         j=n/numprocs;
         ln = j + n%numprocs;
         indice = ln;
         for (i=1; i<numprocs; i++) {
             MPI_Send( &x[indice], j, MPI_DOUBLE, i, i, MPI_COMM_WORLD);
             MPI_Send( &y[indice], j, MPI_DOUBLE, i, i, MPI_COMM_WORLD);
             indice = indice + j;
         }
   }
   else {
          ln = n/numprocs;
          x = malloc(ln * sizeof(double));
          y = malloc(ln * sizeof(double));       
          MPI_Recv ( x, ln, MPI_DOUBLE, 0, myrank, MPI_COMM_WORLD, &status);
          MPI_Recv ( &y[0], ln, MPI_DOUBLE, 0, myrank, MPI_COMM_WORLD, &status);
   }

   ldot = sdot(ln, x, y);
   printf("Producto interno parcial (%d): %f\n", myrank, ldot);
   MPI_Reduce(&ldot,&gdot,1,MPI_DOUBLE,MPI_SUM,0,MPI_COMM_WORLD);
   if (myrank == 0) {
       printf("Producto interno global: %f\n",gdot);
   }
   free(x);
   free(y);
   MPI_Finalize();
}

double sdot(int dim, double *x, double *y)
{
 int i;
 double valor=0;
 for (i=0; i<dim; i++) 
    valor =valor + x[i]*y[i];
 return valor;
}
