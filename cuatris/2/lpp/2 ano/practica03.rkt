#lang racket
(require rackunit)

;;
;;Ejercicio 1
;;
;;A
(define (es-prefijo? pal1 pal2)
  (cond 
        ((> (string-length pal1) (string-length pal2)) #f)
        (else (equal? pal1 (substring pal2 0 (string-length pal1))))))
(check-equal? (es-prefijo? "ante" "anterior") #t) 
(check-equal? (es-prefijo? "ante" "an") #f)

(define (contiene-prefijo prefijo lista-pal)
  (if (null? (cdr lista-pal))
      (list (es-prefijo? prefijo (car lista-pal)))
      (cons (es-prefijo? prefijo (car lista-pal)) (contiene-prefijo prefijo (cdr lista-pal)))))

(check-equal? (contiene-prefijo "ante" '("anterior" "antígona" "antena" "anatema")) (list #t #f #t #f))

;;B
(define (inserta-pos dato pos lista)
  (if (= pos 0)
      (cons dato lista)
      (cons (car lista) (inserta-pos dato (- pos 1) (cdr lista)))))

(check-equal? (inserta-pos 'b 2 '(a a a a)) '(a a b a a))
(check-equal? (inserta-pos 'b 0 '(a a a a)) '(b a a a a))

;;C
 
(define (inserta-ordenada n lista-ordenada)
  (cond ((< n (car lista-ordenada)) (cons n lista-ordenada))
        ((null? (cdr lista-ordenada)) (append lista-ordenada (list n)))
        (else (cons (car lista-ordenada) (inserta-ordenada n (cdr lista-ordenada))))))
  

(check-equal? (inserta-ordenada -5 '(-8)) '(-8 -5))

(define (ordena lista)
  (if (null?  (cdr lista))
      lista
      (inserta-ordenada (car lista) (ordena (cdr lista)))))

(check-equal? (ordena '(2 -1 100 4 -6)) '(-6 -1 2 4 100))

;;
;;EJERCICIO 2
;;
;;A
(define (expande pareja)
  (if (= 0 (cdr pareja))
      '()
      (cons (car pareja) (expande (cons (car pareja) (- (cdr pareja) 1))))))
  
(define (expande-lista lista-parejas)
  (if (null? lista-parejas)
      '()
      (append (expande (car lista-parejas)) (expande-lista (cdr lista-parejas)))))

(define (expande-parejas . pareja_n)
      (expande-lista pareja_n))

;;B
(define (expande2 lista)
  (if (null? (cdr lista))
      (list (car lista))
      (if (integer? (car lista))
          (append (expande (cons (cadr lista) (car lista) )) (expande2 (cddr lista)))
          (append (list (car lista)) (expande2 (cdr lista))))))


;;
;;EJERCICIO 3
;; 

;;A
(define (primer-elemento x lista)
  (if (null? lista)
      (list (list x))
      (append (list (list x (car lista))) (primer-elemento x (cdr lista)))))

;;B

(define (primer-elemento2 x lista)
  (if (null? (cdr lista))
      (list (cons x (car lista)))
      (cons (cons x (car lista)) (primer-elemento2 x (cdr lista)))))

(define (producto-cartesiano lista1 lista2)
  (if (null? (cdr lista1))
      (primer-elemento2 (car lista1) lista2)
      (append (primer-elemento2 (car lista1) lista2) (producto-cartesiano (cdr lista1) lista2))))
  
(check-equal? (producto-cartesiano '(1 2) '(1 2 3)) '((1 . 1) (1 . 2) (1 . 3) (2 . 1) (2 . 2) (2 . 3)))


  


;;
;;EJERCICIO 4
;;  
;;A
;((lambda (x) (* x x)) 3) ; ⇒ 9
;((lambda () (+ 6 4))) ; ⇒ 10
;((lambda (x y) (* x (+ 2 y))) (+ 2 3) 4) ; ⇒ 30
;((lambda (x y) (* x (+ 2 x))) 5) ; ⇒ faltan argumentos


;(define f (lambda (a b) (string-append "***" a b "***")))
;(define g f)
;(procedure? g) ; ⇒ #true
;(g "Hola" "Adios") ; ⇒ ***HolaAdios***

;;B

(define suma-3
   (lambda (x) (+ 3 x)))

(define factorial
  (lambda (x)
    (if (= x 0)
        1
        (* x (factorial (- x 1))))))

;;C

;; (foo + 10 doble 15) ⇒ error, g espera un procedure.

;; (foo doble + 10 15) ⇒ error, f recibe 2 parametros y solo debe recibir 1.

;; (foo + doble 10 15) ⇒ 35

;; (foo string-append (lambda (x) (string-append "***" x)) "Hola" "Adios") ⇒ "***HolaAdios"

;; (bar doble number? 10 15) ⇒ error, f esta recibiendo 2 parametros.

;; (bar string-append string? "Hola" "Adios") ⇒ "HolaAdios"

;; (bar + number? "Hola" 5) ⇒ error


;;
;;EJERCICIO 5
;;
;;A
(define (unePereja pareja)
  (string->symbol (string-append (string (cdr pareja)) (string (car pareja)))))


(define (crea-baraja lista-parejas)
  (if (null? (cdr lista-parejas))
      (list (unePereja (car lista-parejas)))
      (cons (unePereja (car lista-parejas)) (crea-baraja  (cdr lista-parejas)))))

(check-equal?  (crea-baraja '((#\u2660 . #\A) (#\u2663 . #\2) (#\u2665 . #\3) (#\u2666 . #\R))) '(A♠ 2♣ 3♥ R♦))

;;B
(define palos '(#\u2660 #\u2663 #\u2665 #\u2666))
(define valores '(#\2 #\3 #\4 #\5 #\6 #\7 #\8 #\9 #\0 #\J #\Q #\K #\A))
(define (baraja-poker)
  (crea-baraja (producto-cartesiano palos valores)))

;;D

(define (mezcla lista)
  (if (null?  lista)
      '()
      (inserta-pos (car lista) (random 0 (length lista)) (mezcla (cdr lista)))))
      



;;
;;EJERCICIO 6
;;

(define (sameLong? simbolo numero)
  (= (string-length (symbol->string simbolo)) numero))

(check-true (sameLong? 'hola 4))

(define (filtra-simbolos lista-simbolos lista-num)
  (if (null? lista-simbolos)
      '()
      (if (sameLong? (car lista-simbolos) (car lista-num))
          (cons (cons (car lista-simbolos) (car lista-num)) (filtra-simbolos (cdr lista-simbolos) (cdr lista-num)))
          (filtra-simbolos (cdr lista-simbolos) (cdr lista-num)))))


(check-equal? (filtra-simbolos '(este es un ejercicio de examen) '(2 1 2 9 1 6)) (list '(un . 2) '(ejercicio . 9) '(examen . 6)))








  