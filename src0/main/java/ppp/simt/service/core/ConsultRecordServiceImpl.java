package ppp.simt.service.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.core.Person;
import ppp.simt.entity.evaluation.Transcript;
import ppp.simt.entity.production.Application;
import ppp.simt.entity.production.ApplicationTranscript;
import ppp.simt.entity.production.Capacity;
import ppp.simt.entity.production.Certificate;
import ppp.simt.entity.production.ProcessType;
import ppp.simt.entity.production.ProfessionalCard;
import ppp.simt.entity.pv.Student;
import ppp.simt.entity.pv.StudentSession;
import ppp.simt.form.ConsultRecordForm;
import ppp.simt.repository.archive.ArchiveRepository;
import ppp.simt.repository.core.PersonRepository;
import ppp.simt.repository.production.CapacityRepository;
import ppp.simt.repository.pv.StudentSessionRepository;
import ppp.simt.service.archive.ArchiveService;
import ppp.simt.service.evaluation.TranscriptService;
import ppp.simt.service.production.ApplicationService;
import ppp.simt.service.production.ApplicationTranscriptService;
import ppp.simt.service.production.CertificateService;
import ppp.simt.service.production.ProcessTypeService;
import ppp.simt.service.production.ProfessionalCardService;
import ppp.simt.service.pv.StudentService;
import ppp.simt.service.pv.StudentSessionService;

@Service
public  class  ConsultRecordServiceImpl implements ConsultRecordService  {
	@Autowired
	 private PersonRepository personRepository;
	
	@Autowired 
	StudentSessionRepository studentSessionRepository;
	
	@Autowired
	private ArchiveRepository archiverRepository;
	
	@Autowired
	private CapacityRepository capacityRepository;
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private StudentSessionService studentSessionService;
	
	@Autowired
	private CertificateService certificateService;
	
	@Autowired
	private ProfessionalCardService professionalCardService;
	
	@Autowired
	private TranscriptService transcriptService;
	
	@Autowired
	private ArchiveService archiveService;
	
	@Autowired
	private ApplicationTranscriptService  applicationtranscriptService;
	
	@Autowired
	private ProcessTypeService  processTypeService;

