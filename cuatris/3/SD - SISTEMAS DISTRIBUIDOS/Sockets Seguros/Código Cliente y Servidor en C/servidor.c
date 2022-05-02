#include <errno.h>
#include <unistd.h>
#include <malloc.h>
#include <string.h>
#include <arpa/inet.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <resolv.h>
#include "openssl/ssl.h"
#include "openssl/err.h"
#define FAIL    -1

int OpenListener(int port) // se crea el socket y la infraestructura de conexión
{
    int sd;
    struct sockaddr_in addr;
    sd = socket(PF_INET, SOCK_STREAM, 0);
    bzero(&addr, sizeof(addr));
    addr.sin_family = AF_INET;
    addr.sin_port = htons(port);
    addr.sin_addr.s_addr = INADDR_ANY;
    if (bind(sd, (struct sockaddr*)&addr, sizeof(addr)) != 0 )
    {
        perror("error al enlazar el puerto");
        abort();
    }
    if ( listen(sd, 10) != 0 )
    {
        perror("error al configurar el puerto de escucha");
        abort();
    }
    return sd;
}
int isRoot()
{
    if (getuid() != 0)
    {
        return 0;
    }
    else
    {
        return 1;
    }
}
SSL_CTX* InitServerCTX(void)
{
    SSL_METHOD *method;
    SSL_CTX *ctx;
    OpenSSL_add_all_algorithms();  /* se cargan los algoritmos criptográficos */
    SSL_load_error_strings();   /* se cargan los mensajes de error */
    method = TLSv1_2_server_method();  /* creación de instancia de método servidor seguro */
    ctx = SSL_CTX_new(method);   /* se crea el contexto seguro del método servidor */
    if ( ctx == NULL )
    {
        ERR_print_errors_fp(stderr);
        abort();
    }
    return ctx;
}
void LoadCertificates(SSL_CTX* ctx, char* CertFile, char* KeyFile)
{
    /* se obtiene el certificado local desde el fichero .pem */
    if ( SSL_CTX_use_certificate_file(ctx, CertFile, SSL_FILETYPE_PEM) <= 0 )
    {
        ERR_print_errors_fp(stderr);
        abort();
    }
    /* se obtiene al clave privada a partir del certificado */
    if ( SSL_CTX_use_PrivateKey_file(ctx, KeyFile, SSL_FILETYPE_PEM) <= 0 )
    {
        ERR_print_errors_fp(stderr);
        abort();
    }
    /* se verifica la clave privada */
    if ( !SSL_CTX_check_private_key(ctx) )
    {
        fprintf(stderr, "Clave privada no compatible con el certificado público\n");
        abort();
    }
}

void Servlet(SSL* ssl) /* se sirve la conexión */
{
    char buf[1024] = {0};
    int sd, bytes;
    const char* ServerResponse="ACCESO PERMITIDO. Pide tu deseo!!";
    const char *cpValidMessage = "Usuario: SD   Contraseña: 12345678";
    if ( SSL_accept(ssl) == FAIL )     /* do SSL-protocol accept */
        ERR_print_errors_fp(stderr);
    else
    {
        bytes = SSL_read(ssl, buf, sizeof(buf)); /* obtener respuesta */
        buf[bytes] = '\0';
        printf("Mensaje del cliente: \"%s\"\n", buf);
        if ( bytes > 0 )
        {
            if(strcmp(cpValidMessage,buf) == 0)
            {
                SSL_write(ssl, ServerResponse, strlen(ServerResponse)); /* envía respuesta */
            }
            else
            {
                SSL_write(ssl, "NO SÉ DE QUÉ ME HABLAS. FUERA!!", strlen("NO SÉ DE QUÉ ME HABLAS. FUERA!!")); /* send reply */
            }
        }
        else
        {
            ERR_print_errors_fp(stderr);
        }
    }
    sd = SSL_get_fd(ssl);       /* obtiene la conexión de socket seguro */
    SSL_free(ssl);         /* libera la conexión segura */
    close(sd);          /* cierra el socket */
}
int main(int count, char *Argc[])
{
    SSL_CTX *ctx;
    int server;
    char *portnum;
	 //Solo root/sudo puede ejecutar el servidor 
    if(!isRoot())
    {
        printf("¡¡Este programa debe ser ejecutado por root!!");
        exit(0);
    }
    if ( count != 2 )
    {
        printf("Uso: %s <portnum>\n", Argc[0]);
        exit(0);
    }
    // Inicialización de la librería SSL 
    SSL_library_init();
    portnum = Argc[1];
    ctx = InitServerCTX();        /* inicializa SSL */
    LoadCertificates(ctx, "mycert.pem", "mycert.pem"); /* carga de certificados */
    server = OpenListener(atoi(portnum));    /* creación del socket servidor */
    while (1)
    {
        struct sockaddr_in addr;
        socklen_t len = sizeof(addr);
        SSL *ssl;
        int client = accept(server, (struct sockaddr*)&addr, &len);  /* acepta conexiones entrantes */
        printf("Connection: %s:%d\n",inet_ntoa(addr.sin_addr), ntohs(addr.sin_port));
        ssl = SSL_new(ctx);              /* obtención de contexto de conexión seguro */
        SSL_set_fd(ssl, client);      /* conversión del socket en socket seguro SSL */
        Servlet(ssl);         /* arranque del servicio de conexión */
    }
    close(server);          /* cierre del socket servidor */
    SSL_CTX_free(ctx);         /* liberación de contexto seguro */
}
