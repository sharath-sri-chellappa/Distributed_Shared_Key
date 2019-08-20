# server.py 
import socket
from random import randint
import pickle
# import Crypto
# from Crypto.PublicKey import RSA
# from Crypto import Random
# import ast
# create a socket object
import json
publkey = "001100010011000100110000001100110111"
serversocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM) 


# get local machine name
#host = socket.gethostname()                           
port = 9999                                           




# bind to the port
serversocket.bind((' ', port))                                  


# queue up to 5 requests
serversocket.listen(5)
def sxor(s,t):    
    # convert strings to a list of character pair tuples
    # go through each tuple, converting them to ASCII code (ord)
    # perform exclusive or on the ASCII code
    # then convert the result back to ASCII (chr)
    # merge the resulting array of characters as a string
    return "".join(chr(ord(a)^ord(b)) for a,b in zip(s,t))

while True:
    # establish a connection
    clientsocket,addr = serversocket.accept()      
    print("Got a connection from %s" % str(addr))
    # privatekey = clientsocket.recv(1024)
    firstpacket = clientsocket.recv(1024)
    secondpacket = clientsocket.recv(1024)
    passw1 = clientsocket.recv(1024)
    rstring = int(firstpacket.decode('ascii')) ^ int(publkey,2)
    print("Packet 1 : %s" %firstpacket.decode('ascii'))
    # rstring = sxor(firstpacket.decode('ascii'),publkey) 
    print("Packet 1 Decoded: %s" %rstring)
    print("Packet 1 : %s" %secondpacket.decode('ascii'))
    # print("The key from Client 1 is %s" %clientprivatekey['modulus'])
    # print("The Piece from Client 1 is %s" % passw1.decode('ascii'))
    clientsocket.close()




