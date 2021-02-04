#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<unistd.h>
#include<arpa/inet.h>
#include<sys/socket.h>
#include<netinet/in.h>
#include<netdb.h>


int main(int argc, char *argv[]){

    int const PUERTO=9999;// numero del puerto
    int const TAM=100;
    char buffer[TAM];//declaramos buffer con su tamaño
    struct hostent *servidor;//info del host
    struct sockaddr_in cliente;//info de la conexion

    servidor=gethostbyname("servidorJ.c");
    if(servidor==NULL){//comprobamos que el servidor exista
        printf("servidor incorrecto, no se puede establecer la conexion.\n");
        exit(1);
    }

    int conexion=socket(AF_INET,SOCK_STREAM,0);//asignamos socket


    bzero((char *)&cliente, sizeof((char *)&cliente));


    cliente.sin_port=PUERTO;//asignamos puerto
    cliente.sin_family=AF_INET;//afignamos protocolo


     bcopy((char *)servidor->h_addr, (char *)&cliente.sin_addr.s_addr, sizeof(servidor->h_length));



    if(connect(conexion,(struct sockaddr *)&cliente, sizeof(cliente))<0){//conectamos
        //abortamos conexion
        close(conexion);
        printf("ERROR AL CONECTAR HOST \n");
        exit(1);
    }
    printf("conectando \n ······················································\n\n");

    fgets(buffer, TAM, stdin);
    send(conexion, buffer, TAM, 0); //envio
    bzero(buffer, TAM);
    recv(conexion, buffer, TAM, 0); //recepción

    printf("%s",buffer);




    return 0;





















}
