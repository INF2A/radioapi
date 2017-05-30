package main.java;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.*;
import java.net.URL;

/**
 * Created by basva on 17-5-2017.
 */

public class AudioPlayer implements Runnable{
    private BufferedInputStream bufferedInputStream;
    private AdvancedPlayer advancedPlayer;
    private String audioStream = "";

    public AudioPlayer(String audioStream)
    {
        this.audioStream = audioStream;
    }

    public void run()
    {
        try
        {
            bufferedInputStream = new BufferedInputStream(new URL("http://icecast.omroep.nl/" + audioStream).openStream());
            advancedPlayer = new AdvancedPlayer(bufferedInputStream);
            advancedPlayer.play();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (JavaLayerException e)
        {
            e.printStackTrace();
        }
    }
}
