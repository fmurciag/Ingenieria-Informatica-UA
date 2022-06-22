import time
import requests
import math
import random

TOKEN = "BBFF-aiGyMyKStiyhBl1FL7hIcjBSWWq4ye"  # Put your TOKEN here
DEVICE_LABEL = "sensor_vital"  # Put your device label here 
VARIABLE_LABEL_1 = "frecuencia_cardiaca"  # bpm
VARIABLE_LABEL_2 = "presion_arterial"  # 
VARIABLE_LABEL_3 = "nivel_oxigeno"  # 0 - 100 %
VARIABLE_LABEL_4 = "temp_corporal"

x = 0

def build_payload(variable_1, variable_2,  variable_3, variable_4):
    global x

    # frecuencia cardíaca
    value_1 = math.sin((x / 20)-20) * 6 + 64 + random.uniform(-0.5, 0.5)

    # presión arterial
    value_2 = math.sin((x / 20)-20) * 5 + 130 + random.uniform(-0.5, 0.5)

    # nivel de oxígeno
    value_3 = math.sin(x * 2) * (1 / 25) + 95

    # temperatura corporal
    value_4 = math.sin(x * 2) * (1 / 20) + 36.8
    
    x += 1

    # Asignar valores
    payload = { variable_1: value_1,
                variable_2: value_2,
                variable_3: value_3,
                variable_4: value_4
            }

    return payload


def post_request(payload):
    # Creates the headers for the HTTP requests
    url = "http://industrial.api.ubidots.com"
    url = "{}/api/v1.6/devices/{}".format(url, DEVICE_LABEL)
    headers = {"X-Auth-Token": TOKEN, "Content-Type": "application/json"}

    # Makes the HTTP requests
    status = 400
    attempts = 0
    while status >= 400 and attempts <= 5:
        req = requests.post(url=url, headers=headers, json=payload)
        status = req.status_code
        attempts += 1
        time.sleep(1)

    # Processes results
    print(req.status_code, req.json())
    if status >= 400:
        print("[ERROR] Could not send data after 5 attempts, please check \
            your token credentials and internet connection")
        return False

    print("[INFO] request made properly, your device is updated")
    return True


def main():
    payload = build_payload(
VARIABLE_LABEL_1, VARIABLE_LABEL_2, VARIABLE_LABEL_3, VARIABLE_LABEL_4)

    print("[INFO] Attemping to send data")
    post_request(payload)
    print("[INFO] finished")


if __name__ == '__main__':
    while (True):
        main()
        time.sleep(1)
