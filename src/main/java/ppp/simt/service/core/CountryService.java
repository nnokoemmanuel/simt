package ppp.simt.service.core;

import java.util.List;

import ppp.simt.entity.core.Country;


public interface CountryService {
	public List<Country> findAll();
	public void createCountry(Country country);
	public void updateCountry(Country country);
	public Country findCountryById(int countryId);

}
