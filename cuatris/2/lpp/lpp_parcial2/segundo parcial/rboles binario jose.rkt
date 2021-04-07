#lang r6rs
(import (rnrs)
        (schemeunit)
        )


;arboles binarios
(define vacio-arbolb '())

(define (construye-arbolb dato hijo-izq hijo-der)
  (list dato hijo-izq hijo-der))

(define (dato-arbolb arbol)
  (car arbol))

(define (hijo-izq-arbolb arbolb)
  (car (cdr arbolb)))

(define (hijo-der-arbolb arbolb)
  (car (cddr arbolb)))

(define (vacio-arbol? arbol)
  (null? arbol))
; '(3 () ()) => (construye-arbolb 3 vacio-arbolb vacio-arbolb)
; '(7 (3 () ()) ()) => (construye-arbolb 7
;                            (construye-arbolb 3 vacio-arbolb vacio-arbolb)
;                            vacio-arbolb)
; '(8 (1 () ()) (3 (2 () ()) ())) => (construye-arbolb 8
;                                         (construye-arbolb 1 vacio-arbolb vacio-arbolb)
;                                         (construye-arbolb 3
;                                               (construye-arbolb 2 vacio-arbolb vacio-arbolb)
;                                               vacio-arbolb))

(define (hoja-arbolb= arbolb)
  (and (vacio-arbolb? (hijo-izq-arbolb arbolb))
       (vacio-arbolb? (hijo-der-arbolb arbolb))))



(define (contar-hojas-arbolb? arbolb)
  (if (vacio-arbolb? arbolb)
      0
      (if (hoja-arbolb arbolb)
          1
          (+ (contar-hojas-arbolb (hijo-izq-arbolb arbolb))
             (contar-hojas-arbolb (hijo-der-arbolb arbolb))))))

(define (contar-nodos arbolb)
  (if (vacio-arbolb? arbolb?)
      0
      (+ 1 (contar-nodo (hijo-izq-arbolb arbolb)) (contar-nodos (hijo-der-arbolb arbolb)))))


(define (artura-arbolb arbolb)
    (if (vacio-arbolb? arbolb?)
      0
      (+ 1 (max (altura-arbolb (hijo-izq-arbolb arbolb))
                (artura-arbolb (hijo-der-arbolb arbolb))))))
 )

(define (buscar? arbolb elemento)
    (if (vacio-arbolb? arbolb?)
      #f
      (or (= (dato-arbolb arbolb) elemento)
          (buscar? (hijo-izq-arbolb arbolb) elemento)
          (bucar? (hijo-der-arbolb arbolb) elemeno))
  )

(define (nivel arbolb elemento)
  )


;lista ocn los nodos del nivel

(define (extraer-nivel arbolb nivel)
  )