	@Override
	public List<ConsultRecordForm> findRecord(String searchTerm) {
		 List<ConsultRecordForm> records =new ArrayList<ConsultRecordForm>();

		 /*
		  * checking if the cherche term is an archive
		  */
		 int maxApplication=0 ;
		 int tmpId=0 ;
		 String photo="";
	//	 List<Archive> archives=archiverRepository.findByArchiveNumber(searchTerm);
		 
		 Person person = personRepository.findPersonByLicenseNum(searchTerm);
		 System.out.println("person view "+person);	
		 
		 if(person!=null) {
			 
			 List<Student> students = studentService.findByPerson(person);
			 System.out.println(students);
			 List<StudentSession> studentSessions = new ArrayList<StudentSession>();
			 List<StudentSession> approvedStudentSessions = new ArrayList<StudentSession>();
			 for(int j=0; j<students.size();j++){
				 List<StudentSession> currentStudentSessions = studentSessionService.findByStudent(students.get(j));
				 
				 studentSessions.addAll(currentStudentSessions);
			 }

			 
			 //filter studentsessions to establish only those which were at least approved
			 for (int k=0; k<studentSessions.size();k++){
				 if(studentSessions.get(k).getStudentSessionStatus().getId()!=1 || studentSessions.get(k).getStudentSessionStatus().getId()!=3){
					 approvedStudentSessions.add(studentSessions.get(k));
				 }
			 }
			 
			 
			 ConsultRecordForm consultRecord = new ConsultRecordForm();
			 consultRecord.setPerson(person);
			 consultRecord.setStudent(students);
			 //determine max application of studentSession
			 	for (int l=0;l<approvedStudentSessions.size();l++){
			 		if(approvedStudentSessions.get(l).getId()>maxApplication){
			 			maxApplication = approvedStudentSessions.get(l).getId();
			 			photo = approvedStudentSessions.get(l).getPhotoAndSignature();
			 			consultRecord.setPhoto(photo);
			 			consultRecord.setPhotoSource("studentSessionPhotoFolder");
					 	consultRecord.setLastCandidatePhoto(photo);
					 	System.out.println("step1");
					 	consultRecord.setRecordOrigin("studentSession");
					 	consultRecord.setLatestSpeciality(approvedStudentSessions.get(l).getStudent().getSpeciality().getAbbreviation());
					 	List<StudentSession> studentChoosen = new ArrayList<StudentSession>();
					 	studentChoosen.add( approvedStudentSessions.get(l));
					 	consultRecord.setStudentSessions(studentChoosen);
//					 	if(approvedStudentSessions.get(l).getCertificate()!=null){
//					 	//	consultRecord.setLastCertificate(approvedStudentSessions.get(l).getCertificate().getType());
//					 	}
			 		}
			 	}
			 	 System.out.println(1);
			 records.add(consultRecord);			 
		 }else{
			 //big search person

			 List<Person> persons =personRepository.findCandidates(searchTerm);
			 if(persons.size() > 0){
			 for(int i=0; i<persons.size(); i++) {
				 List<Student> students = studentService.findByPerson(persons.get(i));
				 List<StudentSession> studentSessions = new ArrayList<StudentSession>();
				 List<StudentSession> approvedStudentSessions = new ArrayList<StudentSession>();
				 if(students.size() > 0 ){
				 for(int j=0; j<students.size();j++){
					 List<StudentSession> currentStudentSessions = studentSessionService.findByStudent(students.get(j));		 
					 studentSessions.addAll(currentStudentSessions);
				 }
				 //filter studentsessions to establish only those which were at least approved
				 for (int k=0; k<studentSessions.size();k++){
					 if(studentSessions.get(k).getStudentSessionStatus().getId()!=1 || studentSessions.get(k).getStudentSessionStatus().getId()!=3){
						 approvedStudentSessions.add(studentSessions.get(k));
					 }
				 }
				 
				 
				 ConsultRecordForm consultRecord = new ConsultRecordForm();
				 consultRecord.setPerson(persons.get(i));
				 consultRecord.setStudent(students);
				 //determine max application of studentSession
				 	for (int l=0;l<approvedStudentSessions.size();l++){
				 		if(approvedStudentSessions.get(l).getId()>maxApplication){
				 			maxApplication = approvedStudentSessions.get(l).getId();
				 			photo = approvedStudentSessions.get(l).getPhotoAndSignature();
				 			consultRecord.setPhoto(photo);
				 			consultRecord.setPhotoSource("studentSessionPhotoFolder");
						 	consultRecord.setLastCandidatePhoto(photo);
						 	 System.out.println(2+"yessssss icu");
						 
//						 	if(approvedStudentSessions.get(l).getCertificate()!=null){
//						 	//	consultRecord.setLastCertificate(approvedStudentSessions.get(l).getCertificate().getType());
//						 	}
				 		}
				 	}
	 
				
				 System.out.println(2);
				 
				 
				 Set<Student> listOfStudents = persons.get(i).getStudents();
				 List<Student> arrayListOfStudents = new ArrayList<Student>(listOfStudents);
				 if(persons.get(i).getStudents().size() > 0){
					 List<StudentSession> listOfSessions = new ArrayList<StudentSession> ();
					 for(Student student : listOfStudents){
                          listOfSessions.addAll(student.getStudentSessions());	
					 }
					 Comparator<Student> comparatorStudent = Comparator.comparing(Student::getId);
					 Student maxStudent = arrayListOfStudents.stream().max(comparatorStudent).get();
					 if(maxStudent.getStudentSessions().size() > 0){
						 System.out.println("step2");
						 consultRecord.setRecordOrigin("studentSession"); 
						 Comparator<StudentSession> comparator = Comparator.comparing(StudentSession::getId);
						 StudentSession maxSession = listOfSessions.stream().max(comparator).get();
						 consultRecord.setLatestSpeciality(maxSession.getStudent().getSpeciality().getAbbreviation());
						 photo = maxSession.getPhotoAndSignature();
				 		 consultRecord.setPhoto(photo);
				 		 consultRecord.setPhotoSource("studentSessionPhotoFolder");
						 consultRecord.setLastCandidatePhoto(photo);
						 List<StudentSession> addedMaxSession = new ArrayList<StudentSession>();
						 addedMaxSession.add(maxSession);
						 consultRecord.setStudentSessions(addedMaxSession);
					 }else{
						 consultRecord.setRecordOrigin("student");
						 consultRecord.setLatestSpeciality(maxStudent.getSpeciality().getAbbreviation());
					 }
					 
					 
				 }else{
					 consultRecord.setRecordOrigin("person");	
				 }
				 
				 records.add(consultRecord);
				 photo = "";
				 
			 }
		     //we look for archives
			 if(persons.get(i).getArchive() != null ){
				 ConsultRecordForm consultRecord = new ConsultRecordForm();
				 consultRecord.setPerson(persons.get(i));
				 consultRecord.setStudent(null);
				 //determine max application of studentSession
				 photo = persons.get(i).getArchive().getPhotoAndSignature();
				 consultRecord.setPhoto(photo);
				 consultRecord.setPhotoSource("archivePhotoFolder");
				 consultRecord.setRecordOrigin("archive");
				 consultRecord.setLastCandidatePhoto(photo);
				 consultRecord.setArchive(persons.get(i).getArchive());
				 if(persons.get(i).getStudents().size() == 0){
					 consultRecord.setLatestSpeciality("MAE");
					 if(persons.get(i).getArchive().getProfessionalCard() != null){
						 if(persons.get(i).getArchive().getProfessionalCard().getIsPrinted() ==1){
							 ProcessType duplicateOfProfCard = processTypeService.findByAbbreviation("DP");
							 List<ProcessType> possibleProcessType = new ArrayList<ProcessType>();
							 possibleProcessType.add(duplicateOfProfCard);
							 consultRecord.setPossibleProcessType(possibleProcessType);
					     }
				     }else{
						 ProcessType newOfProfCard = processTypeService.findByAbbreviation("NP");
						 List<ProcessType> possibleProcessType = new ArrayList<ProcessType>();
						 possibleProcessType.add(newOfProfCard);
						 consultRecord.setPossibleProcessType(possibleProcessType);
					 }
				 }else{
					 Set<Student> listOfStudents = persons.get(i).getStudents();
					 List<StudentSession> listOfSessions = new ArrayList<StudentSession> ();
					 for(Student student : listOfStudents){
						 listOfSessions.addAll(student.getStudentSessions());
					 }
					 if(listOfSessions.size() > 0){
						 for(StudentSession session:listOfSessions){
							 if(session.getCertificate() != null){
								 if(session.getCertificate().getIsPrinted()==1){
									 ProcessType processType = processTypeService.findByAbbreviation("DC");
									 List<ProcessType> possibleProcessType = new ArrayList<ProcessType>();
									 possibleProcessType.add(processType);
									 consultRecord.setPossibleProcessType(possibleProcessType);
								 }
								 if(session.getCertificate().getProfessionalCard()==null){
									 ProcessType processType = processTypeService.findByAbbreviation("NP");
									 List<ProcessType> possibleProcessType = new ArrayList<ProcessType>();
									 possibleProcessType.add(processType);
									 consultRecord.setPossibleProcessType(possibleProcessType);
									 
								 }else{
									 if(session.getCertificate().getProfessionalCard().getIsPrinted()==1){
										 ProcessType processType = processTypeService.findByAbbreviation("DP");
										 List<ProcessType> possibleProcessType = new ArrayList<ProcessType>();
										 possibleProcessType.add(processType);
										 consultRecord.setPossibleProcessType(possibleProcessType);
									 }
								 }
							 }else{
								 ProcessType processType = processTypeService.findByAbbreviation("NC");
								 List<ProcessType> possibleProcessType = new ArrayList<ProcessType>();
								 possibleProcessType.add(processType);
								 consultRecord.setPossibleProcessType(possibleProcessType);
							 }
							 
							 if(session.getApplicationTranscript() != null){
								 if(session.getApplicationTranscript().getIsPrinted()==1){
									 ProcessType processType = processTypeService.findByAbbreviation("DT");
									 List<ProcessType> possibleProcessType = new ArrayList<ProcessType>();
									 possibleProcessType.add(processType);
									 consultRecord.setPossibleProcessType(possibleProcessType);
								 }
							 }else{
								 ProcessType processType = processTypeService.findByAbbreviation("NT");
								 List<ProcessType> possibleProcessType = new ArrayList<ProcessType>();
								 possibleProcessType.add(processType);
								 consultRecord.setPossibleProcessType(possibleProcessType);
							 }
							 
						 }
						 Comparator<StudentSession> comparator = Comparator.comparing(StudentSession::getId);
						 StudentSession maxSession = listOfSessions.stream().max(comparator).get();
						 consultRecord.setLatestSpeciality(maxSession.getStudent().getSpeciality().getAbbreviation());
				     }
				 }
				 
				 System.out.println(3);
				 if(persons.get(i).getArchive() != null && persons.get(i).getStudents().size() == 0)
					 records.add(consultRecord);
			 }
				
			 }
		  }else{
			
			  //search in certificate 
			  Certificate certificate = certificateService.findByNumber(searchTerm);
			  if(certificate != null){
				     ConsultRecordForm consultRecord = new ConsultRecordForm();
					 consultRecord.setPerson(certificate.getStudentSession().getStudent().getPerson());
					 List<Student> studentsFound = new ArrayList<Student>();
					 studentsFound.add(certificate.getStudentSession().getStudent());
					 consultRecord.setStudent(studentsFound);
					 //determine max application of studentSession
					 photo = certificate.getStudentSession().getPhotoAndSignature();
					 consultRecord.setPhoto(photo);
					 consultRecord.setRecordOrigin("certificate");
					 consultRecord.setCertificate(certificate);
					 consultRecord.setPhotoSource("studentSessionPhotoFolder");
					 consultRecord.setLastCandidatePhoto(photo);
					 consultRecord.setLatestSpeciality(certificate.getStudentSession().getStudent().getSpeciality().getAbbreviation());
					 if(certificate.getIsPrinted() ==1){
						 ProcessType duplicateOfProfCard = processTypeService.findByAbbreviation("DC");
						 List<ProcessType> possibleProcessType = new ArrayList<ProcessType>();
						 possibleProcessType.add(duplicateOfProfCard);
						 consultRecord.setPossibleProcessType(possibleProcessType);
				     }else{
						 ProcessType duplicateOfProfCard = processTypeService.findByAbbreviation("NC");
						 List<ProcessType> possibleProcessType = new ArrayList<ProcessType>();
						 possibleProcessType.add(duplicateOfProfCard);
						 consultRecord.setPossibleProcessType(possibleProcessType);
					 }
					 System.out.println(4);
					 records.add(consultRecord);
				  
			  }else{
				  //search in profesional card
				  ProfessionalCard professionalCard = professionalCardService.findByMatricule(searchTerm);
				  if(professionalCard != null){
					     if(professionalCard.getCertificate() != null ){
						     ConsultRecordForm consultRecord = new ConsultRecordForm();
							 consultRecord.setPerson(professionalCard.getCertificate().getStudentSession().getStudent().getPerson());
							 List<Student> studentsFound = new ArrayList<Student>();
							 studentsFound.add(professionalCard.getCertificate().getStudentSession().getStudent());
							 consultRecord.setStudent(studentsFound);
							 //determine max application of studentSession
							 photo = professionalCard.getCertificate().getStudentSession().getPhotoAndSignature();
							 consultRecord.setPhoto(photo);
							 consultRecord.setRecordOrigin("professionalCard");
							 consultRecord.setProfessionalCard(professionalCard);
							 consultRecord.setPhotoSource("studentSessionPhotoFolder");
							 consultRecord.setLastCandidatePhoto(photo);
							 consultRecord.setLatestSpeciality(professionalCard.getCertificate().getStudentSession().getStudent().getSpeciality().getAbbreviation());
							 System.out.println(5);
							 if(professionalCard.getIsPrinted() ==1){
								 ProcessType duplicateOfProfCard = processTypeService.findByAbbreviation("DP");
								 List<ProcessType> possibleProcessType = new ArrayList<ProcessType>();
								 possibleProcessType.add(duplicateOfProfCard);
								 consultRecord.setPossibleProcessType(possibleProcessType);
						     }else{
								 ProcessType duplicateOfProfCard = processTypeService.findByAbbreviation("NP");
								 List<ProcessType> possibleProcessType = new ArrayList<ProcessType>();
								 possibleProcessType.add(duplicateOfProfCard);
								 consultRecord.setPossibleProcessType(possibleProcessType);
							 }
							 records.add(consultRecord);
				         }else{
				        	 ConsultRecordForm consultRecord = new ConsultRecordForm();
							 consultRecord.setPerson(professionalCard.getArchive().getPerson());
							 consultRecord.setStudent(null);
							 //determine max application of studentSession
							 photo = professionalCard.getArchive().getPhotoAndSignature();
							 consultRecord.setPhoto(photo);
							 consultRecord.setRecordOrigin("professionalCard");
							 consultRecord.setProfessionalCard(professionalCard);
							 consultRecord.setPhotoSource("archivePhotoFolder");
							 consultRecord.setLastCandidatePhoto(photo);
							 consultRecord.setLatestSpeciality("MAE");

							 if(professionalCard.getIsPrinted() ==1){
								 ProcessType duplicateOfProfCard = processTypeService.findByAbbreviation("DP");
								 List<ProcessType> possibleProcessType = new ArrayList<ProcessType>();
								 possibleProcessType.add(duplicateOfProfCard);
								 consultRecord.setPossibleProcessType(possibleProcessType);
						     }else{
								 ProcessType duplicateOfProfCard = processTypeService.findByAbbreviation("NP");
								 List<ProcessType> possibleProcessType = new ArrayList<ProcessType>();
								 possibleProcessType.add(duplicateOfProfCard);
								 consultRecord.setPossibleProcessType(possibleProcessType);
							 }
							 System.out.println(6);
							 records.add(consultRecord);
				        	 
				         }
					  
				  }else{
					  // search in transcript
					  ApplicationTranscript applicationTranscript = applicationtranscriptService.findByMatricule(searchTerm);
					  if(applicationTranscript!=null){
						     ConsultRecordForm consultRecord = new ConsultRecordForm();
							 consultRecord.setPerson(applicationTranscript.getStudentSession().getStudent().getPerson());
							 List<Student> studentsFound = new ArrayList<Student>();
							 studentsFound.add(applicationTranscript.getStudentSession().getStudent());
							 consultRecord.setStudent(studentsFound);
							 //determine max application of studentSession
							 photo = applicationTranscript.getStudentSession().getPhotoAndSignature();
							 consultRecord.setPhoto(photo);
							 consultRecord.setPhotoSource("studentSessionPhotoFolder");
							 consultRecord.setRecordOrigin("applicationTranscript");
							 consultRecord.setApplicationTranscript(applicationTranscript);
							 consultRecord.setLastCandidatePhoto(photo);
							 consultRecord.setLatestSpeciality(applicationTranscript.getStudentSession().getStudent().getSpeciality().getAbbreviation());
							 
								 if(applicationTranscript.getIsPrinted() ==1){
									 ProcessType duplicateOfProfCard = processTypeService.findByAbbreviation("DT");
									 List<ProcessType> possibleProcessType = new ArrayList<ProcessType>();
									 possibleProcessType.add(duplicateOfProfCard);
									 consultRecord.setPossibleProcessType(possibleProcessType);
							     }else{
									 ProcessType duplicateOfProfCard = processTypeService.findByAbbreviation("NT");
									 List<ProcessType> possibleProcessType = new ArrayList<ProcessType>();
									 possibleProcessType.add(duplicateOfProfCard);
									 consultRecord.setPossibleProcessType(possibleProcessType);
								 }
							 
							 System.out.println(7);
							 records.add(consultRecord); 
					  }else{
						  //search in archives
						  List<Archive> archives = archiveService.findByArchiveNumber(searchTerm);
						  if(archives.size() > 0){
							  for(Archive archive : archives){
								     ConsultRecordForm consultRecord = new ConsultRecordForm();
									 consultRecord.setPerson(archive.getPerson());
									 consultRecord.setStudent(null);
									 //determine max application of studentSession
									 photo = archive.getPhotoAndSignature();
									 consultRecord.setPhoto(photo);
									 consultRecord.setPhotoSource("archivePhotoFolder");
									 consultRecord.setLastCandidatePhoto(photo);
									 consultRecord.setRecordOrigin("archive");
									 consultRecord.setArchive(archive);
									 consultRecord.setLatestSpeciality("MAE");
									 
									 if(archive.getProfessionalCard() != null){
										 if(archive.getProfessionalCard().getIsPrinted() ==1){
											 ProcessType duplicateOfProfCard = processTypeService.findByAbbreviation("DP");
											 List<ProcessType> possibleProcessType = new ArrayList<ProcessType>();
											 possibleProcessType.add(duplicateOfProfCard);
											 consultRecord.setPossibleProcessType(possibleProcessType);
									     }else{
											 ProcessType duplicateOfProfCard = processTypeService.findByAbbreviation("NP");
											 List<ProcessType> possibleProcessType = new ArrayList<ProcessType>();
											 possibleProcessType.add(duplicateOfProfCard);
											 consultRecord.setPossibleProcessType(possibleProcessType);
										 }
									 }
									 System.out.println(8);
									 records.add(consultRecord);  
							  }
						  }

					  }
					  System.out.println("search not implemented");
					  
				  }
				  
			  }
			  
		  }

		 }
 
		 return records;
		
	}

}
