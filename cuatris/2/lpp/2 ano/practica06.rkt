#lang racket
(require rackunit)
(define (for-all? predicado lista)
  (or (null? lista)
      (and (predicado (car lista))
           (for-all? predicado (cdr lista)))))
(define (hoja? elem)
 (not (list? elem)))


;;EJERCICIO 1
;;A
(define lista-a '((a b) d (c (e) (f g) h)))

;;B
;;C
;;;1 ((4 (9)) (16 4) ((4) 9))
;;;2 '(2 (3)) ((4 2) ((2) 3))
;;;3 (4 (9)) ((16 4) ((4) 9))



;;EJERCICIO 2
;;A
(define (cuenta-pares lista)
  (cond ((null? lista) 0)
        ((hoja? lista) (if (even? lista) 1 0))
        (else (+ (cuenta-pares (car lista)) (cuenta-pares (cdr lista))))))

(define (cuenta-pares-fos lista)
  (foldr + 0 (map (lambda (x)
                    (if (hoja? x)
                        (if (even? x) 1 0)
                        (cuenta-pares-fos x))) lista)))

;;B
(define (todos-positivos lista)
  (cond ((null? lista) #t)
        ((hoja? lista) (positive? lista))
        (else (and (todos-positivos (car lista)) (todos-positivos (cdr lista))))))

(define (todos-positivos-fos lista)
  (for-all? positive?


;;EJERCICIO 3

(define (cumplen-predicado pred elem)
  (cond ((null? elem) '())
        ((hoja? elem)
         (if (pred elem)
             (list elem)
             '()))
        (else (append (cumplen-predicado pred (car elem))
                      (cumplen-predicado pred (cdr elem))))))           


(define (cumplen-predicado-fos pred lista)
  (foldr append '() (map (lambda (elem)
                           (if (hoja? elem)
                               (if (pred elem) (list elem) '())
                               (cumplen-predicado-fos pred elem)))
                                                                     lista)))
(define (busca-mayores n lista-num)
  (cumplen-predicado (lambda (elem) (> elem n)) lista-num))

(define (empieza-por char lista-pal)
  (cumplen-predicado (lambda (elem)
                       (equal? char (string-ref (symbol->string elem) 0)))
                                                                             lista-pal))








;;EJERCICIO 5
(define (mezclar lista1 lista2 n)
  (cond ((null? lista1) '())
        ((hoja? lista1) (if (
