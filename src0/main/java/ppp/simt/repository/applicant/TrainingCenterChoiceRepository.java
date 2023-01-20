package ppp.simt.repository.applicant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.applicant.Applicant;
import ppp.simt.entity.applicant.TrainingCenterChoice;
import ppp.simt.entity.pv.TrainingCenter;


@Repository("trainingCenterChoiceRepository")
public interface TrainingCenterChoiceRepository extends JpaRepository <TrainingCenterChoice, Integer>{

	TrainingCenterChoice findById(int trainingCenterChoiceId);

	public TrainingCenterChoice save(TrainingCenterChoice trainingCenterChoice);
	
	@Query("SELECT cs FROM TrainingCenterChoice cs WHERE cs.applicant=:applicant AND cs.order=:order")
	public TrainingCenterChoice findTrainingCenterIdByApplicantIdAndFirstChoice(Applicant applicant, String order);
	
	

}
