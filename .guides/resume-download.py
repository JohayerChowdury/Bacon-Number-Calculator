#!/usr/bin/env python3

import os
import random
import requests
import json
# import grade submit function
import sys

import csv
# from shutil import copyfile
import subprocess

TEMPLATE_FILE = '.guides/values.txt'
JSON_FILE = 'my.json'

ADJ_BASE_URL = 'https://code.markhancock.ca/msci240/student-data/'
DEST_ADJ_FILE = 'data/adj.txt'

ADJ_FILE_FIELD = 'ADJ_FILE'
# PUBLIC_FIELDS = ['WATIAM', 'ADJ_FILE', 'ACTOR_NAME', 'ACTOR_ID']

def download(url, dest):
    args = ['wget', '-cO', '-', url]
    with open(dest, 'w') as outfile:
        return subprocess.run(args, stdout=outfile, stderr=subprocess.PIPE, encoding='utf-8')

def main():
    if not os.path.isfile(JSON_FILE):
        print("You must setup the project before you can show your values.")
        exit(1)
        
    with open(TEMPLATE_FILE, 'r') as file:
        template = file.read()

    with open(JSON_FILE, 'r') as file:
        info = json.load(file)
        for field in info.keys():
            template = template.replace(field, info[field])

        # download the right adj.txt
        proc = download(ADJ_BASE_URL + info[ADJ_FILE_FIELD], DEST_ADJ_FILE)
        if proc.returncode > 0:
            template += "\n\nYour data file failed to download (please contact the instructor)."
#                     template += "\n" + proc.stderr
        else:
            template += "\n\nData downloaded successfully."
#                 template += "\n" + proc.stdout + "\n\n" + proc.stderr
        # copy the right adj.txt
#                 copyfile(SRC_ADJ_DIR + row[ADJ_FILE_FIELD], DEST_ADJ_FILE)

        print(template)

    exit(0)
main()