#lang r6rs
(import (rnrs)
        (schemeunit)
        (rnrs mutable-pairs)
        (graphics turtles))

;;Antero Guarinos Caballero 45928934G

;;
;;Ejercicio 1
;;



(define (concat-iter lista n)
  (if (null? lista)
      n
      (concat-iter (cdr lista) (string-append n (car lista)))))
(define (concat lista)
  (concat-iter lista ""))

(display "probando ejercicio 1a")

(newline)

(check-equal? (concat  '("hola" "y" "adiós"))  "holayadiós")

(define (minmax-iter lista res)
  (if (null? lista)
      res
      (minmax-iter (cdr lista) (cons (min(car lista) (car res)) (max (car lista) (cdr res)))))) 



(define (minmax lista)
  (minmax-iter (cdr lista) (cons (car lista) (car lista))))




(display "probando ejercicio 1b")

(newline)

(check-equal? 
(minmax '(2 5 9 12 5 0 4))  '(0 . 12))


;
;Ejercicio 2
;
(define (expande-pareja pareja lista)
  (if (=(cdr pareja) 0)
      lista
      (expande-pareja (cons (car pareja) (- (cdr pareja) 1)) (append  lista (list (car pareja))))))


(define (expande-lista lista res)
  (if (null? lista)
      res
      (expande-lista (cdr lista) (expande-pareja (car lista) res))))



(display "probando ejercicio 2a")

(newline)

(check-equal? 
(expande-lista '((#t . 3) ("LPP" . 2) (b . 4)) '())  '(#t #t #t "LPP" "LPP" b b b b))

(define (rotar num lista)
  (if (= num 0)
      lista
      (rotar (- num 1) (append (cdr lista) (list (car lista))))))

(display "probando ejercicio 2b")

(newline)

(check-equal? 

(rotar 4 '(a b c d e f g))   '(e f g a b c d))


(define (mi-fold-left op res dato)
  (if (null? dato)
      res
      (mi-fold-left op (op res (car dato)) (cdr dato))))


(display "probando ejercicio 3")

(newline)

(check-equal? 

(mi-fold-left (lambda (res dato)
                         (cons dato res)) '() '(1 2 3))  '(3 2 1))

;
;Ejercicio 4
;

(define (binarioite cad n)
  (if (equal? cad "")
      n
      (if(equal? (string->number (substring cad 0 1)) 1)
         (binarioite (substring cad 1 (string-length cad)) (+ n (expt 2 (- (string-length cad) 1))))
         (binarioite (substring cad 1 (string-length cad)) n))))

      
(define (binario-a-decimal cad)
  (binarioite cad 0))

(display "probando ejercicio 4")

(newline)

(check-equal? 

(binario-a-decimal "101101")  45)


;
;Ejercicio 6
;
(turtles #t)
(define (piramide-hexagonal lado decremento)
  (if (> lado 0)
      (begin
        (turn 60)
        (draw lado)
        (turn 60)
        (draw lado)
        (turn 60)
        (draw lado)
        (turn 60)
        (draw lado)
        (turn 60)
        (draw lado)
        (turn 60)
        (draw lado)
        (turn 120)
        (move decremento)
        (turn -120)
        (piramide-hexagonal (- lado decremento) decremento)
        )))
  
  