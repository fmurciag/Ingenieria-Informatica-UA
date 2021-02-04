import json
import os

data = {"preguntas":[]}
salida = str()
while(salida is not "s"):
	os.system('cls' if os.name == 'nt' else 'clear')
	newShit={}
	newShit["tema"] = input("tema: ")
	newShit["numero"] = input("numero: ")
	newShit["enunciado"] = input("enunciado: ").rstrip()
	newShit["0"] = input("0: ")
	newShit["1"] = input("1: ")
	newShit["2"] = input("2: ")
	newShit["3"] = input("3: ")
	newShit["correcto"] = input("correcto: ")
	data["preguntas"].append(newShit)
	print("De momento llevas:", str(data))
	salida = input("\ns para salir: ")

with open('data.json', 'w') as outfile:
    json.dump(data, outfile)