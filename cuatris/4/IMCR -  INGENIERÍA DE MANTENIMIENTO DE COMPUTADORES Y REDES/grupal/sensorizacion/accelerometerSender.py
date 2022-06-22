from ipaddress import ip_address
import socket
import sys
import json
import time
import requests
import math



TOKEN = "BBFF-aiGyMyKStiyhBl1FL7hIcjBSWWq4ye"  # Colocar token aqui
DEVICE_LABEL = "Acelerometro"  # Put your device label here 
VARIABLE_LABEL_1 = "X"  # Put your first variable label here
VARIABLE_LABEL_2 = "Y"  # Put your second variable label here
VARIABLE_LABEL_3 = "G"  # Put your second variable label here



# Set up a TCP/IP server
tcp_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
 
server_ip = "192.168.120.141" # Colocar ip 
server_address = (server_ip, 8080)
tcp_socket.bind(server_address)
 
# Listen on port 8080 
tcp_socket.listen(1)


def build_payload(variable_1, variable_2,variable_3,payload):

    print("Waiting for connection")
    connection, client = tcp_socket.accept()
 
    try:
        print("Connected to client IP: {}".format(client))
         
        # Receive and dprint data 32 bytes at a time, as long as the client is sending something
        data = b""
        while True:
            data += connection.recv(93)
            print("Data", data)
            dataSplit = data.split(b'\n')
            print(dataSplit[0])
            print("Dictionary before parsing: ", dataSplit[0])
            if dataSplit[0] == b'':
                continue

            try: 
                dataJson = json.loads(dataSplit[0])
            except:
                print("NameError Occurred and Handled")
            # printing all elements of dictionary
            print("Dictionary after parsing: ", dataJson)

            print("Timestamp: ", dataJson['accelerometer']['timestamp'])
            print("X: ", dataJson['accelerometer']['value'][0])
            print("Y: ", dataJson['accelerometer']['value'][1])
            print("G: ", dataJson['accelerometer']['value'][2])
            X=dataJson['accelerometer']['value'][0]
            Y=dataJson['accelerometer']['value'][1]
            if X<0:
                x=0
            if Y<0:
                Y=0
            
            payload = {variable_1: X,
               variable_2: Y,
               variable_3: dataJson['accelerometer']['value'][2]}
            estatus=post_request(payload)
 
            #time.sleep(0.5)
            data = b""
            if len(dataSplit) > 1:
                data += dataSplit[1]
            #if not data:
            #    break
            #return payload
 
    finally:
        connection.close()
    


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
        time.sleep(0.01)

    # Processes results
    print(req.status_code, req.json())
    if status >= 400:
        print("[ERROR] Could not send data after 5 attempts, please check \
            your token credentials and internet connection")
        return False

    print("[INFO] request made properly, your device is updated")
    return True


def main():
    payload = {"X": 0,"Y": 0,"G":0}
    print("[INFO] Attemping to send data")
    payload = build_payload(VARIABLE_LABEL_1, VARIABLE_LABEL_2, VARIABLE_LABEL_3,payload)
    print("[INFO] finished")



if __name__ == '__main__':
    while (True):
        main()
        time.sleep(1)

 

 