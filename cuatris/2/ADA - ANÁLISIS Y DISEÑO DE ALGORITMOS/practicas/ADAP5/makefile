#Francisco Juaquin Murcia Gomez 48734281H
OPTS = -Wall -O3 -std=c++0x

all: pow2 pow2.recursiones graphs

pow2: pow2.cc
	g++ ${OPTS} -o pow2 pow2.cc	 

pow2.recursiones: pow2
	./pow2 > pow2.recursiones

graphs: pow2.recursiones pow2.gnuplot
	gnuplot pow2.gnuplot

tar: pow2.cc pow2.gnuplot makefile
	tar cvfz 48734281H.tar.gz pow2.cc pow2.gnuplot makefile
