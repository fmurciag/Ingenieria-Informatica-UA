#include <stdlib.h>
#include <stdio.h>
#include <mpi.h>

#define PI25DT 3.141592653589793238462643
main(int argc, char **argv)
{
   int myrank, nprocs, i, n=500000000;
   double start_time, end_time, totalsec, total;
   double h, pi, x, parte;
   MPI_Status estado;
   double f(double x);

   MPI_Init(&argc,&argv);
   MPI_Comm_rank(MPI_COMM_WORLD, &myrank);
   MPI_Comm_size(MPI_COMM_WORLD, &nprocs);
     
   if (myrank == 0 && argc > 1) sscanf(argv[1], "%i", &n);
   if (myrank == 0) printf("Numero de intervalos: %d\n",n);

// Calculo secuencial
   if (myrank == 0) {
       start_time = MPI_Wtime();
       h=1.0 / n;
       pi=0;
       for (i=1; i<=n; i++) {
          x   = (i-0.5)*h;
          pi  = pi + f(x);
       }
       pi = pi * h;
       end_time = MPI_Wtime();
       totalsec=end_time-start_time;
       printf("Calculo secuencial, PI = %26.24f\n", pi);
       printf("Calculo secuencial, Tiempo: %f\n", totalsec);
       printf("Calculo secuencial, Error: %26.24f\n\n", fabs(pi- PI25DT));
       pi=0;
   }
// Calculo paralelo
   start_time = MPI_Wtime();
   MPI_Bcast(&n,1,MPI_INT,0,MPI_COMM_WORLD);
   h = 1.0 / n;
   parte = 0;
   for (i=myrank+1; i<=n; i=i+nprocs) { 
        x     = (i-0.5)*h;
        parte = parte + f(x);
   }
   parte = parte*h;
   MPI_Reduce(&parte,&pi,1,MPI_DOUBLE,MPI_SUM,0,MPI_COMM_WORLD);
   end_time = MPI_Wtime();

   total=end_time-start_time;
   if (myrank == 0) {
      printf("Calculo paralelo, PI = %26.24f\n", pi);
      printf("Calculo paralelo, Tiempo: %f\n", total);
      printf("Calculo paralelo, Error: %26.24f\n", fabs(pi- PI25DT));
      printf("Speed-up: %5.2f, Eficiencia: %6.2f%\n",totalsec/total, ((totalsec/total)/nprocs)*100);
   }
   MPI_Finalize();
}

double f(double x)
{
 double valor;
 valor =  4.0/(1+x*x);
 return valor;
}
