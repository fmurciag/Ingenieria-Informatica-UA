#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <stdlib.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <unistd.h>

int main(int argc, char *argv[]) 
{
   int cnxn_servidor, cnxn_cliente, puerto;
   socklen_t longc; // Contiene la longitud de la estructura.
   struct sockaddr_in servidor, cliente;

   char buffer[100]; // Contiene los mensajes recibidos.

   if((cnxn_servidor = socket(AF_INET, SOCK_STREAM, 0)) < 0)
   {
      fprintf(stderr, "Error al crear el socket\n");
      return 1;
   }
   else 
   {
      cnxn_servidor = socket(AF_INET, SOCK_STREAM, 0); // Asignación del socket.
      puerto = 9999;
      bzero((char *)&servidor, sizeof((char *)&servidor)); //Rellena toda la estructura de 0s.

      servidor.sin_family = AF_INET; // asignación del protocolo.
      servidor.sin_port = htons(puerto); // asignación del puerto.
      servidor.sin_addr.s_addr = INADDR_ANY; // esta macro especifica nuestra dirección.
   }

   if(bind(cnxn_servidor, (struct sockaddr *)&servidor, sizeof(servidor)) < 0)
   {
      printf("Error al asociar el puerto a la conexion\n");
      close(cnxn_servidor);
      return 1;
   }

   listen(cnxn_servidor, 5); //Estamos a la escucha.
   printf("A la escucha en el puerto %d\n"), ntohs(servidor.sin_port);
   longc = sizeof(cliente); //Asignamos el tamaño de la estructura a esta variable.
   cnxn_cliente = accept(cnxn_servidor, (struct sockaddr *)&cliente, &longc); // Esperamos una conexión.

   if(cnxn_cliente < 0)
   {
      printf("Error al aceptar tráfico\n");
      close(cnxn_servidor);
      return 1;
   }
   printf("Conectando con &s:%d\n", inet_ntoa(cliente.sin_addr), htons(cliente.sin_port));
   if(recv(cnxn_cliente, buffer, 100, 0) < 0) //Si recv() recibe 0 el cliente ha cerrado la conexion. Si es menor que 0 ha habido algún error.
   {
      printf("Error al recibir los datos\n");
      close(cnxn_servidor);
      return 1;
   }
   else // Comenzamos a recibir datos del cliente.
   {
      printf("%s\n", buffer);
      bzero((char *)&buffer, sizeof(buffer));
      send(cnxn_cliente, "Recibido\n", 13, 0);
   }
   close(cnxn_servidor);
   return 0;
}
