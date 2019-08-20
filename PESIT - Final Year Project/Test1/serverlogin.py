# server.py 
import socket
import csv
import ssl
from random import randint
import time
import pickle
# create a socket object
serversocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM) 

# get local machine name
#host = socket.gethostname()                           
port = 9999                                           

# bind to the port
serversocket.bind((' ', port))                                  


# queue up to 5 requests
serversocket.listen(5)                                           


while True:
    # establish a connection
    clientsocket,addr = serversocket.accept()         
    print("Got a connection from %s" % str(addr))
    # privatekey = clientsocket.recv(1024)
    option = clientsocket.recv(1024)
    print("Option is %s" %option.decode('ascii'));
    if int(option.decode('ascii'))==2:  
        username = clientsocket.recv(1024)
        print (username.decode('ascii'))
        accno = clientsocket.recv(1024)
        print (accno.decode('ascii'))
        macadd = clientsocket.recv(1024)
        clientsocket.close()
        # serversocket.settimeout(60)
        clientsocket3,addr = serversocket.accept()
        r = '1234'
        clientsocket3.send(r.encode())
        
        clientsocket3.close()
        # print (macadd.decode('ascii'))
        print("It is reaching here 0")
        user = username.decode('ascii')
        acc = accno.decode('ascii')
        mac = macadd.decode('ascii')
        # data = [[user],[acc],[mac]]
        fieldnames = ['Username','Account_No','MAC_Address']
        with open("D:/Final Year Project/Test1/test.csv", "w", newline='') as fp:
            writer = csv.DictWriter(fp,fieldnames=fieldnames)
            writer.writeheader()    
            writer.writerow({'Username': user, 'Account_No': acc, 'MAC_Address': mac})
        # with open('test.csv', 'w', newline='') as fp:
        #     a = csv.writer(fp, delimiter=',')
        #     a.writerow(data)
        # time.sleep(10)
        clientsocket,addr = serversocket.accept()
        print("Got a connection from %s" % str(addr))
        print("It is reaching here 1")
        passwo =  clientsocket.recv(1024)
        print("It is reaching here 2")
        print(passwo.decode('ascii'))
        clientsocket.close()
        
