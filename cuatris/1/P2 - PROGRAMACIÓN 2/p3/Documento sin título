OPTS=-Wall -g
OBJS=Base.o Ship.o Container.o Util.o prac3.o
CC=g++

prac3: $(OBJS)
	$(CC) $(OPTS) $(OBJS) -o prac3

prac3.o: prac3.cc Base.h Ship.h Container.h Util.h
	$(CC) $(OPTS) -c prac3.cc

Base.o: Base.cc Base.h Container.h Ship.h Util.h
	$(CC) $(OPTS) -c Base.cc

Ship.o: Ship.cc Ship.h Container.h Util.h
	$(CC) $(OPTS) -c Ship.cc

Container.o: Container.cc Container.h Util.h
	$(CC) $(OPTS) -c Container.cc

Util.o: Util.cc Util.h
	$(CC) $(OPTS) -c Util.cc

clean:
	rm -rf $(OBJS)
