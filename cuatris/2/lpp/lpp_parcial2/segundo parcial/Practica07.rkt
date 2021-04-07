#lang r6rs
(import (rnrs)
        (schemeunit))
;;Abstraccion
(define (dato-arbol arbol) 
    (car arbol))

(define (hijos-arbol arbol) 
    (cdr arbol))

(define (hoja-arbol? arbol) 
   (null? (hijos-arbol arbol)))

;;Barrera Arbol Binario
(define (dato-arbolb arbol)
   (car arbol))

(define (hijo-izq-arbolb arbol)
   (cadr arbol))

(define (hijo-der-arbolb arbol)
   (caddr arbol))

(define (vacio-arbolb? arbol)
   (null? arbol))

(define (hoja-arbolb? arbol)
   (and (vacio-arbolb? (hijo-izq-arbolb arbol))
        (vacio-arbolb? (hijo-der-arbolb arbol))))

(define arbolb-vacio '())

;;Ejercicio 1-A-1
(define arbol '(15 (4 (2) (3)) (8 (6)) (12 (9) (10) (11))))

(check-equal? (dato-arbol(cadr(hijos-arbol (caddr (hijos-arbol arbol))))) 10)

;; Ejercicio 1-A-2

;;1- Duelve el primero subarbol o hijo del arbol principal

;;2- Devuelve el subarbol (4 (2) (3))

;; Ejercicio 1-A-3

;;1- Devuelve una lista de sumar cada subarbol -> (9 14 42)

;;2- (fr + 15 (9 14 42)) -> (fr + 57 (9 14)) -> (fr + 71 (9)) -> 80

;;Ejercicio 1-B-1

(define arbolb '(40 (23 (5 () ())(32 (29 () ()) ())) (45 () (56 () ()))))
;(check-equal? (dato-arbolb(hijo-izq-arbolb(hijo-der-arbolb(hijo-izq-arbolb arbolb))))  29)


;;Ejercicio 2-a

(define arbol2 '(a (b (c (d)) (e)) (f)))

(define (bosque-to-string-arbol bosque)
  (if (null? bosque)
      ""
      (string-append (to-string-arbol (car bosque)) (bosque-to-string-arbol (cdr bosque)))))


(define (to-string-arbol arbol)
  (string-append (symbol->string(dato-arbol arbol)) (bosque-to-string-arbol (hijos-arbol arbol))))

;;Con FOS


(define (to-string-arbol-fos arbol)
  (string-append (symbol->string (dato-arbol arbol)) (fold-right string-append "" (map to-string-arbol-fos (hijos-arbol arbol)))))

;; Ejercicio 2-B

(define (veces-bosque bosque elemento)
  (if (null? bosque)
      0
      (+ (veces-arbol elemento (car bosque)) (veces-bosque (cdr bosque) elemento))))

(define (veces-arbol elemento arbol)
  (if (equal? (dato-arbol arbol) elemento)
      (+ 1 (veces-bosque (hijos-arbol arbol) elemento))
      (veces-bosque (hijos-arbol arbol) elemento)))

;;Con Fos

(define (comprobar dato elem)
  (if (equal? dato elem) 1 0))

(define (veces-arbol-fos elemento arbol)
  (+ (comprobar (dato-arbol arbol) elemento)
     (fold-right + 0 (map (lambda (x)
                            (veces-arbol-fos elemento x)) (hijos-arbol arbol)))))

;(display (veces-arbol-fos 'b '(a (b (c) (d)) (b (b) (f)))))


;;Ejercicio 3-A


(define (suma-raices-hijos arbol)
  (fold-right (lambda (x res)
                (+ (dato-arbol x) res)) 0 (hijos-arbol arbol)))
                                 
      


(define arbol3 '(20 (2) (8 (4) (2)) (9 (5))))


;;Ejercicio 3-B

(define (raices-mayores-bosque? bosque)
  (if (null? bosque)
      #t
      (and (raices-mayores-arbol? (car bosque)) (raices-mayores-bosque? (cdr bosque)))))

(define (raices-mayores-arbol? arbol)
  (and (> (dato-arbol arbol) (suma-raices-hijos arbol)) (raices-mayores-bosque? (hijos-arbol arbol))))

(define (raices-mayores-fos? arbol)
  (and (> (dato-arbol arbol) (suma-raices-hijos arbol))
       (fold-right (lambda (x res)
                     (and x res)) #t (map raices-mayores-fos? (hijos-arbol arbol)))))
;;Ejercicio 3-C

(define (comprueba-raices-bosque bosque)
  (if (null? bosque)
      '()
      (cons (comprueba-raices-arbol (car bosque)) (comprueba-raices-bosque (cdr bosque)))))

(define (comprueba-raices-arbol arbol)
  (cons (if(> (dato-arbol arbol) (suma-raices-hijos arbol)) 1 0) (comprueba-raices-bosque (hijos-arbol arbol))))

;;Ejercicio 4-a
(define arbol4 '(a (a (a) (b)) (b (a) (c)) (c)))

(define (es-camino-bosque? lista bosque)
  (cond
    ((null? lista) #f)
    ((null? bosque) #f)
    (else (or (es-camino?  lista (car bosque)) (es-camino-bosque? lista (cdr bosque))))))

(define (es-camino? lista arbol)
  (if (and (hoja-arbol? arbol) (equal? (dato-arbol arbol) (car lista))(null? (cdr lista)) )
      #t
   (if (equal? (car lista) (dato-arbol arbol))
          (es-camino-bosque? (cdr lista) (hijos-arbol arbol))
          #f)))

(display (es-camino? '(a b a) arbol4))

;;Ejercicio 4-b
(define arbol4b '(1 (2 (3 (4) (2)) (5)) (6 (7))))
(define (nodos-nivel-bosque nivel bosque)
  (if (null? bosque)
      '()
      (append (nodos-nivel nivel (car bosque)) (nodos-nivel-bosque nivel (cdr bosque)))))
  

(define (nodos-nivel nivel arbol)
  (if (= nivel 0)
      (list (dato-arbol arbol))
      (nodos-nivel-bosque (- nivel 1)(hijos-arbol arbol))))

;;Ejercicio 5
(define arbolb5 '(9 (5 (3 (1 () ()) (4 () ())) (7 () ()))
                    (15 (13 (10 () ()) (14 () ())) (20 () (23 () ())))))

(define (camino-b-tree b-tree lista)
  (if (null? lista)
      '()
      (cond
        ((equal? (car lista) '<) (camino-b-tree (hijo-izq-arbolb b-tree) (cdr lista)))
        ((equal? (car lista) '>) (camino-b-tree (hijo-der-arbolb b-tree) (cdr lista)))
        ((equal? (car lista) '=) (append (list (dato-arbolb b-tree)) (camino-b-tree b-tree (cdr lista)))))))