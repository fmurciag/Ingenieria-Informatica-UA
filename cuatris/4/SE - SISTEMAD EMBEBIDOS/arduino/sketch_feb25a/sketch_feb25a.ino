#include <WiFiNINA.h>

#define SSID "Redmi"
#define PASSWORD "987654321"

void setup() {
  // initialize digital pin LED_BUILTIN as an output.
  pinMode(LED_BUILTIN, OUTPUT);
  Serial.println("Conectando a "+ (String)SSID+"...");
  WiFi.begin(SSID,PASSWORD);
  int segundos=0;
  while(WiFi.status()!=WL_CONNECTED){
    Serial.println("ERROR: al conectar a " + (String)SSID);
      digitalWrite(LED_BUILTIN, HIGH);   
      delay(500);                      
      digitalWrite(LED_BUILTIN, LOW);    
      delay(500);
    segundos++;
    if(segundos>=5)break;
    Serial.println("Reintentando...");
    WiFi.begin(SSID,PASSWORD);
  }
  if(segundos>=5){
    Serial.println("ERROR: no se ha podido conectar a " + (String)SSID);
    digitalWrite(LED_BUILTIN, LOW);
  }else{
    digitalWrite(LED_BUILTIN, HIGH);
  }
  
}
void loop() {}
