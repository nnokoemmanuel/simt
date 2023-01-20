package ppp.simt.repository.core;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.configuration.SpecialityPrerequisite;
import ppp.simt.entity.core.Speciality;

@Repository("specialityPrerequisiteRepository")
public interface SpecialityPrerequisiteRepository extends JpaRepository <SpecialityPrerequisite, Integer>{

	default List<SpecialityPrerequisite> findBySpecialityAndDIplomaStatus(Speciality speciality, String diplomaStatus, EntityManager em){
		
		String query = "";
		javax.persistence.Query q;
		query = "SELECT req FROM SpecialityPrerequisite req WHERE req.speciality=:speciality AND req.entryDiploma=:diplomaStatus";
		q = em.createQuery(query);
		q.setParameter("speciality", speciality);
		q.setParameter("diplomaStatus", diplomaStatus);
		
		
		return q.getResultList();
	}
	public SpecialityPrerequisite findById(int id);

}
