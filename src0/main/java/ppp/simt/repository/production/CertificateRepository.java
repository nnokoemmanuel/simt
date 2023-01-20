package ppp.simt.repository.production;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.production.Capacity;
import ppp.simt.entity.production.Certificate;
import ppp.simt.entity.pv.StudentSession;

@Repository("certificateRepository")
public interface CertificateRepository extends JpaRepository <Certificate, Integer> {
	
	
	default List<Certificate> findCertificatesByType(String type,EntityManager em){
		String query = "";
		javax.persistence.Query q;
		query = "SELECT s FROM Certificate s WHERE s.type=:type ";
		q = em.createQuery(query);
		q.setParameter("type", type);
		
		
		return q.getResultList();
	}
	
	public Certificate findCertificateByStudentSession(StudentSession studentSession);
	
	Certificate findById(int certificateId);
	public Certificate findByNumber(String certificateNumber);
	

}
