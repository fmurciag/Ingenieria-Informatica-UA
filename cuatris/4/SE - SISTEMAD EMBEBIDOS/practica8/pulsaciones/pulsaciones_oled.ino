// Francisco Javier Pérez Martínez

#include <Adafruit_GFX.h>        
#include <Adafruit_SSD1306.h>
#include <Wire.h>
#include "MAX30105.h"
#include "heartRate.h"

MAX30105 particleSensor;

const byte RATE_SIZE = 4; //Increase this for more averaging. 4 is good.
byte rates[RATE_SIZE]; //Array of heart rates
byte rateSpot = 0;
long lastBeat = 0; //Time at which the last beat occurred
float beatsPerMinute;
int beatAvg;

#define ANCHO_PANTALLA 128 // ancho pantalla OLED
#define ALTO_PANTALLA 32 // alto pantalla OLED

// Objeto de la clase Adafruit_SSD1306
Adafruit_SSD1306 display(ANCHO_PANTALLA, ALTO_PANTALLA, &Wire, -1);

// corazon[] corresponde al logo del corazon que se muestra en la pantalla OLED

static const unsigned char PROGMEM corazon[] =
{ 0x03, 0xC0, 0xF0, 0x06, 0x71, 0x8C, 0x0C, 0x1B, 0x06, 0x18, 0x0E, 0x02, 0x10, 0x0C, 0x03, 0x10,
0x04, 0x01, 0x10, 0x04, 0x01, 0x10, 0x40, 0x01, 0x10, 0x40, 0x01, 0x10, 0xC0, 0x03, 0x08, 0x88,
0x02, 0x08, 0xB8, 0x04, 0xFF, 0x37, 0x08, 0x01, 0x30, 0x18, 0x01, 0x90, 0x30, 0x00, 0xC0, 0x60,
0x00, 0x60, 0xC0, 0x00, 0x31, 0x80, 0x00, 0x1B, 0x00, 0x00, 0x0E, 0x00, 0x00, 0x04, 0x00,  };


void setup() {

  Serial.begin(115200);
  Serial.println("Iniciando pantalla OLED");
 
  // Iniciar pantalla OLED en la dirección 0x3C
  if (!display.begin(SSD1306_SWITCHCAPVCC, 0x3C)) {
    Serial.println("No se encuentra la pantalla OLED");
    while (true);
  }
  display.display();

  Serial.println("Iniciando sensor ...");
  delay(100);
  // Iniciar sensor
  if (!particleSensor.begin(Wire, I2C_SPEED_FAST)) //Use default I2C port, 400kHz speed
  {
    Serial.println("MAX30105 was not found. Please check wiring/power. ");
    while (1);
  }
  Serial.println("Place your index finger on the sensor with steady pressure.");

  particleSensor.setup(); //Configure sensor with default settings
  particleSensor.setPulseAmplitudeRed(0x0A); //Turn Red LED to low to indicate sensor is running
  particleSensor.setPulseAmplitudeGreen(0); //Turn off Green LED
}

void loop() {
 long irValue = particleSensor.getIR();
 
  if(irValue > 7000) { // Si se detecta el dedo ...
      display.clearDisplay();
      display.drawBitmap(5, 5, corazon, 24, 21, WHITE); // Dibujar corazon
      display.setTextSize(2);
      display.setTextColor(WHITE); 
      display.setCursor(50,0);                
      display.println("BPM");             
      display.setCursor(50,18);                
      display.println(beatAvg); // Mostramos por pantalla la media de BPM
      display.display();
      
    if (checkForBeat(irValue) == true) { // Si se detecta latido ...
      display.clearDisplay();  
      display.setTextSize(2);                               
      display.setTextColor(WHITE);             
      display.setCursor(50,0);
      display.println("BPM");
      display.setCursor(50,18);
      display.println(beatAvg);
      display.display();                                       
      delay(100);
      
      //We sensed a beat!
      long delta = millis() - lastBeat;
      lastBeat = millis();
  
      beatsPerMinute = 60 / (delta / 1000.0); // Calculando BPM
  
      if (beatsPerMinute < 255 && beatsPerMinute > 20) // Almacena 4 valores y calcula la media
      {
        rates[rateSpot++] = (byte)beatsPerMinute; // Store this reading in the array
        rateSpot %= RATE_SIZE; //Wrap variable
  
        //Take average of readings
        beatAvg = 0;
        for (byte x = 0 ; x < RATE_SIZE ; x++)
          beatAvg += rates[x];
        beatAvg /= RATE_SIZE;
      }
    }
  
    // Monitor serie
    Serial.print("IR=");
    Serial.print(irValue);
    Serial.print(", BPM=");
    Serial.print(beatsPerMinute);
    Serial.print(", Avg BPM=");
    Serial.print(beatAvg);
    Serial.println();

}
  if (irValue < 50000) { // Si no se detecta el dedo, nos aparece un mensaje indicandolo.
     beatAvg=0;
     display.clearDisplay();
     display.setTextSize(1.5);                    
     display.setTextColor(WHITE);             
     display.setCursor(32,15);                
     display.println("No finger? ");  
     display.display();
     // monitor serie
     Serial.print(" No finger?");
     Serial.println();
  }
}
