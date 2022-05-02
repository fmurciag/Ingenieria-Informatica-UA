
// Solución práctica 12: Programación Orientada a Objetos en Swift (2)

import Foundation

//
// Añadir un ejercicio sencillo sobre operadores y
// otro con algún método o propiedad de tipo
//


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

// a)

// b)

protocol A {
    var a: Int {get set}
    func foo(a: Int, b: Int) -> Int?
}

protocol B {
    mutating func bar()
}

struct MiStruct: A, B {
    var a = 10
    func foo(a: Int, b: Int) -> Int? {
        let res = a > 10 ? a : b
        return res
    }
    mutating func bar() {
        a += 1
    }
}

print(MiStruct())


// c)

struct Equipo: Equatable, Comparable {
    let puntos: Int
    let nombre: String

    static func < (primero: Equipo, segundo: Equipo) -> Bool {
        return
            primero.puntos < segundo.puntos ||
            (primero.puntos == segundo.puntos && primero.nombre < segundo.nombre)
    }
}

let equipo1 = Equipo(puntos: 10, nombre: "Hércules")
let equipo2 = Equipo(puntos: 8, nombre: "Villareal")
print(equipo1 == equipo2) // imprime false
print(equipo1 > equipo2) // imprime true

let ejemplo = [
    Equipo(puntos: 5, nombre: "Hercules"),
    Equipo(puntos: 10, nombre: "Valencia"),
    Equipo(puntos: 8, nombre: "Betis"),
    Equipo(puntos: 1, nombre: "Elche"),
    Equipo(puntos: 5, nombre: "Leganés"),
    Equipo(puntos: 10, nombre: "Barça")
    ]

for e in ejemplo.sorted() {
    print(e)
}


/*
==============================
Ejercicio 2: carrera de coches
==============================
*/


print("""
\n---
Ejercicio 2
---\n
""")

enum MarcaCoche: Int {
   case Mercedes=0, Ferrari, RedBull, McLaren

    static func random() -> MarcaCoche {
        let maxValue = McLaren.rawValue

        let r = Int.random(in: 0...maxValue)
        return MarcaCoche(rawValue: r)!
    }
}

enum TipoCambio: Int {
    case automatico=0, manual

    static func random() -> TipoCambio {
        let maxValue = manual.rawValue

        let r = Int.random(in: 0...maxValue)
        return TipoCambio(rawValue: r)!
    }
}

class Coche {
    var velocidadActual = 0.0
    var distanciaRecorrida = 0.0
    var marcha = 1
    static let velocidadMaxima = 150.0
    static let marchasMaxima = 6
    var marca : MarcaCoche

    init(marca: MarcaCoche) {
        self.marca = marca
    }
    var descripcion: String {
        return "\(marca)"
    }
    func seguimientoCarrera() {
        print("\(descripcion) viajando a \(velocidadActual) kilómetros por hora con la marcha \(marcha)")
        distanciaRecorrida += velocidadActual
    }
    func actualizaVelocidad() {}
}

class CocheAutomatico: Coche {
    override var velocidadActual: Double {
        didSet {
            marcha = min(Int(velocidadActual / 25.0) + 1, Coche.marchasMaxima)
            seguimientoCarrera()
        }
    }
    override var descripcion: String {
        return super.descripcion + " automatico"
    }
    override func actualizaVelocidad() {
      velocidadActual = Double(Int.random(in: 1...Int(Coche.velocidadMaxima)))
    }
}

class CocheManual: Coche {
    override var marcha: Int {
        didSet {
            velocidadActual = 25.0 * Double(marcha)
            seguimientoCarrera()
        }
    }
    override var descripcion: String {
        return super.descripcion + " manual"
    }
    override func actualizaVelocidad() {
       marcha = Int.random(in: 1...Coche.marchasMaxima)
    }
}

class Carrera {
    var coches: [Coche] = []
    let numCoches: Int
    let tiempoFinal : Int
    var tiempoActual: Int = 0 {
        didSet {
            print("\nHoras transcurridas \(tiempoActual)")
        }
    }

    init(numCoches: Int, horas:Int) {
        self.numCoches = numCoches
        tiempoFinal = horas
        for _ in 0..<numCoches {
            switch TipoCambio.random() {
            case .automatico :
                coches.append(CocheAutomatico(marca: MarcaCoche.random()))
            case .manual :
                coches.append(CocheManual(marca: MarcaCoche.random()))
            }
        }
    }

    func empezar() {
        for t in 1...tiempoFinal {
            tiempoActual = t
            for coche in coches {
               coche.actualizaVelocidad()
            }
        }
    }

    func clasificacion() {
        let clasif = coches.sorted(by: {$0.distanciaRecorrida > $1.distanciaRecorrida})
        var i = 1
        for coche in clasif {
            print("\(i). \(coche.descripcion) (\(coche.distanciaRecorrida) kilómetros recorridos)")
            i+=1
        }
    }

    func descripcion() {
        print("\(numCoches) coches con una duración de \(tiempoFinal) horas")
        for coche in coches {
            print("    \(coche.descripcion)")
        }
    }

}


// DEMOSTRACIÓN

