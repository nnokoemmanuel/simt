package ppp.simt.repository.pv;

import org.springframework.stereotype.Repository;

import ppp.simt.entity.pv.StudentSessionStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository("studentSessionStatusRepository")
public interface StudentSessionStatusRepository extends JpaRepository <StudentSessionStatus, Integer>{
	
	StudentSessionStatus findById(int studentSessionStatusId);
}
