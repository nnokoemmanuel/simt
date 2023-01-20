package ppp.simt.repository.applicant;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.applicant.EntranceEligibleCenter;
import ppp.simt.entity.applicant.EntranceEligibleCenterStatus;
import ppp.simt.entity.applicant.EntranceExamCenter;
import ppp.simt.entity.applicant.EntranceExamSession;

import ppp.simt.entity.pv.ExamSession;




@Repository("entranceEligibleCenterRepository")
public interface EntranceEligibleCenterRepository extends JpaRepository <EntranceEligibleCenter, Integer>{
	
	EntranceEligibleCenter findById(int entranceEligibleCenterId);
	
	/**
	 * This method returns a list of all eligible centers based on the search criteria
	 * @param region
	 * @param sessionDate
	 * 
	 */
	
	default List<EntranceEligibleCenter> findByRegionAndSessionDate(int regionId, int sessionId, EntityManager em) {
		
		String query = "";
		javax.persistence.Query q;
		String regionQuery;
		
		if (regionId == 100){
		regionQuery = " ";
		}else{
			regionQuery = "  AND reg.id = :region";
		}
		
		query = "SELECT el FROM EntranceEligibleCenter el, Division div, Region reg, EntranceExamCenter ex, EntranceExamSession es WHERE es.id=:session AND el.entranceExamSession = es.id AND el.entranceExamCenter=ex.id AND ex.division = div.id AND div.region = reg.id" + regionQuery;
		q = em.createQuery(query);
		q.setParameter("session", sessionId);
		if (regionId != 100){
			q.setParameter("region", regionId);
		}
		
		
		return q.getResultList();
		
	}
	 List<EntranceEligibleCenter> findByEntranceExamSessionAndEntranceExamCenter(EntranceExamSession session, EntranceExamCenter center);
	 List<EntranceEligibleCenter> findByEntranceEligibleCenterStatus(EntranceEligibleCenterStatus entranceEligibleCenterrStatus);

	List<EntranceEligibleCenter> findByEntranceExamSession(EntranceExamSession examSession);
	 
}