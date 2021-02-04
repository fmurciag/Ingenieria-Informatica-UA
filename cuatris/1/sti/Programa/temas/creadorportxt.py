import json
import os
import sys

data = {"preguntas":[]}
salida = str()
tema = input("tema: ")

with open("tema.txt", "r") as f:
    content = f.readlines()
content = [x.strip() for x in content]
x=0


def buscaproblema(x, content):
	if str(content[x][0]) not in "123456789ABCD*abcd":
		print("Hay un error en la línea:", x)
		sys.exit()

while(x is not len(content)):
	buscaproblema(x, content)
	os.system('cls' if os.name == 'nt' else 'clear')
	newShit={}
	newShit["tema"] = tema
	print("Tema: ", newShit["tema"])
	newShit["numero"] = content[x].split(".")[0]
	print("numero: ", newShit["numero"])
	newShit["enunciado"] = ''.join([e+"-" for e in content[x].split("-") if e][1:]).strip()[:-1]
	print("enunciado: ", newShit["enunciado"])
	x=x+1
	buscaproblema(x, content)
	newShit["0"] = content[x].split(".")[1].strip()
	print("0: ", newShit["0"])
	if(str(content[x][0])=="*"):
		print("correcto: 0")
		newShit["correcto"] = "0"
	x=x+1
	buscaproblema(x, content)
	newShit["1"] = content[x].split(".")[1].strip()
	print("1: ", newShit["1"])
	if(str(content[x][0])=="*"):
		print("correcto: 1")
		newShit["correcto"] = "1"
	x=x+1
	buscaproblema(x, content)
	newShit["2"] = content[x].split(".")[1].strip()
	print("2: ", newShit["2"])
	if(str(content[x][0])=="*"):
		print("correcto: 2")
		newShit["correcto"] = "2"
	x=x+1
	buscaproblema(x, content)
	newShit["3"] = content[x].split(".")[1].strip()
	print("3: ", newShit["3"])
	if(str(content[x][0])=="*"):
		print("correcto: 3")
		newShit["correcto"] = "2"
	x=x+1

	try:
		newShit["correcto"]
	except KeyError:
		print("Hay un error en el ejercicio: No hay correcto")
		sys.exit()

	data["preguntas"].append(newShit)
	#print("De momento llevas:", str(data))
	salida = input("\ns para salir: ")

with open('data.json', 'w') as outfile:
    json.dump(data, outfile)