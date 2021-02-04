#include <stdio.h>
#include <errno.h>
#include <unistd.h>
#include <malloc.h>
#include <string.h>
#include <sys/socket.h>
#include <resolv.h>
#include <netdb.h>
#include <openssl/ssl.h>
#include <openssl/err.h>
#define FAIL    -1
int OpenConnection(const char *hostname, int port)
{
    int sd;
    struct hostent *host;
    struct sockaddr_in addr;
    if ( (host = gethostbyname(hostname)) == NULL )
    {
        perror(hostname);
        abort();
    }
    sd = socket(PF_INET, SOCK_STREAM, 0);
    bzero(&addr, sizeof(addr));
    addr.sin_family = AF_INET;
    addr.sin_port = htons(port);
    addr.sin_addr.s_addr = *(long*)(host->h_addr);
    if ( connect(sd, (struct sockaddr*)&addr, sizeof(addr)) != 0 )
    {
        close(sd);
        perror(hostname);
        abort();
    }
    return sd;
}
SSL_CTX* InitCTX(void)
{
    SSL_METHOD *method;
    SSL_CTX *ctx;
    OpenSSL_add_all_algorithms();  /* carga de algoritmos critográficos */
    SSL_load_error_strings();   /* activa registro de errores */
    method = TLSv1_2_client_method();  /* crea instancia de método cliente con seguridad TLS */
    ctx = SSL_CTX_new(method);   /* crea contexto seguro */
    if ( ctx == NULL )
    {
        ERR_print_errors_fp(stderr);
        abort();
    }
    return ctx;
}

int main(int count, char *strings[])
{
    SSL_CTX *ctx;
    int server;
    SSL *ssl;
    char buf[1024];
    char acClientRequest[1024] = {0};
    int bytes;
    char *hostname, *portnum;
    if ( count != 3 )
    {
        printf("uso: %s <hostname> <portnum>\n", strings[0]);
        exit(0);
    }
    SSL_library_init();
    hostname=strings[1];
    portnum=strings[2];
    ctx = InitCTX();
    server = OpenConnection(hostname, atoi(portnum)); /* Conexión no-cifrada con servidor */
    ssl = SSL_new(ctx);      /* crea un nueva conexión SSL */
    SSL_set_fd(ssl, server);    /* asocia el descriptor no-cifrado a la conexión cifrada*/
    if ( SSL_connect(ssl) == FAIL )   /* conecta con el servidor de forma cifrada */
        ERR_print_errors_fp(stderr);
    else
    {
        char acUsername[16] = {0};
        char acPassword[16] = {0};
        const char *cpRequestMessage = "Usuario: %s   Contraseña: %s";
        printf("Introduce Usuario: ");
        scanf("%s",acUsername);
        printf("\nIntroduce Password: ");
        scanf("%s",acPassword);
        sprintf(acClientRequest, cpRequestMessage, acUsername,acPassword);   /* construye contestación */
        printf("\n\nAlgoritmos de cifrado: %s \n", SSL_get_cipher(ssl));
        SSL_write(ssl,acClientRequest, strlen(acClientRequest));   /* encripta y envía el mensaje */
        bytes = SSL_read(ssl, buf, sizeof(buf)); /* obtiene la respuesta y la desencripta */
        buf[bytes] = 0;
        printf("\nRecibido: \"%s\"\n", buf);
        SSL_free(ssl);        /* libera la conexión cifrada */
    }
    close(server);         /* cierra el socket */
    SSL_CTX_free(ctx);        /* libera el contexto: algoritmos de cifrado, etc */
    return 0;
}
