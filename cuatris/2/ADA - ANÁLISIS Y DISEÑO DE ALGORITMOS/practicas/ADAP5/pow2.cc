//Francisco Juaquin Murcia Gomez 48734281H
#include <iostream>

using namespace std;
unsigned long nRecus1=0;
unsigned long nRecus2=0;
unsigned long nRecus3=0;


unsigned long pow2_1(int n){ //O(n)
    nRecus1++;
    if(n==0){
        return 1;
    }else{
        
        return 2*pow2_1(n-1);
    }
}

unsigned long pow2_2(int n){//divide y venceras  O(log n)
    nRecus2++;
    if(n==0){
        return 1;
    
    }else if(n%2==0){
        long a=pow2_2(n/2);
        return a*a;
    }else{
        long a=pow2_2(n/2);
        return a*a*2;
    }
}

unsigned long pow2_3(int n){//divide y venceras pero con dos llamadas para tener complejidad exponencial O(2^n)
    nRecus3++;
    if(n==0){
        return 1;
    
    }else if(n%2==0){
        return pow2_3(n/2)*pow2_3(n/2);
    }else{
        return pow2_3(n/2)*pow2_3(n/2)*2;
    }
}




int main(){
    cout<<"n \tpow2_1 \tpow2_2 \tpow2_3"<<endl;
    cout<<"=================================================="<<endl;

    for(int n=0;n<=1000;n+=10){
        pow2_1(n);
        pow2_2(n);
        pow2_3(n);
        

        cout<<n<<"\t"<<nRecus1<<"\t"<<nRecus2<<"\t"<<nRecus3<<endl;

        nRecus1=0;
        nRecus2=0;
        nRecus3=0;
    }
    
}