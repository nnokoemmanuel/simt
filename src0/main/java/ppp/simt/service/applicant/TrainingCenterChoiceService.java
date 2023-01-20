package ppp.simt.service.applicant;

import ppp.simt.entity.applicant.Applicant;
import ppp.simt.entity.applicant.TrainingCenterChoice;
import ppp.simt.entity.pv.TrainingCenter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface TrainingCenterChoiceService {
	public TrainingCenterChoice findById(int trainingCenterChoice);

	public void save(TrainingCenterChoice trainingCenterChoice);
	public TrainingCenterChoice findTrainingCenterIdByApplicantIdAndFirstChoice(Applicant applicant, String order);

}
