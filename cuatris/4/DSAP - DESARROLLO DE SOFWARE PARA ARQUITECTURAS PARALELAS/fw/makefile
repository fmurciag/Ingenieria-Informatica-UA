COMP	= mpicc
OPT     = -O2 -w 

all: exe4

fw: fw.c
	$(COMP)  $(OPT) -o fw fw.c -lm

exe8: fw
		mpirun -np 8 ./fw 

exe4: fw
		mpirun -np 4 ./fw

exe2: fw
		mpirun -np 2 ./fw

clean:
	rm fw

FW:
	mpicc -O2 -w -o FW FW.c -lm

exeA: FW
	mpirun -np 4 ./FW


