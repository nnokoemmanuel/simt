package ppp.simt.service.production;

import java.util.List;

import ppp.simt.entity.production.ProcessType;


public interface ProcessTypeService {

	public List<ProcessType> findAll();
	public ProcessType findProcessTypeById(int processTypeId);
	public ProcessType findByAbbreviation(String abbreviation);

}