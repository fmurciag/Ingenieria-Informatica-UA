#lang racket
; EJERCICIO 1A
;(map (lambda (x)
 ;        (cond 
  ;          ((symbol? x) (symbol->string x))
   ;         ((number? x) (number->string x))
    ;        ((boolean? x) (if x "#t" "#f"))
     ;       (else "desconocido"))) '(1 #t hola #f (1 . 2))) ; ⇒  ("1" "#t" "hola" "#f" "desconocido")     

;(filter (lambda (x) 
 ;           (equal? (string-ref (symbol->string x) 1) #\a)) '(alicante barcelona madrid almería)) ; ⇒ (barcelona maderid)
;
;(foldl (lambda (dato resultado)
 ;        (string-append
  ;        (symbol->string (car dato))
   ;       (symbol->string (cdr dato))
    ;      resultado)) "" '((a . b) (hola . adios) (una . pareja))) ; ⇒ ("unaparejaholaadios")

;(foldr (lambda (dato resultado)
 ;          (cons (+ (car resultado) dato)
  ;               (+ (cdr resultado) 1))) '(0 . 0) '(1 1 2 2 3 3)) ; ⇒ (12 . 6)
  
;EJERCICIO 1B

(define lista '((2 . 7) (3 . 5) (10 . 4) (5 . 5))) ;->((2 . 7) (3 . 5) (10 . 4) (5 . 5))


;(filter even?
 ;       (map (lambda (x) (+ (car x)
  ;                               (cdr x)))
   ;            lista))

;(filter (lambda (x)
 ;           (< (cdr x) (car x)))
  ;      (map (lambda (x)
   ;            (cons (cdr x) (car x))) lista))




