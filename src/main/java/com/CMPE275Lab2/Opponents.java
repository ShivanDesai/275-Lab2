package com.CMPE275Lab2;
import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "opponents")
public class Opponents {
	
	@Id
	@Column(name = "player1")
    private int player1;
	
	@Column(name = "player2")
    private int player2;
	
	public int getPlayer1() {
		return player1;
	}
	public void setPlayer1(int player1) {
		this.player1 = player1;
		System.out.println("setting to "+player1);
}
	
	public long getPlayer2() {
		return player2;
	}
	public void setPlayer2(int player2) {
		this.player2 = player2;
		System.out.println("setting to "+player2);
}
}