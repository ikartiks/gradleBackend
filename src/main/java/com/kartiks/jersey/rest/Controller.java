package com.kartiks.jersey.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kartiks.biz.ManagerUser;
import com.kartiks.db.view.VUser;
import com.kartiks.pojo.Greeting;

@Path("/std")
@Component
@Scope("request")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class Controller {
	
	@Autowired
	ManagerUser userManager;
	
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
	
	//http://localhost:8080/kartik/rest/std/insertUser
	@GET
	@Path("/insertUser")
    @Produces({MediaType.APPLICATION_JSON})
    public VUser insertUser (@Context HttpServletRequest request) {
		VUser user = new VUser();
		user.setEmail("asd@asd.com");
		user.setCompany("FROLLO");
		user.setEnabled(true);
		user.setName("Kartik");
		user.setPassword("asdasd");
		user.setRole("ROLE_SALES_PERSON");
		user.setNumber("0404874569");
		user.setMaxFree(10L);
		if(request == null)
			System.out.println("Request is null");
		else
			System.out.println("Request is not null");
		
		if(userManager == null) {
			System.out.println("User Manager  is null");
			System.err.println("User Manager is  null");
		}
        return userManager.createUserMobile(user,request);
    }

}
