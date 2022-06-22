const mqtt = require('mqtt');
const trilateration = require('trilateration');
const client = mqtt.connect('mqtt://oldbox.cloud');

// Measures
let distance = [];
let intensity = [];

distance.push(0);
distance.push(25);
distance.push(50);
distance.push(75);
distance.push(100);
distance.push(125);
distance.push(150);
distance.push(175);
distance.push(200);
distance.push(225);
distance.push(250);

intensity.push(-43);
intensity.push(-52);
intensity.push(-74);
intensity.push(-77);
intensity.push(-83);
intensity.push(-85);
intensity.push(-87);
intensity.push(-89);
intensity.push(-93);
intensity.push(-97);
intensity.push(-99);

// Adding three beacons for trilateration
trilateration.addBeacon(0, trilateration.vector(2, 4));
trilateration.addBeacon(1, trilateration.vector(5.5, 13));
trilateration.addBeacon(2, trilateration.vector(11.5, 2));

// Adding three beacons
trilateration.addBeacon(0, trilateration.vector(1, 0));
trilateration.addBeacon(1, trilateration.vector(0, 2));
trilateration.addBeacon(2, trilateration.vector(2, 2));

let arduinos = [[], [], []];

client.on('connect', () => {
    client.subscribe('SE/practicaUA2022/murcia');
});

// El mensaje recibido serla del formato nombre:valor
client.on('message', (topic, message) => {
    let msg = message.toString();
    let [arduino, inte] = msg.split(':');
    console.log(msg);

    switch (arduino) {
        case 'fran': if (arduinos[0].length <= 31) arduinos[0].push(inte); break;
        case 'javi': if (arduinos[1].length <= 31) arduinos[1].push(inte); break;
        case 'alex': if (arduinos[2].length <= 31) arduinos[2].push(inte); break;
    }

<<<<<<< HEAD
    if (arduinos.every(arduino => arduino.length == 1)) {
=======
    if (arduinos.every(arduino => arduino.length == 3)) {
        console.log("Sincronizando barrido de arduinos, calculando posición...");
>>>>>>> af27043cbd9bc38c7e5ad50f714cc5387890bbb1
        let a = obtenDistancia(median(arduinos[0]));
        let b = obtenDistancia(median(arduinos[1]));
        let c = obtenDistancia(median(arduinos[2]));

        let pos = trilaterationProcess(a, b, c);
        let arduinos = [[], [], []];

<<<<<<< HEAD
        // client.publish('SE/practicaUA2022/arduinocloud', `${pos.x},${pos.y}`);

        console.log(`${pos.x},${pos.y}`);
=======
        client.publish('SE/practicaUA2022/arduinocloud', `${pos.x},${pos.y}`);
        console.log(`Coords: ${pos.x},${pos.y}`);
>>>>>>> af27043cbd9bc38c7e5ad50f714cc5387890bbb1
    }
});

// function de trilateracion en dos dimensiones
function trilaterationProcess(a, b, c) {

    // Setting the beacons distances
    trilateration.setDistance(0, a);
    trilateration.setDistance(1, b);
    trilateration.setDistance(2, c);

    // Start Calculation
    return trilateration.calculatePosition();

}

// obten la mediana de un array de valores
function median(values) {
    values.sort((a, b) => a - b);
    let half = Math.floor(values.length / 2);
    if (values.length % 2)
        return values[half];
    else
        return (values[half - 1] + values[half]) / 2.0;
}


// Obtiene la distancia basada en la intensitynsidad
function obtenDistancia(ints) {
    if (ints >= -43) return 0;
    let pos = intensity.findIndex((element) => ints >= element);
    // console.log(pos, distance[pos], distance[pos - 1], intensity[pos], intensity[pos - 1]);
    return distance[pos - 1] + (ints - intensity[pos - 1]) * ((distance[pos] - distance[pos - 1]) / (intensity[pos] - intensity[pos - 1]));
}