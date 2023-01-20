package ppp.simt.repository.production;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.core.Office;
import ppp.simt.entity.production.InSlip;
import ppp.simt.entity.production.InSlipStatus;
import ppp.simt.entity.pv.EligibleCenter;

import javax.persistence.EntityManager;
import java.util.List;


@Repository("inSlipRepository")
public interface InSlipRepository extends JpaRepository <InSlip, Integer>{
	
	InSlip findById(int inSlipId);

	/**
	 * Cette methode retourne la liste des inslip sur une periode par status et par office
	 * @param start
	 * @param end
	 * @param inSlipStatus
	 * @param office
	 * @param referenceNum
	 * @return
	 */
	default List<InSlip> findByInSlipAndDateAndStatusAndOffice(java.sql.Date start, java.sql.Date end, InSlipStatus inSlipStatus, int office, String referenceNum, EntityManager em){

		String querry = "";
		javax.persistence.Query q;
		if(referenceNum.isEmpty()){
			if (inSlipStatus==null && office ==100 ){

				querry = "SELECT i FROM InSlip i WHERE i.creationDate BETWEEN :start AND :end ";
				q = em.createQuery(querry);
				q.setParameter("start", start);
				q.setParameter("end", end);

			}else if (inSlipStatus==null && office !=100){

				querry = "SELECT i FROM InSlip i WHERE i.office.id =:office AND i.creationDate BETWEEN :start AND :end ";
				q = em.createQuery(querry);
				q.setParameter("office", office);
				q.setParameter("start", start);
				q.setParameter("end", end);

			}else if (inSlipStatus.getId()!=0 && office ==100){

				querry = "SELECT i FROM InSlip i WHERE i.inSlipStatus =:inSlipStatus AND i.creationDate BETWEEN :start AND :end";
				q = em.createQuery(querry);
				q.setParameter("inSlipStatus", inSlipStatus);
				q.setParameter("start", start);
				q.setParameter("end", end);
			}else {

				if(inSlipStatus.getId() != 4){
				querry = "SELECT i FROM InSlip i WHERE i.inSlipStatus =:inSlipStatus AND i.office.id =:office AND i.creationDate BETWEEN :start AND :end ";
				q = em.createQuery(querry);
				q.setParameter("inSlipStatus", inSlipStatus);
				q.setParameter("office", office);
				q.setParameter("start", start);
				q.setParameter("end", end);
				}else{
					//on renvoie tous les type controlled BONS (STATUT 4 ) et mauvais (STATUT 5)
					querry = "SELECT i FROM InSlip i WHERE i.inSlipStatus in (4,5) AND i.office.id =:office AND i.creationDate BETWEEN :start AND :end ";
					q = em.createQuery(querry);
					q.setParameter("office", office);
					q.setParameter("start", start);
					q.setParameter("end", end);
					
				}
			}

		}else {
			querry = "SELECT i FROM InSlip i WHERE i.referenceNumber =:referenceNumber";
			q = em.createQuery(querry);
			q.setParameter("referenceNumber", referenceNum);
		}

		return q.getResultList();
	}
	
	InSlip findByReferenceNumber(String referenceNumber);
	
	List<InSlip> findByInSlipStatusAndOffice(InSlipStatus inslipStatus,Office office);
	
    List<InSlip> findByOffice(Office office);

	default List<InSlip> findByOfficeAndType(Office office, String inSlipType, EntityManager em){
		String query = "";
		javax.persistence.Query q;
		query = "SELECT inSlip FROM InSlip inSlip WHERE inSlip.eligibleCenter IS NOT NULL AND inSlip.office=:office AND inSlip.inSlipType=:inSlipType ";
		q = em.createQuery(query);
		q.setParameter("office", office);
		q.setParameter("inSlipType", inSlipType);
		
		
		return q.getResultList();
	}

	List<InSlip> findByEligibleCenter(EligibleCenter eligibleCenter);


	
	
}