/*
###################################
FRANCISCO JOAQUIN MURCIA
###################################
*/
#include <stdio.h>
#include <math.h>
#include <mpi.h>
#define maxbloqtam 100

//funcion de multiplicar
void mult(double a[], double b[], double c[], int m) {
    int i,j,k;
    for (i=0; i<m; i++)
        for (j=0; j<m; j++)
            for (k=0; k<m; k++)
                c[i*m+j]=c[i*m+j]+a[i*m+k]*b[k*m+j];
    return; 
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
int main(int argc, char **argv)
{
    int myrank, numprocs, dato;
    int fila, columna, sizeBuffer;
    int bloqtam=0,r,arriba,abajo;
    int error=0;
    int exit=0;
    double raiz;
    MPI_Init(&argc,&argv);
    MPI_Comm_rank(MPI_COMM_WORLD,&myrank);
    MPI_Comm_size(MPI_COMM_WORLD,&numprocs);
    MPI_Status estado;
    

    if (myrank==0){
        //calcular r
        raiz=sqrt(numprocs);
        r=raiz;
        if(raiz-r){//comprobamos si r es exacta
            exit=1;
            MPI_Finalize();
            return 0;
        }
        do{
            printf("Indique el tamaño de los bloques: ");
            scanf("%d",&bloqtam);
            printf("\n");
        }while ((bloqtam<=0)&&(bloqtam>maxbloqtam));
        
    }
    if(exit){//si r es incorrecta abortamos
        MPI_Finalize();
        return 0;
    }

    //enviamos r y bloctam
    MPI_Bcast(&r,1,MPI_INT,0,MPI_COMM_WORLD);     
    MPI_Bcast(&bloqtam,1,MPI_INT,0,MPI_COMM_WORLD);

    //reservamos memoria de los vectores y matrices auxiliares
    double a[bloqtam*bloqtam];
    double b[bloqtam*bloqtam];
    double c[bloqtam*bloqtam];
    double atp[bloqtam*bloqtam];
    int mifila[r-1];

    //definimos el bloque a
    for(int i = 0; i < bloqtam*bloqtam ; i++){
        a[i] = i*(double)(fila*columna+1)/bloqtam;
    }
    //vervector(a,bloqtam*bloqtam,"A");
   
    //definimos el bloque b (matriz identidad)
    for(int i = 0; i < bloqtam ; i++){
        for(int j = 0; j < bloqtam ; j++){
            if(i == j && columna == fila)
                b[i*bloqtam+j] = 1.0;
            else
                b[i*bloqtam+j] = 0.0; 
        }
    }

    //calcular posicion de la malla (fila y columna)
    fila=myrank/r;
    columna=myrank%r;

    //calculamos los procesos de arriba abajo
    if(fila==0){//primer fila
        arriba=(r-1)*r+columna;
        abajo=(fila+1)*r+columna;
    }else if(fila==r-1){//ultima fila
        arriba=(fila-1)*r+columna;
        abajo=columna;
    }else{//en medio 
        arriba=(fila-1)*r+columna;
        abajo=(fila+1)*r+columna;
    }

    //calcular los procesos de mi fila
    int pos=0;
    for(int i = 0; i < numprocs; i++){
        if(fila == i/r && columna != i%r){
            mifila[pos] = i;
            pos++;
        }
    }
    //vervector(mifila,r-1,"MIFILA");

    //declaramos un buffer
    MPI_Pack_size(bloqtam*bloqtam,MPI_DOUBLE,MPI_COMM_WORLD, &sizeBuffer);
    sizeBuffer = numprocs*(sizeBuffer + MPI_BSEND_OVERHEAD);
    double *buffer = malloc(sizeBuffer * sizeof(double));
    //double buffer[sizeBuffer];

    //====================
    //algoritmo de Cannon
    //====================
    MPI_Buffer_attach(buffer, sizeBuffer);
    
    for(int i = 0;i < r;i++){
        if(columna == (fila+i)%r){
            //mandamos la matriz a a todos los proceos
            for(int j = 0; j < r-1; j++){
                MPI_Send(&a,bloqtam*bloqtam,MPI_DOUBLE,mifila[j],999+i,MPI_COMM_WORLD);
            }
            mult(a,b,c,bloqtam);
        }else{//guardamos a en la matriz temporal
            
            int ReciboDe=r*fila+((fila+i)%r);
            MPI_Recv(&atp,bloqtam*bloqtam,MPI_DOUBLE,ReciboDe,999+i,MPI_COMM_WORLD,&estado);
            mult(atp,b,c,bloqtam);
        }
        //rotamos los bloques
        MPI_Bsend(&b, bloqtam*bloqtam, MPI_DOUBLE, arriba, 99+i, MPI_COMM_WORLD);
        MPI_Recv(&b,bloqtam*bloqtam,MPI_DOUBLE,abajo,99+i,MPI_COMM_WORLD,&estado);
    }
    
    MPI_Buffer_detach(buffer, &sizeBuffer);
    
    //calculo del error
    for (int i = 0; i < bloqtam; i++){
        if (abs(a[i] - c[i]) > 0.000000001) error++;
    }
    if (myrank == 0){//proceso 0 suma los errores e inprime los mismos
        int errorTotal = 0;
        for (int i = 1; i < numprocs; i++){
            MPI_Recv(&error, 1, MPI_INT, MPI_ANY_SOURCE, 999, MPI_COMM_WORLD, &estado);
            printf("El error del proceso %d es %d.\n", i, error);
            errorTotal += error;
        }
        printf("El error total es %d.\n", errorTotal);
    }else{//el resto envia su error al 0
        MPI_Send(&error, 1, MPI_INT, 0, 999, MPI_COMM_WORLD);
    }
    MPI_Finalize();

    return 0;
}
