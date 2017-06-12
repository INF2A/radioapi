package main.java;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.io.IOException;
import java.util.HashMap;


/**
 * Created by basva on 16-5-2017.
 */

@Path("/")
public class Radio{


    public Radio() throws IOException {

    }

    @Path("/radio/stations")
    @GET
    public String getStations()
    {
        //TODO return list of stations in json format(wens)
        return "";
    }
    /**
     Streams audio based on given channel.
     @param channel channel to stream

     */

    @Path("/radio/play/{channel}")
    @GET
    public String playRadioChannel(@PathParam("channel") String channel) throws IOException {
        AudioServer.getInstance().Stop();//call the stop method to reset the connection
        HashMap stations = AudioServer.getInstance().getStations();//create a new hashmap with the keys of the stations with the values of the stream url
        if(!AudioServer.getInstance().isConnected){//check if a client is not connected
            AudioServer.getInstance().Connect();//call the connect method to start the server
            try {
                if (stations.containsKey(channel)) {//check if the entered channel exists in the hashmap
                    //if the channel exists call the changeChannel method, with the streaming url of the channel in the hashmap as argument
                    AudioServer.getInstance().ChangeChannel((String)stations.get(channel));}
            } catch (Exception e) {
                System.out.println(e);//channel does not exists.
            }
        }
        return "server started";
    }

    /***
     * method to stop the audioserver
     * @throws IOException
     */
    @Path("/radio/stop")
    @GET
    public void stopRadio() throws IOException {
        AudioServer.getInstance().Stop();//call the Stop method in order to stop the audio stream

    }
}
