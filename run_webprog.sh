#!/bin/bash
scriptdir="$(dirname "$0")"
cd "$scriptdir"

#Build project, generate jar
mvn install

#Start jar
java -jar $scriptdir/target/webprog-0.1.0-SNAPSHOT.jar server config.yml &

#Sleep to finish server load before opening browser
sleep 5s

#Open default browser with localhost on MacOS or Windows
if [[ "$OSTYPE" == "darwin"* ]]; 
    then open "http://localhost:8080"
elif [[ "$OSTYPE" == "msys" ]]; 
    then explorer "http://localhost:8080"
elif [[ "$OSTYPE" == "linux-gnu" ]]; 
    then explorer "http://localhost:8080"
fi
