OPTS=-Wall -g
OBJS=Hoguera.o Scheduler.o main.o
CC=g++

main: $(OBJS)
	$(CC) $(OPTS) $(OBJS) -o main
        
main.o: main.cc Hoguera.h Scheduler.h
	$(CC) $(OPTS) -c main.cc
                
Scheduler.o: Scheduler.cc Scheduler.h Hoguera.h
	$(CC) $(OPTS) -c Scheduler.cc
                        
Hoguera.o: Hoguera.cc Hoguera.h
	$(CC) $(OPTS) -c Hoguera.cc
                                