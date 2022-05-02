; JOSUÉ PEREA MARTINEZ

#lang r6rs
(import (rnrs)
        (schemeunit))

;;
;; Ejercicio 1
;;

;barreras de abstracción

(define (dato-arbol arbol) 
    (car arbol))

(define (hijos-arbol arbol) 
    (cdr arbol))

(define (hoja-arbol? arbol) 
   (null? (hijos-arbol arbol))) 


;a.1)

(define arbol '(15 (4 (2) (3)) (8 (6)) (12 (9) (10) (11))))
(display "Probando apartado a.1) Ejercicio 1\n")
(check-equal? (dato-arbol (cadr (hijos-arbol (caddr (hijos-arbol arbol))))) 10)

;a.2)

;funciones que suman datos de un arbol

(define (suma-datos-arbol arbol)
    (+ (dato-arbol arbol)
       (suma-datos-bosque (hijos-arbol arbol))))

(define (suma-datos-bosque bosque)
    (if (null? bosque)
        0
        (+ (suma-datos-arbol (car bosque)) 
           (suma-datos-bosque (cdr bosque)))))

;1.¿Qué devuelve la invocación a (suma-datos-arbol (car bosque)) que se realiza
;  dentro de la función?

;- Devuelve la suma de los datos del primer hijo de arbol --> 9


(display "Probando apartado a.2.1) Ejercicio 1\n")
(check-equal? (suma-datos-arbol (car (hijos-arbol arbol))) 9)

;2.¿Qué devuelve la primera llamada recursiva a suma-datos-bosque?

;- Devuelve la suma de los datos del resto de hijos (menos el primero), es decir, 56

(display "Probando apartado a.2.2) Ejercicio 1\n")
(check-equal? (suma-datos-bosque (cdr (hijos-arbol arbol))) 56)

;a.3)

;suma-datos-arbol con fos
(define (suma-datos-arbol-fos arbol)
   (fold-right + (dato-arbol arbol) 
       (map suma-datos-arbol-fos (hijos-arbol arbol))))

;1.¿Qué devuelve la invocación a map dentro de la función?

;- Devuelve una lista del tamaño de (hijos-arbol arbol), en la que de cada hijo ha
;- sumado los datos --> '(9 14 42)

(display "Probando apartado a.3) Ejercicio 1\n")
(check-equal? (map suma-datos-arbol-fos (hijos-arbol arbol)) '(9 14 42))

;2.¿Qué invocaciones se realizan a la función + durante la ejecución de fold-right
;  sobre la lista devuelta por la invocación a map? Enuméralas en orden, indicando
;  sus parámetros y el valor devuelto en cada una de ellas.

;- Para cada uno de los elementos de la lista que devuelve map, sumará el resultado
;- que vayamos obteniendo con el siguiente elemento que encuentre, siendo el caso
;- base: (dato-arbol arbol) --> 15. Por lo tanto la traza sería:
;- Primero se llamaría a + con 42 y 15 (+ 42 15) --> 57, después el siguiente
;- elemento (+ 14 57) --> 71, y finalmente el último elemento (+ 71 9) --> 80

;b.1)

;barreras de abstracción de árboles binarios

(define (dato-arbolb arbol)
   (car arbol))

(define (hijo-izq-arbolb arbol)
   (cadr arbol))

(define (hijo-der-arbolb arbol)
   (caddr arbol))

(define (vacio-arbolb? arbol)
   (null? arbol))

(define (hoja-arbolb? arbol)
   (and (vacio-arbolb? (hijo-izq-arbolb arbol))
        (vacio-arbolb? (hijo-der-arbolb arbol))))

(define arbolb-vacio '())

;definicion
(define arbolb '(40 (23 (5 () ())
                        (32 (29 () ())
                            ()))
                    (45 ()
                        (56 () ()))))

(display "Probando apartado b.1) Ejercicio 1\n")
(check-equal? (dato-arbolb (hijo-izq-arbolb (hijo-der-arbolb (hijo-izq-arbolb arbolb))))
              29)

;;
;; Ejercicio 2
;;

;a)

;con recursión mutua

(define (to-string-bosque bosque)
  (if (null? bosque)
      ""
      (string-append (to-string-arbol (car bosque))
                     (to-string-bosque (cdr bosque)))))

(define (to-string-arbol arbol)
  (string-append (symbol->string (dato-arbol arbol))
                 (to-string-bosque (hijos-arbol arbol))))


