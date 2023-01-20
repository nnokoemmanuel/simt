package ppp.simt.repository.production;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.production.ApplicationStatus;


@Repository("applicationStatusRepository")
public interface ApplicationStatusRepository extends JpaRepository <ApplicationStatus, Integer>{

    ApplicationStatus findById(int applicationStatusId);

}
