package ppp.simt.service.applicant;



import ppp.simt.entity.applicant.EntranceEligibleCenterStatus;



public interface EntranceEligibleCenterStatusService {
	
	public EntranceEligibleCenterStatus findEntranceEligibleCenterStatusById(int entranceEligibleCenterStatusId);	

    public EntranceEligibleCenterStatus findById(int entranceEligibleCenterStatusId);

}
