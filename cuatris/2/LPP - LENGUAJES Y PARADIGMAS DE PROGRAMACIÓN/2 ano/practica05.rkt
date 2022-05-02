#lang racket
(require rackunit)
(require graphics/turtles)

;; EJERCICIO 1
;;A
(define (concat-iter lista-cadenas res)
  (if (null? lista-cadenas)
      res
      (concat-iter (cdr lista-cadenas) (string-append res (car lista-cadenas)))))

(define (concat lista-cadenas)
  (concat-iter lista-cadenas ""))


(check-equal? (concat '("hola" "y" "adiós")) "holayadiós")

;;B
(define (construyePareja n pareja)
  (cons (min n (car pareja)) (max n (cdr pareja))))

(define (min-max-iter lista res)
  (if (null? lista)
      res
      (min-max-iter (cdr lista) (construyePareja (car lista) res))))


(define (min-max lista)
  (min-max-iter (cdr lista) (cons (car lista) (car lista))))

(check-equal? (min-max '(2 5 9 12 5 0 4)) '(0 . 12))


;;EJERCICIO 2
;;A
(define (expande-pareja-iter pareja res)
  (if (= 0 (cdr pareja))
      res
      (expande-pareja-iter (cons (car pareja) (- (cdr pareja) 1)) (cons (car pareja) res))))

(define (expande-pareja pareja)
  (expande-pareja-iter pareja '()))

(check-equal? (expande-pareja (cons 'a 4)) '(a a a a))

(define (expande-parejas-iter pareja res)
  (if (= 0 (cdr pareja))
      res
      (expande-pareja-iter (cons (car pareja) (- (cdr pareja) 1)) (cons (car pareja) res))))

(define (expande-parejas . pareja)
  (expande-parejas-iter pareja '()))

(check-equal? (expande-pareja (cons 'a 4)) '(a a a a))

;;B
(define (rotar n lista)
  (if (= n 0)
      lista
      (rotar (- n 1) (append (cdr lista)  (list (car lista))))))

;;(rotar 4 '(a b c d e f g)) ; ⇒ (e f g a b c d)

;;EJERCICIO 3
;;A

(define (mi-foldl fun result lista)
  (if (null? lista)
      result
      (mi-foldl fun (fun (car lista) result) (cdr lista))))

;;(mi-foldl string-append "****" '("hola" "que" "tal"))
;;(mi-foldl cons '() '(1 2 3 4)) ; ⇒ (4 3 2 1)


;;B
(define (prefijo-lista? lista1 lista2)
  (if (null? lista1)
      #t
      (and (equal? (car lista1) (car lista2)) (prefijo-lista? (cdr lista1) (cdr lista2)))))

(check-equal? (prefijo-lista? '() '(a b c d e)) #t)



;;EJERCICIO 4

(define (crea-diccionario)
  (mcons '*diccionario* '()))

(define (busca key dic)
  (cond
    ((null? dic) #f)
    ((equal? key (mcar (mcar dic)))
     (mcar dic))
    (else (busca key (mcdr dic)))))

(define (get key dic)
  (define record (busca key (mcdr dic)))
  (if (not record)
      #f
      (mcdr record)))

(define (put key value dic)
  (define record (busca key (mcdr dic)))
  (if (not record)
      (set-mcdr! dic
                (mcons (mcons key value)
                      (mcdr dic)))
      (set-mcdr! record value))
  value)



(define (pascal-memo fila col dic)
  (cond ((= col 0) 1)
        ((= col fila) 1)
        ((not (equal? (get (cons fila col) dic) #f)) (get (cons fila col) dic))
        (else (put (cons fila col) (+ (pascal-memo (- fila 1) (- col 1) dic)
                  (pascal-memo (- fila 1) col dic)) dic))))

(define diccionario (crea-diccionario))
;;(pascal-memo 8 4 diccionario) ; ⇒ 70
;;(pascal-memo 40 20 diccionario) ; ⇒ 137846528820

;;EJERCICIO 5

(define (koch nivel trazo)
  (if (= nivel 0)
      (draw trazo)
      (begin
      (koch (- nivel 1) (/ trazo 3))
      (turn 60)
      (koch (- nivel 1) (/ trazo 3))
      (turn -120)
      (koch (- nivel 1) (/ trazo 3))
      (turn 60)
      (koch (- nivel 1) (/ trazo 3)))))

(define (copo-nieve nivel trazo)
      (begin
        (koch nivel trazo)
        (turn -120)
        (koch nivel trazo)
        (turn -120)
        (koch nivel trazo)))

;;EJERCICIO 6

(define (cuadrado n)
  (begin
    (draw n)
    (turn 90)
    (draw n)
    (turn 90)
    (draw n)
    (turn 90)
    (draw n)
    (turn 90)))

(define (alfombra-sierpinski tam)
  (if (<= tam 1)
      (cuadrado tam)
      (begin
        (alfombra-sierpinski (/ tam 3))
        (move (/ tam 3))
        (alfombra-sierpinski (/ tam 3))
        (move (/ tam 3))
        (alfombra-sierpinski (/ tam 3))
        (turn 90) (move (/ tam 3))(turn -90)
        (alfombra-sierpinski (/ tam 3))
        (turn 90) (move (/ tam 3))(turn -90)
        (alfombra-sierpinski (/ tam 3))
        (turn 180) (move (/ tam 3))(turn -180)
        (alfombra-sierpinski (/ tam 3))
        (turn 180) (move (/ tam 3))(turn -180)
        (alfombra-sierpinski (/ tam 3))
        (turn -90) (move (/ tam 3))(turn 90)
        (alfombra-sierpinski (/ tam 3))
        (turn -90) (move (/ tam 3))(turn 90)
        )))
       
        
    


  
        
      
      
  















        
