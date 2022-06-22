#include <Adafruit_GFX.h>        
#include <Adafruit_SSD1306.h>
#include <Wire.h>
#include "MAX30105.h"
#include "heartRate.h"
#include <NTPClient.h>
#include <SPI.h>
#include <WiFiUdp.h>
#include <WiFiNINA.h>
#include <ArduinoHttpClient.h>
#include <ArduinoJson.h>
#include <ArduinoECCX08.h>

MAX30105 particleSensor;

const byte RATE_SIZE = 4; // Increase this for more averaging. 4 is good.
byte rates[RATE_SIZE]; // Array of heart rates
byte rateSpot = 0;
long lastBeat = 0; // Time at which the last beat occurred
float beatsPerMinute;
int beatAvg;

#define SCREEN_WIDTH 128 // OLED display width, in pixels
#define SCREEN_HEIGHT 32 // OLED display height, in pixels
#define OLED_RESET    -1 // Reset pin # (or -1 if sharing Arduino reset pin)

// Declaring the display name (display)
Adafruit_SSD1306 display(SCREEN_WIDTH, SCREEN_HEIGHT, &Wire, OLED_RESET);

// little_heart_bmp and big_heart_bmp are two bmp pictures that display on the OLED if called
static const unsigned char PROGMEM little_heart_logo[] =
{ 0x03, 0xC0, 0xF0, 0x06, 0x71, 0x8C, 0x0C, 0x1B, 0x06, 0x18, 0x0E, 0x02, 0x10, 0x0C, 0x03, 0x10,              
0x04, 0x01, 0x10, 0x04, 0x01, 0x10, 0x40, 0x01, 0x10, 0x40, 0x01, 0x10, 0xC0, 0x03, 0x08, 0x88,
0x02, 0x08, 0xB8, 0x04, 0xFF, 0x37, 0x08, 0x01, 0x30, 0x18, 0x01, 0x90, 0x30, 0x00, 0xC0, 0x60,
0x00, 0x60, 0xC0, 0x00, 0x31, 0x80, 0x00, 0x1B, 0x00, 0x00, 0x0E, 0x00, 0x00, 0x04, 0x00,  };

static const unsigned char PROGMEM big_heart_logo[] =
{ 0x01, 0xF0, 0x0F, 0x80, 0x06, 0x1C, 0x38, 0x60, 0x18, 0x06, 0x60, 0x18, 0x10, 0x01, 0x80, 0x08,
0x20, 0x01, 0x80, 0x04, 0x40, 0x00, 0x00, 0x02, 0x40, 0x00, 0x00, 0x02, 0xC0, 0x00, 0x08, 0x03,
0x80, 0x00, 0x08, 0x01, 0x80, 0x00, 0x18, 0x01, 0x80, 0x00, 0x1C, 0x01, 0x80, 0x00, 0x14, 0x00,
0x80, 0x00, 0x14, 0x00, 0x80, 0x00, 0x14, 0x00, 0x40, 0x10, 0x12, 0x00, 0x40, 0x10, 0x12, 0x00,
0x7E, 0x1F, 0x23, 0xFE, 0x03, 0x31, 0xA0, 0x04, 0x01, 0xA0, 0xA0, 0x0C, 0x00, 0xA0, 0xA0, 0x08,
0x00, 0x60, 0xE0, 0x10, 0x00, 0x20, 0x60, 0x20, 0x06, 0x00, 0x40, 0x60, 0x03, 0x00, 0x40, 0xC0,
0x01, 0x80, 0x01, 0x80, 0x00, 0xC0, 0x03, 0x00, 0x00, 0x60, 0x06, 0x00, 0x00, 0x30, 0x0C, 0x00,
0x00, 0x08, 0x10, 0x00, 0x00, 0x06, 0x60, 0x00, 0x00, 0x03, 0xC0, 0x00, 0x00, 0x01, 0x80, 0x00  };

// Wifi ssid and password
const char* ssid       = "nano33iot";
const char* password   = "12345678";

// Define NTP Client to get time
WiFiUDP ntpUDP;
NTPClient timeClient(ntpUDP, "pool.ntp.org", 7200);

// Firebase
#define DEVICE_ID "javi-fran-marcos-oscar"
#define PROJECT_ID "arduino-iot-se-default-rtdb"

void setup() {

  WiFi.begin(ssid, password);

  while ( WiFi.status() != WL_CONNECTED ) {
    delay ( 500 );
    Serial.print ( "." );
  }

  timeClient.begin();
  
  display.begin(SSD1306_SWITCHCAPVCC, 0x3C); // Start the OLED display
  display.display();
  delay(3000);
  
  // Initialize sensor
  particleSensor.begin(Wire, I2C_SPEED_FAST); // Use default I2C port, 400kHz speed
  particleSensor.setup(); // Configure sensor with default settings
  particleSensor.setPulseAmplitudeRed(0x0A); // Turn Red LED to low to indicate sensor is running
}

