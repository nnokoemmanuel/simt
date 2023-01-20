package ppp.simt.repository.production;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.core.Office;
import ppp.simt.entity.production.Application;
import ppp.simt.entity.production.ApplicationTranscript;
import ppp.simt.entity.production.Certificate;

import javax.persistence.EntityManager;

import java.util.List;


@Repository("applicationRepository")
public interface ApplicationRepository extends JpaRepository <Application, Integer>{
	
	Application findById(int appId);

	List<Application> findAppByInSlipId(int inSlipId);

	@Query("SELECT a FROM Application a WHERE a.applicationStatus.id =:status AND a.outSlip.id =:outSlip ORDER BY a.inSlip.id")
	List<Application> findApplicationByOutSlipAndApplicationStatus(@Param("outSlip") int outSlip, @Param("status") int status );

	List<Application> findBySuccessfulyRejectedDateBetweenAndOutSlipIsNull(java.util.Date startDate, java.util.Date enDate);
	List<Application> findByComputerizationDateBetweenAndOffice(java.util.Date startDate, java.util.Date enDate, Office office);

		/**
		 * Cette methode retourne la liste des Application sur une periode par status , par office ,par processType et par inSlipReference ou par fileNumber
		 * @param start
		 * @param end
		 * @param status
		 * @param office
		 * @param inSlipReference
		 * @param processType
		 * @param fileNumber
		 * @return
		 */
		default List<Application> findByInSlipAndDateAndStatusAndOfficeAndProcessTypeAndFileNumber(java.sql.Date start, java.sql.Date end, int status, int office, String inSlipReference, int processType, String fileNumber, EntityManager em){

			String querry = "";
			javax.persistence.Query q = null;
			String subQuerryOffice;
			String subQuerryStatus;
			String subQuerryProcessType;
			String subQuerryInSlipReference;

			if(fileNumber.isEmpty()){

				if(office ==100){
					 subQuerryOffice = "";
				}  else {
					 subQuerryOffice = " AND a.office.id=:office ";
				}
				if(status ==0){
					 subQuerryStatus = "";
				}  else {
					 subQuerryStatus = " AND a.applicationStatus.id=:status ";
				}
				if(processType == 0){
					 subQuerryProcessType="";
				}  else {
					 subQuerryProcessType=" AND a.processType.id=:processType ";
				}

				if(inSlipReference.isEmpty()){
					 subQuerryInSlipReference="";
				}  else {
					 subQuerryInSlipReference=" AND a.inSlip.referenceNumber=:referenceNumber ";
				}

				querry = "SELECT a FROM Application a WHERE a.computerizationDate BETWEEN :start AND :end "
						+subQuerryOffice+subQuerryStatus+subQuerryProcessType+subQuerryInSlipReference;

				q = em.createQuery(querry);
				q.setParameter("start", start);
				q.setParameter("end", end);

				if(office !=100){
					q.setParameter("office", office);
				}

				if(status !=0){
					q.setParameter("status", status);
				}

				if(processType != 0){
					q.setParameter("processType", processType);
				}

				if(!inSlipReference.isEmpty()){
					q.setParameter("referenceNumber", inSlipReference);
				}

			}else {
				querry = "SELECT a FROM Application a WHERE a.serialNumber =:serialNumber";
				q = em.createQuery(querry);
				q.setParameter("serialNumber", fileNumber);
			}

			return q.getResultList();
		}


	//	Application findBySerialNumber(String serialNumber);
	   // List<Application> findByFormSerialNumber(String formSerialNumber);

				
//	    @Query("SELECT" + 
//	    		"  count(a.id) "
//	    		+ " FROM Application AS a JOIN a.capacity AS cap JOIN cap.archive AS arch JOIN arch.category AS cat  WHERE a.applicationStatus.id=9 AND a.outSlip is NOT NULL "
//	    		+ " AND a.computerizationDate BETWEEN :startDate AND :endDate "
//	    		+ "AND a.office.id=:officeId AND cat.id=:categoryId "
//	    		+ "AND (a.capacity.archive is NOT NULL AND a.capacity.candidateSession is NULL ) "
//	    		+ " GROUP BY a.office.id, cat.id")
//	    Long getStatisticPerOfficePerCategoryPerArchive(@Param("startDate") Date sqlStartDate,@Param("endDate") Date sqlendDate, @Param("officeId") int officeId, @Param("categoryId") Long categoryId);
//
//		
//	    @Query("SELECT" + 
//	    		"  count(a.id) "
//	    		+ " FROM Application AS a JOIN a.capacity AS cap JOIN cap.archive AS arch JOIN arch.category AS cat WHERE a.applicationStatus.id=9 AND a.outSlip is NOT NULL "
//	    		+ " AND a.computerizationDate BETWEEN :startDate AND :endDate"
//	    		+ " AND a.office.id=:officeId "
//	    		+ "AND (a.capacity.archive is NOT NULL AND a.capacity.candidateSession is NULL) "
//	    		+ " GROUP BY a.office.id")
//	    Long getDistinctTotalByOfficePerArchive(@Param("startDate") Date sqlStartDate,@Param("endDate") Date sqlendDate, @Param("officeId") int officeId);
	    

	    default List<Application> getDistinctTotalByOfficePerStudentSession(java.sql.Date sqlStartDate ,
	    					java.sql.Date sqlendDate, int officeId, int appStatutId, int processType, EntityManager em){
			
			String querry = "";
			if(appStatutId!=0) {
				if(processType!=0) {
					querry = "SELECT a FROM Application AS a WHERE a.outSlip is NOT NULL"  
							+ " AND a.computerizationDate BETWEEN :startDate AND :endDate"  
							+ " AND a.office.id=:officeId AND a.applicationStatus.id=:appStatutId  AND a.processType.id=:processType" ;
				}
				else {
					querry = "SELECT a FROM Application AS a WHERE a.outSlip is NOT NULL"  
							+ " AND a.computerizationDate BETWEEN :startDate AND :endDate"  
							+ " AND a.office.id=:officeId AND a.applicationStatus.id=:appStatutId" ;
					
					}
			}
			else {
				if(processType!=0) {
					querry = "SELECT a FROM Application AS a WHERE a.outSlip is NOT NULL"  
							+ " AND a.computerizationDate BETWEEN :startDate AND :endDate"  
							+ " AND a.office.id=:officeId AND a.processType.id=:processType" ;
				}
				else {
					querry = "SELECT a FROM Application AS a WHERE a.outSlip is NOT NULL"  
							+ " AND a.computerizationDate BETWEEN :startDate AND :endDate"  
							+ " AND a.office.id=:officeId" ;
					
					}
				
			}
			javax.persistence.Query q = em.createQuery(querry);
			q.setParameter("startDate", sqlStartDate);
			q.setParameter("endDate", sqlendDate);
			q.setParameter("officeId", officeId);
			if(appStatutId!=0) {
			q.setParameter("appStatutId", appStatutId);
				}
			if(processType!=0) {
			q.setParameter("processType", processType);
			}
			return q.getResultList();
		}
		
	    
	    List<Application> findApplicationByArchive(Archive archive);
	    List<Application> findApplicationByTranscript(ApplicationTranscript applicationTranscript);
	    List<Application> findApplicationByCertificate(Certificate certificate);
	    

}
