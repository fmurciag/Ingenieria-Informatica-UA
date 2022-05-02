main : main.o Ajedrez.o Pieza.o
	g++ -o main main.o Ajedrez.o Pieza.o
main.o: main.cc Pieza.h Ajedrez.h
	g++ -c -g -Wall main.cc
Ajedrez.o: Ajedrez.cc Ajedrez.h Pieza.h
	g++ -c -g -Wall Ajedrez.cc
Pieza.o: Pieza.cc Pieza.h
	g++ -c -g -Wall Pieza.cc
 