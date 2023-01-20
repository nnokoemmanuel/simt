package ppp.simt.service.engines;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.production.Application;
import ppp.simt.entity.pv.StudentSession;
import ppp.simt.service.production.ApplicationService;
import ppp.simt.service.pv.StudentSessionService;


@Service
public class QrcodeWriterServiceImpl implements QrcodeWriterService {
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private StudentSessionService candidateSessionService;
	
	@Value("${qrcode_jar_dir}")
	private String qrcodeJarFolder;
	
	@Value("${folder.qrcode.images}")
	String rootQrcodeData;
	
	@Value("${folder.qrcode.images.for.card}")
	String rootQrcodeDataCard;
	
	@Override
	public Process qrCodeGenerate(int applicationId) {
		Application application = applicationService.findAppById(applicationId);
		//List<CandidateSession> cansessionsAll = candidateSessionService.findCandidateSessionByPersonId(application.getCapacity().getPerson().getId());
		//List<Archive> archives =  (List<Archive>) application.getCapacity().getPerson().getArchives();
		
		String jarToexecute = (qrcodeJarFolder + "Qrcode/Qrcode.jar");
	   
	     String applicantId= applicationId+"p";
	        
		
		String finala = "";
		 /*if(application.getCapacity().getPerson().getCandidateSessions()!= null ){
			 if(!(application.getCapacity().getCapacityNumber()==null)) {
			 int processed = 1;
			 List<CandidateSession> canSessions = candidateSessionService.findByPersonIdAndProcessed(application.getCapacity().getPerson(), processed );
			 cansessionsAll.removeAll(canSessions);
			 for (int i = 0; i < cansessionsAll.size(); i++) {
	        	 if(cansessionsAll.get(i).getExamResult()==5) {
			 
	        		 finala=String.format(cansessionsAll.get(i).getCategory().getName(), application.getCapacity().getCapacityNumber());
	        		 String[] tab = {jarToexecute,finala,applicantId};
	        		 
	        		 try {
	        				return	Runtime.getRuntime().exec(tab);
	        				} catch (IOException e) {
	        					// TODO Auto-generated catch block
	        					e.printStackTrace();
	        				}
	        	 }
	        	 }
			 }else if (application.getCapacity().getCapacityNumber()==null) {
				 for (int i = 0; i < cansessionsAll.size(); i++) {
		        		if(cansessionsAll.get(i).getExamResult()==5) { 
		        			finala=String.format(cansessionsAll.get(i).getCategory().getName(), application.getCapacity().getCapacityNumber());
			        		String[] tab = {jarToexecute,finala,applicantId};
			        		
			        		try {
			        			return	Runtime.getRuntime().exec(tab);
			        			} catch (IOException e) {
			        				// TODO Auto-generated catch block
			        				e.printStackTrace();
			        			}
		        		}}
				
			}
		
		//String[] tab = {jarToexecute,finala,applicantId};
		
		
		
		
		 }else {
			 if(application.getCapacity().getPerson().getArchives()!= null ) {
				 for (int i = 0; i < archives.size(); i++) {
					 List<ArchiveCategory> archiveCats = (List<ArchiveCategory>) archives.get(i).getArchiveCategory();
						if(archiveCats!=null) {
							for (int n = 0; n < archiveCats.size(); n++) {
			 
								finala=String.format(archiveCats.get(n).getCategory().getName(), application.getCapacity().getCapacityNumber());
								String[] tab = {jarToexecute,finala};
								try {
									return Runtime.getRuntime().exec(tab);
								} catch (IOException e) {
					// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
				 }
			
		}
			 }*/
		return null;
		
	}

	@Override
	public String encryptInfo(String applicantInformation) {
		 String jarToexecute = (qrcodeJarFolder + "encryptor.jar");	
		 String type="encrypt";
	     String result;
	     String encryptedInfo=null;
	     applicantInformation = (applicantInformation).replace("'", " ");
	     String[] tab = {"java -jar ",jarToexecute,applicantInformation,type};
	     String command ="java -jar "+jarToexecute+" "+applicantInformation+" "+type;
	     Process process = null;
	     ProcessBuilder processBuilder=new  ProcessBuilder( "java", "-jar", jarToexecute,applicantInformation,type);
	     try {
	     Process p = processBuilder.start();
	     BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
	     
	     while((result = in.readLine()) != null){
	         encryptedInfo=result;
	     }
	     int status = p.waitFor();
	   
	     }catch (Exception e) {
			e.printStackTrace();
		}
		
	     return encryptedInfo;
	}
	
	
	@Override
	public String addVersionAndEncryptionSignature(String encryptedApplicantInformation) {
		 
	     return "1,1,"+encryptedApplicantInformation;
	}
	
	@Override
	public String saveQrcode(int applicantId,String finalApplicanInformation,String rootQrcodefolder) throws IOException {
		String jarToexecute = (qrcodeJarFolder + "Qrcode/Qrcode.jar");	
		String[] parameters = {"java -jar ",jarToexecute,finalApplicanInformation,String.valueOf(applicantId),rootQrcodeData};
		String result="success"; 
		String command ="java -jar "+jarToexecute+" "+finalApplicanInformation+" "+applicantId+" "+rootQrcodefolder;
		ProcessBuilder process =new ProcessBuilder("java","-jar",jarToexecute,finalApplicanInformation,String.valueOf(applicantId),rootQrcodeData);
		 try {
			 Process p = process.start();
		     BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		     int status = p.waitFor();
		     result="success";
			 
		} catch (InterruptedException i) {
			i.printStackTrace();
			result="failed";
		}
	return result;
	
	
	}
	
