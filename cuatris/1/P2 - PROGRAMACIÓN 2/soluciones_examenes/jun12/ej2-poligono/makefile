main : main.o Poligono.o Coordenada.o
	g++ -o main main.o Poligono.o Coordenada.o

main.o: main.cc Poligono.h Coordenada.h
	g++ -c -g -Wall main.cc

Poligono.o: Poligono.cc Poligono.h Coordenada.h
	g++ -c -g -Wall Poligono.cc

Coordenada.o: Coordenada.cc Coordenada.h
	g++ -c -g -Wall Coordenada.cc
