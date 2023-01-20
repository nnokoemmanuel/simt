package ppp.simt.service.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.core.Country;
import ppp.simt.repository.core.CountryRepository;


@Service
public class CountryServiceImpl implements CountryService {

	@Autowired
	private CountryRepository countryRepository;
	
	
	@Override
	public List<Country> findAll() {
		return countryRepository.findAll();
	}

	
	
	@Override
	public void createCountry(Country country) {
		countryRepository.save(country);
		
	}

	@Override
	public void updateCountry(Country country) {
		countryRepository.saveAndFlush(country);
		
	}
	
	@Override
	public Country findCountryById(int countryId) {
		
		return countryRepository.findById(countryId);
	}
}
