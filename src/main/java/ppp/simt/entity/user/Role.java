package ppp.simt.entity.user;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role {
 
    @Id
	@Autowired
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
 
    @Column(name="name")
	private String name;
    
    @Column(name="description")
	private String description;
    
 
    @OneToMany(mappedBy = "roleId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<GroupRole> groupRoles = new HashSet<GroupRole>();

	
	public Role() {
		
	}
	public Role(Long id, String name) {
		this.id=id;
		this.name=name;
	}
	 
	
    public Set<GroupRole> getGroupRoles() {
		return groupRoles;
	}

	public void setGroupRoles(Set<GroupRole> groupRoles) {
		this.groupRoles = groupRoles;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
}