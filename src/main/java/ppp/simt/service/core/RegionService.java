package ppp.simt.service.core;

import java.util.List;

import ppp.simt.entity.core.Region;

public interface RegionService {
	
	public List<Region> findAll();
	public Region findById(int id);

}
