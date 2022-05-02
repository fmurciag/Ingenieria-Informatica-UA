main : main.o Album.o Collection.o
	g++ -o main main.o Album.o Collection.o

main.o: main.cc Album.h Collection.h
	g++ -c -g -Wall main.cc

Album.o: Album.cc Album.h
	g++ -c -g -Wall Album.cc

Collection.o: Collection.cc Collection.h Album.h
	g++ -c -g -Wall Collection.cc
