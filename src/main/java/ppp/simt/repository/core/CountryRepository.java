package ppp.simt.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.core.Country;



@Repository("countryRepository")
public interface CountryRepository extends JpaRepository <Country, Integer>{
	
	Country findById(int countryId);

}