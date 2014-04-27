#!/bin/bash

for f in sandbox/*.jar;
do
    echo "Signing: $f"
    jarsigner -keystore ld29.ks -storepass ld29pass $f ld29key
done