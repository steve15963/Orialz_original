from hdfs import InsecureClient
import os
client = InsecureClient("http://cluster1:9870")

with client.read('/user/hadoop/1/964664038d62dd0308ddb33b0793f0b6cb25fd58a384346053f8a526538b7028/output/frame00000001.png') as wr:
    with open('test.png','wb') as file:
        #print(wr.read())
        file.write(wr.read())
