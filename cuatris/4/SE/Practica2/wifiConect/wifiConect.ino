#include <WiFiNINA.h>

#define SSID "Redmi"
#define PASSWORD "987654321"
bool conexionFallida=false;
void setup() {
  // initialize digital pin LED_BUILTIN as an output.
  pinMode(LED_BUILTIN, OUTPUT);
  digitalWrite(LED_BUILTIN, HIGH);
  Serial.println("Conectando a "+ (String)SSID);
  WiFi.begin(SSID,PASSWORD);
 
}
void loop() {
  int segundos=0;
  while(WiFi.status()!=WL_CONNECTED && !conexionFallida){
    Serial.println("ERROR: al conectar a " + (String)SSID);
      digitalWrite(LED_BUILTIN, HIGH);   
      delay(500);                      
      digitalWrite(LED_BUILTIN, LOW);    
      delay(500);
    segundos++;
    if(segundos>=5){
      Serial.println("ERROR: no se ha podido conectar a " + (String)SSID);
      conexionFallida=true;
      break;
    }
    Serial.println("Reintentando...");
    WiFi.begin(SSID,PASSWORD);
  }
  if(conexionFallida){
      digitalWrite(LED_BUILTIN, HIGH);   
      delay(250);                      
      digitalWrite(LED_BUILTIN, LOW);    
      delay(250);
  }else{
    Serial.println("Conectando a "+ (String)SSID);
    digitalWrite(LED_BUILTIN, LOW);
    while(true);
  }
}
