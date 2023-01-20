package ppp.simt.repository.production;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.core.Person;
import ppp.simt.entity.production.Capacity;

@Repository("capacityRepository")
public interface CapacityRepository extends JpaRepository <Capacity, Integer>{
 List<Capacity>  findByCapacityNumber(String searchTerm);
	Capacity findById(int capacityId);
	Capacity findCapacityByCapacityNumber(String capacityNum);
	//Capacity findByPerson(Person person);
	public Capacity save(Capacity capacity);

	}
