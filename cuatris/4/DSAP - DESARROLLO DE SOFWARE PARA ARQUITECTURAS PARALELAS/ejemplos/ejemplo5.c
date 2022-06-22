#include <stdio.h>
#include <mpi.h>

main(int argc, char **argv)
{
   int myrank, numprocs, i;
   MPI_Status estado;
   double x[10], y[10];

   MPI_Init(&argc,&argv);
   MPI_Comm_rank(MPI_COMM_WORLD, &myrank);
   MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
 
   for (i=0; i<10; i++)
   {
         x[i]=(myrank+1) * i;
         y[i]=0.0;
   }
   printf("Soy %d. Antes de recibir el valor de x es: ",myrank);
   for (i=0; i<10; i++) 
   	printf("%4.1f ",x[i]);
   printf("\n");
   MPI_Reduce(&x,&y,10,MPI_DOUBLE,MPI_SUM,0,MPI_COMM_WORLD);
   printf("Soy %d. Despues de recibir el valor de y es: ",myrank);
   for (i=0; i<10; i++) 
	printf("%4.1f ",y[i]);
   printf("\n");

   if (myrank != 0)  
	MPI_Reduce(&x,&x,10,MPI_DOUBLE,MPI_SUM,0,MPI_COMM_WORLD);
   else 
	MPI_Reduce(MPI_IN_PLACE,&x,10,MPI_DOUBLE,MPI_SUM,0,MPI_COMM_WORLD);
   printf("Soy %d. Despues de recibir el valor de x es: ",myrank);
   for (i=0; i<10; i++) 
	printf("%4.1f ",x[i]);
   printf("\n");
   MPI_Finalize();
}