	public String generateApplicantData(int applicationId) {
	Application application = applicationService.findAppById(applicationId);
	//List<CandidateSession> cansessionsAll = candidateSessionService.findCandidateSessionByPersonId(application.getCapacity().getPerson().getId());
	List<Archive> archives = new ArrayList<Archive>();
	//archives.addAll(application.getCapacity().getPerson().getArchives());
	
		 String applicantInfo = "";
		 /*if(application.getCapacity().getPerson().getCandidateSessions()!= null ){
			 String GN,SN,DOB,POB,CAP;
			 SN=application.getCapacity().getPerson().getSurName();
			 GN=application.getCapacity().getPerson().getGivenName();
			 POB=application.getCapacity().getPerson().getPob();
			 DOB=application.getCapacity().getPerson().getDob().toString();
			 
			 if(!(application.getCapacity().getCapacityNumber()==null)) {
				 for (int i = 0; i < cansessionsAll.size(); i++) {
		        	 if(cansessionsAll.get(i).getExamResult()==5) {
		        		 CAP =cansessionsAll.get(i).getCategory().getName();
		        		 applicantInfo="SN: "+SN+"; GN: "+GN+"; POB: "+POB+"; DOB: "+DOB+"; CAP:"+CAP;
		        	 }}
				 }else if (application.getCapacity().getCapacityNumber()==null) {
					 for (int i = 0; i < cansessionsAll.size(); i++) {
			        		if(cansessionsAll.get(i).getExamResult()==5) { 
			        			CAP =cansessionsAll.get(i).getCategory().getName();
				        		 applicantInfo="SN: "+SN+"; GN: "+GN+"; POB: "+POB+"; DOB: "+DOB+"; CAP:"+CAP;
			        		}
			        		}
					 }

		 }else {
			 String GN,SN,DOB,POB,CAP;
			 SN=application.getCapacity().getPerson().getSurName();
			 GN=application.getCapacity().getPerson().getGivenName();
			 POB=application.getCapacity().getPerson().getPob();
			 DOB=application.getCapacity().getPerson().getDob().toString();
			 
			 
			 for (int i = 0; i < archives.size(); i++) {
				 List<ArchiveCategory> archiveCats = (List<ArchiveCategory>) archives.get(i).getArchiveCategory();
					if(archiveCats!=null) {
						for (int n = 0; n < archiveCats.size(); n++) {
							
							CAP =archiveCats.get(n).getCategory().getName();
							applicantInfo="SN: "+SN+"; GN: "+GN+"; POB: "+POB+"; DOB: "+DOB+"; CAP:"+CAP;
						}
						}
					}
		}*/
		 return applicantInfo;
	}
	
	
	public String generateEncryptedQrcode(int applicantId ,boolean isForProfessionalCard) throws IOException {
		String information , encryptedInfo, versionData;
		if(isForProfessionalCard){
			 information =generateApplicantData(applicantId ,isForProfessionalCard);
			 encryptedInfo = encryptInfo(information);
			 versionData=addVersionAndEncryptionSignature(encryptedInfo);
			String  result =saveQrcodeCard(applicantId,versionData,rootQrcodeDataCard);
		    return result;	
		}
		 information =generateApplicantData(applicantId);
		 encryptedInfo = encryptInfo(information);
		 versionData=addVersionAndEncryptionSignature(encryptedInfo);
		String  result =saveQrcode(applicantId,versionData,rootQrcodeData);
	    return result;
	}
	
	

	
	public String generateApplicantData(int studentSessionId , boolean isForProfessionalCard) {
		
		StudentSession studentSession = candidateSessionService.findById(studentSessionId);

		String GN,SN,DOB,POB,OPT,MAT;
		    if(studentSession.getStudent() != null ){
		    	
		    	 SN=studentSession.getStudent().getPerson().getSurName();
				 GN=studentSession.getStudent().getPerson().getGivenName();
				 POB=studentSession.getStudent().getPerson().getPob();
				 SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
				 String dobFormatted = formatDate.format(studentSession.getStudent().getPerson().getDob());
				 DOB=dobFormatted;
				 OPT=studentSession.getStudent().getSpeciality().getAbbreviation();
				 MAT=studentSession.getStudent().getReferenceNum();
				 return "SN: "+SN+"; GN: "+GN+"; POB: "+POB+"; DOB: "+DOB+"; OPTION:"+OPT+"; ID:"+MAT;
				// System.out.println("here");
				//return "SN: SAMPLE ; GN: SAMPLE;DOB: 18/02/2022; POB: YAOUNDE; FILIERE: GENIE CIVIL; PROMOTION : 2021 ";

		    }
				
		return null;
		 
	}
	
	
	@Override
	public String saveQrcodeCard(int applicantId,String finalApplicanInformation,String rootQrcodefolder) throws IOException {
		String jarToexecute = (qrcodeJarFolder + "Qrcode/Qrcode.jar");	
		String[] parameters = {"java -jar ",jarToexecute,finalApplicanInformation,String.valueOf(applicantId),rootQrcodeDataCard};
		String result="success"; 
		String command ="java -jar "+jarToexecute+" "+finalApplicanInformation+" "+applicantId+" "+rootQrcodefolder;
		ProcessBuilder process =new ProcessBuilder("java","-jar",jarToexecute,finalApplicanInformation,String.valueOf(applicantId),rootQrcodeDataCard);
		 try {
			 Process p = process.start();
		     BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		     int status = p.waitFor();
		     result="success";
			 
		} catch (InterruptedException i) {
			i.printStackTrace();
			result="failed";
		}
	return result;
	
	}
	
	
	
	}