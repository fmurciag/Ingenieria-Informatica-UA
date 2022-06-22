#include <WiFiNINA.h>

#define SSID        "nano33iot"
#define PASSWORD    "12345678"

int intentos = 0; // numero de intentos
bool estado = false; // controla el estado de conexión
byte mac[6];

void Wifi_ON() {
  Serial.println("Conectándose al WIFI...");
  WiFi.begin(SSID, PASSWORD);
}

void blink(int n) { // funcion de parpadeo
  digitalWrite(LED_BUILTIN, HIGH); // led on
  delay(n);
  digitalWrite(LED_BUILTIN, LOW); // led of
}

void setup() {
  // inicializa el pin digital LED_BUILTIN como una salida.
  pinMode(LED_BUILTIN, OUTPUT);
  Serial.begin(9600); // inicia la comunicación en serie
  digitalWrite(LED_BUILTIN, HIGH); // led on - inicio
  Wifi_ON();
}

void printMacAddress(byte mac[]) {
  for (int i = 5; i >= 0; i--) {
    if (mac[i] < 16) Serial.print("0");
    Serial.print(mac[i], HEX);
    if (i > 0) Serial.print(":");
  }
  Serial.println();
}

void loop() {

  // si el arduino no esta conectado al wifi, el led parpadea e intenta conectarse
  if (WiFi.status() != WL_CONNECTED) {
      intentos += 1;
      blink(500); // led blink - conexion incorrecta.
      
      if(estado) {
        Serial.println("Conexión perdida " + (String) SSID + "!! Intentando conectarse nuevamente...");
        estado = false;
        delay(500); // una vez pasado este retardo intenta conectarse de nuevo
    }
      Wifi_ON(); // intentar conectarse nuevamente
      
      if (intentos == 5) { // si tras 5 intentos no se conecta, mostrar mensaje de error.
        Serial.println("Error al intentar conectarse al WIFI " + (String) SSID + " tras 5 intentos");
        estado = false;
        intentos = 0;
      }
  } else {
    if(!estado) {
      intentos = 0;
      estado = true;
      digitalWrite(LED_BUILTIN, LOW); // led off - conexion correcta
      Serial.println("\nConexión establecida con éxito!!");
      WiFi.macAddress(mac);
      Serial.print("MAC: ");
      printMacAddress(mac);
      Serial.println();
    }
  }
}
