package com.CMPE275Lab2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/add")
public class AddController {

	@RequestMapping(method = RequestMethod.GET)
	public String add() {
		System.out.println("IM HERE!!!!!_____+===--===");
		return "welcome.jsp";
	}
	
}
