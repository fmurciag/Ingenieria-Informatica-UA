#lang r6rs
(import (rnrs)
        (schemeunit))

;barrera de abstraccion.
;arbol general -cosultoras

(define (to-string-arbol arbol)
  (string-append (symbol->string (dato-arbol arbol))
                 (to-string-bosque (hijos-arbol arbol))))

(define (to-string-bosque bosque)
  (if (null? bosque)
      ""
      (string-append (to-string-arbol (car  bosque))
                     (to-string-bosque (cdr bosque)))))

(define (to-string-arbol-fos arbol)
  (string-append(symbol->string (dato-arbol arbol))
                (fold-right string-append "" (map (to-string-arbol-fos  (hijos-arbol arbol))))))

(define (to-string-arbol-fos2 arbol)
  (fold-right string-append (symbol->string (dato-arbol arbol)) (map to-string-arbol-fos2 (hijos-arbol arbol))))


(define (compara x y)
  (if (equal? x y) 1 0))

(define (veces-arbol elemento arbol)
  (+ (compara elemento (dato-arbol arbol))
     (veces-bosque elemento (hijos-arbol arbol))))


(define (veces-bosque elemento bosque)
  (if (null? bosque)
      0
      (+ (veces-arbol elemento (car bosque))
         (veces-bosque elemento (cdr bosque)))))

(define (veces-arbol-fos elemento arbol)
  (fold-right + (compara (dato-arbol arbol) elemento) (map (lambda (x) (veces-arbol-fos elemento x)) (hijos-arbol arbol))))


(define (veces-arbol-fos2 elemento arbol)
  (fold-right +
              (if (equal? elemento (dato-arbol arbol)) 1 0)
              (map
               (lambda (x) (veces-arbol-fos2 elemento x))
               (hijos-arbol arbol))))


(define (suma-raices-hijos-fos arbol)
  (fold-right + 0 (map dato-arbol (hijos-arbol arbol))))

(define (suma-raices-jordi arbol)
  (fold-right (lambda (arbol res)
                (+ (dato-arbol arbol) res))
              0
              (hijos-arbol arbol)))


(define (raices-mayore-arbol arbol)
  (and (> (dato-arbol arbol)
          (fold-right + 0 (map dato-arbol (hijos-arbol arbol))))
       (raices-mayores-bosque (hijos-arbol arbol))))

(define (raices-mayores-bosque bosque)
  (if (null? bosque)
      #t
  (and (raices-mayores-arbol (car bosque))
       (raices-mayores-bosque (cdr bosque)))))




(define (raices-mayores-arbol-fos arbol)
  (fold-right (lambda (x y)
                (and x r))
              (> (dato-arbol arbol) (fold-right + 0 (map dato-arbol (hijos-arbol arbol))))
              (hijos-arbol arbol))) ;;no se si irá este