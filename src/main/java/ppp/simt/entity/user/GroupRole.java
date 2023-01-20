package ppp.simt.entity.user;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
@Table(name = "group_role")
public class GroupRole {
	
	@Id
	@Autowired
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="role_id")
	private Role roleId;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="group_id")
	private Group groupId;

    public GroupRole(Group groups, Role roles) {
		this.roleId = roles;
		this.groupId = groups;
    }
    public GroupRole() {
		
    }
	public Role getRoleId() {
		return roleId;
	}

	public void setRoleId(Role roleId) {
		this.roleId = roleId;
	}

	public Group getGroupId() {
		return groupId;
	}

	public void setGroupId(Group groupId) {
		this.groupId = groupId;
	}
	
	

}
