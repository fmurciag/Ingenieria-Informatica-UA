#include <stdio.h>
#include <mpi.h>

main(int argc, char **argv)
{
   int myrank, numprocs, dato;

   MPI_Status estado;
   MPI_Init(&argc,&argv);
   MPI_Comm_rank(MPI_COMM_WORLD,&myrank);
   MPI_Comm_size(MPI_COMM_WORLD,&numprocs);


   if(myrank==0){//caso base proceso 0
      printf("introduce un numero: \n");
      scanf("%d", &dato);
      MPI_Send(&dato,1,MPI_INT,myrank+1,1,MPI_COMM_WORLD);
      MPI_Recv(&dato,1,MPI_INT,numprocs-1,1,MPI_COMM_WORLD,&estado);
      printf("Proceso %d,escribo: %d\n",myrank,dato);
      dato+=1;
      printf("Proceso %d,escribo: %d\n",myrank,dato);
   
   }else if (myrank==numprocs-1)//caso ultimo proceso
   {
      MPI_Recv(&dato,1,MPI_INT,myrank-1,1,MPI_COMM_WORLD,&estado);
      printf("Proceso %d,escribo: %d\n",myrank,dato);
      dato+=1;
      MPI_Send(&dato,1,MPI_INT,0,1,MPI_COMM_WORLD);

   }else{//caso general
      MPI_Recv(&dato,1,MPI_INT,myrank-1,1,MPI_COMM_WORLD,&estado);
      printf("Proceso %d,escribo: %d\n",myrank,dato);
      dato+=1;
      MPI_Send(&dato,1,MPI_INT,myrank+1,1,MPI_COMM_WORLD);
   }

   MPI_Finalize();

}
