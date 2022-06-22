#include <stdio.h>
#include <mpi.h>

main(int argc, char **argv)
{
   int myrank, numprocs;
   float dato, res;

   dato = 7.0;
   res = 0.0;

   MPI_Init(&argc,&argv);
   MPI_Comm_rank(MPI_COMM_WORLD, &myrank);
   MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
     
   printf("Soy el proceso %d de un total de %d\n",myrank,numprocs);
            
   if (myrank == 1)  res = dato * dato;

   printf("Antes de recibir, el valor de res es %f\n",res);
   MPI_Bcast(&res          // Referencia al vector donde se almacena/envia
         ,1                // numero de elementos máximo a recibir
         ,MPI_FLOAT        // Tipo de dato
         ,1                // numero del proceso root
         ,MPI_COMM_WORLD); // Comunicador por el que se recibe
   printf("Despues de recibir, el valor de res es %f\n",res);
   MPI_Finalize();
}
