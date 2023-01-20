package ppp.simt.service.production;


import ppp.simt.entity.core.Office;
import ppp.simt.entity.production.InSlip;
import ppp.simt.entity.production.InSlipStatus;
import ppp.simt.entity.production.OutSlip;
import ppp.simt.entity.pv.EligibleCenter;
import ppp.simt.entity.user.User;
import ppp.simt.form.InSlipForm;

import java.util.List;


public interface InSlipService {
	
	public List<InSlip> findAll();
	public InSlip findInSlipById(int inSlipId);
	List<InSlip> findByInSlipAndDateAndStatusAndOffice(java.sql.Date start, java.sql.Date end, InSlipStatus inSlipStatus, int office, String referenceNum);
	public void manageInSlipReset(InSlip inslip,User user);
	public void manageInSlipOpen(InSlip inslip,User user);
	public void manageInSlipClose(InSlip inslip,User user);
	public void updateInSlip(InSlip inSlip);
    public InSlip save(InSlip slip);
    public String  saveInSlip(InSlipForm form, Office office) ;
    public InSlip findByReferenceNumber(String referenceNumber);
    public List<InSlip> findByStatusAndOffice(InSlipStatus inslipStatus,Office office);
    public List<InSlip> findByOffice(Office office);
    public void manageOfficeInSlipClosing(Office office);
	public String generateReferenceNumber(InSlip inslip, String inSlipType);
	public List<InSlip> findByOfficeAndType(Office office, String inSlipType);
	public List<InSlip> findByEligibleCenter(EligibleCenter eligibleCenter);

}