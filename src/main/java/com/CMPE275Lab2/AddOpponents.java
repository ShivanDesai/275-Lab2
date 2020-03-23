package com.CMPE275Lab2;
import java.util.*;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/opponents")
public class AddOpponents {
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity add(@RequestParam(name = "id1") Long id1,@RequestParam(name = "id2") Long id2)
	{
		
		try {
			Configuration con2 = new Configuration().configure().addAnnotatedClass(Player.class);
			SessionFactory sf2 = con2.buildSessionFactory();
			Session session2 = sf2.openSession();
			
			String hql1,hql2;
			String name= new String("Farha");
			Query query1,query2;
			Iterator results;
			hql1 = "FROM Player where playerId = '" + id1 + "'";
			hql2 = "FROM Player where playerId = '" + id2 + "'";
			System.out.println(hql1);
			query1 = session2.createQuery(hql1);
			query2 = session2.createQuery(hql2);
			results = query1.list().iterator();
			System.out.println("ID1"+query1.uniqueResult());
			System.out.println("ID2"+query2.uniqueResult());
			if (query1.uniqueResult()==null || query2.uniqueResult()==null) {
				
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Both the Id's should be of existing players");			
			}
			if(id1.equals(id2))
				{
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Both Opponents cannot be the same");	
				}
			session2.close();
			System.out.println("new conn");
			Configuration opp = new Configuration().configure().addAnnotatedClass(Opponents.class);
			
			SessionFactory sfOpp = opp.buildSessionFactory();
			Session sessionOpp = sfOpp.openSession();
			Transaction tx2 = sessionOpp.getTransaction();
			
			Opponents op=new Opponents();
			op.setPlayer1((int) (long)id1);
			op.setPlayer2((int) (long)id2);
			
			Opponents opReverse=new Opponents();
			opReverse.setPlayer1((int) (long)id2);
			opReverse.setPlayer2((int) (long)id1);
			
			String hql3;
			Query checkBeforeInsertion;
			
			hql3= "FROM Opponents where player1 = '" + id1 + "' and player2 = '" + id2 + "'";
			checkBeforeInsertion= sessionOpp.createQuery(hql3);
			
			if (checkBeforeInsertion.uniqueResult()!=null) {
				
				return ResponseEntity.status(HttpStatus.OK).body("Exists already");			
			}
			
			System.out.println("new transaction"
					+ "");
			try {
				
				tx2.begin();
				
				sessionOpp.save(op);
				sessionOpp.save(opReverse);
				tx2.commit();
				return ResponseEntity.status(HttpStatus.OK).body(op);
			} catch (RuntimeException e) {
				tx2.rollback();
			}
			
		}
		catch(Exception e)
		{
			System.out.println("Exception"+e);
		}
		
		return null;}
	@ResponseBody
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity remove(@RequestParam(name = "id1") Long id1,@RequestParam(name = "id2") Long id2)
	{
		System.out.println("IN DELETE");
		try {
			Configuration con3 = new Configuration().configure().addAnnotatedClass(Player.class);
			SessionFactory sf3 = con3.buildSessionFactory();
			Session session3 = sf3.openSession();
			
			String hql1,hql2;
			Query query1,query2;
			Iterator results;
			hql1 = "FROM Player where playerId = '" + id1 + "'";
			hql2 = "FROM Player where playerId = '" + id2 + "'";
			System.out.println(hql1);
			query1 = session3.createQuery(hql1);
			query2 = session3.createQuery(hql2);
			results = query1.list().iterator();
			System.out.println("ID1"+query1.uniqueResult());
			System.out.println("ID2"+query2.uniqueResult());
			if (query1.uniqueResult()==null || query2.uniqueResult()==null) {
				
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Both the Id's should be of existing players");			
			}
			if(id1.equals(id2))
				{
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Both Opponents cannot be the same");	
				}
			session3.close();
			System.out.println("new conn");
			Configuration removeOpp = new Configuration().configure().addAnnotatedClass(Opponents.class);
			
			SessionFactory sfremoveOpp = removeOpp.buildSessionFactory();
			Session sessionRemoveOp = sfremoveOpp.openSession();
			Transaction tx3 = sessionRemoveOp.getTransaction();
			
			String hql3;
			Query checkBeforeInsertion;
			
			hql3= "FROM Opponents where player1 = '" + id1 + "' and player2 = '" + id2 + "'";
			checkBeforeInsertion= sessionRemoveOp.createQuery(hql3);
			
			if (checkBeforeInsertion.uniqueResult()==null) {
				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("These players are not opponents");			
			}
			String removeQuery1="delete from Opponents where (player1 in (:players)) and (player2 in (:players))";
			Query queryRemove1 = sessionRemoveOp.createQuery(removeQuery1);
//			
//			queryRemove1.setInteger("player1", (int)(long)id1);
//			queryRemove1.setInteger("player2", (int)(long)id2);
			ArrayList<Integer> val=new ArrayList<>();
			val.add((int)(long)id1);
			val.add((int)(long)id2);
			queryRemove1.setParameterList("players", val);
			System.out.println(queryRemove1
					);
			System.out.println("remove transaction");
			try {
				
				tx3.begin();
				
				System.out.println("Removing query1"+queryRemove1.executeUpdate());
				tx3.commit();
				return ResponseEntity.status(HttpStatus.OK).body("Success"
						+ "");
			} catch (RuntimeException e) {
				tx3
				.rollback();
			}
			
		}
		catch(Exception e)
		{
			System.out.println("Exception"+e);
		}
		
		return null;}
}
