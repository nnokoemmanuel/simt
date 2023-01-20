package ppp.simt.service.production;

import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.core.Office;
import ppp.simt.entity.production.Application;
import ppp.simt.entity.production.ApplicationTranscript;
import ppp.simt.entity.production.Certificate;
import ppp.simt.entity.user.User;
import ppp.simt.form.ApplicationForm;

import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;


public interface ApplicationService {
	
	public List<Application> findAll();
	public Application findAppById(int appId);
	public List<Application> findAppByInSlipId(int inSlipId);
	public List<Application> findApplicationByOutSlipAndApplicationStatus(int outSlipId, int status );

	List<Application> findBySuccessfulyRejectedDateBetweenAndOutSlipIsNull(java.util.Date startDate, java.util.Date enDate);
    public void save(Application application);

	List<Application> findByComputerizationDateBetweenAndOffice(java.util.Date startDate, java.util.Date enDate, Office office);

	List<Application> findByInSlipAndDateAndStatusAndOfficeAndProcessTypeAndFileNumber(java.sql.Date start, java.sql.Date end, int status, int office, String inSlipReference, int processType, String fileNumber);
	//public Application findBySerialNumber(String serialNumber);
	//public List<Application> findByFormSerialNumber(String formSerialNumber);
	
	//public Application persistApplicationBean(ApplicationForm applicationForm, User user);
	public Application persistApplicationBean(ApplicationForm applicationForm, User user,HttpServletRequest httpServletRequest);

	
	public void updateApplication(Application application);
	
//	public Long getStatisticPerOfficePerCategoryPerArchive(Date sqlStartDate, Date sqlendDate, Office office,
//			Category category);
//	public Long getDistinctTotalByOfficePerArchive(Date sqlStartDate, Date sqlendDate, Office office);
	public List<Application> getDistinctTotalByOfficePerStudentSession(java.sql.Date sqlStartDate , java.sql.Date sqlendDate, int officeId, int appStatutId, int processType, EntityManager em);
//	public Long getStatisticPerOfficePerCategoryByCandidateSession(Date sqlStartDate, Date sqlendDate, Office office,
//			Category category);
	public List<Application> findApplicationByCertificate(Certificate certificate);
	public List<Application> findApplicationByArchive(Archive archive);
	public List<Application> findApplicationByTranscript(ApplicationTranscript applicationTranscript);

}