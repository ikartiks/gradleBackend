package com.kartiks.jersey.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.kartiks.pojo.Greeting;

@Path("/std")
public class Controller {
	
	//http://localhost:8080/kartik/rest/std/greeting
	//just checking
	@GET
	@Path("/greeting")
    @Produces({MediaType.APPLICATION_JSON})
    public Greeting getTest() {
        Greeting greeting= new Greeting();
        greeting.setName("kartik");
        greeting.setId("isworking");
        return greeting;
    }

}