let carrera = Carrera(numCoches: 2, horas: 3)
print("\nDescripción de la carrera:")
carrera.descripcion()
print("\n!!! Comienza la carrera !!!")
carrera.empezar()
print("\n!!! Clasificación !!!")
carrera.clasificacion()

/*
===========
Ejercicio 3
===========
*/


print("""
\n---
Ejercicio 3
---\n
""")

// a)

protocol P {
   var p: Int { get }
}
class A1: P {
   var p = 0
   var a1 = 0
}
class A2: P {
   var p = 1
   var a2 = 0
}

var array: [P] = [A1(), A2()]
for i in array {
   if let x = i as? A1 {
       print("p: \(i.p), a1: \(x.a1)")
   } else if let x = i as? A2 { 
       print("p: \(i.p), a2: \(x.a2)")
   }
}

// debe imprimir:
// p: 0, a1: 0
// p: 1, a2: 0

// b)

protocol TieneVelocidad {
    func velocidadActual () -> Double
}

class Vehiculo {
    var velocidad = 0.0
    func velocidadActual() -> Double {
        return velocidad
    }
}

class Tren {
    static let velocidadEnMarcha = 300.0
    var pasajeros = 0
    var enMarcha = false
}

extension Vehiculo: TieneVelocidad {}
extension Tren: TieneVelocidad {
    func velocidadActual() -> Double {
        return enMarcha ? Tren.velocidadEnMarcha : 0.0
    }
}

var vehiculo1 = Vehiculo()
var tren1 = Tren()
tren1.enMarcha = true

let transportes: [TieneVelocidad] = [vehiculo1, tren1]

for i in transportes {
    print(i.velocidadActual())
}

/*
===========
Ejercicio 4
===========
*/


print("""
\n---
Ejercicio 4
---\n
""")

struct Timer {
    var segundos: Int
    mutating func paso() {
        if (segundos > 0) {
            segundos -= 1
            Timer.pasosTotales += 1
            if (segundos == 0) {
                print("Tiempo!!!!")
            }
        }
    }
    static func + (primero: Timer, segundo: Timer) -> Timer {
        return Timer(segundos: primero.segundos + segundo.segundos)
    }
    static var pasosTotales = 0
}

var t1 = Timer(segundos: 10)
var t2 = Timer(segundos: 5)
for _ in 0...4 {
    t1.paso()
}
for _ in 0...2 {
    t2.paso()
}
var t3 = t1 + t2
t3.paso()
print("Segundos del temporizador 1: \(t1.segundos)")
print("Segundos del temporizador 2: \(t2.segundos)")
print("Segundos del temporizador 3: \(t3.segundos)")
print("Pasos totales: \(Timer.pasosTotales)")
// Imprime:
// Segundos del temporizador 1: 5
// Segundos del temporizador 2: 2
// Segundos del temporizador 3: 6
// Pasos totales: 9

/*
===========
Ejercicio 5
===========
*/


print("""
\n---
Ejercicio 5
---\n
""")

struct Punto {
    var x = 0.0, y = 0.0
}

struct Tamaño {
    var ancho = 0.0, alto = 0.0
}

struct Circulo {
    var centro = Punto()
    var radio = 0.0
    
    var area: Double {
        get {
            return Double.pi * radio * radio
        }
        set {
            radio = sqrt(newValue / Double.pi)
        }
    }
}

struct Rectangulo {
    var origen = Punto()
    var tamaño = Tamaño()

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

    var area: Double {
        return tamaño.ancho * tamaño.alto
    }
}

protocol Figura {
    var centro: Punto {get set}
    var area: Double {get}
    var tamaño: Tamaño {get}
}

extension Rectangulo: Figura {}

extension Circulo: Figura {
    var tamaño: Tamaño {
        let diametro = radio * 2
        return Tamaño(ancho: diametro, alto: diametro)
    }
}

extension Figura {
    var descripcion: String {
        return "Una figura con centro: \(centro) y área: \(area)"
    }
}

struct AlmacenFiguras {
    var figuras: [Figura] = []

    mutating func añade(figura: Figura) {
        figuras.append(figura)
    }

    var numFiguras: Int {
        return figuras.count
    }

    var areaTotal: Double {
        func areas() -> [Double] {
            return
                figuras.map() {$0.area}
        }
        return areas().reduce(0.0, +)
    }

    func cuentaTipos() -> (Int, Int) {
        var nRect = 0, nCirc = 0
        for fig in figuras {
            print("- Descripción de la figura: \(fig.descripcion)")
            switch fig {
                case let rect as Rectangulo:
                    nRect += 1
                    print("Rectangulo con origen \(rect.origen) y tamaño \(rect.tamaño)")
                case let circ as Circulo:
                    nCirc += 1
                    print("Círculo con centro \(circ.centro) y radio \(circ.radio)")
                default:
                    print("Tipo de figura desconocida")
            }
        }
        return (nRect, nCirc)
    }
}


let r = Rectangulo(origen:Punto(x:3, y:4), tamaño:Tamaño(ancho:10, alto:5))
let c = Circulo(centro:Punto(x:5, y:0), radio:10.0)
var almacen = AlmacenFiguras()
almacen.añade(figura: r)
almacen.añade(figura: c)
print("Total figuras: \(almacen.cuentaTipos())")