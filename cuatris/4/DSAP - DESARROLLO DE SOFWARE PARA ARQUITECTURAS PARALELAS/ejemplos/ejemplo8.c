#include <stdio.h>
#include <mpi.h>
#include <stdlib.h>

#define m 7
#define n 10
main(int argc, char **argv)
{
   void vermatriz(int max, double a[][max], int , int , char []);
   int myrank, numprocs, i, j, lm, root, resto, slice;
   double A[m][n], B[m][n];

   MPI_Init(&argc,&argv);
   MPI_Comm_rank(MPI_COMM_WORLD, &myrank);
   MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
   printf("Soy el proceso %d de un total de %d\n",myrank,numprocs);
   root = 0;
   if (myrank == root)
   {
       resto = m % numprocs;
       for (i=0; i<m; i++)
          for (j=0; j<n; j++)
             A[i][j] = i-j;
//       vermatriz(n,A,m,n,"A");
   }
   else {
       resto = 0;
   }
   slice = m / numprocs;
   lm = slice + resto;

   if (myrank != root) {   
      MPI_Scatter(&A[resto][0],slice*n,MPI_DOUBLE,
               &A[resto][0],slice*n,MPI_DOUBLE,
               root,MPI_COMM_WORLD);
   }
   else {
      MPI_Scatter(&A[resto][0],slice*n,MPI_DOUBLE,
               MPI_IN_PLACE,slice*n,MPI_DOUBLE,
               root,MPI_COMM_WORLD);
   }
   printf("Proceso %d\n",myrank);
   vermatriz(n,A,lm,n,"A");
   for (i=0; i<lm; i++)
       for (j=0; j<n; j++)
           B[i][j] = 2*A[i][j];
   if (myrank != root) {   
      MPI_Gather(&B[resto][0],slice*n,MPI_DOUBLE, 
              &B[resto][0],slice*n,MPI_DOUBLE,
              0, MPI_COMM_WORLD);
   }
   else {
      MPI_Gather(MPI_IN_PLACE,slice*n,MPI_DOUBLE, 
              &B[resto][0],slice*n,MPI_DOUBLE,
              0, MPI_COMM_WORLD);
   }
   if (myrank == 0){
      vermatriz(n,B,m,n,"B");
   }
   MPI_Finalize();
}

void vermatriz(int max, double a[][max], int fil, int col, char nombre[]){
   int i,j;
   printf("%5s=",nombre);
   printf("%6d ",0);
   for (j=1;j<col;j++){
   	printf("%7d ",j);
   }
   printf("\n");
   for (i=0;i<fil;i++){
   	printf("%8d:",i);
   	for (j=0;j<col;j++){
      		printf("%7.3f ",a[i][j]);
   	}
   	printf("\n");
   }
}
