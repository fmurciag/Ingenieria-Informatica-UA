#include <stdio.h>
#include <mpi.h>

#define maxm 1000
#define maxn 900
main(int argc, char **argv)
{
   int myrank,numprocs;
   int i,j,k,indice,columna_ini;
   int m,col_por_proceso;
   double A[maxm][maxn], aux;
   double start_time,end_time,total_time;
   MPI_Status estado;
   int request;
   int MPI_COL;

   MPI_Init(&argc,&argv);
   MPI_Comm_rank(MPI_COMM_WORLD, &myrank);
   MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
     
   printf("Soy el proceso %d de un total de %d.\n",myrank,numprocs);
            
   if (myrank == 0) {
         printf("Numero de columnas a cada proceso (1-%d):",maxn/numprocs);
         scanf("%d", &col_por_proceso);
         m = col_por_proceso*numprocs;
         printf("El orden de la matriz es: %d\n", m);
         for (i=0; i<m; i++) {
             for (j=0; j<m; j++) {
                 A[i][j]=i+j;
             }
         }
         for (i=1; i<numprocs; i++) {
             MPI_Send(&m, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
             MPI_Send(&col_por_proceso, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
         }
   }
   else {
        MPI_Recv(&m, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &estado);
        MPI_Recv(&col_por_proceso, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &estado); 
   }
//  OPCION 1
   if (myrank == 0) {
         start_time = MPI_Wtime();
         columna_ini=col_por_proceso;
         for (k=1; k<numprocs; k++) {
            for (i=0; i<m; i++) {
               for (j=columna_ini; j<columna_ini+col_por_proceso; j++) {
                  MPI_Send(&A[i][j],1,MPI_DOUBLE,k,9,MPI_COMM_WORLD);
               }
            }
            MPI_Recv(&m,1,MPI_INT,k,7,MPI_COMM_WORLD,&estado);
            columna_ini = columna_ini + col_por_proceso;
         }
         end_time = MPI_Wtime();
         total_time=end_time-start_time;
         printf("TIEMPO OPCION 1: %f\n",total_time);
   }
   else {
         for (i=0; i<m; i++) {
            for (j=0; j<col_por_proceso; j++) {
               MPI_Recv(&A[i][j],1,MPI_DOUBLE,0,9,MPI_COMM_WORLD,&estado);
            }
         }
         MPI_Send(&m,1,MPI_INT,0,7,MPI_COMM_WORLD);
   } 
//  OPCION 2
   if (myrank == 0) {
         start_time = MPI_Wtime();
         columna_ini=col_por_proceso;
         for (k=1; k<numprocs; k++) {
            for (i=0; i<m; i++) 
                  MPI_Send(&A[i][columna_ini],col_por_proceso,MPI_DOUBLE,k,9,MPI_COMM_WORLD);
            MPI_Recv(&m,1,MPI_INT,k,7,MPI_COMM_WORLD,&estado);
            columna_ini = columna_ini + col_por_proceso;
         }
         end_time = MPI_Wtime();
         total_time=end_time-start_time;
         printf("TIEMPO OPCION 2: %f\n",total_time);
   }
   else {
         for (i=0; i<m; i++) 
               MPI_Recv(&A[i][0],col_por_proceso,MPI_DOUBLE,0,9,MPI_COMM_WORLD,&estado);
         MPI_Send(&m,1,MPI_INT,0,7,MPI_COMM_WORLD);
   }
// OPCION 3
   MPI_Type_vector(m,1,maxn,MPI_DOUBLE,&MPI_COL);
   MPI_Type_commit(&MPI_COL);
   if (myrank == 0) {
         start_time = MPI_Wtime();
         columna_ini=col_por_proceso;
         for (k=1; k<numprocs; k++) {
            for (j=columna_ini; j<columna_ini+col_por_proceso; j++) 
                  MPI_Send(&A[0][j],1,MPI_COL,k,9,MPI_COMM_WORLD);
            MPI_Recv(&m,1,MPI_INT,k,7,MPI_COMM_WORLD,&estado);
            columna_ini = columna_ini + col_por_proceso;
         }
         end_time = MPI_Wtime();
         total_time=end_time-start_time;
         printf("TIEMPO OPCION 3: %f\n",total_time);
   }
   else {
         for (j=0; j<col_por_proceso; j++) 
               MPI_Recv(&A[0][j],1,MPI_COL,0,9,MPI_COMM_WORLD,&estado);
         MPI_Send(&m,1,MPI_INT,0,7,MPI_COMM_WORLD);
   }
   MPI_Finalize();
}

