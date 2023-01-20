/**
 * Cette classe permet de manipuler les images et les fichiers
 */

package ppp.simt.controller;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.archive.ArchiveFile;
import ppp.simt.entity.production.Application;
import ppp.simt.entity.production.ApplicationFile;
import ppp.simt.entity.pv.StudentSession;
import ppp.simt.entity.pv.StudentSessionFile;
import ppp.simt.entity.user.User;
import ppp.simt.form.FileUploadForm;
import ppp.simt.service.archive.ArchiveFileServiceImpl;
import ppp.simt.service.archive.ArchiveServiceImpl;
import ppp.simt.service.production.ApplicationFileServiceImpl;
import ppp.simt.service.production.ApplicationServiceImpl;
import ppp.simt.service.production.InSlipServiceImpl;
import ppp.simt.service.pv.StudentSessionFileService;
import ppp.simt.service.pv.StudentSessionService;
import ppp.simt.service.pv.EligibleCenterServiceImpl;
import ppp.simt.service.user.UserServiceImpl;
import ppp.simt.util.Constants;
import ppp.simt.util.Logger_;
import ppp.simt.util.Tracker;


@Controller
@RequestMapping("/file")
public class FileController {
	
	public static final int TAILLE_TAMPON = 10240; // 10 ko
	public static final String CHAMP_FICHIER     = "fichier";
	@Autowired
    ServletContext context;

	@Value("${file.folder}")
	private String fileFolder;
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private ArchiveServiceImpl archiveService;
	
	@Autowired
	private InSlipServiceImpl inSlipService;
	
	@Autowired
	private EligibleCenterServiceImpl eligibleCenterService;
	
	@Autowired
	private ArchiveFileServiceImpl archiveFileService;
	
	@Autowired
	private ApplicationServiceImpl applicationService;
	
	@Autowired
	private ApplicationFileServiceImpl applicationFileService;
	
	@Autowired
	private StudentSessionService studentSessionService;

	@Autowired
	private StudentSessionFileService studentSessionFileService;

	
	
	

	@Value("${user.profile.folder}")
	private String profileFolder;
	
	@Value("${archive.file.folder}")
	private String archiveFolder;
	
	@Value("${inslip.file.folder}")
	private String inslipFolder;
	
	@Value("${eligibleCenter.file.folder}")
	private String eligibleCenterFolder;
	
	@Value("${eligibleCenter.experts.file.folder}")
	private String expertseligibleCenterFolder;

	@Value("${studentSession.file.folder}")
	private String studentSessionDiplomeFolder;

	@Value("${studentSession.photo.folder}")
	private String studentSessionPhotoFolder;

	@Value("${studentSession.signature.folder}")
	private String studentSessionSignatureFolder;

	@Value("${application.file.folder}")
	private String applicationFolder;
	
	@Value("${application.photo.folder}")
	private String applicationPhotoFolder;
	
	@Value("${application.signature.folder}")
	private String applicationSignatureFolder;
	
	@Value("${candidateSession.file.folder}")
	private String candidateSessionFolder;
	
	@Value("${trainingCenter.agreement.file.folder}")
	private String trainingCenterAgreementFileFolder;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private Logger_ logger_;
	
	@Autowired
	private Tracker tracker;
		
