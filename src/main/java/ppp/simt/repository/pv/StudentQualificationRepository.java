package ppp.simt.repository.pv;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.pv.StudentQualification;

@Repository("studentQualificationRepository")
public interface StudentQualificationRepository extends JpaRepository<StudentQualification, Integer>{
	

}
