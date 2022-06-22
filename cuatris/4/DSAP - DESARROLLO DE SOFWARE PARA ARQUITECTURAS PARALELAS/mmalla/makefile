COMP	= mpicc
OPT     = -O2 -w 

all: exe4

mmmalla: mmmalla.c
	$(COMP)  $(OPT) -o mmmalla mmmalla.c -lm

exe9: mmmalla
		mpirun -np 9 ./mmmalla --oversubscribe

exe4: mmmalla
		mpirun -np 4 ./mmmalla

exe2: mmmalla
		mpirun -np 2 ./mmmalla

clean:
	rm mmmalla

