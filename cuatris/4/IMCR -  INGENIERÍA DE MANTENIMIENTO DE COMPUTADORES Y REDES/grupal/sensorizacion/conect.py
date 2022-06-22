from ipaddress import ip_address
import socket
import sys
import json
import time
 
# Set up a TCP/IP server
tcp_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
 
server_ip = input("Enter IP address of server: ")
server_address = (server_ip, 8080)
tcp_socket.bind(server_address)
 
# Listen on port 8080 
tcp_socket.listen(1)
 
while True:
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
 
            time.sleep(0.5)
            data = b""
            if len(dataSplit) > 1:
                data += dataSplit[1]
            #if not data:
            #    break
 
    finally:
        connection.close()