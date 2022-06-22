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

  // Limpir buffer
  display.clearDisplay();

  // Tamaño del texto
  display.setTextSize(4);
  // Color del texto
  display.setTextColor(SSD1306_WHITE);
  // Posición del texto
  display.setCursor(10, 32);
  
  // Escribir texto
  display.println("Javi");
  
  // Enviar a pantalla
  display.display();
  delay(2000);

  while(true) {
    // Mover texto de izquierda a derecha
    display.startscrollright(0x00, 0x0F);
    delay(5000);
    display.stopscroll();
  }
}

void loop() {}
