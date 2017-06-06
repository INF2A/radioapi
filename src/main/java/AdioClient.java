package main.java;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import javax.sound.sampled.*;
import java.io.*;
import java.net.Socket;
import java.net.URL;

class AudioClient {

    public final static int SOCKET_PORT = 8093;
    public final static String SERVER = "192.168.1.1";
    public static AdvancedPlayer advancedPlayer;
    public static Socket sock = null;

    public static void main (String [] args ) throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
            try {
                sock = new Socket(SERVER, SOCKET_PORT);
                System.out.println("Connecting...");
                InputStream is = sock.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                while (true) {
                    play(bis);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static void play(BufferedInputStream bis) throws JavaLayerException, InterruptedException {
        advancedPlayer = new AdvancedPlayer(bis);
        advancedPlayer.play();
    }
}
