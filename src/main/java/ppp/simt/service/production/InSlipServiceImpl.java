package ppp.simt.service.production;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ppp.simt.entity.core.Office;
import ppp.simt.entity.production.Application;
import ppp.simt.entity.production.InSlip;
import ppp.simt.entity.production.InSlipStatus;
import ppp.simt.entity.pv.EligibleCenter;
import ppp.simt.entity.user.User;
import ppp.simt.form.InSlipForm;
import ppp.simt.repository.production.InSlipRepository;


@Service
public class InSlipServiceImpl implements InSlipService {
	
	@Autowired
	private InSlipRepository inSlipRepository;
	
	@Autowired
	private InSlipStatusService inSlipStatusService;

	@Autowired
	EntityManager em;

	@Value("${inslip.file.folder}")
	private String slipFolder;	  

	@Override
	public List<InSlip> findAll() {
		return inSlipRepository.findAll();
	}


	@Override
	public InSlip findInSlipById(int inSlipId) {
		return inSlipRepository.findById(inSlipId);
	}

	@Override
	public List<InSlip> findByInSlipAndDateAndStatusAndOffice(java.sql.Date start, java.sql.Date end, InSlipStatus inSlipStatus, int office, String referenceNum){

		return inSlipRepository.findByInSlipAndDateAndStatusAndOffice(start, end, inSlipStatus, office, referenceNum,em);
	}

	
	@Override
	public void updateInSlip(InSlip inSlip) {
		inSlipRepository.saveAndFlush(inSlip);
		
	}
	
	@Override
	public void manageInSlipReset(InSlip inSlip,User user){
		
			if( inSlip!= null ){
				InSlipStatus status = inSlipStatusService.findById(1);
				inSlip.setStatus(status);
				updateInSlip(inSlip);
			}
	}
	
	@Override
	public void manageInSlipOpen(InSlip inSlip,User user){
		
			if( inSlip!= null ){
				InSlipStatus status = inSlipStatusService.findById(2);
				inSlip.setStatus(status);
				updateInSlip(inSlip);
			}
	}
	@Override
	public void manageInSlipClose(InSlip inSlip,User user){
		
			if( inSlip!= null ){
				InSlipStatus status = inSlipStatusService.findById(4);
				inSlip.setStatus(status);
				updateInSlip(inSlip);
			}
	}


	@Override
	public InSlip save(InSlip slip) {
		return inSlipRepository.save(slip);
	}

    @Override
    public  String  saveInSlip(InSlipForm form,Office office) {
		 InSlipStatus status = inSlipStatusService.findById(1);
		 Date date = new Date();
		 LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		 int year  = localDate.getYear()-2000;
		 int month = localDate.getMonthValue();
		  String monthAstring =""+month;
	 	if(month<10) monthAstring="0"+month;
	 	InSlip slip;
	 	if(form.getId()==0)
		slip=new InSlip();
	 	else slip=inSlipRepository.findById(form.getId());
		 slip.setCreationDate(new java.util.Date());
		 String slipRef = office.getAbreviation()+"-"+form.getSlipNumber()+"-"+monthAstring+""+year;
		 if( findByReferenceNumber(slipRef) != null) return "KOEXIST";
		 slip.setReferenceNumber(slipRef);
		 slip.setTotal(form.getTotal());
		 
		 slip.setStatus(status);
		 slip.setOffice(office);
		 save(slip);
		 String filename=form.getFilename();
		 System.out.println("file name "+filename);
	          String[] fileNameArray = filename.split("\\.");
	          System.out.println("file name array "+fileNameArray);
	          String extension =  fileNameArray[fileNameArray.length-1];
	          System.out.println("extension "+extension);
			  try{  
			  byte barr[]=form.getFile().getBytes();  
		        BufferedOutputStream bout=new BufferedOutputStream(  
		        		new FileOutputStream(slipFolder+slip.getId()+"."+extension));
		        bout.write(barr);  
		        bout.flush();  
		        bout.close();  
		        }catch(Exception e){
		        	if(form.getId()==0)
		        	inSlipRepository.delete(slip);
		        	System.out.println("KOsssssssssssssssssssssssssssssssssssssssss");
		        	return "KO";
		        	}		 
		  return "OK";
	 }
	 
	 @Override
	 public InSlip findByReferenceNumber(String referenceNumber){
		 
		 return inSlipRepository.findByReferenceNumber(referenceNumber);
		 
	 }
	 
	 @Override
	 public List<InSlip> findByStatusAndOffice(InSlipStatus inslipStatus,Office office){
		 
		 return inSlipRepository.findByInSlipStatusAndOffice(inslipStatus,office);
		 
	 }


	@Override
	public List<InSlip> findByOffice(Office office) {
		return inSlipRepository.findByOffice(office);
	}
	
	@Override
	public void manageOfficeInSlipClosing(Office office) {
		InSlipStatus currentStatus = inSlipStatusService.findById(2);
		ArrayList<InSlip> inSlipsOfOffice = (ArrayList<InSlip>) inSlipRepository.findByInSlipStatusAndOffice(currentStatus,office);
		for(InSlip inSlip : inSlipsOfOffice){
			Set<Application> inSlipApplications = inSlip.getApplications();
			if((inSlipApplications.size()+inSlip.getNotFound()) == inSlip.getTotal()){
				
				InSlipStatus newStatus = inSlipStatusService.findById(3);
				inSlip.setStatus(newStatus);//fermons le inSlip
				updateInSlip(inSlip);
			}
			
		}
		
	}


	@Override
	public String generateReferenceNumber(InSlip inslip, String inSlipType) {
		String referenceNumber = "";
		 Date date = new Date();
		 LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		 int year  = localDate.getYear()-2000;
		 int month = localDate.getMonthValue();
		  String monthAstring =""+month;
	 	if(month<10) monthAstring="0"+month;
		List<InSlip> inSlipPerTypeInOffice = findByOfficeAndType(inslip.getOffice(), inSlipType);
		int currentIndex = inSlipPerTypeInOffice.size() + 1;
		referenceNumber = "" + inslip.getOffice().getAbreviation() + "-"+currentIndex+"-"+monthAstring+""+year+"-"+inSlipType;
		
		return referenceNumber;
	}


	@Override
	public List<InSlip> findByOfficeAndType(Office office, String inSlipType) {
		return inSlipRepository.findByOfficeAndType(office,inSlipType, em);
	}


	@Override
	public List<InSlip> findByEligibleCenter(EligibleCenter eligibleCenter) {
		// TODO Auto-generated method stub
		return inSlipRepository.findByEligibleCenter(eligibleCenter);
	}
	 

	}
