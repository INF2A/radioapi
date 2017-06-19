package main.java;

import java.io.*;
import java.net.*;
import java.util.HashMap;

/**
 * Created by rickb on 30-5-2017.
 */
public final class AudioServer {

    public boolean isConnected = false;//boolean used to check if the server is connected
    private InetAddress addr;//addres of the server
    private final static int SOCKET_PORT = 8093;//port the server uses to connect

    private HashMap<String,String> stations = new HashMap<>();//hashmap used to store all available radio stations and their urls
    private BufferedInputStream bis = null;//bufferd input stream used to get the audio stream
    private OutputStream os = null;//output stream used to send the audio stream to the client
    private ServerSocket servsock = null;//instance of a server socket, so a client can connect to the server
    private Socket sock = null;//instance of a socket, also used to connect with a client

    private static AudioServer instance = new AudioServer();//create a instance of the AudioServer class, so only one instance can be used

    /***
     *
     * @return instance of the AudioServer
     */
    public static AudioServer getInstance() {
        return instance;
    }

    /***
     * constructor of the AudioServer class
     * the addr is set to local host
     * calls the setStation method to set all stations with url's
     */
    private AudioServer() {
        try {
            addr = InetAddress.getByName("0.0.0.0");//st the addr to local host
            setStations();//call the setStations method to fill the stations hashmap with available stations and their urls
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /***
     * changes the streaming channel
     * @param channel channel to change to
     * @throws IOException
     */
    public void ChangeChannel(String channel) throws IOException{
        if(bis != null) bis.close();//close the buffered input stream if a stream is already asigned
        bis = new BufferedInputStream(new URL(channel).openStream());//open a new bufferd input stream with the selected channel url
        play(bis);// call the play method with the bufferd input stream as argument, the play method will then stream the audio data to the connected client
    }

    /***
     * this method will connect the server with a client, by creating a new server socket.
     * it will then wait for the client to connect and set the output stream to the outputstream of the client and set the isConnected bool to true
     * @throws IOException
     */
    public void Connect() throws IOException {
        while (!isConnected) {//check if a client is connected
            try {
                servsock = new ServerSocket(SOCKET_PORT, 50, addr);//create a new serversocket on local host and port 8093
                System.out.println("Waiting...");
                try {
                    sock = servsock.accept();//connect with the client
                    os = sock.getOutputStream();//set the output stream to the outputstream of the client
                    isConnected = true;//set the isConnected to true
                    System.out.println("Accepted connection : " + sock);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage() + ": An Inbound Connection Was Not Resolved");//client could not connect
                }
            } catch (Exception e) {
                System.out.println(e.getMessage() + ": Error");//server addres is already in use
            }
        }
    }

    /***
     * this method will send the bufferd input stream containing the audio data to the connected client
     * @param stream bufferd input stream containing the audio data
     * @throws IOException
     */
    public void play(BufferedInputStream stream) throws IOException {
        try {
            // send audio stream
            byte[] buf = new byte[1024];//create a new buffer of 1024 bytes
            int len;//int to store the lenght of the buffer
            //System.out.println("About to write");
            while (true) {//send the audio stream
                len = stream.read(buf);// set len to the size of stream.read
                //        System.out.println("len: " + len);
                if (len > 0) {//if len is greater than 0
                    os.write(buf, 0, len);//write the audio data to the  output stream
                } else {
                    break;//stop the stream if there is no data available
                }
            }
        }
        catch (Exception e)
        {
            Stop();//reset the connection if the stream died
            e.printStackTrace();
        }
    }

    /***
     * method to close the connection with the client and reset the buffered input stream
     * @throws IOException
     */

    public void Stop() throws IOException {
        if(os != null)os.flush();//flush the outputstream of the client if it is not null
        if(os !=null)os.close();//close the outputstream of the client if it is not null
        if(sock !=null)sock.close();//close the socket if it is not null
        if(servsock !=null)servsock.close();//close the server socket if it is not null
        if(bis !=null)bis.close();//close the bufferd inputstream if it is not null

        bis= null;//set the bufferd inputstream to null
        os = null;//set the outputstream to null
        sock = null;//set the socket to null
        servsock = null;//set the serversocket to null
        isConnected = false;//set isConnected bool to false
    }

    /***
     * method to fill the hashmap with stations and their stream url's
     */
    private void setStations(){
        stations.put("3fm","http://icecast.omroep.nl/3fm-bb-mp3");
        stations.put("radio2","http://icecast.omroep.nl/radio2-bb-mp3");
        stations.put("538","http://vip-icecast.538.lw.triple-it.nl/RADIO538_MP3");
        stations.put("Arrow Classic Rock","http://91.221.151.155:80/;?.mp3");
        stations.put("Q-music","http://icecast-qmusic.cdp.triple-it.nl/Qmusic_nl_live_96.mp3");
        stations.put("Radio Veronica","http://8543.live.streamtheworld.com/VERONICACMP3");
        stations.put("Sky Radio 101 FM","http://8573.live.streamtheworld.com:80/SKYRADIO_SC");
        stations.put("Slam! fm","http://stream.slam.nl/slam");
        stations.put("Radio 10","http://vip-icecast.538.lw.triple-it.nl/RADIO10_MP3");
        stations.put("RadioNL","http://stream.radionl.fm/radionl");
    }

    /***
     * returns the hashmap with stations and streaming urls
     * @return hashmap with stations(Key) and streaming urls(Value)
     */
    public HashMap getStations()
    {
        return stations;
    }

}


