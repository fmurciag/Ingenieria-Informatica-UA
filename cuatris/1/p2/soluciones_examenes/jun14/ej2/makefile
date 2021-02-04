main : main.o Biblioteca.o Libro.o
	g++ -o main main.o Biblioteca.o Libro.o

main.o: main.cc Biblioteca.h Libro.h
	g++ -c -g -Wall main.cc

Biblioteca.o: Biblioteca.cc Biblioteca.h Libro.h
	g++ -c -g -Wall Biblioteca.cc

Libro.o: Libro.cc Libro.h
	g++ -c -g -Wall Libro.cc
