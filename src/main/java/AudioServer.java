package main.java;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

/**
 * Created by rickb on 30-5-2017.
 */
public class AudioServer {
    public final static int SOCKET_PORT = 8083;
    BufferedInputStream bis = new BufferedInputStream(new URL("http://icecast.omroep.nl/radio2-bb-mp3").openStream());

    OutputStream os = null;
    ServerSocket servsock = null;
    Socket sock = null;
    public AudioServer() throws IOException{


    }
    public void Start() throws IOException {
        try {
            servsock = new ServerSocket(SOCKET_PORT);
            while (true) {
                System.out.println("Waiting...");
                try {
                    sock = servsock.accept();
                    System.out.println("Accepted connection : " + sock);
                    os = sock.getOutputStream();
                    // send file
                    //while (true) {
                        /*

                        byte[] mybytearray = new byte[128];
                        bis.read(mybytearray, 0, mybytearray.length);
                        System.out.println("Sending  audio stream: " + mybytearray.length + " bytes)");
                        os.write(mybytearray, 0, mybytearray.length);
                        System.out.println("Done.");
                        os.flush();
                        */
                        byte[] buf = new byte[1024];
                        int len;
                        System.out.println("About to write");
                        while (true) {
                            len = bis.read(buf);
                            System.out.println("len: " + len);
                            if (len > 0) {
                                os.write(buf, 0, len);
                            } else {
                                break;
                            }
                        }

                } catch (IOException ex) {
                    System.out.println(ex.getMessage() + ": An Inbound Connection Was Not Resolved");
                }
            }
        }finally {
            if (bis != null) bis.close();
            if (os != null) os.close();
            if (sock!=null) sock.close();
            if (servsock != null)
                servsock.close();
        }
    }

    public void Stop() throws IOException {
        if (bis != null) bis.close();
        if (os != null) os.close();
        if (sock!=null) sock.close();
        if (servsock != null)
            servsock.close();
    }
}


