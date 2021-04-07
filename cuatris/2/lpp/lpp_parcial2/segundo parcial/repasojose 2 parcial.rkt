#lang r6rs
(import (rnrs)
        (schemeunit))

#|

general y binario
    23
   /
  12

general y binario

    23
    / 
   12 \
   /   2
  2    /\
      3  4
|#

;generadoras binario

(define vacio-arbolb '())
(define (contruye-arbolb dato hijo-izq hijo-der)
  (list dato hijo-izq hijo-der))

;generadoras general
(define (contruye-arbol dato lista-arboles)
  (cons dato lista-arboles))

(define arbol1
  (contruye-arbolb 23 (contruye-arbolb 12 vacio-arbolb vacio-arbolb) vacio-arbolb))

(define arbolg1
  (contruye-arbol 23
                  (list
                   (contruye-arbol 12 '()))))

(define arbolb2
  (contruye-arbolb 23 (contruye-arbolb 12 (contruye-arbolb 2 vacio-arbolb vacio-arbolb) vacio-arbolb) (contruye-arbolb 2 (contruye-arbolb 3 vacio-arbolb vacio-arbolb) (contruye-arbolb 4 vacio-arbolb vacio-arbolb))))



(define arbolg2
  (contruye-arbol 23
                  (list
                  (contruye-arbol 12 (list (contruye-arbol 2 '())))
                  (contruye-arbol 2 (list (contruye-arbol 3 '())
                                          (contruye-arbol 4 '()))))))


(define (dato-arbol arbol)
  (car arbol))
(define (hijos-arbol arbol)
  (cdr arbol))

(define (dato-arbolb arbolb)
  (car arbolb))
(define (hijo-izq-arbolb arbolb)
  (cadr arbolb))
(define (hijo-der-arbolb arbolb)
  (caddr arbolb))

;sacar el elemento 3 del arbolb


(define ejercicioex '(1 (3 (4) (6 (10)) (8)) 8))

(define ejercicioex2 '((((3)) (10) (6)) (2)))
(define (hoja? dato)
  (not (list? dato)))

(define (compara-listas lista1 lista2)
  (if (null? lista1)
      '()
      (if (hoja? lista1)
          (if (>= lista2 lista1)
              (list #t)
              (list #f)
              )
          (append (compara-listas (car lista1) (car lista2)) (compara-listas (cdr lista1) (cdr lista2))))))


(define (cuenta-elem-tree arbol elem)
  (if (null? arbol)
      0
      (if (equal? (dato-arbol arbol)  elem)
          (+ 1 (cuenta-elem-tree-bosque (hijos-arbol arbol) elem))
          (cuenta-elem-tree-bosque (hijos-arbol arbol) elem))))

(define (cuenta-elem-tree-bosque arbol elem)
  (if (null? arbol)
      0
      (+ (cuenta-elem-tree (car arbol) elem)
         (cuenta-elem-tree-bosque (cdr arbol) elem))))


(define (cuadrado lista aux)
  (if (null? lista)
      aux
      (cuadrado (cdr lista) (append  aux (list (* (car lista) (car lista)))))))


(define (rotar-tail lista veces)
  (if (= veces 0)
      lista
      (rotar-tail (append (cdr lista) (list (car lista))) (- veces 1))))

(define (hoja-arbol? arbol) 
   (null? (hijos-arbol arbol)))
(define (lista-nivel-arbol nivel arbol)
  (if (= nivel 0)
      (list dato-arbol)
      (lista-nivel-arbol-bosque (- nivel 1) (hijos-arbol arbol))))

(define (lista-nivel-arbol-bosque nivel arbol)
  (if (null? arbol)
      '()
      (append (lista-nivel-arbol nivel (car arbol))
              (lista-nivel-arbol-bosque nivel (cdr arbol)))))

;;ejercicio 1

(define (bar-iter lista res)
  (if (null? lista)
      res
      (bar-iter (cdr lista) (append (list(car lista)) res))))
              
;ejercicio 2

(define lista2 '((2) ((1) (6) 5)))
(display (car(cadadr lista2)))

(define arbol2 '(20 (10 (5)) (25 (27))))
(newline)
(display (caar(hijos-arbol(car(hijos-arbol arbol2)))))

(define arbolb22 '(20 (10 (5 () ()) ()) (25 () (27 () ()))))

(newline)
(display(dato-arbolb(hijo-izq-arbolb(hijo-izq-arbolb arbolb22))))




(define porra '(8 (3 (2)) (4 (1) (6 (1) (2))) (7) (9 (8 (3) (4)) (5))))


(define (ej2 lista)

  (for-all (lambda (x) (null? (hijos-arbol x))) (hijos-arbol lista)))

(define (ej22 arbol)
  (if (null? arbol)
      '()
      (if (hoja? arbol)
          '() 
          (if (ej2 arbol)
               (list (cons (dato-arbol arbol) (length (hijos-arbol arbol))))
               (ejj22-bosque (hijos-arbol arbol))))))


(define (ejj22-bosque arbol)
  (if (null? arbol)
      '()
      (append (ej22 (car arbol)) (ejj22-bosque (cdr arbol)))))
  

(define (to-string-arbol arbol)
   (string-append (symbol->string (dato-arbol arbol))
                  (to-string-bosque (hijos-arbol arbol))))

(define (to-string-bosque bosque)
   (if (null? bosque)
       ""
       (string-append (to-string-arbol (car bosque))
                      (to-string-bosque (cdr bosque)))))
(define arbol22 '(a (b (c (d)) (e)) (f)))

(define (nivel-lista lista nivel)
  (if (null? lista)
      lista
      (if (hoja? lista)
          (if (= nivel 0)
              (list lista)
              '())
          (append (nivel-lista (car lista) (- nivel 1))
                  (nivel-lista (cdr lista) nivel)))))

(define (construye-arbol dato lista-arboles)  
   (cons dato lista-arboles))



(define (sumador-dato arbol)
      (if (hoja? arbol)
          arbol
          (+ (dato-arbol arbol) (sumador-dato-bosque (hijos-arbol arbol)))))


(define (sumador-dato-bosque arbol)
  (if (null? arbol)
      0
      (+ (sumador-dato (car arbol)) (sumador-dato-bosque (cdr arbol)))))



(define (suma-datos arbol)
  (if (null? arbol)
      '()
      (construye-arbol (sumador-dato arbol) (suma-datos-bosque (hijos-arbol arbol)))))

(define (suma-datos-bosque bosque)
  (if (null? bosque)
      '()
      (cons (suma-datos (car bosque)) (suma-datos-bosque (cdr bosque)))))




(define (vacio-arbolb? arbol)
   (null? arbol))

(define (camino-parcial arbolb nivel camino)
  (if (vacio-arbolb? arbolb)
      '()
      (if (equal? (car camino) 'd)
          (if (= nivel 0)
              (cons (dato-arbolb arbolb) (camino-parcial (hijo-der-arbolb arbolb) nivel (cdr camino)))
              (camino-parcial (hijo-der-arbolb arbolb) (- nivel 1) (cdr camino))) 
          (if (equal? (car camino) 'i)
              (if (= nivel 0)
                  (cons (dato-arbolb arbolb) (camino-parcial (hijo-izq-arbolb arbolb) nivel (cdr camino)))
                  (camino-parcial (hijo-izq-arbolb arbolb) (- nivel 1) (cdr camino)))
              '()))))
      
          
          
      
