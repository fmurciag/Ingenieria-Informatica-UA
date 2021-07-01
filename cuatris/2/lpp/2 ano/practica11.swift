
// Solución práctica 11: Programación Orientada a Objetos en Swift (1)

import Foundation

/*
===========
Ejercicio 1
===========
*/

print("""
---
Ejercicio 1
---\n
""")

print("a)")

struct MiEstructura {
    var x = 0
}

class MiClase {
    var x = 0
}

func foo(_ c: MiClase, _ x: Int) {
    c.x = x
}

var s1 = MiEstructura()
var s2 = s1
let c1 = MiClase()
var c2 = c1

s1.x = 10
c1.x = 10
print ("s2.x: \(s2.x), c2.x: \(c2.x)")

foo(c1, 20)
print("c1.x, después de llamar a la función: \(c1.x)")

print("b)")

struct Coord {
    var x: Double
    var y: Double

    func movida(incX: Double, incY: Double) -> Coord {
        return Coord(x: x+incX, y: y+incY)
    }

    mutating func mueve(incX: Double, incY: Double) {
        x = x + incX
        y = y + incY
    }
}

struct Cuadrado {
    var esquina = Coord(x: 0.0, y: 0.0)
    var lado: Double

    // Versión 1
    func movido1(incX: Double, incY: Double) -> Cuadrado {
        let cuadrado = Cuadrado(esquina: self.esquina.movida(incX: incX, incY: incY),
                                lado: self.lado)
        return cuadrado
    }

    // Versión 2
    func movido2(incX: Double, incY: Double) -> Cuadrado {
        var cuadrado = self
        cuadrado.esquina.mueve(incX: incX, incY: incY)
        return cuadrado
    }
}

print("""
La versión 1 crea un nuevo cuadrado en el que usa como esquina una esquina nueva 
que devuelve el método "movida" aplicado sobre la esquina actual.
La versión 2 crea también un nuevo cuadrado (al hacer la asignación se hace una
copia) y después llama al método mutador "mueve".
""")

/*
===========
Ejercicio 2
===========
*/

print("""
---
Ejercicio 2
---\n
""")

print("a)")

struct Valor {
    // Propiedad de la instancia
    var valor: Int = 0 {
        willSet {
            // Antes de actualizar el valor de la instancia
            // actualiza el valor de la propiedad z de Valor (propiedad del tipo)
            Valor.z += newValue
        }        
        didSet {
            // Después de actualizar el valor de la instancia
            // corrige su valor para que no sea mayor que 10
            if valor > 10 {
                valor = 10
            }
        }
    }
    static var z = 0
}

var v1 = Valor()
var v2 = Valor()
v1.valor = 20
v2.valor = 8
print(v1.valor + v2.valor + Valor.z)
// Imprime 46


/* Apartado b */

print("b)")

class Base {
    // Propiedades almacenadas
    var a = 100
    var b = 2
    var c = 10

    // Propiedad calculada
    var x: Int {a+b}

    // Método de instancia
    func suma() -> Int {
        return a+b
    }

    // Propiedad del tipo
    static var valor = 0

    // Método del tipo
    static func incrementa() -> Int {
        valor += 1
        return valor
    }
}

class Derivada: Base {
    // No podemos sobreescribir el valor de una propiedad almacenada
    // override var a = 200
    // Pero sí que podemos sobreescribir el getter y el setter
    override var a: Int {
        get {
            super.a + 1000
        }
        set(nuevoValor) {
            print("Actualizando valor de a con : \(nuevoValor)")
            super.a = nuevoValor
        }
    }

    // Sí que podemos añadir un observador en la clase derivada
    override var c: Int {
        willSet(nuevoValor) {
            print("Se va a cambiar el valor de c: \(nuevoValor)")
        }
    }

    // Sí que podemos sobreescribir el getter de una 
    // propiedad calculada y añadirle un setter
    override var x: Int {
        get {a*b}
        set(nuevoValor) {
            a = nuevoValor
            b = nuevoValor
        }
    }

    // Sobreescribimos el método de instancia
    // y dentrol llamamos al método de la clase padre
    override func suma() -> Int {
        let suma = super.suma()
        return suma + 1000
    }
}


print("\nb)")

