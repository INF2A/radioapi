import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * Created by basva on 16-5-2017.
 */

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
@Path("/")
public class Radio {
    //LOCALHOST:8080/sayHello
    @Path("/sayHello")
    @GET
    @Produces("application/json")
    public String sayHello()
    {
        return "Hello World";
    }

    //LOCALHOST:8080/sayHello/{NAME}
    @Path("/sayHello/{n}")
    @GET
    @Produces("application/json")
    public String sayHello(@PathParam("n") String c) {
        return "Hello " + c;
    }
}
