package ppp.simt.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ppp.simt.entity.user.Group;
import ppp.simt.entity.user.GroupRole;

import java.util.List;

@Repository("groupRoleRepository")
public interface GroupRoleRepository extends JpaRepository<GroupRole, Integer>{
    public GroupRole save(GroupRole groupRole);

    public List<GroupRole> findByGroupId(long id);

    @Transactional
    @Modifying
    @Query("delete from GroupRole g where g.groupId  = :groupId")
    void deleteGroupRole(@Param("groupId") Group groupId );
    
    public List<GroupRole> findRolesByGroupId(Group group);

}
