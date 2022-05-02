#lang r6rs
(import (rnrs)
        (schemeunit))

; arbol general APUNTES LIBRETA ARBOLES



;raiz + arboles (que pueden faltar si no tienen hijos)

(define dibu1 '(39))

(define dibu2 '(30 (12) (18) (10)))

(define dibu3 '(90 (12 (23) (34)) (43) (45)))


;barrera de abstraccion
(define (dato-arbol arbol)
  (car arbol))

(define (hijos-arbol arbol)
  (cdr arbol))

(define (hoja-arbol? arbol)
  (null? (hijos-arbol arbol)))

(define (construye-arbol dato lista-arbol)
  (cons dato lista-arbol))
;no existe el arbol vacio

; construir un arbol con la barrera de abstraccion
(define dibu5 '(10 (23 (45) (54)) (90 (34 (54))) (90)))
(define dibu6 '(43))
(define dibu6-abs (construye-arbol 43 '()))

(define dibu5-abs
  (construye-arbol 10
                   (list
                    (construye-arbol 23 (list (construye-arbol 45 '()) (construye-arbol 54 '())))
                    (construye-arbol 90 (list (construye-arbol 34 (list (construye-arbol 54 '())))))
                    (construye-arbol 90 (list)))))


(define (suma-datos-bosque bosque)
  (if (null? bosque)
      0
      (+ (suma-datos (car bosque)) (suma-datos-bosque (cdr bosque)))))


(define (suma-datos arbol)
  (+ (dato-arbol arbol) (suma-datos-bosque(hijos-arbol arbol))))



(define (suma-datos-fos arbol)
  (+ (dato-arbol arbol) (fold-right + 0(map suma-datos-fos (hijos-arbol arbol)))))


(define (buscar-bosque bosque item)
  (if (null? bosque)
      #f
      (or (buscar (car bosque) item) (buscar-bosque (cdr bosque) item))))

(define (buscar arbol item)
  (if (equal? (dato-arbol arbol) item)
      #t
      (buscar-bosque (hijos-arbol arbol) item)))


;preguntar esta mierda porque el or puede dar problemas y hay que hacer un lambda
(define (buscar-fos arbol item)
  (or (= (dato-arbol arbol) item) (fold-right or #f (map (lambda (x) (buscar-fos x item))) (hijos-arbol arbol))))

(define (extraer-bosque bosque minimo maximo)
  (if (null? bosque)
      '()
      (append (extraer-entre (car bosque) minimo maximo) (extraer-entre-bosque (cdr bosque) minimo maximo))))
(define (extraer-entre arbol minimo maximo)
  (if (and(< (dato-arbol arbol) maximo) (> (dato-arbol arbol) minimo))
      (cons (dato-arbol arbol) (extraer-entre (hijos-arbol) minimo maximo))
      (extraer-bosque (hijos-arbol arbol) minimo maximo)))


