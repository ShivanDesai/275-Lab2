package com.CMPE275Lab2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/add")
public class AddController {

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public ArrayList<String> add(@RequestParam("t1") int i) { // I'm not using this param for now, but that how you access params from get requests
		System.out.println("IM HERE!!!!!_____+===--===");
		ArrayList<String> l = new ArrayList<String>();
		l.add("something");
		l.add("bs");
		l.add("whatever");
		return l;
	}
	
}
