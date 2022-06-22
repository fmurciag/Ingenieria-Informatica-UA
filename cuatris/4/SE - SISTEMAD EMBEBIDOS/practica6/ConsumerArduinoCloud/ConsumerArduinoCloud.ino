#include "thingProperties.h";
#include <ArduinoMqttClient.h>
#if defined(ARDUINO_SAMD_MKRWIFI1010) || defined(ARDUINO_SAMD_NANO_33_IOT) || defined(ARDUINO_AVR_UNO_WIFI_REV2)
#include <WiFiNINA.h>
#elif defined(ARDUINO_SAMD_MKR1000)
#include <WiFi101.h>
#elif defined(ARDUINO_ESP8266_ESP12)
#include <ESP8266WiFi.h>
#endif

const char broker[] = "test.mosquitto.org";
int        port     = 1883;
const char topic[]  = "SE/practicaUA2022/arduinocloud";

WiFiClient wifiClient;
MqttClient mqttClient(wifiClient);

void setup() {
  Serial.begin(9600);
  delay(1500);

  Serial.print("Attempting to connect to WPA SSID: ");
  Serial.println(SSID);
  while (WiFi.begin(SSID, PASS) != WL_CONNECTED) {
    // failed, retry
    Serial.print("Failed connection to WiFi, retrying...\n");
    delay(5000);
  }

  Serial.println("You're connected to the network");
  Serial.println();


  initProperties();
  ArduinoCloud.begin(ArduinoIoTPreferredConnection);
  setDebugMessageLevel(2);
  ArduinoCloud.printDebugInfo();
  Serial.println();


  Serial.print("Attempting to connect to the MQTT broker: ");
  Serial.println(broker);

  if (!mqttClient.connect(broker, port)) {
    Serial.print("MQTT connection failed! Error code = ");
    Serial.println(mqttClient.connectError());

    while (1);
  }

  Serial.println("You're connected to the MQTT broker!");
  Serial.println();

  Serial.print("Subscribing to topic: ");
  Serial.println(topic);
  Serial.println();

  mqttClient.subscribe(topic);
  Serial.print("Waiting for messages on topic: ");
  Serial.println(topic);
  Serial.println();
}

void loop() {
  ArduinoCloud.update();

  if (mqttClient.parseMessage()) {
    bool next = FALSE;
    char character;
    String x_string;
    String y_string;

    // Obtain data per character.
    while (mqttClient.available()) {
      character = (char)mqttClient.read();

      // Message format is x,y
      if (character != ',' && !next) {
        x_string += (char)mqttClient.read();
      } else if (character == ',') {
        x = (float)x_string;
        next = TRUE;
      } else if ( character != ',' && next) {
        y_string += (char)mqttClient.read();
      }
    }
    y = (float)y_string;
  }
}

void onDataChange()  {}
void onEjercicioChange()  {}
