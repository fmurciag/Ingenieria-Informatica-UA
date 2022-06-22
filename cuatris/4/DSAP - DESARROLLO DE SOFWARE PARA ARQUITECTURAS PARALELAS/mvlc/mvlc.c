/*
###################################
FRANCISCO JOAQUIN MURCIA
###################################
*/
#include <stdio.h>
#include <mpi.h>


#define maxm 500
#define maxn 500
//funcion para imprimir por matriz
void vermatriz(int lda, double a[][lda], int m, int n, char nombre[])
{
 int i,j;
 printf("%4s =",nombre);
 printf("%6d ",0);
 for (j=1;j<n;j++){
     printf("%7d ",j);
 }
 printf("\n");
 for (i=0;i<m;i++){
     printf("%8d:",i);
     for (j=0;j<n;j++){
         printf("%7.3f ",a[i][j]);
     }
     printf("\n");
 }
 printf("\n");printf("\n");printf("\n");      
}
//funcion para imprimir el vector
void vervector(double a[], int m, char nombre[])
{
 int i;
 printf("%6s = ",nombre);
 for (i=0;i<m;i++){
     printf("%7.3f ",a[i]);
     if ((i+1)%5  == 0) printf("\n");
 }
 printf("\n"); 
}
//multripicador
void matvec(int lda,double sol[maxm], double a[][lda],double x[], int m, int n){
    for (int i=0;i<m;i++){
        sol[i] = 0;
        for (int j=0;j<n;j++) {
            sol[i] = sol[i]  + a[i][j] * x[j];
        }
    }
}
int main(int argc, char **argv)
{
   int myrank, numprocs, dato;
   double a[maxm][maxn], x[maxn], sol[maxm];
   MPI_Init(&argc,&argv);
   MPI_Comm_rank(MPI_COMM_WORLD,&myrank);
   MPI_Comm_size(MPI_COMM_WORLD,&numprocs);
   MPI_Status estado;
    int i, j;
   int m, n, nRepartido, nPadre;
   int col_por_proceso=0;
   

   if (myrank==0)
   {
        //peticion de datos
        printf("Numero de filas (1 - %d): ",maxm);
        scanf("%d",&m);
        while ((m <= 0) || (m > maxm)) {
            printf("Numero de filas (1 - %d): ",maxm);
            scanf("%d",&m);
        }
        printf("Numero de columnas (1 - %d): ",maxn);
        scanf("%d",&n);
        while ((n <= 0) || (n > maxn)) {
            printf("Numero de columnas (1 - %d): ",maxn);
            scanf("%d",&n);
        }
        //repartimos las columnas a cada proceso y para el prceso root
        nRepartido=n/numprocs;
        nPadre=n%numprocs+nRepartido;
   }
   //enviamos las filas y columnas de cada hijo
    MPI_Bcast(&nRepartido,1,MPI_INT,0,MPI_COMM_WORLD);
    MPI_Bcast(&m,1,MPI_INT,0,MPI_COMM_WORLD);
    if (myrank==0)
    {
        //generamos la matriz y vector
        for (i=0; i<m; i++){
            for (j=0; j<n; j++){
                a[i][j] =  i+j;
            }
        }
        for (j=0; j<n; j++) x[j]= j;
        //imprimimos la matriz y el vector
        if ((m <= 10) && (n <= 10)) {
            vermatriz(maxn, a, m, n, "A");
            printf("\n");
            vervector(x, m, "x");
            printf("----------------------------------------------------------------\n\n\n"); 
        }

        int columna=nPadre;
        //enviamos las columnas correspondientes a cada hijo
        for (int k=1; k<numprocs; k++) {//procesos
            for (int i=0; i<m; i++){//filas
                for (int j=columna; j<columna+nRepartido; j++){//columnas
                    //printf("Division: dato%7.3f - fila%d - columna%d \n",a[i][j], i, j,columna);
                    MPI_Send(&a[i][j],1,MPI_DOUBLE,k,7,MPI_COMM_WORLD);
                }
            }
            MPI_Send(&x[columna],nRepartido,MPI_DOUBLE,k,6,MPI_COMM_WORLD);//enviamos el vector correspondiente
            columna+=nRepartido; 
        }
        //vermatriz(maxn, a, m, nPadre, "A'");
        matvec(maxn, sol,a,x,m, nPadre);//multripicamos A'*X'
        printf("Vector proceso %d\n",myrank);
        vervector(sol, m, "x'");//imprimimos el vector resultado del proceso 0
                
   }else{//codigo de los procesos hijos
        //recivimos la matriz
        for (int i=0; i<m; i++){
            for (int j=0; j<nRepartido; j++){
                MPI_Recv(&a[i][j],1,MPI_DOUBLE,0,7,MPI_COMM_WORLD,&estado);
                //printf("dato%7.3f - fila%d - columna%d \n",a[i][j], i, j);
            }
        }
        
        MPI_Recv(&x[0],nRepartido,MPI_DOUBLE,0,6,MPI_COMM_WORLD,&estado);//recivimos el vector

        //vermatriz(maxn, a, m, nRepartido, "A'");
        matvec(maxn, sol,a,x,m, nRepartido);//multiplicamos A'*X'
        printf("Vector proceso %d\n",myrank);
        vervector(sol, m, "x'");//imprimimos el vector resultado del hijo

   }if (myrank != 0){//sumamos los resultados de las ejecuciones e imprimimos el vector resulatdo
        MPI_Reduce(&sol,&sol,m,MPI_DOUBLE,MPI_SUM,0,MPI_COMM_WORLD);
   }    
    else{
        MPI_Reduce(MPI_IN_PLACE,&sol,m,MPI_DOUBLE,MPI_SUM,0,MPI_COMM_WORLD); 
        printf("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\nSolucion"); 
        vervector(sol, m, "A*x");
        printf("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n"); 
    }
    MPI_Finalize();

   return 0;
}
