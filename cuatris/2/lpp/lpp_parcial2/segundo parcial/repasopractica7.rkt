#lang r6rs
(import (rnrs))

;;
;; Barrera de abstracción arbol
;;

(define (dato-arbol arbol) 
    (car arbol))

(define (hijos-arbol arbol) 
    (cdr arbol))

(define (hoja-arbol? arbol) 
   (null? (hijos-arbol arbol)))

(define (construye-arbol dato hijos)
  (cons dato hijos))

;;
;; Barrera de abstracción arbol binario
;;

(define (dato-arbolb arbol)
   (car arbol))
   
(define (hijo-izq-arbolb arbol)
   (cadr arbol))

(define (hijo-der-arbolb arbol)
   (caddr arbol))

(define (hoja-arbolb? arbol)
  (and (vacio-arbolb? (hijo-izq-arbolb arbol))
       (vacio-arbolb? (hijo-der-arbolb arbol))))
(define (vacio-arbolb? x)
   (null? x))

(define arbolb-vacio '())
(define arbolg '(15
                 (4 (2) (3))
                 (8 (6))
                 (12 (9) (10) (11))))
(define arbol2 '(a (b (c (d)) (e)) (f)))

(define (to-string-arbol arbol)
  (string-append(symbol->string (dato-arbol arbol)) (to-string-bosque (hijos-arbol arbol))))

(define (to-string-bosque bosque)
  (if (null? bosque)
      ""
      (string-append (to-string-arbol (car bosque)) (to-string-bosque (cdr bosque)))))

(define (to-string-arbol-fos arbol)
  (fold-right string-append (symbol->string(dato-arbol arbol)) (map to-string-arbol-fos (hijos-arbol arbol))))
  
(define (veces-arbol dato arbol)
  (+ (if (equal? dato (dato-arbol arbol))
         1
         0)
     (veces-arbol-bosque dato (hijos-arbol arbol))))

(define (veces-arbol-bosque dato bosque)
  (if (null? bosque)
      0
      (+ (veces-arbol dato (car bosque)) (veces-arbol-bosque dato (cdr bosque)))))



(define arbol3 '(20 (2) (8 (4) (2)) (9 (5))))
(define (suma-raices-hijos arbol)
  (fold-right + 0 (map dato-arbol (hijos-arbol arbol))))


(define (raices-mayores-arbol? arbol)
  (and (if (>(dato-arbol arbol) (suma-raices-hijos arbol))

           #t
           #f)
       (raices-mayores-arbol-bosque? (hijos-arbol arbol))))

(define (raices-mayores-arbol-bosque? bosque)
  (if (null? bosque)
      #t
      (and(raices-mayores-arbol? (car bosque)) (raices-mayores-arbol-bosque? (cdr bosque)))))
      
 (define (raices-mayores-arbol-fos? arbol)
   (fold-right (lambda (x res) (and x res)) (if (>(dato-arbol arbol) (suma-raices-hijos arbol))
                                                #t
                                                #f) (map raices-mayores-arbol-fos? (hijos-arbol arbol))))
                              
  
 (define (comprueba-raices-arbol arbol)
   (construye-arbol (if (raices-mayores-arbol? arbol)
                       1
                       0) (comprueba-raices-bosque (hijos-arbol arbol))))

(define (comprueba-raices-bosque bosque)
  (if (null? bosque)
      '()
      (cons (comprueba-raices-arbol (car bosque)) (comprueba-raices-bosque (cdr bosque)))))
                                       

(define (es-camino? lista arbol)
  (and (if (equal? (car lista) (dato-arbol arbol))
           (escamino-bosque? (hijos-arbol arbol) (cdr lista))
           #f
           )))

(define (escamino-bosque? bosque lista)
  (if (and (null? bosque) (not (null? lista)))
      #f
      (if (and (null? lista) (null? bosque))
          #t
          
          (or(es-camino? lista (car bosque)) (escamino-bosque? (cdr bosque) lista))))) 
      
                 
 (define arbol5 '(a (a (a) (b)) (b (a) (c)) (c)))
(define arbol6 '(1 (2 (3 (4) (2)) (5)) (6 (7))))

(define (nodos-nivel nivel arbol)
  (if (= nivel 0)
       (dato-arbol arbol)
      (nodos-nivel-bosque (- nivel 1) (hijos-arbol arbol))))

(define (nodos-nivel-bosque nivel bosque)
  (if (null? bosque)
      '()
      (cons (nodos-nivel nivel (car bosque)) (nodos-nivel-bosque nivel (cdr bosque)))))
  
  
(define (camino-b-tree arbol camino)
  (if (equal? (car camino) '<)
      










