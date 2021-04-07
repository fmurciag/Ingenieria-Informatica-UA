#lang r6rs
(import (rnrs))


(define (hoja? lista)
  (not (list? lista)))
(define (cuenta-pares lista)
  (if (null? lista)
      0
      (if (hoja? lista)
          (if (even? lista)
              1 0)
          (+ (cuenta-pares (car lista)) (cuenta-pares(cdr lista))))))

(define (cuenta-pares-fos lista)
  (fold-right + 0 (map (lambda (x) (if (hoja? x)
                                       (if (even? x)
                                           1
                                           0)
                                       (cuenta-pares-fos x))) lista)))

(define (prueba lista)
   (if (null?  lista)
       1 0))
(define (todos-positivos lista)
  (if (null? lista)
      #t
      (if (hoja? lista)
          (if (> lista 0)
              #t
              #f)
      (and (todos-positivos (car lista)) (todos-positivos (cdr lista))))))


(define (todos-positivos-fos lista)
  (fold-right (lambda (x res) (and x res)) #t (map (lambda (x) (if (hoja? x)
                                                                     (if (> x 0)
                                                                         #t
                                                                         #f)
                                                                     (todos-positivos-fos x))) lista)))




(define (cumplen-predicado funcion lista)
  (fold-right (lambda (x res) (append x res)) '() (map (lambda (x) (if (hoja? x)
                                                                       (if(funcion x)
                                                                          (list x)
                                                                          '()
                                                                          )
                                                                       (cumplen-predicado funcion x))) lista)))


(define (plana? lista)
   (or (null? lista)
       (and (hoja? (car lista))
            (plana? (cdr lista)))))


(define (mayor lista1 lista2)
  (if (> (length lista1) (length lista2))
      lista1
      lista2))
(define (mayor-plana lista)
(fold-right (lambda (x res) (mayor x res)) '() (map (lambda (x) (cond
                                                                  ((hoja? x) '())
                                                                  ((plana? x) x)
                                                                  (else (mayor-plana x)))) lista)))
                                                                             


(define(sustituye-elem lista old new )
  (if (null? lista)
      '()
      (if (hoja? lista)
          (if (equal? lista old)
              new
              lista)
          (cons (sustituye-elem (car lista) old new )
                (sustituye-elem (cdr lista)old new )))))
  


(define (sustituye-elem-fos lista old new)
          (fold-right (lambda (x res) (cons x res)) '() (map (lambda (x) (if (hoja? x)
                                                                             (if (equal? x old)
                                                                                 new
                                                                                 x)
                                                                             (sustituye-elem-fos x old new))) lista))) 

(define (diff-listas lista1 lista2)
  (if (null? lista1)
      lista1
      (if (hoja? lista1)
          (if (not (equal? lista1 lista2))
              (list(cons lista1 lista2))
              '()
              )
          (append (diff-listas (car lista1) (car lista2)) (diff-listas (cdr lista1) (cdr lista2))))))




(define (cuenta-hojas-debajo-nivel lista n)
  (if (null? lista)
      0
      (if (hoja? lista)
          (if (< n 0)
              1
              0)
          (+ (cuenta-hojas-debajo-nivel (car lista) (- n 1)) (cuenta-hojas-debajo-nivel (cdr lista) n)))))
          
       

(define (cuenta-hojas-debajo-nivel-fos lista n)
  (fold-right + 0 (map (lambda (x) (if (hoja? x)
                                       (if (< n 1)
                                           1
                                           0)
                                       (cuenta-hojas-debajo-nivel-fos x (- n 1)))) lista)))
  
  


(define (max-pareja p q)
  (if (>= (cdr p) (cdr q))
      p
      q))
(define (sumo1-nivel-pareja pareja)
  (cons (car pareja)
        (+ 1 (cdr pareja))))


(define (nivel-elemento lista)
  (fold-right (lambda (x res) (max-pareja x res)) (cons 0 0) (map (lambda (x) (if (hoja? x)
                                                                                  (cons x 0)
                                                                                  (sumo1-nivel-pareja (nivel-elemento x)))) lista)))

          

