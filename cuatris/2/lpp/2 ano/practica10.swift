import Swift


func maxOpt(_ x: Int?, _ y: Int?) -> Int?{
    if let d1 = x, let d2 = y {
        return max(d1, d2)
    }else if let d1 = x {
        return d1
    }else{
        return y
    }
}

let res1 = maxOpt(nil, nil) 
let res2 = maxOpt(10, nil)
let res3 = maxOpt(-10, 30)
print("res1 = \(String(describing: res1))")
print("res2 = \(String(describing: res2))")
print("res3 = \(String(describing: res3))")
// Imprime:
// res1 = nil
// res2 = Optional(10)
// res3 = Optional(30)


func pareja(n: Int, pareja: (Int?, Int?)) -> (Int?, Int?){
     if(n % 2 == 0){
             return (pareja.0, maxOpt( pareja.1, n))
        }else{
           
            return (maxOpt(n, pareja.0), pareja.1)
        }
}


func parejaMayorParImpar2(numeros: [Int]) -> (Int?, Int?) {
    if(numeros.isEmpty){
        return (nil,nil)
    }else{
        let resto = Array(numeros.dropFirst());
        let p = parejaMayorParImpar2(numeros: resto)
        return pareja(n: numeros[0], pareja: p )  
    }

}

let numeros = [-10, 202, 12, 100, 204, 2]
print("\n******\n1b1) Función parejaMayorParImpar2(numeros:)\n******")
print(parejaMayorParImpar2(numeros: numeros))
// Imprime:
// parejaMayorParImpar2(numeros: [-10, 202, 12, 100, 204, 2])
// (nil, Optional(204))

func sumaMaxParesImpares(numeros: [Int]) -> Int{
    let pareja = parejaMayorParImpar2(numeros: numeros)
    if let d1 = pareja.0, let d2 = pareja.1{
        return d1 + d2
    }else if let d1 = pareja.0 {
        return d1
    }else if let d2 = pareja.1 {
        return d2
    }else {
        return 0;
    }
}

let numeros2 = [-10, 202, 12, 100, 204, 2] 
let emptyNumeros = [Int]()   
print("\n**\n1b2) Función sumaMaxParesImpares(numeros: (numeros2))\n**") 
print(sumaMaxParesImpares(numeros: numeros2)) 
print(sumaMaxParesImpares(numeros: emptyNumeros)) 
// Imprime: 
// sumaMaxParesImpares(numeros: [-10, 202, 12, 100, 204, 2]) 
// 204
// 0

///
//ejercicio 2
///
// // let nums = [1,2,3,4,5,6,7,8,9,10]
// // nums.filter{$0 % 3 == 0}.count
// //3
// let nums2 = [1,2,3,4,5,6,7,8,9,10]
// nums2.map{$0+100}.filter{$0 % 5 == 0}.reduce(0,+)
// //215
// let cadenas = ["En", "un", "lugar", "de", "La", "Mancha"]
// cadenas.sorted{$0.count < $1.count}.map{$0.count}
// //[2, 2, 2, 2, 5, 6]
// let cadenas2 = ["En", "un", "lugar", "de", "La", "Mancha"]
// cadenas2.reduce([]) {
//     (res: [(String, Int)], c: String) -> [(String, Int)] in
//         res + [(c, c.count)]}.sorted(by: {$0.1 < $1.1})
// //[("En", 2), ("un", 2), ("de", 2), ("La", 2), ("lugar", 5), ("Mancha", 6)]

func suma(palabras: [String], contienen: Character) -> Int{
    let p = palabras.filter{$0.contains(contienen)}
    return p.reduce(0, {$0 + $1.count})
}

print("\n**\n2c1) Función suma(palabras:contienen:)\n**") 
print(suma(palabras: ["hola", "buenas", "tardes"], contienen: "e"))

func sumaMenoresMayores(nums: [Int], pivote: Int) -> (Int, Int){
    let menor = nums.filter{$0 < pivote}
    let mayor = nums.filter{$0 >= pivote}
    let sumMenor = menor.reduce(0, +)
    let sumMayor = mayor.reduce(0, +)
    return (sumMenor , sumMayor)
}
print(sumaMenoresMayores(nums: [1, 2, 3, 4, 5, 6], pivote: 5))


indirect enum Arbol<T> {
    case nodo(T, [Arbol<T>])
}

func toArray<T>(arbol: Arbol<T>) -> [T] {
    switch arbol {
        case let .nodo(dato, hijos): 
            
        return [dato] + toArray(bosque: hijos)
    }
}

func toArray<T>(bosque: [Arbol<T>] ) -> [T] {
    if bosque.isEmpty {
        return []
    }else {
        let primero = bosque[0]
        let resto = Array(bosque.dropFirst())
        return toArray(arbol: primero) + toArray(bosque: resto)
    }
}

let arbolInt: Arbol = .nodo(53, 
                            [.nodo(13, []), 
                             .nodo(32, []), 
                             .nodo(41, 
                                   [.nodo(36, []), 
                                    .nodo(39, [])
                                   ])
                            ])
let arbolString: Arbol = .nodo("Zamora", 
                               [.nodo("Buendía", 
                                      [.nodo("Albeza", []), 
                                       .nodo("Berenguer", []), 
                                       .nodo("Bolardo", [])
                                      ]), 
                                .nodo("Galván", [])
                               ])

print(toArray(arbol: arbolInt))
print(toArray(arbol: arbolString))

func toArrayFos<T>(arbol: Arbol<T>) -> [T] {
    switch arbol {
        case let .nodo(dato, hijos): 
            
        return hijos.map(toArrayFos).reduce([dato], +)
    }
}
print(toArrayFos(arbol: arbolInt))
print(toArrayFos(arbol: arbolString))

