main : main.o Ciudad.o Partido.o
	g++ -o main main.o Ciudad.o Partido.o
main.o: main.cc Partido.h Ciudad.h
	g++ -c -g -Wall main.cc
Ciudad.o: Ciudad.cc Ciudad.h Partido.h
	g++ -c -g -Wall Ciudad.cc
Partido.o: Partido.cc Partido.h
	g++ -c -g -Wall Partido.cc
	 