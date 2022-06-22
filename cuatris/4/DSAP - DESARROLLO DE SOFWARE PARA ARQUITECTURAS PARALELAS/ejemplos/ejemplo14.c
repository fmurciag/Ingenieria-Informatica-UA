#include <stdio.h>
#include <mpi.h>
#include <stdlib.h>

#define maxntask  8
#define maxsize 1000000
main(int argc, char **argv)
{
   int ntask, myrank;
   int size,sizeBuffer;
   int i,j,k;
   double *a, *c, *buffer;
   int arriba, abajo, numerror;
   int irequest,control;
   MPI_Status status;
   MPI_Request request;

   MPI_Init(&argc,&argv);
   MPI_Comm_rank(MPI_COMM_WORLD, &myrank);
   MPI_Comm_size(MPI_COMM_WORLD, &ntask);

   if (ntask > maxntask) {
         MPI_Finalize();
         return 0;
   }
   if ( myrank == 0 ) {
         printf("Orden de cada vector: ");
         scanf("%d", &size);
         printf("Variable de control: 0 (problemas en ISEND),  1 (Solucion con uso de BSEND) ");
         scanf("%d", &control);
   }

   MPI_Bcast(&size,1,MPI_INT,0,MPI_COMM_WORLD);
   MPI_Bcast(&control,1,MPI_INT,0,MPI_COMM_WORLD);

   if (((control != 0) && (control != 1)) || (size > maxsize)){ 
         if (myrank == 0) printf("Parametros no validos....saliendo!!!\n");
         MPI_Finalize();
         return 0;
   }

   abajo = (myrank + 1) % ntask;
   arriba = (myrank + ntask - 1) % ntask;
 
   a = (double *)malloc(size * sizeof(double));
   c = (double *)malloc(size * sizeof(double));

   for (i=0; i<size; i++) {
       a[i] = myrank;
       c[i] = myrank;
   }

   if (control == 1) {
      MPI_Pack_size(size,MPI_DOUBLE,MPI_COMM_WORLD, &sizeBuffer);
      if (myrank == 0) printf("Espacio requerido en buffer: %d\n",sizeBuffer);
      sizeBuffer = ntask*(sizeBuffer + MPI_BSEND_OVERHEAD);
      buffer = (double *)malloc(sizeBuffer);
      MPI_Buffer_attach( buffer, sizeBuffer);
   }

   for (i=0; i<ntask; i++) {
       if (control == 0) {
          MPI_Isend(a,size,MPI_DOUBLE,arriba,i,MPI_COMM_WORLD,&request);
          MPI_Recv(a,size,MPI_DOUBLE,abajo,i,MPI_COMM_WORLD,&status);
          MPI_Wait(&request,&status);
       }
       else if (control == 1) {
          MPI_Bsend(a,size,MPI_DOUBLE,arriba,i,MPI_COMM_WORLD);
          MPI_Recv(a,size,MPI_DOUBLE,abajo,i,MPI_COMM_WORLD,&status);
       }
   }
   if (control == 1) MPI_Buffer_detach(buffer, &sizeBuffer);

   numerror=0;
   for (i=0; i< size; i++) {
      if (a[i]-c[i] != 0) numerror=numerror+1;
   }
   printf("Numero de identificacion de proceso %d, Numero de errores: %d\n", myrank, numerror);
   free(a);
   free(c);
   if (control == 1) free(buffer);
   MPI_Finalize();
}

