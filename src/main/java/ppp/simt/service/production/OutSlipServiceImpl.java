package ppp.simt.service.production;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ppp.simt.entity.core.Office;
import ppp.simt.entity.production.Application;
import ppp.simt.entity.production.ApplicationStatus;
import ppp.simt.entity.production.OutSlip;
import ppp.simt.entity.user.User;
import ppp.simt.form.OutSlipForm;
import ppp.simt.repository.production.OutSlipRepository;

import javax.persistence.EntityManager;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Service
public class OutSlipServiceImpl implements OutSlipService {
	
	@Autowired
	private OutSlipRepository outSlipRepository;
	
	@Autowired
	private ApplicationStatusService applicationStatusService;
	

	@Autowired
	EntityManager em;

	@Value("${outslip.file.folder}")
	private String slipFolder;

	@Override
	public List<OutSlip> findAll() {
		return outSlipRepository.findAll();
	}

	@Override
	public List<OutSlip> findByOutSlipAndDateAndStatusAndOffice(java.sql.Date start, java.sql.Date end, int status, int office, String referenceNum){

		return outSlipRepository.findByOutSlipAndDateAndStatusAndOffice(start, end, status, office, referenceNum,em);
	}
	
	@Override
	public OutSlip findOutSlipById(int outSlipId) {
		return outSlipRepository.findById(outSlipId);
	}
	
	@Override
	public void manageOutSlipReset(OutSlip outSlip,User user){
		
			if( outSlip!= null ){
				outSlip.setStatus(1);
				updateOutSlip(outSlip);
			}
	}
	@Override
	public void manageOutSlipConfirm(OutSlip outSlip,User user){

		if( outSlip!= null ){
			outSlip.setStatus(4);
			updateOutSlip(outSlip);
		}
	}

	@Override
	public void manageOutSlipValidate(OutSlip outSlip,User user){
		
			if( outSlip!= null ){
				outSlip.setStatus(2);
				updateOutSlip(outSlip);
			}
	}
	
	@Override
	public void manageOutSlipDeliver(OutSlip outSlip,User user){
		
			if( outSlip!= null ){
				outSlip.setStatus(3);
				outSlip.setDeliveryDate(new Date());
				updateOutSlip(outSlip);
				
				Set<Application> outSlipApplications =outSlip.getApplications();
				//we change out slip applications statues to delivered
				for(Application application : outSlipApplications){
					ApplicationStatus applicationStatusSuccesfullyPrinted = applicationStatusService.findApplicationStatusById(6);
					ApplicationStatus applicationStatusDELIVERED = applicationStatusService.findApplicationStatusById(9);
					if(application.getApplicationStatus().equals(applicationStatusSuccesfullyPrinted)){
						
						application.setApplicationStatus(applicationStatusDELIVERED);
						/*if(application.getCapacity()!= null){
							//we change the on process value of capacities
							application.getCapacity().setOnProcess(0);
						}*/
						
					}
				}
			}
	}
    
	@Override
	public void updateOutSlip(OutSlip outSlip) {
		outSlipRepository.saveAndFlush(outSlip);
		
	}

	@Override
	public List<OutSlip> findByOfficeAndCreationDateBetween(Office office, java.util.Date startDate, java.util.Date endDate) {
		// TODO Auto-generated method stub
		return outSlipRepository.findByOfficeAndCreationDateBetween(office, startDate,endDate);
	}

	@Override
	public void save(OutSlip slip) {
		// TODO Auto-generated method stub
		outSlipRepository.save(slip);
	}

	public  String  saveOutSlip(OutSlipForm form, int id) {

		OutSlip slip = outSlipRepository.findById(id);

		//slip.setCreationDate(new java.util.Date());
		//slip.setReferenceNumber(office.getAbreviation()+"-"+form.getSlipNumber()+"-"+monthAstring+""+year);
		//slip.setTotal(form.getTotal());
		slip.setStatus(4);
		save(slip);

		String filename=form.getFilename();
		String[] fileNameArray = filename.split("\\.");
		String extension =  fileNameArray[fileNameArray.length-1];
		try{
			byte barr[]=form.getFile().getBytes();
			BufferedOutputStream bout=new BufferedOutputStream(
					new FileOutputStream(slipFolder+slip.getId()+"."+extension));
			bout.write(barr);
			bout.flush();
			bout.close();
		}catch(Exception e){

				//inSlipRepository.delete(slip);
			return "KO";
		}
		return "OK";
	}

	@Override
	public List<OutSlip> findByOffice(Office office) {
		return outSlipRepository.findByOffice(office);
	}

}