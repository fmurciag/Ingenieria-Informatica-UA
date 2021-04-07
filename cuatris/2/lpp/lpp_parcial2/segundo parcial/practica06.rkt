#lang r6rs
(import (rnrs)
        (schemeunit))

(define (hoja? lista)
  (not (list? lista)))

(define (plana? lista)
   (or (null? lista)
       (and (hoja? (car lista))
            (plana? (cdr lista)))))


;;
;; Ejercicio 1
;;

(define lista-a '((a b) d (c (e) (f g) h)))
(define lista-b '(1 (6 (3) 10) (2) 8))


;;
;;Ejercicio 2
;;

(define (cuenta-pares lista)
  (if (null? lista)
      0
      (if (hoja?  lista)
          (if (even? lista)
              1
              0)
          (+ (cuenta-pares (car lista) ) (cuenta-pares (cdr lista))))))

(define (cuenta-fos lista)
  (fold-right + 0 (map (lambda (x) (if (hoja? x)
                                       (if (even? x)
                                           1
                                           0)
                                       (cuenta-fos x))) lista)))


  (display "probando ej2 a ")
(newline)


(check-equal? (cuenta-pares '(1 (2 3) 4 (5 6))) 3)

  (display "probando ej2 con fos ")
(newline)
(check-equal? (cuenta-fos '(1 (2 3) 4 (5 6))) 3)


(define (todos-pos lista)
  (if (null? lista)
      #t
      (if(hoja? lista)
          (< lista 0)   
         (and (todos-pos (car lista)) (todos-pos (cdr lista))))))




(define (todos-fos lista)
  (fold-right (lambda (x e) (and x e)) #t (map (lambda (x) (if (hoja? x)
                                                               (> x 0)
                                                               (todos-fos x))) lista)))
                                                               
             




  (display "probando todos-positivos a ")
(newline)


(check-equal? (todos-fos '(1 (2 (3 (-3))) 4)) #f)

  (display "probando todos positivos con fos ")
(newline)
(check-equal? (todos-fos '(1 (2 (3 (3))) 4)) #t)

;
;Ejercicio 3
;

(define (cumplen pred lista)
  (if(null? lista)
     '()
     (if(hoja? (car lista))
        (if (pred (car lista))
            (cons (car lista) (cumplen pred (cdr lista)))
            (cumplen pred (cdr lista)))
        (append (cumplen pred (car lista)) (cumplen pred (cdr lista))))))


            
(define (cumplen-fos pred lista)
  (fold-right (lambda (x r) (append x r)) '() (map (lambda (x) (if (hoja? x)
                                                                   (if (pred x)
                                                                       (list x)
                                                                       '())
                                                               (cumplen-fos pred x))) lista)))


  (display "probando cumplen pred a ")
(newline)


(check-equal? (cumplen even? '(1 (2 (3 (4))) (5 6))) '(2 4 6))

  (display "probando cumplen pred con fos ")
(newline)
;(check-equal? (cumplen-predicado pair? '(((1 . 2) 3 (4 . 3) 5) 6)) #t)



(define (busca num lista)
  (cumplen (lambda (x) (> x num)) lista))

(define (empieza letra lista)
  (cumplen (lambda (x) (equal? letra (string-ref (symbol->string x) 0))) lista))


  (display "probando buscar mayor ")
(newline)


(check-equal? (busca 10 '(-1 (20 (10 12) (30 (25 (15)))))) '(20 12 30 25 15))

  (display "probando buscar caracter ")
(newline)
(check-equal? (empieza #\m '((hace (mucho tiempo)) (en) (una galaxia ((muy muy) lejana)))) '(mucho muy muy))


             
          
          



                  
          
              
  




