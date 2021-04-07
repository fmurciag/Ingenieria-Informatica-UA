#lang r6rs
(import (rnrs)
        (schemeunit))

(define (hoja? lista)
  (not (list? lista)))


;;
;; Ejercicio 5
;;

(define (cuenta lista n)
  (cond
    ((null? lista) 0)
    ((and (hoja? lista) (< n 0)) 1)
    ((and (hoja? lista) (>= n 0)) 0)
    (else (+ (cuenta (car lista) (- n 1)) (cuenta (cdr lista) n)))))


(define (altura lista)
  (cond
    ((null? lista) 0)
    ((hoja? lista) 0)
    (else (max (+ 1 (altura (car lista))) (altura (cdr lista))))))


(define (nivel-elemento lista)
  (nivel-elemento-aux lista 0))

(define (pareja-max p1 p2)
  (if (> (cdr p1) (cdr p2))
      p1
      p2))
 

(define (nivel-elemento-aux lista na)
  (if (null? lista)
      (cons 0 0)
      (if (hoja? lista)
          (cons lista na)
          (pareja-max (nivel-elemento-aux (car lista) (+ na 1)) (nivel-elemento-aux (cdr lista) na)))))



;;
;;Ejercicio 4
;;

(define (sustituye-elem lista old new)
  (if (null? lista)
      '()
      (if (hoja? lista)
          (if (equal? lista old) new lista)
          (cons (sustituye-elemen (car lista) old new) (sustituye-elem (cdr lista) old new)))))

(define (diff-lista l1 l2)
  (if (null? lista1)
     '()
     (if (hoja? l1)
         (if (not (equal? l1 l2))
             (list (cons l1 l2))
             '()
             )
         (append (diff-lista (car l1) (car l2))
                 (diff-lista (cdr l1) (cdr l2))))))


;1 Definir una funcion que reciba dos listas y devuelve #t si tienen la misma estructura y #f si no.

;2 definir una funcion que reciba una lista y devuelva una pareja con el mayor elemento de la lista y el nivel donde se muestra

; 3 definir una funcon que reciba una lista y un nivel, devuelve el mayor elemento de ese nivel

; 
          
















    