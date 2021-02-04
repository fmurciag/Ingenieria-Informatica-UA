main : main.o Playlist.o Cancion.o
	g++ -o main main.o Playlist.o Cancion.o
main.o: main.cc Playlist.h Cancion.h
	g++ -c -g -Wall main.cc
Playlist.o: Playlist.cc Playlist.h Cancion.h
	g++ -c -g -Wall Playlist.cc
Cancion.o: Cancion.cc Cancion.h
	g++ -c -g -Wall Cancion.cc
