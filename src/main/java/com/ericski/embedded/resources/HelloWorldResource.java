package com.ericski.embedded.resources;

import com.codahale.metrics.annotation.Timed;
import java.util.Date;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
 
@Path("/hello")
public class HelloWorldResource {
 
    @GET
    @Timed
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello() 
    {
        return "Hello World [" + new Date() + "]";
    }
}