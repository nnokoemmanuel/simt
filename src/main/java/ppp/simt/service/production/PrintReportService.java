package ppp.simt.service.production;

import ppp.simt.entity.production.Application;
import ppp.simt.entity.production.PrintReport;

public interface PrintReportService {
	
	public PrintReport findById(int appId);
	public PrintReport findByApplication(Application application);
	public void save(PrintReport printReport);

}
