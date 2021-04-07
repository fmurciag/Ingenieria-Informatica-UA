#lang r6rs
(import (rnrs))


(define (hoja? lista)
    (not (list? lista)))
;;
;; Barrera de abstracción arbol
;;

(define (dato-arbol arbol) 
    (car arbol))

(define (hijos-arbol arbol) 
    (cdr arbol))

(define (hoja-arbol? arbol) 
   (null? (hijos-arbol arbol)))

(define (construye-arbol dato hijos)
  (cons dato hijos))

;;
;; Barrera de abstracción arbol binario
;;

(define (dato-arbolb arbol)
   (car arbol))
   
(define (hijo-izq-arbolb arbol)
   (cadr arbol))

(define (hijo-der-arbolb arbol)
   (caddr arbol))

(define (hoja-arbolb? arbol)
  (and (vacio-arbolb? (hijo-izq-arbolb arbol))
       (vacio-arbolb? (hijo-der-arbolb arbol))))
(define (vacio-arbolb? x)
   (null? x))


(define lista '((((6)) (10) (3)) (2)))

(define arbol '(1 (3 (4) (6 (10)) (8)) (8)))

(define (suma-listas lista1 lista2)
  (if(null? lista1)
     '()
     (if (hoja? lista1)
         (+ lista1 lista2)
         (cons (suma-listas (car lista1) (car lista2))
               (suma-listas (cdr lista1) (cdr lista2))))))

(define (compara-listas l1 l2)
  (if (null? l2)
      '()
      (if (hoja? l1)
          (list(>= l1 l2))
          (append (compara-listas (car l1) (car l2)) (compara-listas (cdr l1) (cdr l2))))))
                  
  

(define (cuenta-elem arbol elem)
  (+ (if (equal? elem (dato-arbol arbol))
         1
         0)
     (cuenta-elem-bosque (hijos-arbol arbol) elem)))

(define (cuenta-elem-bosque bosque elem)
  (if (null? bosque)
      0
      (+ (cuenta-elem (car bosque) elem) (cuenta-elem-bosque (cdr bosque) elem))))

(define (cuadra-lista lista)
  (if(null? lista)
     lista
     (cuadra-lista (cons (*(car lista) (car lista)) (cdr lista)))))
(define (cuadrado-lista lista)
(cuadrado-lista-aux lista '()))


(define (cuadrado-lista-aux lista result)
(if (null? lista)
result
(cuadrado-lista-aux (cdr lista)
(append
(list (* (car lista)
(car lista)))result))))

(define (listas-nivel-arbol n arbol)
  (if (= n 0)
      (cons (dato-arbol arbol) (lista-nivel-bosque (- n 1) (hijos-arbol arbol)))
      (lista-nivel-bosque (- n 1) (hijos-arbol arbol))))

(define (lista-nivel-bosque n bosque)
(if (null? bosque) '()
(append (listas-nivel-arbol n (car bosque))
(lista-nivel-bosque n (cdr bosque)))))
(define arbo11 '(1 (9) (3 (4) (10)) (8) (15)))



