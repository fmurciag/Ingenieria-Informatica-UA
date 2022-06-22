#ifndef _COMIDA_H_
#define _COMIDA_H_
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <sys/types.h>
#include <unistd.h>

typedef struct Monitor{
    int palillosDisponibles[5];//palillos disponibles por filosofo
    pthread_mutex_t mutex;
    pthread_cond_t estado[5];
}Monitor_t;


Monitor_t iniciar();//inicializacion del monitor

void cogerPalillo(Monitor_t *m,int filosofo);//bloquear el recorso
void dejarPalillo(Monitor_t *m,int filosofo);// desbloquear el recurso




#endif