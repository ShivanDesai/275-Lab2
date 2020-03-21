package com.CMPE275Lab2;


import java.util.*;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/sponsor")
public class AddSponsor {
	
	Util util = new Util();

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity add(
			@RequestParam(name = "name") String name,
			@RequestParam(name = "description", required = false) String description,
			@RequestParam(name = "street", required = false) String street,
			@RequestParam(name = "city", required = false) String city,
			@RequestParam(name = "state", required = false) String state,
			@RequestParam(name = "zip", required = false) String zip,
			@RequestHeader("accept") String responseFormat
		) { 

		name = util.trimInput(name);
		description = util.trimInput(description);
		street = util.trimInput(street);
		city = util.trimInput(city);
		state = util.trimInput(state);
		zip = util.trimInput(zip);
		
		try {
			Configuration con = new Configuration().configure().addAnnotatedClass(Sponsor.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			Transaction tx = session.getTransaction();
			String hql;
			Query query;
			Iterator results;
			System.out.println(responseFormat);
			
			
			if (name.length() < 2) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Required params are missing");
			}
			
			hql = "FROM Sponsor where name = '" + name + "'";
			query = session.createQuery(hql);
			results = query.list().iterator();
			if (results.hasNext()) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Sponsor name already exists");			
			}
			
			Sponsor sponsor = new Sponsor();
			sponsor.setName(name);
			sponsor.setDescription(description);
			sponsor.getAddress().setStreet(street);
			sponsor.getAddress().setCity(city);
			sponsor.getAddress().setState(state);
			sponsor.getAddress().setZip(zip);
				
			try {
				tx.begin();
				session.save(sponsor);
				tx.commit();
			} catch (RuntimeException e) {
				tx.rollback();
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(sponsor);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());			
		}
	}
}
