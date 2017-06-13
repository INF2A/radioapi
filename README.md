# Radio api
This api is made for project Cluster & SmartMirror, it is designed to run on a raspberry pi in a docker container. 
This application will stream audio data to a connected client in order to play music. See the Audio Client for more info.
The Api runs on a tomcat server in order to handle http requests and is used to start the stream. 
in order to send the actual audio data, websockets in both the server and client are used.

# Setup tomcat server

https://www.jetbrains.com/help/idea/2017.1/creating-and-running-your-first-web-application.html

# Examples

do the following to make a api call when the application is running

<b>connect to the application(should start when you run the application on your local machine, not if its run on a server/raspberry pi):</b><br>
{ip_cluster}:{port_api}/<br>
http://localhost:8080/<br>

<b>To start a music stream</b><br> 
{ip_cluster}:{poort_api}/radio/play/{channel} <br>
http://localhost:8080/radio/play/3fm<br>

when the api call is made, it will start the server and wait for a connection. 
So the client has to make sure to make this api call, and then connect to the server with the websocket

the server runs on 127.0.0.1
the socket is open on port 8093

<b>in order to connect the client make sure you use the right ip addres and websocket ports!</b>

to change the channel, make the same api request and reconnect the client

# Client

more information about the Audio Client: https://github.com/INF2A/Smart-mirror/blob/master/src/widgets/AudioClient.java

# Cluster

more information about the cluster: https://github.com/INF2A/RPI-docker-cluster

# SmartMirror

more information about the SmartMirror: https://github.com/INF2A/Smart-mirror