var d = Derivada()
print(d.a) // Se ejecuta getter clase derivada - Imprime 1100 (super.a + 1000)
d.a = 400  // Se ejecuta setter clase derivada - Guarda 400 en super.a e imprime mensaje
print(d.a) // Imprime 1400 (super.a + 1000)
print(d.x) // Se ejecuta getter clase derivada de la propiedad calculada x - Imprime 2800 (a*b)
d.x = 0    // Se ejecuta setter clase derivada de la propiedad calculada x - Actualiza con el valor 0 a y b
print(d.a) // Imprime 1000
print(d.b) // Imprime 0
d.c = 10   // Se ejecuta el observador de la propiedad c de clase derivada -
           // Imprime "Se va a cambiar el valor de c a 10"

// Accedemos a la propiedad y método estático de la clase
// base en la clase derivada

print(Derivada.valor) // Imprime 0
print(Derivada.incrementa()) // Imprime 1
print(d.suma()) // Imprime 2000 (a + b + 1000)

/*
Resumen:
- ¿Se puede sobreescribir el valor de una propiedad almacenada? ¿Y calculada?
    Se pueden sobreescribir los getters y setters. Si la propiedad padre es de solo
    lectura, la propiedad sobreescrita puede ser de lectura y escritura. Una propiedad
    padre de lectura-escritura no se puede sobreescribir con una de sólo lectura.
    Se puede comprobar en las variables 'a' y 'x' de la clase derivada.
- ¿Se puede añadir un observador a una propiedad de la clase base en una clase derivada?
    Sí. Lo hemos hecho en la variable 'c' de la clase derivada.
- ¿Hereda la clase derivada propiedades y métodos estáticos de la clase base?
    Sí. Lo hemos comprobado accediendo a valor e incrementa() en la clase derivada
- ¿Cómo se puede llamar a la implementación de un método de la clase base en una sobreescritura 
   de ese mismo método en la clase derivada?
    Usando 'super'. Lo hemos hecho en el método 'suma()' de la clase derivada.
*/



/*
================================
Ejercicio 3: Partidos de fútbol
================================
*/

// /*
//    El ejercicio puede solucionarse de varias formas.
//    Proponemos a continuación una en la que se modela el problema 
//    con tres estructuras:
//
//    Equipo - Contiene el nombre y los puntos de un equipo y un método
//             para actualizar las puntaciones
//    Partido - Nombres y goles del partido
//    Liga - Contiene array de equipos y partidos jugados, así como los 
//           métodos para actualizar una jornada
// */

struct Partido {
    let local : String
    let golesLocal : Int
    let visitante : String
    let golesVisitante : Int
}

struct Equipo {
    let nombre : String
    var puntos : Int
    
    mutating func actualizaPuntos(puntos: Int) {
        self.puntos += puntos
    }
}

struct Liga {
    var equipos: [Equipo]
    var partidos: [Partido]
    
    func mostrarPuntuaciones() {
        for e in equipos {
            print("\(e.nombre): \(e.puntos) puntos")
        }
    }
    
    func mostrarPartidos() {
        for p in partidos {
            print("\(p.local) \(p.golesLocal) - \(p.visitante) \(p.golesVisitante)")
        }
    }
    
    func buscarEquipo(nombre:String) -> Int? {
        var i = 0
        var encontrado = false
        while !encontrado && i < equipos.count {
            encontrado = equipos[i].nombre == nombre
            if !encontrado {
                i+=1
            }
        }
        if !encontrado {
            return nil
        }
        else {
            return i
        }
    }
    
    func puntosPartido(favor:Int, contra:Int) -> Int {
        if favor > contra {
            return 3
        }
        else if favor == contra {
            return 1
        }
        else {
            return 0
        }
    }
    
    mutating func añadirPartidos(jornada:[Partido]) {
        self.partidos+=jornada
        actualizarPuntuaciones(jornada:jornada)
    }
    
    mutating func actualizarPuntuaciones(jornada:[Partido]) {
        for p in jornada {
            if let i = buscarEquipo(nombre:p.local) {
                equipos[i].actualizaPuntos(puntos:puntosPartido(favor:p.golesLocal,
                                                                contra:p.golesVisitante))
            }
            if let i = buscarEquipo(nombre:p.visitante) {
                equipos[i].actualizaPuntos(puntos:puntosPartido(favor:p.golesVisitante,
                                                                contra:p.golesLocal))
            }
        }
    }
}

