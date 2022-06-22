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
   int *vector;
   int cont=0;
   int numVeces = 0,resultado=0;
   int secuencial=0,paralelo=0;
   MPI_Status estado;
   MPI_Init(&argc,&argv);
   MPI_Comm_rank(MPI_COMM_WORLD,&myrank);
   MPI_Comm_size(MPI_COMM_WORLD,&numprocs);

   if (myrank==0)
   {
      
      printf("tamaño del vector: ");
      scanf("%d", &TAM);
      if(TAM<1 || TAM>MAXTAM)return 0;
      vector=malloc(TAM*sizeof(int));
       TAMrepartido=TAM/numprocs;
       resto=TAM%numprocs;
       indice=resto+TAMrepartido;
      for(int i=0;i<TAM;i++)
      {
         vector[i]=rand()%MAXENTERO+1;
      }
      printf("============Calculo seciencial===========\n\n");
      double start_time=MPI_Wtime();
      
      for (int i=0; i<REPETICIONES; i++){
         for (int j=0; j<TAM; j++){
            if (vector[j] == NUMBUSCADO){
               numVeces++;
            } 
            
         }
      }
      double end_time=MPI_Wtime();
       secuencial=end_time-start_time;
      printf ("Veces que aparece el %d en %d repeticiones del vector de tamañoo %d: %d veces, el %5.2f% \n", NUMBUSCADO, REPETICIONES, TAM,numVeces,(100.0*numVeces)/(TAM*REPETICIONES));
      printf("tiempo:%f\n",secuencial);


      printf("============Calculo paralelo===========\n\n");
      
      //hacemops las llamadas a los procesos hijos
      //printf("valor de indice:%d en %d",indice,myrank);
      start_time=MPI_Wtime();
      int indiceDeHilos=indice;
      for (int i = 1; i < numprocs; i++)
      {  
         MPI_Send(&TAMrepartido,1,MPI_INT,i,1,MPI_COMM_WORLD);
         MPI_Send(&vector[indiceDeHilos],TAMrepartido,MPI_INT,i,1,MPI_COMM_WORLD);
         indiceDeHilos+=TAMrepartido;

      }
      for (int i=0; i<REPETICIONES; i++){
         for (int j=0; j<indice; j++){
            if (vector[j] == NUMBUSCADO){
               resultado++;
            } 
            
         }
      }
      for (int i = 1; i < numprocs; i++)
      {
         MPI_Recv(&cont,1,MPI_INT,i,1,MPI_COMM_WORLD,&estado);
         resultado+=cont;
      }
       end_time=MPI_Wtime();
       paralelo=end_time-start_time;
      printf ("Veces que aparece el %d en %d repeticiones del vector de tamaño %d: %d veces, el %5.2f% \n", NUMBUSCADO, REPETICIONES, TAM,resultado,(100.0*resultado)/(TAM*REPETICIONES));
      printf("tiempo:%f\n",paralelo);


      printf("============Informe===========\n\n");
      double sp = secuencial / paralelo;
      printf("Speed-up= %f \n",sp);
      printf("Eficiencia= %f \n",sp/(double)numprocs);


      
   }else{
      MPI_Recv(&TAMrepartido,1,MPI_INT,0,1,MPI_COMM_WORLD,&estado);
      vector=malloc(TAMrepartido*sizeof(int));
      MPI_Recv(vector,TAMrepartido,MPI_INT,0,1,MPI_COMM_WORLD,&estado);
      //printf("valor de n:%d en %d",TAMrepartido,myrank);
      for (int i=0; i<REPETICIONES; i++){
         for (int j=0; j<TAMrepartido; j++){
            if (vector[j] == NUMBUSCADO){
               cont++;
            } 
            
         }
      }
      MPI_Send(&cont,1,MPI_INT,0,1,MPI_COMM_WORLD);
     
   }
   


   MPI_Finalize();

   return 0;
}
