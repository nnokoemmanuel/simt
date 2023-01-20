package ppp.simt.repository.core;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.core.Region;

@Repository("regionRepository")
public interface RegionRepository extends JpaRepository <Region, Integer>{
	
	Region findById(int regionId);
	List<Region> findAll();

}
