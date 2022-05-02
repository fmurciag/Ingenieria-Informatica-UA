#Francisco Juaquin Murcia Gomez 48734281H
OPTS = -Wall -O3 -std=c++11

maxsum-bb: maxsum-bb.cc
	g++ ${OPTS} -o maxsum-bb maxsum-bb.cc	 


filter: filter.cc
	g++ ${OPTS} -o filter filter.cc

all: maxsum-bb filter

clear: 
	rm filter maxsum-bb

tar: maxsum-bb.cc  makefile
	tar cvfz 48734281H.tar.gz maxsum-bb.cc makefile memoria.pdf
