#include <stdio.h>
#include <mpi.h>
#include <stdlib.h>

main(int argc, char **argv)
{
   int msglen;
   double *mensaje1, *mensaje2;
   int rank,dest,ori,numprocs;
   int send_eti, recv_eti, i;
   MPI_Status status;
   MPI_Request request1, request2;

   MPI_Init(&argc,&argv);
   MPI_Comm_rank(MPI_COMM_WORLD, &rank);
   MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
   if (numprocs != 2) {
         if (rank == 0) printf("Ejecuta con solo 2 procesos!!\n");
         MPI_Finalize ();
         return 0;
   }
   printf("Soy el proceso %d de un total de %d\n",rank,numprocs);
   if (rank == 0) {
         printf("Longitud de los vectores a enviar: ");
         scanf("%i",&msglen);
   }
   MPI_Bcast(&msglen  // Referencia al vector donde se almacena/envia
         ,1      // numero de elementos maximo a recibir
         ,MPI_INT  // Tipo de dato
         ,0      // numero del proceso root
         ,MPI_COMM_WORLD); // Comunicador por el que se recibe

   mensaje1 = (double *)malloc(msglen * sizeof(double));
   mensaje2 = (double *)malloc(msglen * sizeof(double));
   for (i=0; i<msglen; i++)
        {
            mensaje1[i]= 100;
            mensaje2[i]=-100;
        }
   if ( rank == 0 ) { 
         dest = 1;
         ori = 1;
         send_eti = 10;
         recv_eti = 20;
   } else {
         dest = 0;
         ori = 0;
         send_eti = 20;
         recv_eti = 10;
   }
   MPI_Isend ( mensaje1, msglen, MPI_DOUBLE,dest, send_eti, MPI_COMM_WORLD, &request1);
   MPI_Irecv ( mensaje2, msglen, MPI_DOUBLE,ori, recv_eti, MPI_COMM_WORLD, &request2);
   MPI_Wait ( &request1, &status );
   MPI_Wait ( &request2, &status );
   printf("Tarea %d: ", rank);
   for (i=0; i<3; i++) printf("m2[%d]=%6.1f ", i, mensaje2[i]);  printf("\n");
   free(mensaje1);
   free(mensaje2);
   MPI_Finalize();
}

