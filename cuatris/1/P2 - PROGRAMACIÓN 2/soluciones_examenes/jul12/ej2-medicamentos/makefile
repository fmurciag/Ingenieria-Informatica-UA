main : main.o Paciente.o Medicamento.o
	g++ -o main main.o Paciente.o Medicamento.o
main.o: main.cc Paciente.h Medicamento.h
	g++ -c -g -Wall main.cc
Paciente.o: Paciente.cc Paciente.h Medicamento.h
	g++ -c -g -Wall Paciente.cc
Medicamento.o: Medicamento.cc Medicamento.h
	g++ -c -g -Wall Medicamento.cc
