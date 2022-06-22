#include <Adafruit_Sensor.h>

#include <boarddefs.h>
#include <IRremote.h>
#include <IRremoteInt.h>
#include <ir_Lego_PF_BitStreamEncoder.h>

#include <DHT.h>
#include <DHT_U.h>

#include <IRremote.h>



#define DHT11_PIN 13 //Sensor output conectado al pin 13
#define DHTTYPE DHT11
DHT dht(DHT11_PIN, DHTTYPE); //creating the object sensor


#define temperatura_ideal 27


//Señales decodificadas, arrays a rellenar, cambiar para mandos diferentes

unsigned int AC_apagar[] = {};  
unsigned int AC_encender[] = {};
 
unsigned int ajustar24[] = {};  
unsigned int ajustar25[] = {};  
unsigned int ajustar26[] = {}; 
unsigned int ajustar27[] = {};
unsigned int ajustar28[] = {};  
unsigned int ajustar29[] = {};  
unsigned int ajustar30[] = {};  


IRsend irsend;


int AC_temperatura=0;
int anterior;
int frecuencia = 38; // 38Khz

boolean AC = false; //Flag para saber si el AC se encuentra encendido o apagado


void setup(){

Serial.begin(9600);
dht.begin();
}


void loop(){

  delay(2000);
  

  float temperatura = dht.readTemperature();
  float humedad = dht.readHumidity();

  Serial.print(F("Humedad: "));
  Serial.print(humedad);
  Serial.print(F("%  Temperatura: "));
  Serial.print(temperatura);
  Serial.print(F("°C "));
  Serial.print(F("Temperatura AC "));
  Serial.print(AC_temperatura);
  Serial.print(F("°C "));
  
  
  //Si está apagado, y la temperatura actual es superior a la deseada
  if (AC == false && (temperatura >= temperatura_ideal+4)){
    irsend.sendRaw(AC_encender, sizeof(AC_encender) / sizeof(AC_encender[0]), frecuencia); delay(2000); //Encendemos el AC
    
    delay(2000);
    
    irsend.sendRaw(ajustar27, sizeof(ajustar27) / sizeof(ajustar27[0]), frecuencia); //Ajustamos la temperatura a la ideal, 27ºC
    AC_temperatura = 27; AC=true;
  }
  
  //Si está encendido, y la temperatura actual es inferior a la deseada
  if (AC == true && (temperatura <= (temperatura_ideal-3))){
  
    irsend.sendRaw(AC_apagar, sizeof(AC_apagar) / sizeof(AC_apagar[0]), frecuencia);  delay(2000);//Apagamos el AC
    AC_temperatura = 0; AC=false;
  }
  
  //Si la temperatura actual ha cambiado de la medida anteriormente
  if ( temperatura != anterior){
  
    //Si la temperatura actual está por encima o a 3 grados de la temperatura deseada
    if (temperatura >= temperatura_ideal+3){
    
      irsend.sendRaw(ajustar24, sizeof(ajustar24) / sizeof(ajustar24[0]), frecuencia); delay(2000);//Ajustamos a 24ºC
      
      AC_temperatura = 24; 
    
    }
    
    //Si la temperatura actual está a 2 grados por encima de la temperatura deseada
    if (temperatura == temperatura_ideal+2){
    
      irsend.sendRaw(ajustar25, sizeof(ajustar25) / sizeof(ajustar25[0]), frecuencia); delay(2000);//Ajustamos a 25ºC
      
      AC_temperatura = 25; 
    
    }
    
    //Si la temperatura actual está a 1 grado por encima de la temperatura deseada
    if (temperatura == temperatura_ideal+1){
    
      irsend.sendRaw(ajustar26, sizeof(ajustar26) / sizeof(ajustar26[0]), frecuencia); delay(2000);//Ajustamos a 26ºC
      
      AC_temperatura = 26; 
    
    }
    
    //Si está a la temperatura ideal
    if (temperatura == 27 ){
    
      irsend.sendRaw(ajustar27, sizeof(ajustar27) / sizeof(ajustar27[0]), frecuencia); //Ajustamos a 27ºC
      
      AC_temperatura = 27; 
    
    }
    
    //Si la temperatura está 1 grado por debajo de la deseada
    if (temperatura == temperatura_ideal-1){
    
      irsend.sendRaw(ajustar28, sizeof(ajustar28) / sizeof(ajustar28[0]), frecuencia); delay(2000);//Ajustamos a 28ºC
      
      AC_temperatura = 28; 
    
    }
    
    //Si la temperatura actual está 2 grados por debajo de la deseada
    if (temperatura == temperatura_ideal-2){
    
      irsend.sendRaw(ajustar29, sizeof(ajustar29) / sizeof(ajustar29[0]), frecuencia); delay(2000);//Ajustamos a 29ºC
      
      AC_temperatura = 29; 
    
    }
    
    //Si la temperatura está por debajo de 3 grados de la deseada
    if (temperatura <= temperatura_ideal-3 ){
    
      irsend.sendRaw(ajustar30, sizeof(ajustar30) / sizeof(ajustar30[0]), frecuencia); delay(2000);//Ajustamos a 30ºC
      
      AC_temperatura = 30; 
    
    }

  }

anterior = temperatura;

}
