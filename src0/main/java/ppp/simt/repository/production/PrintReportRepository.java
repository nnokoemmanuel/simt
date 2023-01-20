package ppp.simt.repository.production;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.production.Application;
import ppp.simt.entity.production.PrintReport;

@Repository("printReportRepository")
public interface PrintReportRepository extends JpaRepository <PrintReport, Integer>{
	
	PrintReport findById(int id);
	PrintReport findByApplication(Application application);

}
