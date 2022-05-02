#lang r6rs

(import (rnrs)
        (schemeunit)
        )
;propuesto: calcular los n primeros terminos de la serie de fibonacci usando tail recursion



;contar el numero de elementos que tiene una lista sin usar length con tail recursion


(define (longlista lista)
  (longlista-aux lista 0))



(define (longlista-aux lista resultado)
  (if (null? lista)
      resultado
      (longlista-aux (cdr lista) (+ 1 resultado))))


;realizar una funcion con tail recursion que elimine las posiciones pares de la lista
;la primera posicion es uno)
; (eliminar-pp '(1 2 3 4 5 6)


(define (eliminar-pp lista)
  (eliminarpp-aux 1 lista))

;esta forma esta mal
(define (eliminarpp-aux n lista)
  (if (null? lista)
      lista
      (if (even? n)
         (cons (car lista) (eliminarpp-aux (+ n 1) (cdr lista)))
         (eliminarpp-aux (+ n 1) (cdr lista)))))

(define (eliminarppbien lista res)
  (cond
    ((null? lista) res)
    ((null? (cdr lista)) (cons (car lista) res))
    (else (eliminarppbien (cddr lista) (append res (list (car lista)))))))


;;invertir lista
(define (invertir lista '())
  (if (null? lista)
      lista
      (invertir (cdr lista) (cons (car lista) resultado))))



;realizar una funcion que reciba una lista y elimina de la lista los elementos pares

(define (borrarpares lista res)
  (if (null? lista)
      lista
      (if (odd? (car lista))
          (borrarpares (cdr lista) (append res (list (car lista))))
          (borrarpares (cdr lista) res))))


;realizar una funcion que mediandte tail recursion implemente fold-lett

(define (mifoldleft funcion lsita res)
  (if (null? lista)
      res
      (mifoldleft funcion (cdr lista) (funcion resultado (car lista)))))
  






