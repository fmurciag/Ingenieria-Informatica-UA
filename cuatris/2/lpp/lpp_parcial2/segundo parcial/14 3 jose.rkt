#lang r6rs
(import (rnrs)
        (schemeunit))

;LISTAS PLANAS Y LISTAS ESTRUCTURADAS

;hoja
(define (hoja? dato)
  (not (list? dato)))

;lista plana, solo contiene hojas

(define (plana? lista)
  (if (null? lista)
      #t
      (and (hoja? (car lista)) (plana? (cdr lista)))))

;lsita estructurada, contiene alguna lsita

(define (estructurada? lista)
  (if (null? lista)
      #f
      (or (list? (car lista)) (estructurada? (cdr lista)))))

(define (hojas lista)
  (if (hoja? lista)
      (+ (hojas (car lista)) (hojas (cdr lista)))))





(define (hojas2 lista)
  (if (null? lista)
      0
      (if (hoja? (car lista))
          (+1 (hojas2 (cdr lista)))
          (+ (hojas2 (car lista)) hojas2 (cdr lista)))))




;contar la cantidad de elementos pares qu ehay en una lista estructurada
(define (cuentapares lista)
  (if (null? lista)
      0
      (if (hoja? (car lista))
          (if (even? (car lista))
              (+ 1 (cuentapares (cdr lista)))
              (cuentapares (cdr lista)))
          (+ (cuentapares (car lista)) (cuentapares (cdr lista))))))


;ahora con fos
(define (cpl lista)
  (fold-right + 0 (map (lambda (x) (if (hoja? x)
                                       (if (even? x)
                                           1
                                           0)
                                       (cpl x))) lista)))


;contar la cantidad de elemtnos que hay en un nivel i en una lista
(define (contar lista i)
  (cond
    ((and (hoja? lista) (= i 0)) 1)
    ((and (hoja? lista) (not(= i 0))) 1)
    (else (+ (contar (car lista) (-i 1))
             (contar (cdr lista) i)))))



;quitar a todos los elementos de una lista estructurada el valor d, de forma que si al quitarlo se queda negativo, se corregirá a 00
(define (qe lista d)
  (if (null? lista)
      lista
      (if (hoja? lista)
          (max (- lista d) 0)
          (cons (qe (car lista) d) (qe (cdr lista) d)))))

;(quitar elementos '(12 (3 4 5) ((5)