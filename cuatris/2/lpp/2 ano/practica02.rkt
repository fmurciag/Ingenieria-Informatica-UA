#lang racket
(require rackunit)


;;
;; Ejercicio 1
;;
;; 1A
(define (minimo lista)
  (if (equal? (cdr lista) '())
      (car lista)
      (min (car lista) (minimo (cdr lista)))))

(check-equal? (minimo '(1 8 6 4 3)) 1)
(check-equal? (minimo '(1 -1 3 -6 4)) -6)

;; A2-1 (8 6 4 3)
;; A2-2 3
;; A2-3 (menor 1 3)

;; 1B 
(define (concatena lista-chars)
  (if (null? lista-chars)
      ""
      (string-append (string (car lista-chars)) (concatena (cdr lista-chars)))))

(check-equal? (concatena '(#\H #\o #\l #\a)) "Hola")
(check-equal? (concatena '(#\S #\c #\h #\e #\m #\e #\space #\m #\o #\l #\a)) "Scheme mola")

;; 1C
(define (contiene-lista? lista dato)
  (if (null? lista)
      #f
      (or (equal? dato (car lista)) (contiene-lista? (cdr lista) dato))))
(define (contiene? cadena char)
  (contiene-lista? (string->list cadena) char))

(check-equal? (contiene? "Hola" #\o) #t)
(check-equal? (contiene? "Esto es una frase" #\space) #t)
(check-equal? (contiene? "Hola" #\h) #f)

;;
;; Ejercicio 2
;;

;; A
(define (binario-a-decimal lista-bits)
  (if (null? lista-bits)
      0
      (+ (* (car lista-bits) (expt 2 (- (length lista-bits) 1))) (binario-a-decimal (cdr lista-bits)))))

(check-equal? (binario-a-decimal '(1 1 1 1)) 15)
(check-equal? (binario-a-decimal '(1 1 0)) 6)
(check-equal? (binario-a-decimal '(1 0)) 2)

;;B
(define (ordenada-creciente? lista-nums)
  (if (null? (cdr lista-nums))
      #t
      (and (< (car lista-nums) (car(cdr lista-nums))) (ordenada-creciente? (cdr lista-nums)))))

(check-equal? (ordenada-creciente? '(-1 23 45 59 99))  #t)
(check-equal? (ordenada-creciente? '(12 50 -1 293 1000))  #f)
(check-equal? (ordenada-creciente? '(3)) #t)
      
;;
;; Ejercicio 3
;;

;; A1
(define p1 (list (cons 1 2) (list 3 4)))
;;A2
(check-equal? (cdar p1) 2)
(check-equal? (cadadr p1) 4)
;;B1
(define p2 (cons (list (cons 7 (cons 8 9)) (list 1 2) 3) (cons 10 11)))
;;B2
(check-equal? (cdr (cdr (car (car p2)))) 9)
(check-equal? (car (cdr (car (cdr (car p2))))) 2)

;;
;; Ejercicio 4
;;

(define cartas1 '(3O 5E AC 2B 5O 5C 4B))
(define cartas2 '(CE AO 3B AC 2E SC 4C))

(define (obten-palo caracter)
  (cond
    ((equal? #\O caracter) 'Oros)
    ((equal? #\C caracter) 'Copas)
    ((equal? #\E caracter) 'Espadas)
    (else 'Bastos)))

(define (obten-valor caracter)
  (cond
    ((equal? #\A caracter) 1)  ; As
    ((equal? #\S caracter) 10) ; Sota
    ((equal? #\C caracter) 11) ; Caballo
    ((equal? #\R caracter) 12) ; Rey
    (else (- (char->integer caracter) (char->integer #\0)))))

(define (carta simbolo)
  (cons (obten-valor (string-ref (symbol->string simbolo) 0))
        (obten-palo (string-ref (symbol->string simbolo) 1))))


(define (sumaCartas lista n)
  (if (= n 0)
      0
      (+ (car (carta(car lista))) (sumaCartas (cdr lista) (- n 1)))))



(define (blackjack cartas1 n1 cartas2 n2)
  (cond
    ((and (> (sumaCartas cartas1 n1) 21) (> (sumaCartas cartas2 n2) 21)) -1)
    ((> (sumaCartas cartas2 n2) 21) 1)
    ((> (sumaCartas cartas1 n1) 21) 2)
    ((> (sumaCartas cartas1 n1) (sumaCartas cartas2 n2)) 1)
    ((< (sumaCartas cartas1 n1) (sumaCartas cartas2 n2)) 2)
    (else 0)))


(check-equal? (blackjack cartas1 5 cartas2 4) 0) ; ⇒ 0
(check-equal? (blackjack cartas1 5 cartas2 3) 1) ; ⇒ 1
(check-equal? (blackjack cartas1 3 cartas2 4) 2); ⇒ 2
(check-equal? (blackjack cartas1 7 cartas2 6) -1); ⇒ -1
(check-equal? (blackjack cartas1 7 cartas2 3) 2); ⇒ 2
(check-equal? (blackjack cartas1 3 cartas2 7) 1); ⇒ 2


;;
;;Ejercicio 5
;;
(define (suma-izq pareja n)
  (cons (+ (car pareja) n) (cdr pareja)))
(define (suma-der pareja n)
  (cons (car pareja) (+ (cdr pareja) n)))


(define (suma-impares-pares lista-num)
  (if (null? lista-num) (cons 0 0)
      (if (odd? (car lista-num))
          (suma-izq (suma-impares-pares (cdr lista-num)) (car lista-num))
          (suma-der (suma-impares-pares (cdr lista-num)) (car lista-num)))))

(check-equal? (suma-impares-pares '(3 2 1 4 8 7 6 5)) (cons 16  20))
(check-equal? (suma-impares-pares '(3 1 5)) (cons 9 0))

;;
;;Ejercicio 6
;;

(define (cadena-mayor lista)
  (if (null? lista)
      (cons "" 0)
      (if (>(string-length (car lista)) (cdr (cadena-mayor (cdr lista))))
          (cons (car lista) (string-length (car lista)))
          (cadena-mayor (cdr lista)))))
          
       
  
(check-equal? (cadena-mayor '("vamos" "a" "obtener" "la" "cadena" "mayor")) (cons "obtener" 7))
(check-equal? (cadena-mayor '("prueba" "con" "maximo" "igual")) (cons "maximo" 6))
(check-equal? (cadena-mayor '()) (cons "" 0))










  