package ppp.simt.service.production;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.production.ApplicationStatus;
import ppp.simt.entity.production.ProcessType;
import ppp.simt.repository.production.ProcessTypeRepository;

import java.util.List;


@Service
public class ProcessTypeServiceImpl implements ProcessTypeService {
	
	@Autowired
	private ProcessTypeRepository processTypeRepository;


	@Override
	public List<ProcessType> findAll() {
		return processTypeRepository.findAll();
	}

	@Override
	public ProcessType findProcessTypeById(int processTypeId) {

		return processTypeRepository.findById(processTypeId);
	}
	
	@Override
	public ProcessType findByAbbreviation(String abbreviation) {

		return processTypeRepository.findByAbbreviation( abbreviation);
	}
	
	

}