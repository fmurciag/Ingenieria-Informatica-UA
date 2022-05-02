#include "tabbcom.h"
#include "tvectorcom.h"
#include "tlistacom.h"
#include <iostream>
#include <queue> 

using namespace std;



int
main(void)
{
   TComplejo c1(1,1),c2(2,2),c3(3,3),c4(4,4),c5(5,5),c6(6,6),c7(7,7),c8(8,8),c9(9,9),c10(10,10);
  TListaCom l1;
TComplejo *x;
TABBCom a;



l1.InsCabeza(c7);
l1.InsCabeza(c6);
l1.InsCabeza(c5);
  l1.InsCabeza(c4);
  l1.InsCabeza(c3);
  l1.InsCabeza(c2);
  l1.InsCabeza(c1);

  cout<<l1<<endl;


x=a.MostrarNiveles(l1);
cout<<l1<<endl;
  int longitud=l1.Longitud();

for(int i=0;i<longitud;i++){
        cout<<x[i]<<"  ";
    }
    cout<<endl;

    cout<<a<<endl;




  return 0;
}
