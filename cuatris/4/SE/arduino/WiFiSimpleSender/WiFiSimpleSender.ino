void setup() {
  pinMode(LED_BUILTIN, OUTPUT);
  digitalWrite(LED_BUILTIN, HIGH); 
}
void loop() {
  for(int i=0;i<=5;i++){
    for(int j=0;j<i;j++){
      digitalWrite(LED_BUILTIN, HIGH);   
      delay(200);                      
      digitalWrite(LED_BUILTIN, LOW);    
      delay(200);
    }
    delay(500);
  }  
}