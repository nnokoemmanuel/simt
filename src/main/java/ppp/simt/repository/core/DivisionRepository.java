package ppp.simt.repository.core;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.core.Division;

@Repository("divisionRepository")
public interface DivisionRepository extends JpaRepository <Division, Integer>{
	
	Division findById(int divisionId);
	List<Division> findAll();

}
