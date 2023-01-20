package ppp.simt.entity.core;

import javax.persistence.*;

import ppp.simt.entity.user.User;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="office")
public class Office implements Serializable {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="abreviation")
	private String abreviation;
    
    @OneToMany(mappedBy = "office", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<User>();
    
    
	public Office() {
		super();
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAbreviation() {
		return abreviation;
	}


	public void setAbreviation(String abreviation) {
		this.abreviation = abreviation;
	}


	public Set<User> getUsers() {
		return users;
	}


	public void setUsers(Set<User> users) {
		this.users = users;
	}
	

}