// DEMOSTRACIÓN

print("\nEjercicio 3\n")

var liga: Liga

liga = Liga(equipos:[Equipo(nombre:"Real Madrid", puntos:0),
                     Equipo(nombre:"Barcelona", puntos:0),
                     Equipo(nombre:"Atlético Madrid", puntos:0),
                     Equipo(nombre:"Valencia", puntos:0),
                     Equipo(nombre:"Athlétic Bilbao", puntos:0),
                     Equipo(nombre:"Sevilla", puntos:0)],
            partidos:[])

print("\nPuntuación antes de los partidos:")
liga.mostrarPuntuaciones()

var partidos = [Partido(local:"Real Madrid", golesLocal:0,
                        visitante:"Barcelona", golesVisitante:3),
                Partido(local:"Sevilla", golesLocal:1,
                        visitante:"Athlétic Bilbao", golesVisitante:1),
                Partido(local:"Valencia", golesLocal:2,
                        visitante:"Atlético Madrid", golesVisitante:1),
                Partido(local:"Liverpool", golesLocal:4, visitante:"Barcelona", golesVisitante:0)]

liga.añadirPartidos(jornada:partidos)
print("\nResultados:")
liga.mostrarPartidos()

print("\nPuntuación después de los partidos:")
liga.mostrarPuntuaciones()


/*
================================
Ejercicio 4: Figuras geométricas
================================
*/


// Trabajamos con coordenadas de pantalla,
// donde la esquina superior izquierda tiene las coordenadas
// (0,0), la coordenada X crece hacia la derecha y la Y 
// crece hacia abajo

// Función auxiliar que calcula el área de un triángulo

func areaTriangulo(_ p1: Punto, _ p2: Punto, _ p3: Punto) -> Double {
    let det = p1.x * (p2.y - p3.y) + p2.x * (p3.y - p1.y) + p3.x * (p1.y - p2.y)
    return abs(det)/2
}

struct Punto {
    var x = 0.0, y = 0.0
}

struct Tamaño {
    var ancho = 0.0, alto = 0.0
}

class Figura {
    var origen: Punto
    var tamaño: Tamaño

    var area: Double {
        return tamaño.ancho * tamaño.alto
    }

    var centro: Punto {
        get {
            let centroX = origen.x + (tamaño.ancho / 2)
            let centroY = origen.y + (tamaño.alto / 2)
            return Punto(x: centroX, y: centroY)
        }
        set {
            origen.x = newValue.x - (tamaño.ancho / 2)
            origen.y = newValue.y - (tamaño.alto / 2)
        }
    }

    init(origen: Punto, tamaño: Tamaño) {
      self.origen = origen
      self.tamaño = tamaño
    }
}

class Cuadrilatero: Figura {
    var p1, p2, p3, p4: Punto
    override var centro: Punto {
        get {
            super.centro
        }
        set {
            let incX = newValue.x - centro.x
            let incY = newValue.y - centro.y
            // Se llama al setter de la figura
            super.centro = newValue
            // Se actualiza las coordenadas de los puntos
            p1 = Punto(x: p1.x + incX, y: p1.y + incY)
            p2 = Punto(x: p2.x + incX, y: p2.y + incY)
            p3 = Punto(x: p3.x + incX, y: p3.y + incY)
            p4 = Punto(x: p4.x + incX, y: p4.y + incY)
        }
    }

    override var area: Double {
        let areaTriangulo1 = areaTriangulo(p1, p2, p3)
        let areaTriangulo2 = areaTriangulo(p3, p4, p1)
        return areaTriangulo1 + areaTriangulo2
    }


    init(p1: Punto, p2: Punto, p3: Punto, p4: Punto) {
        self.p1 = p1
        self.p2 = p2
        self.p3 = p3
        self.p4 = p4
        let minX = min(p1.x, p2.x, p3.x, p4.x)
        let minY = min(p1.y, p2.y, p3.y, p4.y)
        let maxX = max(p1.x, p2.x, p3.x, p4.x)
        let maxY = max(p1.y, p2.y, p3.y, p4.y)
        let alto = maxY - minY
        let ancho = maxX - minX
        super.init(origen: Punto(x: minX, y: minY), tamaño: Tamaño(ancho: ancho, alto: alto))
    }
}

