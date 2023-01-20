package ppp.simt.service.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import ppp.simt.entity.core.Division;
import ppp.simt.repository.core.DivisionRepository;

@Service
public class DivisionServiceImpl implements DivisionService{
	
	@Autowired
	private DivisionRepository divisionRepository;
	
	public List<Division> findAll() {
		return divisionRepository.findAll();
	}
	
	public Division findById(int divisionId){
		return divisionRepository.findById(divisionId);
	}

}