(define arbol2 '(a (b (c (d)) (e)) (f)))
(display "Probando apartado a) con recursión mutua Ejercicio 2\n")
(check-equal? (to-string-arbol arbol2) "abcdef")

;con fos

(define (to-string-arbol-fos arbol)
  (fold-left (lambda (r x)
               (string-append r x))
             
             (symbol->string (dato-arbol arbol))
             (map to-string-arbol-fos
              (hijos-arbol arbol))))
             
(display "Probando apartado a) con fos Ejercicio 2\n")
(check-equal? (to-string-arbol-fos arbol2) "abcdef")


;b)

;con recursión mutua

(define (veces-bosque dato bosque)
  (if (null? bosque)
      0
      (if (hoja-arbol? (car bosque))
          (if (equal? dato (dato-arbol (car bosque)))
              (+ 1 (veces-bosque dato (cdr bosque)))
              (veces-bosque dato (cdr bosque)))
          (+ (veces-arbol dato (car bosque))
             (veces-bosque dato (cdr bosque))))))
              
      
(define (veces-arbol dato arbol)
  (if (equal? dato (dato-arbol arbol))
      (+ 1 (veces-bosque dato (hijos-arbol arbol)))
      (veces-bosque dato (hijos-arbol arbol))))

(display "Probando apartado b) con recursión mutua Ejercicio 2\n")
(check-equal? (veces-arbol 'b '(a (b (c) (d)) (b (b) (f)))) 3)
(check-equal? (veces-arbol 'g '(a (b (c) (d)) (b (b) (f)))) 0)

;con fos

(define (iguales dato elem)
  (if (equal? dato elem)
      1
      0))

(define (veces-arbol-fos elemento arbol)
  (+ (iguales (dato-arbol arbol) elemento)
     (fold-right +
                 0
                 (map (lambda (x)
                        (veces-arbol-fos elemento x))
                      (hijos-arbol arbol)))))

(display "Probando apartado b) con fos Ejercicio 2\n")
(check-equal? (veces-arbol-fos 'b '(a (b (c) (d)) (b (b) (f)))) 3)
(check-equal? (veces-arbol-fos 'g '(a (b (c) (d)) (b (b) (f)))) 0)

;;
;;Ejercicio 3
;;

;a)
(define (suma-raices-hijos arbol)
  (fold-right +
              0
              (map dato-arbol (hijos-arbol arbol))))

(define arbol3 '(20 (2) (8 (4) (2)) (9 (5))))
(display "Probando apartado a) Ejercicio 3\n")
(check-equal? (suma-raices-hijos arbol3) 19)              
(check-equal? (suma-raices-hijos (cadr (hijos-arbol arbol3))) 6)               

;b)

(define (raices-mayores-arbol? arbol)
  (and (> (dato-arbol arbol)
          (fold-right +
                      0
                      (map dato-arbol
                           (hijos-arbol arbol))))
       (raices-mayores-bosque? (hijos-arbol arbol))))

(define (raices-mayores-bosque? bosque)
  (if (null? bosque)
      #t
      (and (raices-mayores-arbol? (car bosque))
           (raices-mayores-bosque? (cdr bosque)))))

(display "Probando apartado b) con recursión mutua Ejercicio 3\n")
(check-equal? (raices-mayores-arbol? arbol3)
              #t)
(check-equal? (raices-mayores-arbol? '(20 (2) (8 (4) (5)) (9 (5))))
              #f)

;fos
(define (raices-mayores-arbol?-fos arbol)
  (fold-right (lambda (x r)
                (and x r))
              (> (dato-arbol arbol) (fold-right +
                                         0
                                         (map dato-arbol
                                              (hijos-arbol arbol))))
              (map raices-mayores-arbol?-fos
                   (hijos-arbol arbol))))

(display "Probando apartado b) con fos Ejercicio 3\n")
(check-equal? (raices-mayores-arbol?-fos arbol3)
              #t)
(check-equal? (raices-mayores-arbol?-fos '(20 (2) (8 (4) (5)) (9 (5))))
              #f)

;c

(define (comprueba-raices-bosque bosque)
  (if (null? bosque)
      '()
      (cons (comprueba-raices-arbol (car bosque))
            (comprueba-raices-bosque (cdr bosque)))))


(define (comprueba-raices-arbol arbol)
  (cons (if (> (dato-arbol arbol)
               (suma-raices-hijos arbol))
            1
            0)
        (comprueba-raices-bosque (hijos-arbol arbol))))

(display "Probando apartado c) Ejercicio 3\n")
(check-equal? (comprueba-raices-arbol arbol3)
              '(1 (1) (1 (1) (1)) (1 (1))))
(check-equal? (comprueba-raices-arbol '(20 (2) (8 (4) (5)) (9 (5))))
              '(1 (1) (0 (1) (1)) (1 (1))))


;;
;; Ejercicio 4
;;

;a)
;(define (es-camino? lista arbol))
  