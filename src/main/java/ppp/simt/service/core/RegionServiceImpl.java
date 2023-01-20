package ppp.simt.service.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import ppp.simt.entity.core.Region;
import ppp.simt.repository.core.RegionRepository;

@Service
public class RegionServiceImpl implements RegionService{
	
	@Autowired
	private RegionRepository regionRepository;
	
	public List<Region> findAll() {
		return regionRepository.findAll();
	}
	
	public Region findById(int regionId){
		return regionRepository.findById(regionId);
	}

}
