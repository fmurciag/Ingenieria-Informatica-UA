;;Francisco joaquin murcia gomez

#lang racket
(require rackunit)

;;
;;Ejercicio 1
;;

;;A
(define (binario-a-decimal a b c d)
  (+ (* a (expt 2 3)) (* b (expt 2 2)) (* c (expt 2 1)) (* d (expt 2 0))))

(check-equal? (binario-a-decimal 1 1 1 1) 15)
(check-equal? (binario-a-decimal 0 1 1 0) 6)
(check-equal? (binario-a-decimal 0 0 1 0) 2)

;;B
(define (binario-a-hexadecimal a b c d)
  (cond
    ((= (binario-a-decimal a b c d) 10) #\A)
    ((= (binario-a-decimal a b c d) 11) #\B)
    ((= (binario-a-decimal a b c d) 12) #\C)
    ((= (binario-a-decimal a b c d) 13) #\D)
    ((= (binario-a-decimal a b c d) 14) #\E)
    ((= (binario-a-decimal a b c d) 15) #\F)
    (else (integer->char (+ 48 (binario-a-decimal a b c d))))))

(check-equal? (binario-a-hexadecimal 1 1 1 1) #\F)
(check-equal? (binario-a-hexadecimal 0 1 1 0) #\6)
(check-equal? (binario-a-hexadecimal 1 0 1 0) #\A)

;;
;; Ejercicio 2
;;

;;A
(define (menor-de-tres n1 n2 n3)
  (cond
    ((and (< n1 n2) (< n1 n3)) n1)
    ((and (< n2 n1) (< n2 n3)) n2)
    (else n3)))

(check-equal? (menor-de-tres 2 8 1) 1)

;;B
(define (menor x y)
  (if (< x y)
      x
      y))
      

(define (menor-de-tres-v2 n1 n2 n3)
  (menor (menor n1 n2) n3))

(check-equal? (menor-de-tres-v2 3 0 3) 0)

;;
;;Ejercicio 3
;;

;;A

;;aplicativo
;;(f (g (+ 2 1)) (+ 1 1))
;;(f (g 3) (+ 1 1))
;;(f  (2 . 3) 2)
;; ((2 . 3) . 2)
;;normal
;;(f (g (+ 2 1)) (+ 1 1))
;;((g (+ 2 1)) . (+ 1 1))
;;((2 . (+2 1)). 2)
;;((2 . 3) . 2)

;;B

;;aplicativo
;;(g 0 (f 10))
;;(g 0 (/ 10 0))
;;(g 0 (error divison enre cero))
;;normal
;;(g 0 (f 10))
;;(if (= x 0) 0 (f 10))
;;(if #t 0 (f 10))
;;0

;;
;;Ejercicio 4
;;

(define (suma p)
  (+ (car p) (cdr p)))

(define (prox7 p)
  (abs (- (suma p) 7)))
  
  
(define (tirada-ganadora t1 t2)
  (if (<(prox7 t1) (prox7 t2))
          1
          (if (>(prox7 t1) (prox7 t2))
              2
              0)))

(check-equal? (tirada-ganadora (cons 1 3) (cons 1 6)) 2)
(check-equal? (tirada-ganadora (cons 1 5) (cons 2 2)) 1)
(check-equal? (tirada-ganadora (cons 6 2) (cons 3 3)) 0)








;;
;;Ejercicio 6
;;

(define (difCuadrada a b)
  (* (abs (- a b)) (abs (- a b))))

(define (distancia a b)
  (round(sqrt (+ (difCuadrada (car a) (car b))(difCuadrada (cdr a) (cdr b))))))

(define (tipo-triangulo a b c)
  (cond
    ((= (distancia a b) (distancia b c) (distancia a c)) "equilatero")
    ((or (= (distancia a b) (distancia b c)) (= (distancia a c) (distancia a b)))"isósceles")
    (else "escaleno")))
  


    
(check-equal? (tipo-triangulo (cons 1 1) (cons  1 6) (cons 6 1)) "isósceles")
(check-equal? (tipo-triangulo (cons -2 3) (cons  2 6) (cons 5 3)) "escaleno")
(check-equal? (tipo-triangulo (cons -3 0) (cons  3 0) (cons 0 5.1961)) "equilatero")
