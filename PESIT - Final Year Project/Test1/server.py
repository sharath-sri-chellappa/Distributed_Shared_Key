# server.py 
import socket
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
    clientsocket2,addr2 = serversocket.accept()
    print("Got a connection from %s" % str(addr2))
    print("Make sure that the password that you are about to enter does not exceed 9 digits")
    password = input('Enter the password to be stored ')
    password_n = int(password)
    chosen_prime = 5915587277
    random_r = randint(0,chosen_prime)
    pass1_n = (password_n+random_r)%chosen_prime
    pass1 = str(pass1_n)
    dict1 = {'loginid':pass1}
    resp1 = pickle.dumps(dict1)
    pass2_n = (password_n+2*random_r)%chosen_prime
    pass2 = str(pass2_n)
    dict2 = {'loginid':pass2 }
    resp2 = pickle.dumps(dict2)
    clientsocket.send(resp1)
    clientsocket2.send(resp2)
    query = input('Enter the string to query ')
    clientsocket.send(query.encode('ascii'))
    clientsocket2.send(query.encode('ascii'))
    passw1 = clientsocket.recv(1024)
    print("The Piece from Client 1 is %s" %passw1.decode('ascii'))
    passw2 = clientsocket2.recv(1024)
    print("The Piece from Client 2 is %s" %passw2.decode('ascii'))
    clientsocket.close()
    clientsocket2.close()
