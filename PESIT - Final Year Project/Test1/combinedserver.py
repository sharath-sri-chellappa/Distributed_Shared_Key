# server.py 
import socket
from random import randint
import pickle
from bitarray import bitarray
import hashlib
import csv
import time
import pyotp
import pickle
# create a socket object
import json
publkey = "001100010011000100110000001100110111"
serversocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM) 
serversocket2 = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
serversocket3 = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# get local machine name
# host = socket.gethostname()                           
port = 9999                                           
# port2 = 9090
# port3 = 4040
# bind to the port
serversocket.bind((' ', port))
# serversocket2.bind((' ', port2))
# serversocket3.bind((' ', port3))

# queue up to 5 requests
serversocket.listen(5)
# serversocket2.listen(5)
# serversocket3.listen(5)

while True:
    # establish a connection
    clientsocket3,addr = serversocket.accept()      
    print("Got a connection from %s" % str(addr))
    # privatekey = clientsocket.recv(1024)
    option = clientsocket3.recv(1024)
    print("Option is %s" %option.decode('ascii'));
    if int(option.decode('ascii'))==1:
        firstpacket = clientsocket3.recv(1024)
        print("Packet 1 : %s" %firstpacket.decode('ascii'))
        secondpacket = clientsocket3.recv(1024)
        # passw1 = clientsocket3.recv(1024)
        rstring = int(firstpacket.decode('ascii')) ^ int(publkey,2)
        password = str(rstring)
        print("Packet 1 Decoded: %s" %password)
        
       
        clientsocket3.close()
        clientsocket,addr = serversocket.accept()      
        print("Got a connection from %s" % str(addr))
        clientsocket2,addr2 = serversocket.accept()
        print("Got a connection from %s" % str(addr2))
        # print("Make sure that the password that you are about to enter does not exceed 9 digits")
        # password = input('Enter the password to be stored ')
        OTP = int(password[0:4])
        login_id = password[4:9]
        password_n = int(password[9:])
        #password_n = int(''.join(str(ord(each_char)) for each_char in password_char))
        #password_n = int(password)
        chosen_prime = 5915587277
        hash_object = hashlib.md5(login_id.encode())
        login = hash_object.hexdigest()    
        # chosen_prime = 48112959837082048697
        random_r = randint(0,chosen_prime)
        pass1_n = (password_n+random_r)%chosen_prime
        pass1 = str(pass1_n)
        dict1 = {login:pass1}
        resp1 = pickle.dumps(dict1)
        pass2_n = (password_n+2*random_r)%chosen_prime
        pass2 = str(pass2_n)
        dict2 = {login:pass2 }
        resp2 = pickle.dumps(dict2)
        clientsocket.send(resp1)
        clientsocket2.send(resp2)
        print("Packet 2 : %s" %secondpacket.decode('ascii'))
        retreive_query = str(secondpacket.decode('ascii'))
        #print(retreive_query)
        OTP_retreive = int(retreive_query[0:4])
        login_id_retreive = retreive_query[4:9]
        password_char_retreive = retreive_query[9:41]
        #print(password_char_retreive)
        hash_object_retreive = hashlib.md5(login_id_retreive.encode())
        login_query = hash_object_retreive.hexdigest()
        clientsocket.send(login_query.encode('ascii'))
        clientsocket2.send(login_query.encode('ascii'))
        passw1 = clientsocket.recv(1024)
        print("The Piece from Client 1 is %s" %passw1.decode('ascii'))
        retreived_p1 = int(passw1.decode('ascii'))
        passw2 = clientsocket2.recv(1024)
        StringUtils.substring(passw2.decode('ascii'), 0, 10)
        print("The Piece from Client 2 is %s" %passw2.decode('ascii'))
        retreived_p2 = int(passw2.decode('ascii'))
        retreived_pass = (2*retreived_p1-retreived_p2)%chosen_prime
        print("The retreived password is %s" %retreived_pass)
        packet = retreived_pass*10000+OTP
        #print(packet)
        packet_str = str(packet)
        hash_object = hashlib.md5(packet_str.encode())
        regenerated_packet = hash_object.hexdigest()
        print("hashed pass with OTP is %s"%str(regenerated_packet))
        password_ascii_retreive = ''.join(str(ord(c)) for c in password_char_retreive)
        regenerated_ascii_packet = ''.join(str(ord(c)) for c in regenerated_packet)
        #print(password_ascii_retreive)
        #print(regenerated_ascii_packet)
        #print(int(password_ascii_retreive) - int(regenerated_ascii_packet))
        if ((int(password_ascii_retreive) - int(regenerated_ascii_packet))==0) :
            print("access granted");    
        else :
            print("password entered is wrong");
        clientsocket.close()
        clientsocket2.close()
    elif int(option.decode('ascii'))==2:  
        username = clientsocket3.recv(1024)
        print (username.decode('ascii'))
        accno = clientsocket3.recv(1024)
        print (accno.decode('ascii'))
        macadd = clientsocket3.recv(1024)
        clientsocket3.close()
        # serversocket.settimeout(60)
        clientsocket3,addr = serversocket.accept()
        totp = pyotp.TOTP('base32secret3232')
        r = totp.now()
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
        time.sleep(20)
        clientsocket5,addr = serversocket.accept()
        print("Got a connection from %s" % str(addr))
        print("\n");
        passwo =  clientsocket5.recv(1024)
        print("It is reaching here 2\n")
        print(passwo.decode('ascii'))
        print("\n")
        clientsocket5.close()
    else :
        print("Option is not present and is wrong");
