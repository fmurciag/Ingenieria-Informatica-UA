COMP	= mpicc
OPT     = -O3 -w

anillo: anillo.c
	$(COMP)  $(OPT) -o anillo anillo.c

exe: anillo
		mpirun -np 4 ./anillo
clean:
	rm anillo