	/**
	 * @uthor paule generique controller to save file on the server
	 * Envoie d"une image sur le serveur
	 * param file the object file uploaded
	 * param entityID the id value in DB of the Object concerned by the upload
	 * param number the number of the file uploaded in case we upload many files for one object
	 * param entity the name of the entity concerned
	 * 
	 */
	@ResponseBody
	@RequestMapping(value="/upload", method=RequestMethod.POST )
	public String uploadFile(@ModelAttribute("fileUploadForm") @Validated FileUploadForm fileUploadForm ,BindingResult result,HttpSession session
            ,HttpServletRequest request, HttpServletResponse response  ) throws ServletException, IOException {
			if(result.hasErrors()){
				if(result.getFieldError("exension")!= null){
					logger_.log(Constants.NORMAL_LOG_DIR, "BAD file extension "+result.getFieldError("exension").getCode(),request);
					return result.getFieldError("exension").getCode();
					
				}
			}

		String filename=fileUploadForm.getFileName();
        String[] fileNameArray = filename.split("\\.");
        String extension =  fileNameArray[fileNameArray.length-1];
        String fileInDbField =null;
        String entityID=fileUploadForm.getEntityID();

        //int archivId=Integer.parseInt(entityID);

        int number=fileUploadForm.getNumber();
        String entity=fileUploadForm.getEntity();
        String candidateSessionId= null;
        
        if(number < 0 ){
        	fileInDbField = entityID + "."+ extension;
        	if(entity.equals("application")){
        		//check if application is generated from CandidateSession
        		if(entityID.contains("@")){
        			String[] splittingResult=entityID.split("@");
        			entityID=splittingResult[0];
        			candidateSessionId=splittingResult[1];
        			fileInDbField = entityID + "."+ extension;
        		}
        	}
        }else{
        	if(entity.equals("archive")){
        		fileInDbField = entityID +"_"+(number+1)+ "."+ extension;
        		
        	}else{
		        if(number >= 1 ) 
		        	fileInDbField = entityID +"_"+number+ "."+ extension; 
		        else 
		        	fileInDbField = entityID + "."+ extension;
        	}
	        	
	    }
        // Écriture du fichier sur le disque 

        switch (entity) {
		case "user":
			//Télechargement de la photo de l'utilisateur
			User user = userService.findUserById(Integer.parseInt(entityID));
	        user.setImage(fileInDbField);
	        userService.update(user);
	        buildOnDisk(fileUploadForm.getFile(), profileFolder ,fileInDbField);
	        break;
		case "archive":
			//Télechargement Archive file sur disque et création entité Archive File
			Archive archive = archiveService.findArchiveById(Integer.parseInt(entityID));
			// we create archives files and we link them to the archive
			ArchiveFile archiveFileUploaded = new ArchiveFile(fileInDbField,archive,0);
			archiveFileService.createArchiveFile(archiveFileUploaded);
	        buildOnDisk(fileUploadForm.getFile(), archiveFolder ,fileInDbField);
	        if(number >= 1)
	        	return "ok.file.uploaded-"+number;
	        break;
		case "archiveEdition":
			//Télechargement Archive file sur disque et création entité Archive File
			archive = archiveService.findArchiveById(Integer.parseInt(entityID));
			List<ArchiveFile> archiveFiles =archiveFileService.findByArchiveOrderByIdDesc(archive);
			ArchiveFile archiveFile = new ArchiveFile();
			int taile = archiveFiles.size()+1;
			archiveFile.setFileName(archive.getId()+"_"+taile+"."+ extension);
			archiveFile.setArchive(archive);
			archiveFile.setDeleted(0);
			archiveFileService.save(archiveFile);
			fileInDbField = archive.getId()+"_"+taile+"."+ extension;
			buildOnDisk(fileUploadForm.getFile(), archiveFolder ,fileInDbField);
			if(number >= 1)
				return "ok.file.uploaded-"+number;
			break;
		     
	        
		case "eligibleCenter":
			//Télechargement eligibleCenter sur disque et création entité Archive File
	        buildOnDisk(fileUploadForm.getFile(), eligibleCenterFolder ,fileInDbField);
	        break;
		case "entranceEligibleCenter":
			//Télechargement eligibleCenter sur disque et création entité Archive File
	        buildOnDisk(fileUploadForm.getFile(), expertseligibleCenterFolder ,fileInDbField);
	        break;
		case "trainingCenter":
			//Télechargement eligibleCenter sur disque et création entité Archive File
			fileInDbField = fileInDbField.split("OKWITHAGREEMENT-")[1]; //name of agreement file on disk is agreementId.pdf
	        buildOnDisk(fileUploadForm.getFile(), trainingCenterAgreementFileFolder ,fileInDbField);
	        break;
		case "studentSession":
			//Télechargement studentSession sur disque et création entité Archive File
			StudentSession studentSession= studentSessionService.findById(Integer.parseInt(entityID));
			if(number == -1) {
				buildOnDisk(fileUploadForm.getFile(), studentSessionDiplomeFolder, fileInDbField);
				StudentSessionFile studentSessionFile = new StudentSessionFile();
				studentSessionFile.setDeleted(0);
				studentSessionFile.setFileName(fileInDbField);
				studentSessionFile.setStudentSession(studentSession);
				studentSessionFileService.save(studentSessionFile);
			}
			if(number == -2) {
				buildOnDisk(fileUploadForm.getFile(), studentSessionPhotoFolder, fileInDbField);
				studentSession.setPhotoAndSignature(fileInDbField);
				studentSessionService.createStudentSession(studentSession);
			}
			if(number == -3) {
				buildOnDisk(fileUploadForm.getFile(), studentSessionSignatureFolder, fileInDbField);
				studentSession.setPhotoAndSignature(fileInDbField);
				studentSessionService.createStudentSession(studentSession);
			}
			//buildOnDisk(fileUploadForm.getFile(), studentSessionDiplomeFolder ,fileInDbField);
			break;
			case "application":
			//Télechargement Application file  sur disque et création entité Archive File
			Application application = applicationService.findAppById(Integer.parseInt(entityID));
			// we create applications files and we link them to the application
			

	        if(number < 0){
		        if(number == -1){
		        	//cas photo
		        	System.out.println(application.getPhoto());
		        	 application.setPhoto(fileInDbField);
		        	 applicationService.updateApplication(application);
		        	 buildOnDisk(fileUploadForm.getFile(), applicationPhotoFolder ,fileInDbField);
		        	 User currentUser = userService.getLogedUser(request);
		        	 if(candidateSessionId != null){
		        		 //photo has been modified during processing form
		        		 tracker.track(application, "MODIFIED THE PHOTO FROM CANDIDATE SESSION REF " +candidateSessionId + " WHEN PROCESSING APPLICATION ", request);
						 logger_.log(Constants.NORMAL_LOG_DIR, "USER WITH ID "+currentUser.getId()+" AND USERNAME "+currentUser.getUsername()+ "MODIFIED THE PHOTO FROM CANDIDATE SESSION REF : "+candidateSessionId+" WHEN PROCESSING APPLICATION ID "+application.getId()); 
		        	 }
		        }
		        if(number == -2){
		        	//cas signature
		        	 application.setSignature(fileInDbField);
		        	 applicationService.updateApplication(application);
		        	 buildOnDisk(fileUploadForm.getFile(), applicationSignatureFolder ,fileInDbField);
		        	 User currentUser = userService.getLogedUser(request);
		        	 if(candidateSessionId != null){
		        		 //photo has been modified during processing form
		        		 tracker.track(application, "MODIFIED THE SIGNATURE FROM CANDIDATE SESSION REF " +candidateSessionId + " WHEN PROCESSING APPLICATION ", request);
						 logger_.log(Constants.NORMAL_LOG_DIR, "USER WITH ID "+currentUser.getId()+" AND USERNAME "+currentUser.getUsername()+ "MODIFIED THE PHOTO FROM CANDIDATE SESSION REF : "+candidateSessionId+" WHEN PROCESSING APPLICATION ID "+application.getId()); 
		        	 }
		        }
            }else{
            	ApplicationFile applicationFileUploaded = new ApplicationFile(fileInDbField,application,0);
    			applicationFileService.createApplicationFile(applicationFileUploaded);
		        if(number >= 0 ){
		        	 buildOnDisk(fileUploadForm.getFile(), applicationFolder ,fileInDbField);
		        }
		        if(number >= 1){
		        	return "ok.file.uploaded-"+number;
		        }
            }
	        break; 
		default:
			break;
		}

        return "ok.file.uploaded"; 
	}
	
