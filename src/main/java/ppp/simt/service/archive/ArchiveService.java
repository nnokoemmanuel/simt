package ppp.simt.service.archive;


import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.core.Country;
import ppp.simt.entity.core.Person;
import ppp.simt.entity.user.User;
import ppp.simt.form.ArchiveForm;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;


public interface ArchiveService {
	public List<Archive> findAll();
	public void createArchive(Archive archive);
	public void updateArchive(Archive archive); 
	public Archive findArchiveById(int archiveId);

	//public List<Archive> findByStatus(int status);
	public List<Archive> findByStatus(String status);
	
	public void manageArchiveReset(Archive archive, User user);
	public void manageArchiveValide(Archive archive,User user);
	public void manageArchiveSuspend(Archive archive,User user);

	
	//Archive persistArchiveBean(ArchiveForm archiveForm) throws ParseException;
	Archive persistArchiveBean(ArchiveForm archiveForm, HttpServletRequest httpServletRequest) throws ParseException;
	public Archive save(Archive archive);
	public List<Archive> findByStatusAndRegistrationDateBetween(String archiveStatus,java.util.Date startDate, java.util.Date enDate);
	public List<Archive> findByArchiveNumber(String archiveNumber);
	public List<Archive> findByRegistrationDateBetween(java.util.Date startDate, java.util.Date enDate);
	public Archive findByPerson(Person person);
	public void deleteArchive(Archive archive);
	
	
	
	

}
