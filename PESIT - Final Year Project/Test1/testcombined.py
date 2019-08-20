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
import split
import join
# create a socket object
import json
publkey = "001100010011000100110000001100110111"
serversocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM) 
# serversocket2 = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
# serversocket3 = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# get local machine name
# host = socket.gethostname()                           
port = 9999                                           
# port2 = 8888
# port3 = 7777
# bind to the port
serversocket.bind((' ', port))
#serversocket2.bind((' ', port2))
#serversocket3.bind((' ', port3))

# queue up to 10 requests
serversocket.listen(10)
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
        secondpacket = clientsocket3.recv(1024)
        clientsocket3.close()
        clientsocket,addr = serversocket.accept()      
        print("Got a connection from %s" % str(addr))
        clientsocket2,addr2 = serversocket.accept()
        print("Got a connection from %s" % str(addr2))
        split.split("Bank_Statement.pdf","Hellp")
        # print("Make sure that the password that you are about to enter does not exceed 9 digits")
        # password = input('Enter the password to be stored ')
        def trial(existing_bit,one_time_key):    
            print("current existing bit")
            print(existing_bit)
            hash_object = hashlib.md5(one_time_key.encode())
            one_time_key_hash = hash_object.hexdigest()
            hash_object = hashlib.md5(one_time_key_hash.encode())
            one_time_key_hash = hash_object.hexdigest()
            print("after hashing the one time key")
            print(one_time_key_hash)
            loc_pass = int(one_time_key_hash,16) ^ existing_bit
            print("loc_pass")
            print(loc_pass)
            existing_bit = int(one_time_key,16) ^ loc_pass
            print("existing_bit")
            #print(existing_bit)
            return (existing_bit);
        #password_n = int(''.join(str(ord(each_char)) for each_char in password_char))
        #password_n = int(password)
        print("Packet 2 : %s" %secondpacket.decode('ascii'))
        retreive_query = str(secondpacket.decode('ascii'))
        #print(retreive_query)
        OTP_retreive = int(retreive_query[0:4])
        login_id_retreive = retreive_query[4:9]
        password_char_retreive = retreive_query[9:41]
        one_time_key = retreive_query[41:73]
        print("one_time_key")
        print(one_time_key)
        #print(int(one_time_key,16))
        with open('D:/Final Year Project/Test1/test.csv', 'rt') as f:
            reader = csv.reader(f, delimiter=',') # good point by @paco
            for row in reader:
                
                content = trial(int(row[1]),one_time_key)
                print (content)
        fieldnames = ['Account_No','Location_Pass']
        with open("D:/Final Year Project/Test1/test.csv", "w", newline='') as fp:
            writer = csv.DictWriter(fp,fieldnames=fieldnames)
            # writer.writeheader()    
            writer.writerow({'Account_No': login_id,'Location_Pass': content})
        hash_object_retreive = hashlib.md5(login_id_retreive.encode())
        login_query = hash_object_retreive.hexdigest()
        clientsocket.send(option)
        clientsocket2.send(option)
        clientsocket.send(login_query.encode('ascii'))
        time.sleep(2)
        clientsocket2.send(login_query.encode('ascii'))
        passw1 = clientsocket.recv(1024)
        passw4 = passw1.decode('ascii')
        print("The Piece from Client 1 is %s" %passw1.decode('ascii'))
        retreived_p1 = int(passw4[:10])
        print("retreived_p1")
        print(retreived_p1)
        time.sleep(2)
        passw2 = clientsocket2.recv(1024)
        passw3 = passw2.decode('ascii')
        # StringUtils.substring(passw2.decode('ascii'), 0, 10)
        print("The Piece from Client 2 is %s" %passw3[:10])
        retreived_p2 = int(passw3[:10])
        print("retreived_p2")
        print(retreived_p2)
        print("chosen prime")
        print(chosen_prime)
        retreived_pass = (2*pass1_n-pass2_n)%chosen_prime 
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
            join.join("Hellp","Bank_Statement2.pdf")
        else :
            print("access granted");
        clientsocket.close()
        clientsocket2.close()
    elif int(option.decode('ascii'))==2:  
        totp = pyotp.TOTP('base32secret3232')
        r = totp.now()
        clientsocket3.send(r.encode())
        print(r)
        clientsocket3.close()
        # print (macadd.decode('ascii'))
        print("It is reaching here 0")
        clientsocket5,addr = serversocket.accept()
        one_time_key = clientsocket5.recv(1024)
        firstpacket = clientsocket5.recv(1024)
        print("Packet 1 : %s" %firstpacket.decode('ascii'))
        # passw1 = clientsocket3.recv(1024)
        rstring = int(firstpacket.decode('ascii')) ^ int(publkey,2)
        password = str(rstring)
        print("Packet 1 Decoded: %s" %password)
        OTP = int(password[0:4])
        login_id = password[4:9]
        password_n = int(password[9:13])
        #one_time_key = password[13:45]
        print(one_time_key);
        print(int(one_time_key,16))
        location_pass = 12
        csv_storage_loaction_pass = int(one_time_key,16)^location_pass
        print("csv_storage_loaction_pass")
        print(csv_storage_loaction_pass)
        fieldnames = ['Login_Id']
        # user = username.decode('ascii')
        # acc = accno.decode('ascii')
        # mac = macadd.decode('ascii')
        # data = [[user],[acc],[mac]]
        fieldnames = ['Account_No','Location_Pass']
        with open("D:/Final Year Project/Test1/test.csv", "w", newline='') as fp:
            writer = csv.DictWriter(fp,fieldnames=fieldnames)
            writer.writerow({'Account_No': login_id, 'Location_Pass': csv_storage_loaction_pass})
        clientsocket,addr = serversocket.accept()      
        print("Got a connection from %s" % str(addr))
        clientsocket2,addr2 = serversocket.accept()
        print("Got a connection from %s" % str(addr2))
        #password_n = int(''.join(str(ord(each_char)) for each_char in password_char))
        #password_n = int(password)
        chosen_prime = 5915587277
        hash_object = hashlib.md5(login_id.encode())
        login = hash_object.hexdigest()    
        # chosen_prime = 48112959837082048697
        random_r = randint(0,chosen_prime)
        print("password entered")
        print(password_n)
        print("random number")
        print(random_r)
        pass1_n = (password_n+random_r)%chosen_prime
        print("piece one being stored")
        print(pass1_n)
        pass1 = str(pass1_n)
        dict1 = {login:pass1}
        resp1 = pickle.dumps(dict1)
        pass2_n = (password_n+2*random_r)%chosen_prime
        print("piece two being stored")
        print(pass2_n)
        pass2 = str(pass2_n)
        dict2 = {login:pass2 }
        resp2 = pickle.dumps(dict2)
        clientsocket.send(option)
        clientsocket2.send(option)
        clientsocket.send(resp1)
        time.sleep(2)
        clientsocket2.send(resp2)
    else :
        print("Option is not present and is wrong");
