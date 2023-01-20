package ppp.simt.repository.archive;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.core.Person;
import ppp.simt.entity.user.GroupRole;

import java.util.List;

@Repository("archiveRepository")
public interface ArchiveRepository extends JpaRepository <Archive, Integer>{
	
	Archive findById(int archiveId);
	List<Archive> findByRegistrationDateBetweenAndStatus(java.util.Date startDate,java.util.Date endDate,int status);
	List<Archive> findByRegistrationDateBetween(java.util.Date startDate,java.util.Date endDate);
	//List<Archive> findByStatus(int status);
	List<Archive> findByStatus(String status);
	List<Archive> findByArchiveNumber(String searchTerm);
	public Archive save(Archive archive);
	List<Archive> findByStatusAndRegistrationDateBetween(String archiveStatus,java.util.Date startDate, java.util.Date enDate);
	public Archive findByPerson(Person person);
	
	

}