class Circulo: Figura {
    var radio: Double {
        didSet {
            let incRadio = radio - oldValue
            origen.x -= incRadio
            origen.y -= incRadio
            let alto = radio * 2
            let ancho = radio * 2
            tamaño = Tamaño(ancho: ancho, alto: alto)
        }
    }
    override var area: Double {
        get {
            Double.pi * radio * radio
        }
        set {
            let radioCuadrado = newValue / Double.pi
            radio = radioCuadrado.squareRoot()
        }
    } 

    init(centro: Punto, radio: Double) {
        self.radio = radio
        let minX = centro.x - radio
        let minY = centro.y - radio
        let alto = radio * 2
        let ancho = radio * 2
        super.init(origen: Punto(x: minX, y: minY), tamaño: Tamaño(ancho: ancho, alto: alto))
    }
}


struct AlmacenFiguras {
    var figuras = [Figura]()

    var numFiguras: Int {
        return figuras.count
    }

    var areaTotal: Double {
        return figuras.reduce(0) {$0 + $1.area}
    }

    mutating func añade(figura: Figura) {
        figuras.append(figura)
    }

    mutating func desplaza(incX: Double, incY: Double) {
        for figura in figuras {
            let centro = figura.centro
            let nuevoCentro = Punto(x: centro.x + incX, y: centro.y + incY)
            figura.centro = nuevoCentro
        }
    }
}


print("\nEjercicio 4")

// Cuadrilaterio

var cuadrilatero = Cuadrilatero(p1: Punto(x: 2, y: 6), p2: Punto(x: 5, y: 2),
                                p3: Punto(x: 8, y: 7), p4: Punto(x: 3, y: 8))
print("\nCuadrilátero")
print("------------")
print("p1: \(cuadrilatero.p1), p2: \(cuadrilatero.p2)")
print("p3: \(cuadrilatero.p3), p4: \(cuadrilatero.p4)")
print("Origen: \(cuadrilatero.origen)")
print("Tamaño: \(cuadrilatero.tamaño)")
print("Centro: \(cuadrilatero.centro)")
print("Área: \(cuadrilatero.area)")
let nuevoCentro = Punto(x: 10, y: 10)
print("Movemos el centro a la posición \(nuevoCentro)")
cuadrilatero.centro = nuevoCentro
print("Origen: \(cuadrilatero.origen)")
print("Centro: \(cuadrilatero.centro)")
print("Área: \(cuadrilatero.area)")
print("p1: \(cuadrilatero.p1), p2: \(cuadrilatero.p2)")
print("p3: \(cuadrilatero.p3), p4: \(cuadrilatero.p4)")

// Circulo

var circulo = Circulo(centro: Punto(x: 12, y: 12), radio: 5)
print("\nCírculo")
print("-------")
print("Centro: \(circulo.centro)")
print("Radio: \(circulo.radio)")
print("Área: \(circulo.area)")
print("Origen: \(circulo.origen)")
print("Tamaño: \(circulo.tamaño)")
let nuevoRadio = 3.0
print("Nuevo radio: \(nuevoRadio)")
circulo.radio = nuevoRadio
print("Radio: \(circulo.radio)")
print("Centro: \(circulo.centro)")
print("Origen: \(circulo.origen)")
print("Tamaño: \(circulo.tamaño)")
let nuevaArea = 100.0
circulo.area = nuevaArea
print("Nueva area: \(nuevaArea)")
print("Centro: \(circulo.centro)")
print("Radio: \(circulo.radio)")
print("Área: \(circulo.area)")
print("Origen: \(circulo.origen)")
print("Tamaño: \(circulo.tamaño)")

// Almacén

var almacen = AlmacenFiguras()
almacen.añade(figura: cuadrilatero)
almacen.añade(figura: circulo)
print("Total áreas de las figuras: \(almacen.areaTotal)")
almacen.desplaza(incX: 100, incY: 100)
print("Nuevos centros de las figuras:")
for figura in almacen.figuras {
    print("Centro: \(figura.centro)")
}