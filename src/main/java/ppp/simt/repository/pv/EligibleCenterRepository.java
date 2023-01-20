package ppp.simt.repository.pv;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.pv.EligibleCenter;
import ppp.simt.entity.pv.EligibleCenterStatus;
import ppp.simt.entity.pv.ExamCenter;
import ppp.simt.entity.pv.ExamSession;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



@Repository("eligibleCenterRepository")
public interface EligibleCenterRepository extends JpaRepository <EligibleCenter, Integer>{
	
	EligibleCenter findById(int eligibleCenterId);
	
	/**
	 * This method returns a list of all eligible centers based on the search criteria
	 * @param region
	 * @param sessionDate
	 * 
	 */
	
	default List<EligibleCenter> findByRegionAndSessionDate(int regionId, int sessionId, EntityManager em) {
		
		String query = "";
		javax.persistence.Query q;
		String regionQuery;
		
		if (regionId == 100){
		regionQuery = " ";
		}else{
			regionQuery = "  AND reg.id = :region";
		}
		
		query = "SELECT el FROM EligibleCenter el, Division div, Region reg, ExamCenter ex, ExamSession es WHERE es.id=:session AND el.examSession = es.id AND el.examCenter=ex.id AND ex.division = div.id AND div.region = reg.id" + regionQuery;
		q = em.createQuery(query);
		q.setParameter("session", sessionId);
		if (regionId != 100){
			q.setParameter("region", regionId);
		}
		
		
		return q.getResultList();
		
	}
	 List<EligibleCenter> findByExamSessionAndExamCenter(ExamSession session, ExamCenter center);
	 List<EligibleCenter> findByEligibleCenterStatus(EligibleCenterStatus eligibleCenterStatus);

	List<EligibleCenter> findByExamSession(ExamSession examSession);
	 
}