/*
###################################
FRANCISCO JOAQUIN MURCIA
###################################
*/
#include <stdio.h>
#include <mpi.h>


#define MAXENTERO 100
#define REPETICIONES 100000
#define NUMBUSCADO 10
#define MAXTAM 100000


int main(int argc, char **argv)
{
   int myrank, numprocs, dato;
   int TAM,TAMrepartido,resto,indice;
   int cont=0;
   int numVeces = 0,resultado=0;
   double secuencial,paralelo;
   double start_time;
   double end_time;
   MPI_Status estado;
   MPI_Init(&argc,&argv);
   MPI_Comm_rank(MPI_COMM_WORLD,&myrank);
   MPI_Comm_size(MPI_COMM_WORLD,&numprocs);

   if (myrank==0)
   {
      //parte inicial
      printf("tamaño del vector (1-100000): ");
      scanf("%d", &TAM);
      if(TAM<1 || TAM>MAXTAM)return 0;

      int vector[TAM];//creamos un vector de punteros con tamaño TAM
      TAMrepartido=TAM/numprocs;//indicamos el tamaño que le toca a cada proceso
      resto=TAM%numprocs;
      indice=resto+TAMrepartido;//indicamos el tamaño que va a tener el proceso padre
      //srand(1);
      for(int i=0;i<TAM;i++)//rellenmos el vector
      {
         vector[i]=rand()%MAXENTERO+1;
      }
      printf("=========================Calculo secuencial========================\n\n");
      start_time=MPI_Wtime();//iniciamos el crono
      
      for (int i=0; i<REPETICIONES; i++){//recorremos el vecor y contamos los buscados 
         for (int j=0; j<TAM; j++){
            if (vector[j] == NUMBUSCADO){
               numVeces++;
            } 
            
         }
      }
      end_time=MPI_Wtime();//paramos el crono
      //imprimimos resultados
      secuencial=end_time-start_time;
      printf ("Veces que aparece el %d en %d repeticiones del vector de tamañoo %d: %d veces, el %5.2f% \n", NUMBUSCADO, REPETICIONES, TAM,numVeces,(100.0*numVeces)/(TAM*REPETICIONES));
      printf("tiempo:%f\n",secuencial);


      printf("=========================Calculo paralelo==========================\n\n");
      //este codigo lo realiza el proceso padre
      
      //printf("valor de indice:%d en %d",indice,myrank);
      start_time=MPI_Wtime();//iniciamos el crono
      int indiceDeHilos=indice;
      //hacemops las llamadas a los procesos hijos 
      //enviandoles el indece donde comenzara su vecor y su tamaño para que construyan el vector
      for (int i = 1; i < numprocs; i++)
      {  
         MPI_Send(&TAMrepartido,1,MPI_INT,i,1,MPI_COMM_WORLD);
         MPI_Send(&vector[indiceDeHilos],TAMrepartido,MPI_INT,i,1,MPI_COMM_WORLD);
         indiceDeHilos+=TAMrepartido;

      }
      for (int i=0; i<REPETICIONES; i++){//hacemos la busqueda del numero
         for (int j=0; j<indice; j++){
            if (vector[j] == NUMBUSCADO){
               resultado++;
            } 
            
         }
      }
      for (int i = 1; i < numprocs; i++)//recivimos y sumamos los resultados de los procesos hijos
      {
         MPI_Recv(&cont,1,MPI_INT,i,1,MPI_COMM_WORLD,&estado);
         resultado+=cont;
      }
      end_time=MPI_Wtime();//paramos el crono
      //imprimimos resultados
       paralelo=end_time-start_time;
      printf ("Veces que aparece el %d en %d repeticiones del vector de tamaño %d: %d veces, el %5.2f% \n", NUMBUSCADO, REPETICIONES, TAM,resultado,(100.0*resultado)/(TAM*REPETICIONES));
      printf("tiempo:%f\n",paralelo);


      printf("============Informe===========\n\n");
      double sp = secuencial / paralelo;
      printf("Speed-up= %f \n",sp);
      printf("Eficiencia= %f \n",sp/(double)numprocs);


      
   }else{//codigo de los procesos hijos
      MPI_Recv(&TAMrepartido,1,MPI_INT,0,1,MPI_COMM_WORLD,&estado);//recivimos el tamaño del vector
      int vector[TAMrepartido];//creamos el vectro que va a leer
      MPI_Recv(vector,TAMrepartido,MPI_INT,0,1,MPI_COMM_WORLD,&estado);//recivimos el trozo de vector que nos interesa para este proceso

      for (int i=0; i<REPETICIONES; i++){//contamos 
         for (int j=0; j<TAMrepartido; j++){
            if (vector[j] == NUMBUSCADO){
               cont++;
            } 
            
         }
      }

      MPI_Send(&cont,1,MPI_INT,0,1,MPI_COMM_WORLD);//enviamos el resulatdo al padre
     
   }
   


   MPI_Finalize();

   return 0;
}
