package com.kartiks.spring.restx;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/movie")
public class SpringController {
	
	
	//http://localhost:8080/kartik/movie/kartik%20is%20awesome
	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public String getMovie(@PathVariable String name, ModelMap model) {
		
		model.addAttribute("movie", name);
		//list.jsp will be searced in place mentioned by 
		/*
		 
		 <bean
			class="org.springframework.web.servlet.view.InternalResourceViewResolver">
			<property name="prefix" value="/WEB-INF/springpages/" />
			<property name="suffix" value=".jsp" />
			<!--prefix is the direcory where it will search for view (html/jsp/* files) 
				suffix adds extension to returned view( from controller ) -->
		</bean>
		 * */
		return "list";
	}

	//http://localhost:8080/kartik/movie/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getDefaultMovie(ModelMap model) {
		
		model.addAttribute("movie", "this is default movie");
		return "list";
	}
}
