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
@RequestMapping("/player")
public class AddController {
	
	Util util = new Util();

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity add(
			@RequestParam(name = "firstname") String firstname,
			@RequestParam(name = "lastname") String lastname,
			@RequestParam(name = "email") String email,
			@RequestParam(name = "description", required = false) String description,
			@RequestParam(name = "street", required = false) String street,
			@RequestParam(name = "city", required = false) String city,
			@RequestParam(name = "state", required = false) String state,
			@RequestParam(name = "zip", required = false) String zip,
			@RequestParam(name = "sponsor", required = false) String sponsor,
			@RequestParam(name = "opponents", required = false) String opponents,
			@RequestHeader("accept") String responseFormat
		) { 
		
			firstname = util.trimInput(firstname);
			lastname = util.trimInput(lastname);
			email = util.trimInput(email);
			description = util.trimInput(description);
			street = util.trimInput(street);
			city = util.trimInput(city);
			state = util.trimInput(state);
			zip = util.trimInput(zip);
			sponsor = util.trimInput(sponsor);
			
		try {
			Configuration con = new Configuration().configure().addAnnotatedClass(Player.class);
			SessionFactory sf = con.buildSessionFactory();
			Session session = sf.openSession();
			Transaction tx = session.getTransaction();
			String hql;
			Query query;
			Iterator results, sponsorInfo;
			System.out.println(responseFormat);
			if (util.requiredAndIsEmpty(firstname) || 
				util.requiredAndIsEmpty(lastname) || 
				util.requiredAndIsEmpty(email)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Required params are missing");
			}
			
			if (opponents != null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Opponents cannot be passed as parameters");	
			}
			
			hql = "FROM Player WHERE email = '" + email + "'";
			query = session.createQuery(hql);
			results = query.list().iterator();
			if (results.hasNext()) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");			
			}
			
			if (!util.notRequiredAndIsEmpty(sponsor)) {

				Configuration con1 = new Configuration().configure().addAnnotatedClass(Sponsor.class);
				SessionFactory sf1 = con1.buildSessionFactory();
				Session session1 = sf1.openSession();
				hql = "FROM Sponsor WHERE name = '" + sponsor + "'";
				query = session1.createQuery(hql);
				sponsorInfo = query.list().iterator();
				if (!sponsorInfo.hasNext()) {
					return ResponseEntity.status(HttpStatus.CONFLICT).body("Sponsor does not exists");			
				}				
			}
			
			Player player = new Player();
			player.setFirstname(firstname);
			player.setLastname(lastname);
			player.setEmail(email);
			player.setDescription(description);
			player.getAddress().setStreet(street);
			player.getAddress().setCity(city);
			player.getAddress().setState(state);
			player.getAddress().setZip(zip);
			player.setSponsor(sponsor);
			
			try {
				tx.begin();
				session.save(player);
				tx.commit();
			} catch (RuntimeException e) {
				tx.rollback();
			}
	
	//		String hql = "FROM Player";
			hql = "SELECT firstname, lastname FROM Player";
			query = session.createQuery(hql);
			results = query.list().iterator();
			
			while(results.hasNext()) {
	//			Player fName = (Player) results.next();
	//			System.out.println(fName.getFirstname());
				Object[] tuple = (Object []) results.next();
				System.out.println(tuple[0] + " " + tuple[1]);
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(player);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());			
		}
	}
	
}
