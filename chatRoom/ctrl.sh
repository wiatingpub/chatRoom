#!/bin/sh
case "$1" in
    build)
        cd ../lib/socket
        sudo mvn install
        cd ../../chatRoom/
        sudo cp ./conf.d/chatRoom.conf /home/ubuntu/proj/nginx-center/conf.d/chatRoom.conf
    ;;
    start)
        nohup sudo docker-compose up > chatRoom.log 2>&1 &
        tail -f chatRoom.log
    ;;
    stop)
        sudo docker-compose down
    ;;
    login)
        docker exec -it $Container /bin/bash
    ;;
    info)
        docker ps -a -f name=$Container
    ;;
    port)
        docker port $Container
    ;;
    *)
        echo "Usage: build|run|init[chmod,conf]|start|stop|remove|login|info|port"
        exit 3
    ;;
esac