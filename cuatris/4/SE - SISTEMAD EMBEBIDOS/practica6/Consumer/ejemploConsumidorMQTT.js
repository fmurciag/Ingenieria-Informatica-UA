// Carga la libreria.
const mqtt = require('mqtt');

// Crea un cliente MQTT.
const client = mqtt.connect('mqtt://oldbox.cloud');

// Al conectar al broker, se suscribe a la topic 'SE/practicaUA2022/murcia'.
client.on('connect', () => {
    client.subscribe('SE/practicaUA2022/murcia');
});

// Al recibir un mensaje, se procesa, en este caso se muestra por pantalla
//   y se manda a otro topico.
client.on('message', (topic, message) => {
    console.log(message.toString());
    client.publish('SE/practicaUA2022/otroTopico', message.toString());
});


