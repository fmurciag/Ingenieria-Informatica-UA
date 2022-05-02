#lang racket
(require rackunit)
;;EJERCICIO 1a1
(define (menor a b)
  (if (< a b)
      a
      b))
  
(define (minimo lista)
  (if (null? (cdr lista))
      (car lista)
      (menor (car lista) (minimo (cdr lista)))))

(check-equal? (minimo '(1 8 6 4 3)) 1) ; ⇒ 1
(check-equal? (minimo '(1 -1 3 -6 4)) -6) ; ⇒ -6
;;EJERCICIO 1a2
;(8 6 4 3)
;
;;EJERCICIO 1B
(define (concatena lista-chars)
  (if (null? (cdr lista-chars))
      (string (car lista-chars))
      (string-append (string (car lista-chars)) (concatena (cdr lista-chars)))))

(check-equal? (concatena '(#\H #\o #\l #\a))"Hola") ; ⇒ "Hola"
(check-equal? (concatena '(#\S #\c #\h #\e #\m #\e #\space #\m #\o #\l #\a)) "Scheme mola")  
; ⇒ "Scheme mola"
;EJERCICIO 1C
(define (contiene-lista? lista dato)
  (if (equal? (car lista) dato)
      #t
      #f)
  (if (equal? (car lista) dato)
      #t
      (contiene-lista? (cdr lista) dato)))

(define (contiene? cadena char)
  (contiene-lista? (string->list cadena)))

;EJERCICIO 2a

(define (binario-a-decimal lista-bits)
  (if (null? (cdr lista-bits))
      (car lista-bits)
      (+ (* (car lista-bits) (expt 2 (- (length lista-bits) 1))) (binario-a-decimal (cdr lista-bits)))))

(check-equal? (binario-a-decimal '(1 1 1 1)) 15) ; ⇒ 15
(check-equal? (binario-a-decimal '(1 1 0)) 6) ; ⇒ 6
(check-equal? (binario-a-decimal '(1 0)) 2) ; ⇒ 2
      
;EJERCICIO 2b
(define (menorBool a b)
  (if (< a b)
      #t
      #f))
(define (ordenada-creciente? lista-nums)
  (if (null? (cdr lista-nums))
      #t
      (if (menorBool (car lista-nums) (car(cdr lista-nums)))
          (ordenada-creciente? (cdr lista-nums))
           #f)))

(check-equal? (ordenada-creciente? '(-1 23 45 59 99)) #t)  ; ⇒ #t
(check-equal? (ordenada-creciente? '(12 50 -1 293 1000)) #f)  ; ⇒ #f
(check-equal? (ordenada-creciente? '(3)) #t)  ; ⇒ #t

;;EJERCICIO 3a1
 (define p1 (list (cons 1 2)(list 3 4)))
;;EJERCICIO 3a1
;(cdr (car p1)); 2
;(car (cdr (car(cdr p1)))); 4
(check-equal? (cdr (car p1)) 2)
 (check-equal? (car (cdr (car(cdr p1)))) 4)
;EJERCICIO 3b1
(define p2 (cons (list (cons 7 (cons 8 9)) (list 1 2) 3) (cons 10 11)))
;EJERCICIO 3b2
;(cdr (cdr (car (car p2)))); 9
(check-equal? (cdr (cdr (car (car p2)))) 9)
;(car (cdr (car (cdr (car p2))))); 2
(check-equal? (car (cdr (car (cdr (car p2))))) 2)

;EJERCICIO 4
(define cartas1 '(3O 5E AC 2B 5O 5C 4B))
(define cartas2 '(CE AO 3B AC 2E SC 4C))

(define (d21 a)
  (- 21 a))

(define (ganador a b)
  (cond
    ((and (< a 0) (< b 0)) -1)
    ((< a 0)  2)
    ((< b 0)  1)
    ((= a b) 0)
    ((< a b) 1)
    (else 2)))

(define (obten-valor caracter)
  (cond
    ((equal? #\A caracter) 1)  ; As
    ((equal? #\S caracter) 10) ; Sota
    ((equal? #\C caracter) 11) ; Caballo
    ((equal? #\R caracter) 12) ; Rey
    (else (- (char->integer caracter) (char->integer #\0)))))

(define (puntos carta n)
  (if (= 0 n)
      (obten-valor (string-ref (symbol->string (car carta)) 0))
      (+ (obten-valor (string-ref (symbol->string (car carta)) 0)) (puntos (cdr carta) (- n 1)))))
  


(define (blackjack cartas1 n1 cartas2 n2)
  (ganador (d21 (puntos cartas1 n1)) (d21 (puntos cartas2 n2))))

;EJERCICIO 6

(define (mayor a b)
  (if (> (cdr a) (cdr b))
      a
      b))


   
(define (cadena-mayor lista)
  (if (null? lista)
      (cons ""  0)
   (if (null? (cdr lista))
      (cons (car lista) (string-length (car lista)))
      (mayor (cons (car lista) (string-length (car lista))) (cadena-mayor (cdr lista))))))

;EJERCICIO 5A

(define (suma-izq pareja n)
  (cons (+ (car pareja) n) (cdr pareja)))
(define (suma-der pareja n)
  (cons (car pareja) (+ (cdr pareja) n)))

(check-equal? (suma-izq (cons 10 20) 3) (cons 13 20))  ; ⇒ (13 . 20)
(check-equal?  (suma-der (cons 10 20) 5) (cons 10 25))  ; ⇒ (10 . 25)
             
  
  
  

      
     


  
  
      

      
      
  


