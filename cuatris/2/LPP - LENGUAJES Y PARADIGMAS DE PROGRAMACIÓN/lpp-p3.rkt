#lang racket

;EJERCICIO 1A
(define l '())
(define (es-prefijo? pal1 pal2)
  (if (string=?(substring pal2 0 (string-length pal1)) pal1)
      #t
      #f))

(define (contiene-prefijo prefijo lista-pal)
 
  (if (null? (cdr lista-pal))
       (list (es-prefijo? prefijo (car lista-pal)))
       (append (list (es-prefijo? prefijo (car lista-pal))) (contiene-prefijo prefijo  (cdr lista-pal)))))



;EJERCICIO 1B

(define (inserta-pos dato pos lista)
  (if (= pos 0)
      (append (list dato) (cdr lista))
      (append  (list (car lista)) (inserta-pos dato (- pos 1) (cdr lista)))))

;EJERCICIO 1C

(define (menor a b)
  (if (< a b)
      #t
      #f))

(define (mayor a b)
  (if (> a b)
      #f
      #t))


(define (inserta-ordenada n lista)
  (if (and (menor (car lista) n) (mayor n (car (cdr lista))))
      (append (list (car lista)) (append (list n) (cdr lista)) )
      (append  (list (car lista)) (inserta-ordenada n (cdr lista)))))

(define (ordena lista)
  (if (null? (cdr lista))
      lista
      (inserta-ordenada (cdr lista) (ordena (cdr lista)))))

;EJERCICIO 2A

(define(expande-pareja pareja)
  (if (= (cdr pareja) 0)
      '()
      (append (list (car pareja)) (expande-pareja (cons (car pareja) (- (cdr pareja) 1))))))


(define (expande-lista lista-parejas)
  (if (null? (cdr lista-parejas))
      (expande-pareja (car lista-parejas))
      (append (expande-pareja (car lista-parejas)) (expande-lista (cdr lista-parejas)))))

(define (expande-parejas pareja1 pareja2 . pareja_n)
  (if (null? pareja_n)
      (expande-lista (list pareja1 pareja2))
      (expande-lista (append (list pareja1 pareja2) pareja_n))))
      ;(append (list pareja1) (expande-parejas pareja2 (car pareja_n) . (cdr pareja_n)))))

;EJERCICIO 2B

(define (expande2 lista)
  (if (null? (cdr lista))
      (list (car lista))
      (if (integer? (car lista))
          (append (expande-pareja (cons (cadr lista) (car lista) )) (expande2 (cddr lista)))
          (append (list (car lista)) (expande2 (cdr lista))))))


;EJERCICIO 4a

;((lambda (x) (* x x)) 3) ; ⇒ 9
;((lambda () (+ 6 4))) ; ⇒ 10
;((lambda (x y) (* x (+ 2 y))) (+ 2 3) 4) ; ⇒ 30
;((lambda (x y) (* x (+ 2 x))) 5) ; ⇒ faltan argumentos


;(define f (lambda (a b) (string-append "***" a b "***")))
;(define g f)
;(procedure? g) ; ⇒ #true
;(g "Hola" "Adios") ; ⇒ ***HolaAdios***

;ejercicio 4b

;(define (suma-3 x)
  ; (+ x 3))

;((lambda (x) (+ 3 x))n)

;(define (factorial x)
;   (if (= x 0)
;      1
;      (* x (factorial (- x 1)))))
      

;((lambda (x)
;   (if (= x 0)
;      1
;      (* x (factorial (- x 1))))) n)






          
      
      
      
      
      
             
  

  
  
  

 
      
      
  

      
     