package ppp.simt.service.production;

import ppp.simt.entity.core.Person;
import ppp.simt.entity.production.Capacity;

import java.util.List;


public interface CapacityService {
	public List<Capacity> findAll();
	public void createCapacity(Capacity capacity);
	public void updateCapacity(Capacity capacity);
	public Capacity findCapacityById(int capacityId);
	public String generateCapacityNum(int applicationId);
	public java.sql.Date capacityExpiryDateCal(int applicationId,Capacity capacityNew);
	public Capacity findCapacityByCapacityNumber(String capacityNum);
	//public Capacity findByPerson(Person person);
	public Capacity save(Capacity capacity);
	
	
}
