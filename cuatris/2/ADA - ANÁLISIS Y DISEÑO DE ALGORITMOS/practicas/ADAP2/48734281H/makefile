#Francisco Juaquin Murcia Gomez 48734281H
OPTS = -Wall -O3 -std=c++0x

qs-vs-hs: qs-vs-hs.cc
	g++ ${OPTS} -o qs-vs-hs qs-vs-hs.cc	 

qs-vs-hs.CPUtimes: qs-vs-hs
	./qs-vs-hs > qs-vs-hs.CPUtimes

graphs: qs-vs-hs.CPUtimes qs-vs-hs.gnuplot
	gnuplot qs-vs-hs.gnuplot

all: qs-vs-hs qs-vs-hs.CPUtimes graphs

tar: qs-vs-hs.cc qs-vs-hs.gnuplot makefile
	tar cvfz 48734281H.tar.gz qs-vs-hs.cc qs-vs-hs.gnuplot makefile

clean: 
	rm qs-vs-hs qs-vs-hs.CPUtimes fit.log qs-vs-hs*.png Quicksort.png Heapsort.png *~
 