	/**
	 * @uthor MPA
	 * fonction generique qui récupère une image sur le server
	 * @param folderName nom du dossier contenant le fichier de nom fileName
	 * @param fileName nom du fichier
	 * @param response
	 * @throws IOException
	 */
	
	@RequestMapping(value="/download")
	public void getFile(@RequestParam String fileName,@RequestParam String folderName, HttpServletResponse response) throws IOException{

		String realFolderName=env.getProperty(folderName);
		try{  
		File file = new File(realFolderName+fileName);
        response.setHeader("Content-Type", context.getMimeType(fileName));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");    
        Files.copy(file.toPath(), response.getOutputStream());  
		}catch(Exception e){System.out.println(e);}
	}
	
	
	/**
	 * @uthor MPA
	 * fonction generique function to create file in file System
	 * param folder string value of the directory where the file will be saved
	 * param fileInDBName string value of the name of the file stored in database
	 */
	public void buildOnDisk(MultipartFile fileUploaded,String folder ,String fileInDBName){
		
		String fileOutputName = folder+fileInDBName;
		
		  try{  
		  
		  byte barr[]=fileUploaded.getBytes();  
	        BufferedOutputStream bout=new BufferedOutputStream(  
	        		new FileOutputStream(fileOutputName));
	        bout.write(barr);  
	        bout.flush();  
	        bout.close();  
	        }catch(Exception e){System.out.println(e);}
	}
	/**
	 * @uthor MPA
	 * fonction generique function to auto add attachement file on archive create/edit view
	 * this function returns either block file view or row file view
	 * 
	 */
	 @RequestMapping(value="/load/documents/{entityConcerned}/{isAnewRowBlockType}", method=RequestMethod.GET)
	 public String loadNewFileBlock(HttpServletRequest httpServletRequest,Model model,@PathVariable("entityConcerned") String entityConcerned, @PathVariable("isAnewRowBlockType") int isAnewRowBlockType) {
		 if(entityConcerned.equals("archive")){
		    if(isAnewRowBlockType==0)
		    	return "/core/archive/addedFiles/new-file-block";
	        return "/core/archive/addedFiles/new-file-row";
		 }else if(entityConcerned.equals("application")){
			    if(isAnewRowBlockType==0)
			    	return "/core/application/addedFiles/new-file-block";
		        return "/core/application/addedFiles/new-file-row";
		}
		 return "none";
	 }
	
	
	
}
