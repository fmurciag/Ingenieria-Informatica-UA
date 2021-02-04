#Francisco Juaquin Murcia Gomez 48734281H




set terminal png


set xlabel "n"
set ylabel "llamadas recursivas"

set yrange [0:2100]


##########################
#pow2
set output "pow2.png"
set title  " 2^n complejidad"

plot "pow2.recursiones" using 1:2 with lines title "pow2_1 O(n)", "pow2.recursiones" using 1:3 with lines title "pow2_2 O(log(n))", "pow2.recursiones" using 1:4 with lines title "pow2_3 O(2^n)"



