main : main.o Partida.o Jugador.o
	g++ -o main main.o Partida.o Jugador.o
main.o: main.cc Partida.h Jugador.h
	g++ -c -g -Wall main.cc
Partida.o: Partida.cc Partida.h Jugador.h
	g++ -c -g -Wall Partida.cc
Jugador.o: Jugador.cc Jugador.h
	g++ -c -g -Wall Jugador.cc
