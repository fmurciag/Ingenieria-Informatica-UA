import Foundation
import Swift
//Ejercicio 1
func prefijos(prefijo: String, palabras: [String]) -> [Bool] {

    if(palabras.isEmpty){
        return []
    }else{
        let p = palabras[0]

        return [p.hasPrefix(prefijo)] + prefijos(prefijo: prefijo ,palabras: Array(palabras.dropFirst()))
    }
}
let array = ["anterior", "antígona", "antena"]
let prefijo = "ante"
print("\n******\n1a) Función prefijos(prefijo:palabras:)\n******")
print(prefijos(prefijo: prefijo, palabras: array))
// Imprime: [true, false, true]

func parejaMayorParImpar(numeros: [Int]) -> (Int, Int) {
    if(numeros.isEmpty){
        return (0,0)
    }else{
        if(numeros[0] % 2 == 0){
             return (pareja.0, max(parejaMayorParImpar(Array(numeros.dropFirst())), numeros[0]))
        }else{
           
            return (max(numeros[0], parejaMayorParImpar(Array(numeros.dropFirst()))), pareja.1)
        }
        
    }

}

//Ejercicio 2
//A
func compruebaParejas(_ numeros:[Int], funcion:(Int) -> Int) -> [(Int, Int)]{
    if numeros.count <= 1 {
        return []
    }else {
        let primero = numeros[0];
        let segundo = numeros[1]
        let resto = Array(numeros.dropFirst())
        if funcion(primero) == segundo{
            return [(primero, segundo)] + compruebaParejas(resto, funcion:funcion)
        }else {
            return compruebaParejas(resto, funcion:funcion)
        } 
    }
}

func cuadrado(x: Int) -> Int {
   return x * x
}
//B
print(compruebaParejas([2, 4, 16, 5, 10, 100, 105], funcion: cuadrado))
// Imprime [(2,4), (4,16), (10,100)]

func coinciden(parejas:[(Int,Int)], funcion:(Int) -> Int) -> [Bool]{
    if parejas.isEmpty {
        return []
    }else {
        let primero = parejas[0]
        let resto = Array(parejas.dropFirst())
        
        return [funcion(primero.0) == primero.1] + coinciden(parejas:resto, funcion:funcion)
        
    }
}
let array2 = [(2,4), (4,14), (4,16), (5,25), (10,100)]

print(coinciden(parejas: array2, funcion: cuadrado))

//ejercicio 3
enum Movimiento{
    case deposito (Double)
    case cargoRecibo  (String, Double)
    case cajero (Double)
}


func calcula(movimiento:Movimiento,total:(Double,[String])) -> (Double,[String]) {
    switch movimiento {
        case let .deposito(cantidad):
            return (total.0 + cantidad, total.1)
        case let .cargoRecibo(nombre, cantidad):
            return (total.0 - cantidad, [nombre] + total.1)
        case let .cajero(cantidad):
            return (total.0 - cantidad, total.1)
       
    }
}

func aplica(movimientos:[Movimiento]) -> (Double,[String]) {
    if movimientos.isEmpty {
        return (0, [])
    }else {
        let primero = movimientos[0]
        let resto = Array(movimientos.dropFirst())
        return calcula(movimiento: primero, total: aplica(movimientos:resto))
    }
}
let movimientos: [Movimiento] = [.deposito(830.0), .cargoRecibo("Gimnasio", 45.0), .deposito(400.0), .cajero(100.0), .cargoRecibo("Fnac", 38.70)]
print(aplica(movimientos: movimientos))
//Imprime (1046.3, ["Gimnasio", "Fnac"])

//Ejercicio 4
indirect enum ArbolBinario{
    case vacio
    case nodo(Int, ArbolBinario, ArbolBinario )
}


let arbol: ArbolBinario = .nodo(8, 
                                .nodo(2, .vacio, .vacio), 
                                .nodo(12, .vacio, .vacio))

func suma(arblb:ArbolBinario) -> Int {
    switch arblb {
    case  .vacio:
        return 0
    case let .nodo(dato, izq, der):
        return dato + suma(arblb:izq) + suma(arblb:der)
    }
}
print(suma(arblb: arbol))
// Imprime: 22

//ejercicio 5

indirect enum Arbol {
    case nodo(Int, [Arbol])
}
func suma(arbol: Arbol, cumplen : (Int) -> Bool ) -> Int {
    switch arbol {
        case let .nodo(dato, hijos): 
            if (cumplen(dato)) {
                return dato + suma(bosque: hijos, cumplen: cumplen )
            }
        return suma(bosque: hijos, cumplen: cumplen )
    }
}

func suma(bosque: [Arbol], cumplen : (Int) -> Bool ) -> Int {
    if bosque.isEmpty {
        return 0
    }else {
        let primero = bosque[0]
        let resto = Array(bosque.dropFirst())
        return suma(arbol: primero, cumplen: cumplen) + suma(bosque: resto, cumplen: cumplen)
    }
}
/*
Definimos el árbol

    10
   / | \
  3  5  8
  |
  1

*/

let arbol1 = Arbol.nodo(1, [])
let arbol3 = Arbol.nodo(3, [arbol1])
let arbol5 = Arbol.nodo(5, [])
let arbol8 = Arbol.nodo(8, [])
let arbol10 = Arbol.nodo(10, [arbol3, arbol5, arbol8])

func esPar(x: Int) -> Bool {
    return x % 2 == 0
}

print("La suma del árbol es: \(suma(arbol: arbol10, cumplen: esPar))")
// Imprime: La suma del árbol genérico es: 18


