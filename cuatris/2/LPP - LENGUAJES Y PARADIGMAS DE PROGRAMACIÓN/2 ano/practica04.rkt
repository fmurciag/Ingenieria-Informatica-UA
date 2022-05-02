#lang racket
(require rackunit)

;;
;;Ejercicio 1
;;
;;A
#;(map (lambda (x)
         (cond 
            ((symbol? x) (symbol->string x))
            ((number? x) (number->string x))
            ((boolean? x) (if x "#t" "#f"))
            (else "desconocido"))) '(1 #t hola #f (1 . 2))) ; ⇒ ("1" "#t" "hola" "#f" "desconocido") 

#;(filter (lambda (x) 
            (equal? (string-ref (symbol->string x) 1) #\a)) '(alicante barcelona madrid almería)) ; ⇒    (barcelona maderid)

#;(foldr append '() '((1 2) (3 4 5) (6 7) (8))) ; ⇒ ("unaparejaholaadios")

#;(foldr (lambda (dato resultado)
           (cons (+ (car resultado) dato)
                 (+ (cdr resultado) 1))) '(0 . 0) '(1 1 2 2 3 3)) ; ⇒ (12 . 6)

#;(apply + (map cdr '((1 . 3) (2 . 8) (2 . 4)))) ; ⇒ 15

#;(apply min (map car (filter (lambda (p)
                                  (> (car p) (cdr p))) 
                                  '((3 . 1) (1 . 20) (5 . 2))))) ; ⇒ 3
;;B

; Los siguientes ejercicios utilizan esta definición de lista

(define lista '((2 . 7) (3 . 5) (10 . 4) (5 . 5)))


; Queremos obtener una lista donde cada número es la suma de las
; parejas que son pares

#;(filter even?
        (map (lambda (x) (+ (car x)
                            (cdr x)))
               lista))
; ⇒ (8 14 10)

; Queremos obtener una lista de parejas invertidas donde la "nueva"
; parte izquierda es mayor que la derecha.

#;(filter (lambda (x) (> (car x) (cdr x)))
        (map (lambda (x) (cons (cdr x) (car x))) lista))
; ⇒ ((7 . 2) (5 . 3))

; Queremos obtener una lista cuyos elementos son las partes izquierda
; de aquellas parejas cuya suma sea par.

#;(foldr (lambda (dato resultado) (cons (car dato) resultado)) '()
        (filter (lambda (x) (even? (+ (car x) (cdr x)))) lista))
; ⇒ (3 10 5)

;; c)

(define (f1 x) (lambda (y z) (string-append y z x)))
(define g1 (f1 "a"))
(check-equal? (g1 "clase" "lpp") "claselppa")



(define (f2 x) (lambda (y z) (list y x z)))
(define g2 (f2 "lpp"))
(check-equal? (g2 "hola" "clase") (list "hola" "lpp" "clase"))


(define (f g) (lambda(z x) (g z x)))
(check-equal? ((f cons) 3 4) '(3 . 4))



;;
;;EJERCICIO 2
;;

(define (unePereja pareja)
  (string->symbol (string-append (string (cdr pareja)) (string (car pareja)))))

(define (crea-baraja lista-parejas)
  (map unePereja lista-parejas))

(check-equal?  (crea-baraja '((#\u2660 . #\A) (#\u2663 . #\2) (#\u2665 . #\3) (#\u2666 . #\R))) '(A♠ 2♣ 3♥ R♦))


(define (expande pareja)
  (if (= 0 (cdr pareja))
      '()
      (cons (car pareja) (expande (cons (car pareja) (- (cdr pareja) 1))))))

(define (expande-lista lista-parejas)
  (foldr append '() (map expande  lista-parejas)))

(check-equal? (expande-lista '((#t . 3) ("LPP" . 2) (b . 4))) '(#t #t #t "LPP" "LPP" b b b b))

;;
;;EJERCICIO 3
;;
;;A
(define (suma-n-izq n lista-parejas)
  (map (lambda (x) (cons (+ n (car x)) (cdr x))) lista-parejas))

(check-equal? (suma-n-izq 10 '((1 . 3) (0 . 9) (5 . 8) (4 . 1))) '((11 . 3) (10 . 9) (15 . 8) (14 . 1)))

;;B
(define (aplica-2 func lista-parejas)
  (map (lambda (x) (func (car x) (cdr x))) lista-parejas))

(check-equal? (aplica-2 + '((2 . 3) (1 . -1) (5 . 4))) '(5 0 9))
(check-equal? (aplica-2 (lambda (x y) (if (even? x) y (* y -1))) '((2 . 3) (1 . 3) (5 . 4) (8 . 10))) '(3 -3 -4 10))

;;C
(define (sameLong? simbolo numero)
  (= (string-length (symbol->string simbolo)) numero))

(define (filtra-simbolos lista-simbolos lista-num)
   (filter (lambda (x) (sameLong? (car x) (cdr x))) (map (lambda (x y) (cons x y)) lista-simbolos lista-num)))

(check-equal? (filtra-simbolos '(este es un ejercicio de examen) '(2 1 2 9 1 6)) '((un . 2) (ejercicio . 9) (examen . 6)))

;;
;;EJERCICIO 4
;;
;;A
; Función auxiliar que devuelve la parte derecha
; de una pareja si la parte izquierda es #t. Sino
; devuelve #f

(define (devuelve-si-existe pareja)
  (if (equal? (car pareja) #t)
      (cdr pareja)
      #f))

(define (mi-index-of lista dato)
  (devuelve-si-existe 
   (foldl (lambda (elemento resultado)
            (cond
              ((car resultado) resultado) ; el car es #t: hemos encontrado el dato
                                          ; y no modificamos el resultado
              ((equal? dato elemento) (cons #t (cdr resultado))) ; encontramos el dato: construimos
                                                ; la pareja con #t y la posición actual
              (else (cons #f (+ 1 (cdr resultado)))))) ; no es el dato: construimos la pareja con
                                                       ; #f e incrementamos el resultado
          (cons #f 0)  ; resultado inicial: pareja con #f (no encontrado) y 0 (posición inicial)
          lista)))

(check-equal? (mi-index-of '(a b c d f) 'c) 2)
(check-equal? (mi-index-of '(1 2 3 4 5) 10) #f)

;;B

(define (busca-mayor mayor? lista)
  (foldl
   (lambda (x y)
   (if (mayor? x y)
       x
       y))
       (car lista) (cdr lista)))

(check-equal? (busca-mayor > '(2 7 5 6)) 7)

;;C
(define (posicion-mayor mayor? lista)
  (index-of lista (busca-mayor mayor? lista)))

(check-equal? (posicion-mayor > '(2 7 5 6)) 1)

;;
;;EJERCICIO 4
;;
(define orden '(#\2 #\3 #\4 #\5 #\6 #\7 #\8 #\9 #\0 #\J #\Q #\K #\A))
(define lista-manos '((9♦ 2♦ K♥ 0♦ 7♥) (6♦ 4♠ 7♣ 5♥ 4♦) (0♣ 4♣ 5♠ 3♥ J♥)))

(define (valor-carta carta orden)
  (+ 1 (index-of orden (string-ref (symbol->string carta) 0))))

(define (mano-ganadora lista-manos)
  (posicion-mayor >
                  (map (lambda (x) (busca-mayor >
                                                (map (lambda (carta) (valor-carta carta orden)) x)
                                                )) lista-manos)))


(check-equal? (mano-ganadora lista-manos) 0)




