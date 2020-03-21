package com.CMPE275Lab2;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "player")
public class Player {

	Player() {
		this.address = new Address();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "playerId")
    private long id;
	
	@Column(name = "firstname")
    private String firstname;
	
	@Column(name = "lastname")
    private String lastname;
	
	@Column(name = "email")
    private String email;
	
	@Column(name = "description")
    private String description;
	
	@Embedded
	@AttributeOverrides(
			value = {
					@AttributeOverride(name = "street", column = @Column(name = "street")),
					@AttributeOverride(name = "city", column = @Column(name = "city")),
					@AttributeOverride(name = "state", column = @Column(name = "state")),
					@AttributeOverride(name = "zip", column = @Column(name = "zipcode"))
			}
	)
	private Address address;
	
	@Column(name = "sponsor")
    private String sponsor;
	

//	@OneToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "sponsor", referencedColumnName = "name")
//	private Sponsor sponsorInfo;
//	
//	public Sponsor getSponsorInfo() {
//		return sponsorInfo;
//	}
//
//	public void setSponsorInfo(Sponsor sponsorInfo) {
//		this.sponsorInfo = sponsorInfo;
//	}

//	@OneToMany(mappedBy = "")
//	private List<Player> opponents;
	
	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}
	
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

    
}