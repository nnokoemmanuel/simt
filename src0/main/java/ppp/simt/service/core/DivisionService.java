package ppp.simt.service.core;

import java.util.List;

import ppp.simt.entity.core.Division;

public interface DivisionService {
	
	public List<Division> findAll();
	public Division findById(int id);

}
