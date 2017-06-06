package main.java;
import javazoom.jl.decoder.JavaLayerException;
import org.json.simple.JSONArray;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.IOException;


/**
 * Created by basva on 16-5-2017.
 */

@Path("/")
public class Radio{
    AudioServer server = new AudioServer();

    public Radio() throws IOException {

    }

    @Path("/radio/stations")
    @GET
    public Response getStations()
    {
        JSONArray jsonArray = JsonParser.parseURL("http://api.dirble.com/v2/stations?token=eee714d4777dac23143a6ffac3");
        return Response.ok(jsonArray.toJSONString()).build();
    }

    /*
    Streams radio based on given stream URL.

    http://icecast.omroep.nl/3fm-bb-mp3
    http://icecast.omroep.nl/radio2-bb-mp3

    @param streamURL

    @return Response
    */
    @Path("/radio/play/")
    @GET
    public void playRadio() throws IOException {
        server.Start();
        //Thread t = new Thread(new AudioPlayer(streamURL));
        //t.start();

        //return Response.ok().build();
    }


    /*

    */
    @Path("/radio/stop")
    @GET
    public void stopRadio() throws IOException {
        server.Stop();
    }
}
