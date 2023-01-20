package ppp.simt.service.production;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.production.Application;
import ppp.simt.entity.production.Capacity;
import ppp.simt.repository.production.CapacityRepository;
import ppp.simt.service.archive.ArchiveService;
import ppp.simt.service.pv.StudentSessionService;

import java.util.ArrayList;
import java.util.List;


@Service
public class CapacityServiceImpl implements CapacityService {

	@Autowired
	private CapacityRepository capacityRepository;
	
	@Autowired
	private ApplicationService applicationService;

	@Override
	public List<Capacity> findAll() {
		return capacityRepository.findAll();
	}



	@Override
	public void createCapacity(Capacity capacity) {
		capacityRepository.save(capacity);

	}

	@Override
	public void updateCapacity(Capacity capacity) {
		capacityRepository.saveAndFlush(capacity);

	}

	@Override
	public Capacity findCapacityById(int capacityId) {

		return capacityRepository.findById(capacityId);
	}

	@Override
	public String generateCapacityNum(int applicationId) {
		Application application = applicationService.findAppById(applicationId);
		String officeRef = application.getOffice().getAbreviation();
		String capacityNumber = officeRef+'-'+application.getNumber()+application.getApplicationStatus().getId()+application.getProcessType().getId();
		return capacityNumber;
	}



	@Override
	public java.sql.Date capacityExpiryDateCal(int applicationId,Capacity capacityNew) {
		Application application = applicationService.findAppById(applicationId);
		//Person person = application.getCapacity().getPerson();
		//List<CandidateSession> cansessionsAll = candidateSessionService.findCandidateSessionByPersonId(person.getId());
		List<Archive> archives = new ArrayList<Archive>();
		//archives.addAll(application.getCapacity().getPerson().getArchives());
		
	/*	if(!(application.getCapacity().getPerson().getArchives()== null)) {
		java.sql.Date dob = application.getCapacity().getPerson().getDob();
		Date today = new Date();
		LocalDate currentDate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localDob = dob.toLocalDate();
            int age = Period.between(localDob, currentDate).getYears();
            
			for (int i = 0; i < archives.size(); i++) {
				
					if(age<=50) {
						LocalDate expiringDate = currentDate.plusYears(5);
						java.util.Date Edate = java.sql.Date.valueOf(expiringDate);
						archives.get(i).setExpiryDate(Edate);
					}else if (age > 50 && age<=60) {
						LocalDate expiringDate = currentDate.plusYears(3);
						java.util.Date Edate = java.sql.Date.valueOf(expiringDate);
						archives.get(i).setExpiryDate(Edate);
				
					}else if (age>60) {
						LocalDate expiringDate = currentDate.plusYears(1);
						java.util.Date Edate = java.sql.Date.valueOf(expiringDate);
						archives.get(i).setExpiryDate(Edate);
				
					}
					
					archiveService.save(archives.get(i));
            
		}
		
		}
		if (!(application.getCapacity().getPerson().getCandidateSessions() == null)) {
				
				java.sql.Date dob = application.getCapacity().getPerson().getDob();
				Date today = new Date();
				LocalDate currentDate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate localDob = dob.toLocalDate();
				int processed= 1;
		        int age = Period.between(localDob, currentDate).getYears();
		            			        	
			    //List<CandidateSession> canSessions = candidateSessionService.findByPersonIdAndProcessed(application.getCapacity().getPerson(), processed);
			        //cansessionsAll.removeAll(canSessions);
			         for (int i = 0; i < cansessionsAll.size(); i++) {
			        	 if(cansessionsAll.get(i).getExamResult()==5) {
		            if(age<=50) {
		            	LocalDate expiringDate = currentDate.plusYears(5);
		            	java.util.Date Edate = java.sql.Date.valueOf(expiringDate);
		            	cansessionsAll.get(i).setExpiryDate(Edate);
		            }else if (age > 50 && age<=60) {
		            	LocalDate expiringDate = currentDate.plusYears(3);
		            	java.util.Date Edate = java.sql.Date.valueOf(expiringDate);
		            	cansessionsAll.get(i).setExpiryDate(Edate);
						
					}else if (age>60) {
						LocalDate expiringDate = currentDate.plusYears(1);
		            	java.util.Date Edate = java.sql.Date.valueOf(expiringDate);
		            	cansessionsAll.get(i).setExpiryDate(Edate);
						
					}
			        	 }
			        	 candidateSessionService.save(cansessionsAll.get(i)) ;
			         }
		            		           
				
				
			}*/
			
		
		
		return null;
	}



	@Override
	public Capacity findCapacityByCapacityNumber(String capacityNum) {
		return  capacityRepository.findCapacityByCapacityNumber(capacityNum);
	}

	@Override
	public Capacity save(Capacity capacity) {
		return capacityRepository.save(capacity);
	}


}
