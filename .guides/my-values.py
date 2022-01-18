#!/usr/bin/env python3

import os
import json

TEMPLATE_FILE = '.guides/values.txt'
JSON_FILE = 'my.json'

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
        print(template)

    exit(0)

main()