package ppp.simt.service.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.core.Office;
import ppp.simt.repository.user.OfficeRepository;


@Service
public class OfficeServiceImpl implements OfficeService {

	@Autowired
	private OfficeRepository regionRepository;
	
	
	@Override
	public List<Office> findAll() {
		return regionRepository.findAll();
	}

	
	
	@Override
	public void createOffice(Office office) {
		regionRepository.save(office);
		
	}

	@Override
	public void updateOffice(Office office) {
		regionRepository.saveAndFlush(office);
		
	}
	
	@Override
	public Office findOfficeById(int office_id) {
		
		return regionRepository.findById(office_id);
	}
}
