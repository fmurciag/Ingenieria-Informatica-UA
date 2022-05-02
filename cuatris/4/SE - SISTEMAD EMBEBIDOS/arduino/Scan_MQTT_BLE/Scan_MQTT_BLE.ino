/*
  ArduinoMqttClient - WiFi Simple Sender

  This example connects to a MQTT broker and publishes a message to
  a topic once a second.

  The circuit:
  - Arduino MKR 1000, MKR 1010 or Uno WiFi Rev.2 board

  This example code is in the public domain.
*/
#include <ArduinoBLE.h>
#include <ArduinoMqttClient.h>
#if defined(ARDUINO_SAMD_MKRWIFI1010) || defined(ARDUINO_SAMD_NANO_33_IOT) || defined(ARDUINO_AVR_UNO_WIFI_REV2)
  #include <WiFiNINA.h>
#elif defined(ARDUINO_SAMD_MKR1000)
  #include <WiFi101.h>
#elif defined(ARDUINO_ESP8266_ESP12)
  #include <ESP8266WiFi.h>
#endif

#include "arduino_secrets.h"
///////please enter your sensitive data in the Secret tab/arduino_secrets.h
char ssid[] = SECRET_SSID;        // your network SSID (name)
char pass[] = SECRET_PASS;    // your network password (use for WPA, or use as key for WEP)

// To connect with SSL/TLS:
// 1) Change WiFiClient to WiFiSSLClient.
// 2) Change port value from 1883 to 8883.
// 3) Change broker value to a server with a known SSL/TLS root certificate 
//    flashed in the WiFi module.

WiFiClient wifiClient;
MqttClient mqttClient(wifiClient);

const char broker[] = "test.mosquitto.org";
int        port     = 1883;
const char topic[]  = "arduino/simple";

const long interval = 1000;
unsigned long previousMillis = 0;

void ConectarWifi_Broker(){
  // attempt to connect to Wifi network:
  Serial.print("Attempting to connect to WPA SSID: ");
  Serial.println(ssid);
  while (WiFi.begin(ssid, pass) != WL_CONNECTED) {
    // failed, retry
    Serial.print(".");
    delay(5000);
  }

  Serial.println("You're connected to the network");

  Serial.print("Attempting to connect to the MQTT broker: ");
  Serial.println(broker);

  if (!mqttClient.connect(broker, port)) {
    Serial.print("MQTT connection failed! Error code = ");
    Serial.println(mqttClient.connectError());

    while (1);
  }

  Serial.println("You're connected to the MQTT broker!");
  Serial.println();
}

int Promedio(int arreglo[]) {
  // Vamos a acumular aquí la sumatoria
  int sumatoria = 0;
  for (int x = 0; x < 5; x++) {
    sumatoria += arreglo[x];
  }
  // El promedio es la sumatoria dividida entre el número de elementos
  int promedio = sumatoria / 5;
  return promedio;
}


void setup() {
  //Initialize serial and wait for port to open:
  Serial.begin(9600);

  //ConectarBLE
  if (!BLE.begin()) {
    Serial.println("starting Bluetooth® Low Energy module failed!");

    while (1);
  }
  Serial.println();

}

void loop() {
  int RSSIs[5];
  int rssi=0;
  for(int i=0;i<5;i++){
    BLE.scan();

    BLEDevice peripheral = BLE.available();

    if (peripheral) {
      // discovered a peripheral
      Serial.println("Discovered a peripheral");
      Serial.println("-----------------------");

      // print address
      Serial.print("Address: ");
      Serial.println(peripheral.address());

      // print the local name, if present
      if (peripheral.hasLocalName()) {
        Serial.print("Local Name: ");
        Serial.println(peripheral.localName());
      }
      // print the advertised service UUIDs, if present
      if (peripheral.hasAdvertisedServiceUuid()) {
        Serial.print("Service UUIDs: ");
        for (int i = 0; i < peripheral.advertisedServiceUuidCount(); i++) {
          Serial.print(peripheral.advertisedServiceUuid(i));
          Serial.print(" ");
        }
        Serial.println();
      }

      // print the RSSI
      Serial.print("RSSI: ");
      Serial.println(peripheral.rssi());
      RSSIs[i]=peripheral.rssi();

      Serial.println();
    }
    delay(500);
  }
  rssi=Promedio(RSSIs);

  BLE.end();

  ConectarWifi_Broker();
  for(int i=0;i<3;i++){  
    // call poll() regularly to allow the library to send MQTT keep alives which
    // avoids being disconnected by the broker
    mqttClient.poll();

    // avoid having delays in loop, we'll use the strategy from BlinkWithoutDelay
    // see: File -> Examples -> 02.Digital -> BlinkWithoutDelay for more info

    Serial.print("Sending message to topic: ");
    Serial.println(topic);
    Serial.print("rssi ");
    Serial.println(rssi);

    // send message, the Print interface can be used to set the message contents
    mqttClient.beginMessage(topic);
 
    mqttClient.print(rssi);
    mqttClient.endMessage();

    Serial.println();
    delay(500);
  }
  WiFi.end();
  
  
}
