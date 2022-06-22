#include <stdio.h>
#include <mpi.h>

main(int argc, char **argv)
{
   int myrank, numprocs, i;
   MPI_Status estado;
   float dato, res;

   dato = 7.0;
   res = 0.0;

   MPI_Init(&argc,&argv);
   MPI_Comm_rank(MPI_COMM_WORLD, &myrank);
   MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
     
   printf("Soy el proceso %d de un total de %d.\n",myrank,numprocs);
            
   if (myrank > 0)
   {
      res = dato * myrank;
      MPI_Send(&res             // referencia al vector de elementos a enviar
    	      ,1                // tamaño del vector a enviar
    	      ,MPI_FLOAT        // Tipo de dato que envias
    	      ,0                // dentificador del proceso destino
    	      ,8                // etiqueta
    	      ,MPI_COMM_WORLD); // Comunicador por el que se recibe
   }
   else
   {
     printf("Antes de recibir, el valor de res es %f\n",res);
     for ( i=1 ; i<numprocs ; i++ )
     { 
       MPI_Recv(&res            // Referencia al vector donde se almacenara lo recibido
               ,1               // numero de elementos máximo a recibir
      	       ,MPI_FLOAT       // Tipo de dato que recibe
    	       ,i               // identificador del proceso origen del que se recibe
    	       ,8               // etiqueta
    	       ,MPI_COMM_WORLD  // Comunicador por el que se recibe
    	       ,&estado);       // estructura informativa del estado
     	printf("Despues de recibir de %d, el valor de res es %f\n", i, res);
        printf("estado.MPI_SOURCE (origen del mensaje) \t= %d \nestado.MPI_TAG (etiqueta del mensaje) \t= %d \nestado.count_lo (número de bytes) \t= %d\n", estado.MPI_SOURCE, estado.MPI_TAG, estado.count_lo);
     }
   }
   MPI_Finalize();
}
