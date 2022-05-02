void setup() {
  // put your setup code here, to run once:

}

void loop() {
  char fecha[19];
  DateTime now = RTC.now(); //Obtener fecha y hora actual.

  int dia = now.day();
  int mes = now.month();
  int anio = now.year();
  int hora = now.hour();
  int minuto = now.minute();
  int segundo = now.second();

  sprintf( fecha, "%.2d.%.2d.%.4d %.2d:%.2d:%.2d", dia, mes, anio, hora, minuto, segundo);
}
