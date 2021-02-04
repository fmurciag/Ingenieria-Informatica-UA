#Francisco Juaquin Murcia Gomez 48734281H




set terminal png


set xlabel "Array size"
set ylabel "millones de pasos de programa"

set yrange [0:]
set xtics ( "2¹⁵" 32768, "2²º" 1048576, "2²¹" 2097152)

##########################
#Quicksort
set output "Quicksort.png"
set title  " Quicksort"

plot "qs-vs-hs.CPUtimes" using 1:2 with lines title "random array", "qs-vs-hs.CPUtimes" using 1:4 with lines title "sorted array", "qs-vs-hs.CPUtimes" using 1:6 with lines title "sorted reverse array"



##########################################################################
#Heapsort

set output "Heapsort.png"
set title "Heapsort"


plot "qs-vs-hs.CPUtimes" using 1:3 with lines title "random array", "qs-vs-hs.CPUtimes" using 1:5 with lines title "sorted array", "qs-vs-hs.CPUtimes" using 1:7 with lines title "sorted reverse array"

################################################
#ajustes
#------------------------------------------------------------------------



##Fit to a (n log n + k) function: 
y(x)=a*x*log(x) + b 
fitFunctionRaQ="Fitting quickSort step values to f(n) = n log(n)+k"
fit y(x) "qs-vs-hs.CPUtimes" using 1:2 via a,b

y2(x)=a*x*log(x) + b 
fitFunctionRaH="Fitting heapsort step values to f(n) = n log(n)+k"
fit y2(x) "qs-vs-hs.CPUtimes" using 1:3 via a,b

y3(x)=c*x*log(x) + d 
fitFunctionSaQ="Fitting quickSort step values to f(n) = n log(n)+k"
fit y3(x) "qs-vs-hs.CPUtimes" using 1:4 via c,d

y4(x)=c*x*log(x) + d 
fitFunctionSaH="Fitting heapsort step values to f(n) = n log(n)+k"
fit y4(x) "qs-vs-hs.CPUtimes" using 1:5 via c,d

y5(x)=e*x*log(x) + f 
fitFunctionRSaQ="Fitting quickSort step values to f(n) = n log(n)+k"
fit y5(x) "qs-vs-hs.CPUtimes" using 1:6 via e,f

y6(x)=e*x*log(x) + f 
fitFunctionRSaH="Fitting heapsort step values to f(n) = n log(n)+k"
fit y6(x) "qs-vs-hs.CPUtimes" using 1:7 via e,f


#------------------------------------------------------------------------

############################################
#Quicksort vs Heapsort (random array)

set output "qs-vs-hs-RA.png"
set title "Quicksort vs Heapsort (random array)"


plot "qs-vs-hs.CPUtimes" using 1:2 with lines title "Quicksort",  "qs-vs-hs.CPUtimes" using 1:3 with lines title "Heapsort",y(x) title fitFunctionRaQ, y2(x) title fitFunctionRaH

########################################################
#Quicksort vs Heapsort (sorted array)

set output "qs-vs-hs-SA.png"
set title "Quicksort vs Heapsort (sorted array)"


plot "qs-vs-hs.CPUtimes" using 1:4 with lines title "Quicksort", "qs-vs-hs.CPUtimes" using 1:5 with lines title "Heapsort", y3(x) title fitFunctionSaQ, y4(x) title fitFunctionSaH

########################################################
#Quicksort vs Heapsort (sorter reverse array)

set output "qs-vs-hs-RSA.png"
set title "Quicksort vs Heapsort (reverse sorted array)"


plot "qs-vs-hs.CPUtimes" using 1:6 with lines title "Quicksort", "qs-vs-hs.CPUtimes" using 1:7 with lines title "Heapsort", y5(x) title fitFunctionRSaQ, y6(x) title fitFunctionRSaH


