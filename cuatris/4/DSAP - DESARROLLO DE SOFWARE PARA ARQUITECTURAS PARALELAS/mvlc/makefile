COMP	= mpicc
OPT     = -O2 -w

all: exe4

contar: mvlc.c
	$(COMP)  $(OPT) -o mvlc mvlc.c

exe8: contar
		mpirun -np 8 ./mvlc

exe4: contar
		mpirun -np 4 ./mvlc

exe2: contar
		mpirun -np 2 ./mvlc

clean:
	rm mvlc

