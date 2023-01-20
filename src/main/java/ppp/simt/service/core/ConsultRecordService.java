package ppp.simt.service.core;

import java.util.List;

import ppp.simt.form.ConsultRecordForm;

public interface  ConsultRecordService {
	 
  List<ConsultRecordForm> findRecord(String searchTerm);
  
}
