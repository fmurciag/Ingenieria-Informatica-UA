#include <stdio.h>
#include <mpi.h>

main(int argc, char **argv)
{
   int myrank, numprocs, resultlen;
   char name[MPI_MAX_PROCESSOR_NAME];
   
   MPI_Init(&argc,&argv);
   MPI_Comm_rank(MPI_COMM_WORLD, &myrank);
   MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
   MPI_Get_processor_name(&name,&resultlen);
     
   printf("Hello World: Soy el proceso %d de un total de %d. Estoy en el nodo %s y en el core %d\n",myrank,numprocs,name,sched_getcpu());
            
   MPI_Finalize();
}

