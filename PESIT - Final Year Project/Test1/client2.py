# client.py  
import socket
import ssl
import pickle
import csv
# create a socket object
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM) 
# get local machine name
host = socket.gethostname()                           

port = 9999

# connection to hostname on the port.
s.connect((host, port))                               
option = s.recv(1024)
if int(option.decode('ascii'))==2:
    # Receive no more than 1024 bytes
    passw = s.recv(1024)                                     
    password = pickle.loads(passw)
    for name, pas in password.items():
        print (name,pas)
    fieldnames = ['Login_Id','Password_Piece']
    with open("D:\Final Year Project\Test1\client2.csv", "w", newline='') as fp:
        writer = csv.DictWriter(fp,fieldnames=fieldnames)
        writer.writeheader()    
        writer.writerow({'Login_Id': name, 'Password_Piece': pas})
    s.close()
elif int(option.decode('ascii'))==1:
    quer = s.recv(1024)
    query = quer.decode('ascii')
    print ('Client 2:The query is %s' %quer.decode('ascii'))
    #for name, pas in password.items():
    #    if name == query:
    #        s.send(pas.encode('ascii'))
    #    else:
    #        print('Client 2:Not existing for the corresponding query')         
    n=0;i=0;
    with open('D:\Final Year Project\Test1\client2.csv', 'rt') as f:
        reader = csv.reader(f, delimiter=',') # good point by @paco
        for row in reader:
            for field in row:
                if query == row[0]:
                     #if the username shall be on column 3 (-> index 2)
                    s.send(str(row[1]).encode('ascii'))
                    i=1;
                else:
                    n=n+1
                #else:
                #    print('Client 1:Not existing for the corresponding query')
        if i == 0:
            return_code = "-1"
            print('Client 1:Not existing for the corresponding query')
            s.send(return_code.encode('ascii'))
    s.close()

