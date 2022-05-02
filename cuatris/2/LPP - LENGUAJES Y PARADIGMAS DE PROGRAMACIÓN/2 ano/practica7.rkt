#lang racket
(require rackunit)
;; Barrera de abstracción
;; Selectores
(define (dato-arbol arbol) 
    (car arbol))
(define (hijos-arbol arbol) 
    (cdr arbol))
(define (hoja-arbol? arbol) 
   (null? (hijos-arbol arbol)))
;; Constructor
(define (construye-arbol dato lista-arboles)
   (cons dato lista-arboles))
;; Selectores arbol binario
(define (dato-arbolb arbol)
   (car arbol))
(define (hijo-izq-arbolb arbol)
   (cadr arbol))
(define (hijo-der-arbolb arbol)
   (caddr arbol))
(define arbolb-vacio '())
(define (vacio-arbolb? arbol)
   (equal? arbol arbolb-vacio))
(define (hoja-arbolb? arbol)
   (and (vacio-arbolb? (hijo-izq-arbolb arbol))
        (vacio-arbolb? (hijo-der-arbolb arbol))))
;; Constructor arbol binario
(define (construye-arbolb dato hijo-izq hijo-der)
    (list dato hijo-izq hijo-der))
;;esta?
(define (exists? predicado lista)
  (and (not (null? lista))
       (or (predicado (car lista))
           (exists? predicado (cdr lista)))))
(define (for-all? predicado lista)
  (or (null? lista)
      (and (predicado (car lista))
           (for-all? predicado (cdr lista)))))


;;EJERCICIO1
;;a1
(define arbol '(15 (4 (2) (3))
                   (8 (6))
                   (12 (9) (10) (11))))

(check-equal? (dato-arbol (cadr (hijos-arbol (caddr (hijos-arbol arbol))))) 10)
;;a2

;a3
; '(9 14 42)
; 1º 15+42 ; 2º 57+14 ; 3º 71+9
;b
(define arbolb '(40
                  (23
                   (5 () ())
                   (32
                    (29 () ())
                    ()))
                  (45
                   ()
                   (56 () ()))))

(check-equal? (dato-arbol (hijo-izq-arbolb (hijo-der-arbolb (hijo-izq-arbolb arbolb)))) 29)
;EJERCICIO2
;a
(define (to-string-arbol arbol)
  (string-append (symbol->string (dato-arbol arbol)) (to-string-bosque (hijos-arbol arbol))))

(define (to-string-bosque bosque)
  (if (null? bosque)
      ""
      (string-append (to-string-arbol (car  bosque)) (to-string-bosque (cdr bosque)))))

