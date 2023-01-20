package ppp.simt.service.production;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.production.Application;
import ppp.simt.entity.production.PrintReport;
import ppp.simt.repository.production.PrintReportRepository;

@Service
public class PrintReportServiceImpl implements PrintReportService{
	
	@Autowired
	private PrintReportRepository printReportRepository;

	@Override
	public PrintReport findById(int appId) {
		return printReportRepository.findById(appId);
	}

	@Override
	public PrintReport findByApplication(Application application) {
		return printReportRepository.findByApplication(application);
	}

	@Override
	public void save(PrintReport printReport) {
		printReportRepository.save(printReport);
	}
	
	

}
