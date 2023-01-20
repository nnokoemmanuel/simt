package ppp.simt.repository.production;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.core.Office;
import ppp.simt.entity.production.OutSlip;


@Repository("outSlipRepository")
public interface OutSlipRepository extends JpaRepository <OutSlip, Integer>{

    /**
     * Cette methode retourne la liste des outslip sur une periode par status et par office
     * @param start
     * @param end
     * @param status
     * @param office
     * @param referenceNum
     * @return
     */
    default List<OutSlip> findByOutSlipAndDateAndStatusAndOffice(java.sql.Date start, java.sql.Date end, int status, int office, String referenceNum, EntityManager em){

        String querry = "";
        javax.persistence.Query q;
        if(referenceNum.isEmpty()){

            if (status==0 && office ==100 ){
                System.out.println("moise1");

                querry = "SELECT o FROM OutSlip o WHERE o.creationDate BETWEEN :start AND :end ";
                q = em.createQuery(querry);
                q.setParameter("start", start);
                q.setParameter("end", end);

            }else if (status== 0&& office !=100){
                System.out.println("moise2");

                querry = "SELECT o FROM OutSlip o, Application a WHERE o.office.id =:office  AND a.successfulyRejectedDate BETWEEN :start AND :end ";
                q = em.createQuery(querry);
                q.setParameter("office", office);
                q.setParameter("start", start);
                q.setParameter("end", end);


            }else if (status!=0 && office ==100){
                System.out.println("moise3");

                querry = "SELECT o FROM OutSlip o WHERE o.status =:status AND o.creationDate BETWEEN :start AND :end";
                q = em.createQuery(querry);
                q.setParameter("status", status);
                q.setParameter("start", start);
                q.setParameter("end", end);
            }else {
                querry = "SELECT o FROM OutSlip o WHERE o.status =:status AND o.office.id =:office AND o.creationDate BETWEEN :start AND :end ";
                q = em.createQuery(querry);
                q.setParameter("status", status);
                q.setParameter("office", office);
                q.setParameter("start", start);
                q.setParameter("end", end);
            }

        }else {
            querry = "SELECT o FROM OutSlip o WHERE o.referenceNumber =:referenceNumber";
            q = em.createQuery(querry);
            q.setParameter("referenceNumber", referenceNum);
        }

        return q.getResultList();
    }
    
    OutSlip findById(int outSlipId);
    
    List<OutSlip> findByOfficeAndCreationDateBetween(Office office, java.util.Date startDate, java.util.Date endDate);
    List<OutSlip> findByOffice(Office office);
}