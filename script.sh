#!/bin/bash


 VAR=$(sudo netstat -tnlp | grep 8080)

 IFS=' ' read -ra vStr <<< "$VAR"

 #PID/java
 VAR2=${vStr[6]}

 #extract PID
 IFS='/' read -ra vStr <<< "$VAR2"

 PID=${vStr[0]}

 # Date time for log
 DATE=$(date '+%m%d%y-%H:%M:%S')

while true
 do
   echo "loop start"
   echo "PID VALUE  : " $PID


        if [ -z "$PID" ]; then
                echo "it's not working"
                echo "Start the program"
                echo "Start the server"

                echo $(nohup java -jar /home/ubuntu/burgerput/cicd/deploy/burgerputProject-0.0.2-SNAPSHOT.jar > \
/home/ubuntu/burgerput/log/$DATE.log 2>&1 &)

                break

        else
                echo "It's working kill the process : $PID"

                echo $(kill -9 ${PID})

                sleep 2

                # Check the result
                VAR=$(sudo netstat -tnlp | grep 8080)
                IFS=' ' read -ra vStr <<< "$VAR"

                #PID/java
                VAR2=${vStr[6]}

                #extract PID
                IFS='/' read -ra vStr <<< "$VAR2"

                PID=${vStr[0]}
                echo "Research The PID :"$PID

        fi
 done


