package ppp.simt.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ppp.simt.entity.user.Role;



@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer>{
	Role findByName(String name);
	public Role findById(long id);
	
	/*@Transactional
    @Modifying
    @Query("delete from  User g where g.roles  = :roles")
    void deleteRole(@Param("roles") Role roles );*/


}
