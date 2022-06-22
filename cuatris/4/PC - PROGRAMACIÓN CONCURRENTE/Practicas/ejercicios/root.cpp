#include <iostream>
#include <math.h>
using namespace std;


using F = double(double);

double root (F f, double a, double b, double epsilon){
    double puntoMedio=(a+b)/2.0;
    if(abs(f(puntoMedio)) <= epsilon){
        return puntoMedio;

    }else if(f(a) * f(puntoMedio)>0){
        root(f, puntoMedio, b, epsilon);
    }else if(f(b) * f(puntoMedio)>0){
        root(f, a, puntoMedio, epsilon);
    }
    
    
}

double myfn2(double x) {

    return 3.0*x*x - 6.0;
}


int main (){
    
    // cout<<root(sin,0.5,4.0,0.00000000001);
    cout << root(myfn2, 7.0, -7.0, 0.0001);
    cout<<"\n";
    return 0;
}