bool firebaseDatabasePut(String path, DynamicJsonDocument jsonDoc, String idToken = "") {
  String jsonStr;
  serializeJson(jsonDoc, jsonStr);
  Serial.println("Saving to RTDB: " + path + " = " + jsonStr);
  
  String host = String(PROJECT_ID) + ".europe-west1.firebasedatabase.app";
  WiFiSSLClient wifiClient;
  HttpClient httpClient = HttpClient(wifiClient, host, 443);
  
  String url = "/" + path + ".json";
  if (idToken != "") {
    url = url + "?auth=" + idToken;
  }
  httpClient.put(url, "application/json", jsonStr);
  int statusCode = httpClient.responseStatusCode();
  String response = httpClient.responseBody();
  
  if (statusCode != 200) {
    Serial.println(String(statusCode) + " " + response);
    return false;
  }
  Serial.println("Saved to RTDB.");
  return true;
}

/**
 * Converts a String into a JsonDocument of correct size.
 * In case of error, the returned `jsonDoc.isNull()` will be true.
 */
DynamicJsonDocument toJsonDocument(String str, int size = 0) {
  if (size == 0) {
    size = JSON_OBJECT_SIZE(1) + str.length();
  }
  DynamicJsonDocument jsonDoc(str.length() * 2);
  DeserializationError error = deserializeJson(jsonDoc, str);
  if (error) {
    Serial.println("JSON " + String(error.c_str()) + ": " + str);
    jsonDoc.clear();
  }
  return jsonDoc;
}

void loop() {
  // Reading the IR value it will permit us to know if there's a finger on the sensor or not and also detecting a heartbeat
  long irValue = particleSensor.getIR();

  // If a finger is detected
  if(irValue >= 7000) {                                           
    fingerDetected();

    //If a heart beat is detected
    if (checkForBeat(irValue) == true) {
      showHeartBeatValue();
      
      timeClient.update(); 
      Serial.println(timeClient.getFormattedTime());
      if(beatAvg != 0) {
        String msg = "{heartRate:" + (String)beatAvg + ", name:'javi-fran-marcos-oscar', time:'" + (String)timeClient.getFormattedTime() + "'}";
        DynamicJsonDocument jsonMsg = toJsonDocument(msg);
        firebaseDatabasePut(DEVICE_ID, jsonMsg);
      }
    }
  }

  // If no finger is detected it inform the user and put the average BPM to 0 or it will be stored for the next measure
  else {       
     noFingerDetected();
  }
}

void fingerDetected() {
  display.clearDisplay();
  display.drawBitmap(5, 5, little_heart_logo, 24, 21, WHITE);
  
  display.setTextSize(2);
  display.setTextColor(WHITE); 
  display.setCursor(50,0);                
  display.println("BPM");           
  display.setCursor(50,18);                
  display.println(beatAvg); 
  display.display();
  Serial.print("AVG BPM = ");
  Serial.println(beatAvg);
}

void noFingerDetected() {
  beatAvg=0;
  display.clearDisplay();
  display.setTextSize(1);                    
  display.setTextColor(WHITE);             
  display.setCursor(30,5);                
  display.println("Please Place "); 
  display.setCursor(30,15);
  display.println("your finger ");  
  display.display();
}

void showHeartBeatValue() {
  display.clearDisplay();
  display.drawBitmap(0, 0, big_heart_logo, 32, 32, WHITE);
  
  display.setTextSize(2);
  display.setTextColor(WHITE);             
  display.setCursor(50,0);                
  display.println("BPM");             
  display.setCursor(50,18);                
  display.println(beatAvg); 
  display.display();
  delay(100);

  long delta = millis() - lastBeat; // Measure duration between two beats
  lastBeat = millis();
  
  beatsPerMinute = 60 / (delta / 1000.0); // Calculating the BPM

  // To calculate the average we store some values (4) then do some math to calculate the average
  if (beatsPerMinute<255 && beatsPerMinute>20) {
    rates[rateSpot++] = (byte)beatsPerMinute; // Store this reading in the array
    rateSpot %= RATE_SIZE; // Wrap variable
  
    // Take average of readings
    beatAvg = 0;
    
    for (byte i=0; i<RATE_SIZE; i++) {
      beatAvg += rates[i];
    }
      
    beatAvg /= RATE_SIZE;
  }
}