;(define arbol2 '(a (b (c (d)) (e)) (f)))
;;(to-string-arbol arbol2) ; ⇒ "abcdef"

(define (to-string-arbol-foo arbol)
  (foldr string-append "" (cons (symbol->string (dato-arbol arbol))
         (map  to-string-arbol-foo (hijos-arbol arbol)))))

;(to-string-arbol-foo arbol2) ; ⇒ "abcdef"
;b
(define (veces-arbol dato arbol)
  (if (equal? dato (dato-arbol arbol))
      (+ 1 (veces-bosque dato (hijos-arbol arbol)))
      (veces-bosque dato (hijos-arbol arbol))))

(define (veces-bosque dato bosque)
  (if (null? bosque)
      0
      (+ (veces-arbol dato (car bosque)) (veces-bosque dato (cdr bosque)))))

;(veces-arbol 'b '(a (b (c) (d)) (b (b) (f)))) ; ⇒ 3
;(veces-arbol 'g '(a (b (c) (d)) (b (b) (f)))) ; ⇒ 0

(define (veces-arbol-fos dato arbol)
  (foldr + (if (equal? dato (dato-arbol arbol)) 1 0)
                         (map (lambda (x) (veces-arbol-fos  dato x)) (hijos-arbol arbol))))

;(veces-arbol-fos 'b '(a (b (c) (d)) (b (b) (f)))) ; ⇒ 3
;(veces-arbol-fos 'g '(a (b (c) (d)) (b (b) (f)))) ; ⇒ 0

;EJERCICIO3
;A
(define (hojas-cumplen pred arbol)
  (if (and (hoja-arbol? arbol) (pred (dato-arbol arbol)))
      (cons (dato-arbol arbol) (hojas-cumplen-bosque pred (hijos-arbol arbol)))
      (hojas-cumplen-bosque pred (hijos-arbol arbol))))

(define (hojas-cumplen-bosque pred bosque)
  (if (null? bosque)
      '()
      (append (hojas-cumplen pred (car bosque)) (hojas-cumplen-bosque pred (cdr bosque)))))

(define arbol1 '(10 (2) (12 (4) (2)) (10 (5))))
(define arbol2 '(10 (2) (12 (4) (2)) (10 (6))))
;(hojas-cumplen even? arbol1) ; ⇒ '(2 4 2)
;(hojas-cumplen even? arbol2) ; ⇒ '(2 4 2 6)

(define (hojas-cumplen-fos pred arbol)
    (if (and (hoja-arbol? arbol) (pred (dato-arbol arbol)))
      (cons (dato-arbol arbol) '())
      (foldr append '() (map (lambda (x) (hojas-cumplen-fos pred x)) (hijos-arbol arbol)))))
                       
;(hojas-cumplen-fos even? arbol1) ; ⇒ '(2 4 2)
;(hojas-cumplen-fos even? arbol2) ; ⇒ '(2 4 2 6)                       
                       
;B
(define (todas-hojas-cumplen? pred arbol)
   (if (hoja-arbol? arbol) 
       (pred (dato-arbol arbol))
  (todas-hojas-cumplen-bosque? pred (hijos-arbol arbol))))
      

(define (todas-hojas-cumplen-bosque? pred bosque)
  (if (null? bosque)
      #t
      (and  (todas-hojas-cumplen? pred (car bosque)) (todas-hojas-cumplen-bosque? pred (cdr bosque)))))

;(todas-hojas-cumplen? even? arbol1) ; ⇒ #f
;(todas-hojas-cumplen? even? arbol2) ; ⇒ #t

(define (todas-hojas-cumplen?-foo pred arbol)
  (if (hoja-arbol? arbol)
      (pred (dato-arbol arbol))
      (for-all? (lambda (x) (todas-hojas-cumplen?-foo pred x)) (hijos-arbol arbol))))
;(todas-hojas-cumplen?-foo even? arbol1) ; ⇒ #f
;(todas-hojas-cumplen?-foo even? arbol2) ; ⇒ #t

;EJERCICIO4

(define (suma-raices-hijos arbol)
  (foldr + 0 (map dato-arbol (hijos-arbol arbol))))


(define arbol3 '(20 (2) (8 (4) (2)) (9 (5))))
;(suma-raices-hijos arbol3) ; ⇒ 19
;(suma-raices-hijos (cadr (hijos-arbol arbol3))) ; ⇒ 6


(define (raices-mayores-arbol? arbol)
  (and 
      (> (dato-arbol arbol) (suma-raices-hijos arbol))
      (raices-mayores-arbol?-bosque (hijos-arbol arbol))))

(define (raices-mayores-arbol?-bosque bosque)
  (or (null? bosque)
      
      (and (raices-mayores-arbol? (car bosque)) (raices-mayores-arbol?-bosque (cdr bosque)))))

;(raices-mayores-arbol? arbol3) ; ⇒ #t
;(raices-mayores-arbol? '(20 (2) (8 (4) (5)) (9 (5)))) ; ⇒ #f
(define (raices-mayores-arbol?-fos arbol)
  (and
     (> (dato-arbol arbol) (suma-raices-hijos arbol))
     (for-all? raices-mayores-arbol?-fos  (hijos-arbol arbol))))

;(raices-mayores-arbol?-fos arbol3) ; ⇒ #t
;(raices-mayores-arbol?-fos '(20 (2) (8 (4) (5)) (9 (5)))) ; ⇒ #f

(define (comprueba-raices-arbol arbol)
  (construye-arbol (if (> (dato-arbol arbol) (suma-raices-hijos arbol)) 1 0) (comprueba-raices-arbol-bosque (hijos-arbol arbol))))

(define (comprueba-raices-arbol-bosque bosque)
  (if (null? bosque)
      '()
      (construye-arbol (comprueba-raices-arbol (car bosque)) (comprueba-raices-arbol-bosque (cdr bosque)))))


;(comprueba-raices-arbol arbol3) ; ⇒ (1 (1) (1 (1) (1)) (1 (1)))
;(comprueba-raices-arbol '(20 (2) (8 (4) (5)) (9 (5)))) 
; ⇒ (1 (1) (0 (1) (1)) (1 (1)))

(define (comprueba-raices-arbol-fos arbol)
  (construye-arbol (if (> (dato-arbol arbol) (suma-raices-hijos arbol)) 1 0)
                   (foldr cons '() (map comprueba-raices-arbol-fos (hijos-arbol arbol)))))


;EJERCICIO4
#;(define (es-camino? lista arbol)
  (if (null? lista)
      #f
      (and (equal? (car lista) (dato-arbol arbol?)) (es-camino-bosque? (cdr lista) (hijos-arbol arbol)))))





(define arbol_5b '(1 (2 (3 (4) (2)) (5)) (6 (7))))
(define (nodos-nivel nivel arbol)
  (if (= 0 nivel)
      (list (dato-arbol arbol))
      (nodos-nuvel-bosque (- nivel 1) (hijos-arbol arbol))))

(define (nodos-nuvel-bosque nivel bosque)
  (if (null? bosque)
      '()
      (append (nodos-nivel nivel (car bosque)) (nodos-nuvel-bosque nivel (cdr bosque)))))



#;(define (nodos-nivel nivel arbol)
  (if (= nivel 0)
      (list (dato-arbol arbol))
      (foldl (lambda (subarbol lista)
               (append lista (nodos-nivel (- nivel 1) subarbol))) '() (hijos-arbol arbol))))


;EJERCICIO6

(define (ordenado-entre? arbol min max)
  (if (vacio-arbolb? arbol)
      #t
      (and (<= (dato-arbolb arbol) max) (>= (dato-arbolb arbol) min)
           (ordenado-entre? (hijo-izq-arbolb arbol) min (dato-arbolb arbol))
           (ordenado-entre? (hijo-der-arbolb arbol) (dato-arbolb arbol) max))))



(define arbolb1 '(20 (13 (2 () ())
                         (18 () ()))
                     (40 (25 () () )
                         (43 () ()))))
(define arbolb2 '(20 (13 (2 () ())
                         (22 () ()))
                     (40 (25 () () )
                         (43 () ()))))

;(ordenado-entre? arbolb1 0 50) ; ⇒ #t
;(ordenado-entre? arbolb2 0 50) ; ⇒ #f
;(ordenado-entre? arbolb1 0 30) ; ⇒ #f


(define (ordenado-menor? arbol max)
  (if (vacio-arbolb? arbol)
      #t
      (and (<= (dato-arbolb arbol) max)
           (ordenado-menor? (hijo-izq-arbolb arbol) (dato-arbolb arbol))
           (ordenado-entre? (hijo-der-arbolb arbol) (dato-arbolb arbol) max))))

(define (ordenado-mayor? arbol min)
  (if (vacio-arbolb? arbol)
      #t
      (and (>= (dato-arbolb arbol) min)
           (ordenado-entre? (hijo-izq-arbolb arbol) min (dato-arbolb arbol))
           (ordenado-mayor? (hijo-der-arbolb arbol) (dato-arbolb arbol)))))

(check-true (ordenado-menor? arbolb1 50))
(check-false (ordenado-menor? arbolb1 40))
(check-false (ordenado-menor? arbolb2 50))
(check-true (ordenado-mayor? arbolb1 0))
(check-false (ordenado-mayor? arbolb1 20))
(check-false (ordenado-mayor? arbolb2 0))


(define (ordenado? arbol)
  (if (vacio-arbolb? arbol)
      #t
      (and (ordenado-menor? (hijo-izq-arbolb arbol) (dato-arbolb arbol)) (ordenado-mayor? (hijo-der-arbolb arbol) (dato-arbolb arbol)))))

;(ordenado? arbolb1) ; ⇒ #t
;(ordenado? arbolb2) ; ⇒ #f
      
;EJERCICIO7

(define (camino-b-tree arbol camino)
  (cond
    ((or (vacio-arbolb? arbol) (null? camino)) '())
    ((equal? '< (car camino)) (camino-b-tree (hijo-izq-arbolb arbol) (cdr camino)))
    ((equal? '> (car camino)) (camino-b-tree (hijo-der-arbolb arbol) (cdr camino)))
    (else (cons (dato-arbolb arbol) (camino-b-tree arbol (cdr camino))))))
      




(define arbolb6 '(9 (5 (3 (1 () ())
                          (4 () ()))
                       (7 () ()))
                    (15 (13 (10 () ())
                            (14 () ()))
                        (20 ()
                            (23 () ())))))
(camino-b-tree arbolb6 '(= < < = > =)) ;'(9 3 4))
(camino-b-tree arbolb6 '(> = < < =)) ;'(15 10))



      
