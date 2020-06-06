#!/bin/sh
case "$1" in
    start)
        nohup sudo docker-compose up &
    ;;
    stop)
        docker stop $Container
    ;;
    remove)
        docker stop $Container
        docker rm $Container
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