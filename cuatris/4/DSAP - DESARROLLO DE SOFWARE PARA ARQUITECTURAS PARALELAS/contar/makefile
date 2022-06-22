COMP	= mpicc
OPT     = -O3 -w

all: exe

contar: contar.c
	$(COMP)  $(OPT) -o contar contar.c

exe: contar
		mpirun -np 8 contar
		
exe8: contar
		mpirun -np 8 ./contar

exe4: contar
		mpirun -np 4 ./contar

exe2: contar
		mpirun -np 2 ./contar

clean:
	rm contar

