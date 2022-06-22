// Francisco Javier Pérez Martínez

#include <SPI.h>
#include <Wire.h>
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>
 
// Definir constantes
#define ANCHO_PANTALLA 128 // ancho pantalla OLED
#define ALTO_PANTALLA 64 // alto pantalla OLED
 
// Objeto de la clase Adafruit_SSD1306
Adafruit_SSD1306 display(ANCHO_PANTALLA, ALTO_PANTALLA, &Wire, -1);
 
void setup() {
  
  Serial.begin(9600);
  delay(100);
  Serial.println("Iniciando pantalla OLED");
 
  // Iniciar pantalla OLED en la dirección 0x3C
  if (!display.begin(SSD1306_SWITCHCAPVCC, 0x3C)) {
    Serial.println("No se encuentra la pantalla OLED");
    while (true);
  }

  int num = 60;

  do {
      setFeatures(); // asignar tamaño, color y posición del texto.
      Serial.println(num); // mostrar por el monitor serie #debug
      display.println(num); // muestra en pantalla el numero introducido y lo decrementa en 1.
      num--;
      delay(1000); // delay de 1s por cada decremento.
      display.display(); // Enviar a pantalla
      display.clearDisplay(); // limpiamos el numero anterior.
  
  } while (num >= 0);
}

void setFeatures() {
  // Tamaño del texto
  display.setTextSize(4);
  // Color del texto
  display.setTextColor(SSD1306_WHITE);
  // Posición del texto
  display.setCursor(10, 32);
}
 
void loop() {}
