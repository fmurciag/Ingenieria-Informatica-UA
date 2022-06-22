//francisco joaquin murcia gomez 48734281H
#include<iostream>
#include<math.h>
#include "tcomplejo.h"
using namespace std;

TComplejo::TComplejo(){
    this->re=0;
    this->im=0;
}
TComplejo::TComplejo(double re){
    this->re=re;
    this->im=0;
}
TComplejo::TComplejo(double re , double im){
    this->re=re;
    this->im=im;
}
TComplejo::TComplejo (const TComplejo& c){
    re=c.re;
    im=c.im;
}
TComplejo::~TComplejo(){
    this->re=0;
    this->im=0;
}
void TComplejo::Re(double re){
    this->re=re;
}
void TComplejo::Im(double im){
    this->im=im;
}
TComplejo& TComplejo::operator= (const TComplejo& c){
    if(this !=&c){
        (*this).~TComplejo();

        this->re=c.re;
        this->im=c.im;
    }
    return  (*this);
    
}

TComplejo TComplejo::operator+ (double re)const{
    TComplejo suma(this->re+re,this->im);
        return suma;
}
TComplejo TComplejo::operator+ (const TComplejo &c)const{
    TComplejo suma(this->re+c.re,this->im+c.im);
    return suma;
}
TComplejo TComplejo::operator- (double re)const{
	 TComplejo resta(this->re-re,this->im);
	 return resta;
}
TComplejo TComplejo::operator- (const TComplejo &c)const{
    TComplejo resta(this->re-c.re,this->im-c.im);
    return resta;
}
TComplejo TComplejo::operator* (const TComplejo& c)const{
    TComplejo mult(0,0);
    mult.re=this->re*c.re-this->im*c.im;
    mult.im=this->re*c.im-this->im*c.re;
    return mult;

}
TComplejo TComplejo::operator* (double re)const{
    TComplejo mult(this->re*re,this->im*re);
    return mult;

}
bool TComplejo::operator== (const TComplejo& c)const{

    if(this->re==c.re && this->im==c.im){
        return true;
    }
    return false;

}
bool TComplejo::operator!= (const TComplejo& c)const{
    
    return !((*this)==c);

}
double TComplejo::Arg(void){
    double r=(*this).re;
    double i=(*this).im;
    double a=atan2(i, r);
    return a;


}
double TComplejo::Mod(void)const{
    double r=(*this).re;
    double i=(*this).im;
    double pa=sqrt ( pow(r,2) + pow(i,2));
    return pa;

}
ostream& operator<<( ostream & o, const TComplejo & c){
    o<< "(" << c.Re() << " " << c.Im() << ")" ;
    return o;
    
}

 TComplejo operator+ (double x, const TComplejo& c){
	  TComplejo suma(c.re + x, c.im);
	  return suma;

}
 TComplejo operator- (double x, const TComplejo& c) {
	 TComplejo resta(x-c.re,-c.im);
	 return resta;


}
 TComplejo operator* (double x ,const TComplejo& c){
	 TComplejo mult(c.re*x,c.im*x);
	 return mult;

}


