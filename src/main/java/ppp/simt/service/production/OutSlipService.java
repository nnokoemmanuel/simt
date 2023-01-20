package ppp.simt.service.production;

import ppp.simt.entity.core.Office;
import ppp.simt.entity.production.OutSlip;
import ppp.simt.entity.user.User;
import ppp.simt.form.OutSlipForm;

import java.util.List;


public interface OutSlipService {
	
	public List<OutSlip> findAll();
	List<OutSlip> findByOutSlipAndDateAndStatusAndOffice(java.sql.Date start, java.sql.Date end, int status, int office, String referenceNum);
	public OutSlip findOutSlipById(int outSlipId);
	public void manageOutSlipReset(OutSlip outSlip,User user);
	public void manageOutSlipConfirm(OutSlip outSlip,User user);
	public void manageOutSlipDeliver(OutSlip outSlip,User user);
	public void manageOutSlipValidate(OutSlip outSlip,User user);
	public void updateOutSlip(OutSlip outSlip);
	public List<OutSlip> findByOfficeAndCreationDateBetween(Office office, java.util.Date startDate, java.util.Date endDate) ;
    void save(OutSlip sli);
	public String  saveOutSlip(OutSlipForm form, int id) ;
	public List<OutSlip> findByOffice(Office office);
}