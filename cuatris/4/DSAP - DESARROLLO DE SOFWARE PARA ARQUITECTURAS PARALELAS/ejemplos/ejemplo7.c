#include <stdio.h>
#include <mpi.h>
#include <stdlib.h>

main(int argc, char **argv)
{
   int myrank, numprocs, i, lm, m, root;
   MPI_Status estado;
   double *x, *y;
   double ldot, gdot;
   double sdot(int, double *, double *);

   MPI_Init(&argc,&argv);
   MPI_Comm_rank(MPI_COMM_WORLD, &myrank);
   MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
   printf("Soy el proceso %d de un total de %d\n",myrank,numprocs);
   root = 0;
   if (myrank == root)
   {
        printf("Longitud de los vectores en cada proceso: ");
	scanf("%i",&lm);
	m = lm * numprocs;
        printf("Longitud de los vectores a multiplicar: %i\n",m);
        x = (double *)malloc(m * sizeof(double));
        y = (double *)malloc(m * sizeof(double));
        for (i=0; i<m; i++)
        {
            x[i]= i+1;
            y[i]=2*(i+1);
        }
   }
   MPI_Bcast(&lm           // Referencia al vector donde se almacena/envia
         ,1                // numero de elementos maximo a recibir
         ,MPI_INT          // Tipo de dato
         ,0                // numero del proceso root
         ,MPI_COMM_WORLD); // Comunicador por el que se recibe
   if (myrank != root)
   {
	x = (double *)malloc(lm * sizeof(double));
	y = (double *)malloc(lm * sizeof(double));
     
   }
   if (myrank != root) {
   	MPI_Scatter(x,lm,MPI_DOUBLE,
                    x,lm,MPI_DOUBLE,
                    root,MPI_COMM_WORLD);
   	MPI_Scatter(y,lm,MPI_DOUBLE,
                    y,lm,MPI_DOUBLE,
                    root,MPI_COMM_WORLD);
   }
   else {
   	MPI_Scatter(x,lm,MPI_DOUBLE,
                    MPI_IN_PLACE,lm,MPI_DOUBLE,
                    root,MPI_COMM_WORLD);
   	MPI_Scatter(y,lm,MPI_DOUBLE,
                    MPI_IN_PLACE,lm,MPI_DOUBLE,
                    root,MPI_COMM_WORLD);
   }

   printf("Soy %d. Despues de scatter el valor de x es:",myrank);
   for (i=0; i<5; i++) 
	printf("%4.1f ",x[i]);
   printf("\n");
   printf("Soy %d. Despues de scatter el valor de y es:",myrank);
   for (i=0; i<5; i++) 
	printf("%4.1f ",y[i]);
   printf("\n");

   ldot = sdot(lm,x,y);
   printf("Producto interno parcial del proceso %d: %f\n",myrank, ldot);
   MPI_Reduce(&ldot,&gdot,1,MPI_DOUBLE,MPI_SUM,root,
                     MPI_COMM_WORLD);
   if (myrank == root) printf("Producto interno global: %f\n",gdot);


   free(x);
   free(y);
   MPI_Finalize();
}

double sdot(int lm, double *x, double *y)
{
       int k;
       double valor;
       valor = 0.0;
       for (k=0;k<lm;k++)
       {
          valor = valor + x[k]*y[k];
       }
       return valor;
}
