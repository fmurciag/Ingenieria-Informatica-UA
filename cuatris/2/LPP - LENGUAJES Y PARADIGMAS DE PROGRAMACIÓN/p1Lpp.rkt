#lang racket
;;ejercicio 1 a
(define (binario-a-decimal b3 b2 b1 b0)
 (+ (+ (* b3 (expt 2 3)) (* b2 (expt 2 2)))
    (+ (* b1 (expt 2 1)) (* b0 (expt 2 0)))))

;;ejercicio 1 b
(define (binario-a-hexadecimal b3 b2 b1 b0)
  
  (cond ((equal? (binario-a-decimal b3 b2 b1 b0) 10) ;;A
         (integer->char 65))
        (( equal? (binario-a-decimal b3 b2 b1 b0) 11);;B 
         (integer->char 66))
        ((equal? (binario-a-decimal b3 b2 b1 b0) 12);;C
         (integer->char 67))
        ((equal? (binario-a-decimal b3 b2 b1 b0) 13);;D
         (integer->char 68))
        ((equal? (binario-a-decimal b3 b2 b1 b0) 14);;E
         (integer->char 69))
        ((equal? (binario-a-decimal b3 b2 b1 b0) 15);;F
         (integer->char 70)) 
        (else (binario-a-decimal b3 b2 b1 b0))));; de 0 a 9

;;ejercicio 2
(define (menor-de-tres n1 n2 n3)
  (if (and (<= n1 n2) (<= n1 n3))
      n1
     (if (and (<= n2 n1) (<= n2 n3))
      n2
      n3)))
;;ejercicio 2 v2
(define (menor x y)
  (if (< x y)
      x
      y))
(define (menor-de-tres-v2 n1 n2 n3)
  (menor (menor n1 n2) n3))
     
;;ejercicio 3a
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

;;aplicativa
;;(g 0 (f 10))
;;(g 0 (/ 10 0))
;;(g 0 (error divison enre cero))
;;normal
;;(g 0 (f 10))
;;(if (= x 0) 0 (f 10))
;;(if #t 0 (f 10))
;;0


;;ejercicio 4
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

  
        


 


   











  