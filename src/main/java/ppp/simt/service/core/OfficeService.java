package ppp.simt.service.core;

import java.util.List;

import ppp.simt.entity.core.Office;


public interface OfficeService {
	public List<Office> findAll();
	public void createOffice(Office office);
	public void updateOffice(Office office);
	public Office findOfficeById(int office_id);


}
