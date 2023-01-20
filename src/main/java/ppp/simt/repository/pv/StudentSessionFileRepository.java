package ppp.simt.repository.pv;

import java.util.List;
import java.util.Set;


import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import ppp.simt.entity.pv.StudentSession;
import ppp.simt.entity.pv.StudentSessionFile;



@Repository("studentSessionFileRepository")
public interface StudentSessionFileRepository extends JpaRepository <StudentSessionFile, Integer>{
	
	StudentSessionFile findById(int studentSessionid);
	Set<StudentSessionFile> findByStudentSessionAndDeleted(StudentSession studentSession, int deleted);
	List<StudentSessionFile> findAll();
	
}
