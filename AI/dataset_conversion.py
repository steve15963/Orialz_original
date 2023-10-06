import os
from os import path

### constant ###
train_path = "./train/labels/"
train_path_sav = "./train_conv/labels/"

valid_path = "./valid/labels/"
valid_path_sav = "./valid_conv/labels/"

test_path = "./test/labels/"
test_path_sav = "./test_conv/labels/"

label = 0


### conversion ###
# train data
if path.isdir(train_path):
    print("train data processing start")
    if not path.isdir(train_path_sav):
        os.makedirs(train_path_sav)

    file_list = os.listdir(train_path)
    for file_name in file_list:
        print("file: {}".format(file_name))
        file = open(train_path + file_name, 'r')
        new_file = open(train_path_sav + file_name, 'w')

        lines = file.readlines()
        for line in lines:
            str_arr = line.split(' ')
            str_arr[0] = str(label)
            new_file.write(' '.join(str_arr))
        
        file.close()
        new_file.close()
    print("train data processing end")

# valid data
if path.isdir(valid_path):
    print("valid data processing start")
    if not path.isdir(valid_path_sav):
        os.makedirs(valid_path_sav)

    file_list = os.listdir(valid_path)
    for file_name in file_list:
        print("file: {}".format(file_name))
        file = open(valid_path + file_name, 'r')
        new_file = open(valid_path_sav + file_name, 'w')

        lines = file.readlines()
        for line in lines:
            str_arr = line.split(' ')
            str_arr[0] = str(label)
            new_file.write(' '.join(str_arr))
        
        file.close()
        new_file.close()
    print("valid data processing end")

# test data
if path.isdir(test_path):
    print("test data processing start")
    if not path.isdir(test_path_sav):
        os.makedirs(test_path_sav)

    file_list = os.listdir(test_path)
    for file_name in file_list:
        print("file: {}".format(file_name))
        file = open(test_path + file_name, 'r')
        new_file = open(test_path_sav + file_name, 'w')

        lines = file.readlines()
        for line in lines:
            str_arr = line.split(' ')
            str_arr[0] = str(label)
            new_file.write(' '.join(str_arr))
        
        file.close()
        new_file.close()
    print("test data processing end")

print("Done !")
