package ppp.simt.service.engines;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.applicant.EntranceEligibleCenter;
import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.production.Application;
import ppp.simt.entity.production.InSlip;
import ppp.simt.entity.production.OutSlip;
import ppp.simt.entity.pv.EligibleCenter;
import ppp.simt.entity.user.User;
import ppp.simt.service.core.CoreService;


@Service
public class StateCheckerServiceImpl implements StateCheckerService {

	@Autowired
	private CoreService coreService;
	
	
	
	/*
	 * this function returns a string ok if the object can change state and ko if not
	 */
	
	@Override
	public String stateEmbedded(Object object, User user, String action){
		//check object class --- check user roles 
		
		//==== start Objects of type EligibleCenter 
        if (object instanceof EligibleCenter) {
        	EligibleCenter eligiblecenter = (EligibleCenter) object;
        	 
        	return eligibleCenterStateChecker(eligiblecenter, user, action);

    	}
      //==== end Objects of type EligibleCenter 
        
        
      //==== start Objects of type InSlip 
        else  if (object instanceof InSlip) {
        	InSlip inSlip = (InSlip) object;
     
        	return inSlipStateChecker( inSlip,  user, action);
 	     }
      //==== end Objects of type InSlip
        
        
      //==== start Objects of type OutSlip 
        else  if (object instanceof OutSlip) {
        	OutSlip outSlip = (OutSlip) object;
     	  return outSlipStateChecker( outSlip, user, action);

 	     }
        
      //==== end Objects of type OutSlip
        
        
        //==== start Objects of type EntranceEligibleCenter 
        else  if (object instanceof EntranceEligibleCenter) {
        	EntranceEligibleCenter entranceEligibleCenter = (EntranceEligibleCenter) object;
     	  return entranceEligibleCenterStateChecker( entranceEligibleCenter, user, action);

 	     }
        
      //==== end Objects of type OutSlip



		//==== start Objects of type Archive
		else  if (object instanceof Archive) {
			Archive archive = (Archive) object;
			return archiveStateChecker( archive, user, action);

		}

		//==== end Objects of type OutSlip
        
      //==== start Objects of type Archive
      		else  if (object instanceof Application) {
      			Application application = (Application) object;
      			return applicationStateChecker( application, user, action);

      		}

      		//==== end Objects of type OutSlip
        return "ko";

	}
	
	

	/*
	 *@uthor MPA
	 *this function helps to checks user role prequisites and eligiblecenter state for a given action 
	 */
	
	private String eligibleCenterStateChecker(EligibleCenter eligiblecenter, User user, String action){
		
		  if(action.equals("reset")){
  			if(coreService.isUserHasRoleOf("ROLE_PV_CONTROLLER" , user) && ( eligiblecenter.getEligibleCenterStatus().getId() == 3 ) ){
  				return "ok";
  			}else{
  				if( eligiblecenter.getEligibleCenterStatus().getId() != 3)
  					return "koBadState";
  				if(!coreService.isUserHasRoleOf("ROLE_PV_CONTROLLER" , user))
  					return "koBadRole";
  			}
  	   }else if(action.equals("close")){
  		   if(coreService.isUserHasRoleOf("ROLE_PV_CONTROLLER" , user) && ( eligiblecenter.getEligibleCenterStatus().getId() == 2 ) )
 					return "ok";
 			   else{
     				if( eligiblecenter.getEligibleCenterStatus().getId() != 2)
  					return "koBadState";
  				if(!coreService.isUserHasRoleOf("ROLE_PV_CONTROLLER" , user))
  					return "koBadRole";
 			   }
  	   }else if(action.equals("validate")){
  		   if(coreService.isUserHasRoleOf("ROLE_PV_CONTROLLER" , user) && ( eligiblecenter.getEligibleCenterStatus().getId() == 5 ) )
 				return "ok";
 			   else{
     				if( eligiblecenter.getEligibleCenterStatus().getId() != 5)
  					return "koBadState";
  				if(!coreService.isUserHasRoleOf("ROLE_PV_CONTROLLER" , user))
  					return "koBadRole";
			   }
  	   }else if(action.equals("open")){
  		   if(coreService.isUserHasRoleOf("ROLE_MANAGE_PV" , user) && ( eligiblecenter.getEligibleCenterStatus().getId() == 1 ) )
 				return "ok";
 			   else{
     				if( eligiblecenter.getEligibleCenterStatus().getId() != 1)
  					return "koBadState";
  				if(!coreService.isUserHasRoleOf("ROLE_MANAGE_PV" , user))
  					return "koBadRole";
			   }
  	   }else if(action.equals("generate")){
  		   if(coreService.isUserHasRoleOf("ROLE_GENERATE_PV" , user) && ( eligiblecenter.getEligibleCenterStatus().getId() == 3 ) ){
  			   return "ok";
  		   }else{
     				if( eligiblecenter.getEligibleCenterStatus().getId() != 3)
  					return "koBadState";
  				if(!coreService.isUserHasRoleOf("ROLE_GENERATE_PV" , user))
  					return "koBadRole";
			   }
  	   }else if(action.equals("reset_pv")){
    		   if(coreService.isUserHasRoleOf("ROLE_GENERATE_PV" , user) && ( eligiblecenter.getEligibleCenterStatus().getId() == 5 ) ){
    			 return "ok";
    		   }else{
       				if( eligiblecenter.getEligibleCenterStatus().getId() != 5)
    					return "koBadState";
    				if(!coreService.isUserHasRoleOf("ROLE_GENERATE_PV" , user))
    					return "koBadRole";
  			   }
    	   }
		  return null;
	}
	
	/*
	 *@uthor MPA
	 *this function helps to checks user role prequisites and in slip state for a given action 
	 */
	
	private String inSlipStateChecker(InSlip inSlip, User user, String action){
		
		   if(action.equals("open")){
    			if(coreService.isUserHasRoleOf("ROLE_MANAGE_INSLIP" , user) && ( inSlip.getStatus().getId() == 1  ) ){
    				return "ok";
    			}else{
    				if( inSlip.getStatus().getId() != 1)
    					return "koBadState";
    				if(!coreService.isUserHasRoleOf("ROLE_MANAGE_INSLIP" , user))
    					return "koBadRole";
    			}
    	   }else if(action.equals("close")){
    		   if(coreService.isUserHasRoleOf("ROLE_MANAGE_INSLIP" , user) && ( inSlip.getStatus().getId() == 2 ) )
   					return "ok";
   			   else{
	       				if( inSlip.getStatus().getId() != 2)
	    					return "koBadState";
	    				if(!coreService.isUserHasRoleOf("ROLE_MANAGE_INSLIP" , user))
	    					return "koBadRole";
   			   }
    	   }else if(action.equals("reset")){
    		   if(coreService.isUserHasRoleOf("ROLE_MANAGE_INSLIP" , user) && ( inSlip.getStatus().getId() == 2 ) )
   				return "ok";
   			   else{
	       				if( inSlip.getStatus().getId() != 2)
	    					return "koBadState";
	    				if(!coreService.isUserHasRoleOf("ROLE_PV_CONTROLLER" , user))
	    					return "koBadRole";
  			   }
    	   }
		   
		   return null;
    	
	}
	
	/*
	 *@uthor MPA
	 *this function helps to checks user role prequisites and out slip state for a given action 
	 */ 
	
	private String outSlipStateChecker(OutSlip outSlip, User user, String action){
		  if(action.equals("validate")){
   			if(coreService.isUserHasRoleOf("ROLE_MANAGE_OUTSLIP" , user) && ( outSlip.getStatus() == 1  ) ){
   				return "ok";
   			}else{
   				if( outSlip.getStatus() != 1)
   					return "koBadState";
   				if(!coreService.isUserHasRoleOf("ROLE_MANAGE_OUTSLIP" , user))
   					return "koBadRole";
   			}
   	   }else if(action.equals("deliver")){
   		   if(coreService.isUserHasRoleOf("ROLE_MANAGE_OUTSLIP" , user) && ( outSlip.getStatus() == 2 ) )
  					return "ok";
  			   else{
	       				if( outSlip.getStatus() != 2)
	    					return "koBadState";
	    				if(!coreService.isUserHasRoleOf("ROLE_MANAGE_OUTSLIP" , user))
	    					return "koBadRole";
  			   }
   	   }else if(action.equals("reset")){
   		   if(coreService.isUserHasRoleOf("ROLE_MANAGE_OUTSLIP" , user) && ( outSlip.getStatus() == 2 ) )
  				return "ok";
  			   else{
	       				if( outSlip.getStatus() != 2)
	    					return "koBadState";
	    				if(!coreService.isUserHasRoleOf("ROLE_MANAGE_OUTSLIP" , user))
	    					return "koBadRole";
 			   }
		  }else if(action.equals("confirm")){
			  if(coreService.isUserHasRoleOf("ROLE_MANAGE_OUTSLIP" , user) && ( outSlip.getStatus() == 3 ) )
				  return "ok";
			  else{
				  if( outSlip.getStatus() != 3)
					  return "koBadState";
				  if(!coreService.isUserHasRoleOf("ROLE_MANAGE_OUTSLIP" , user))
					  return "koBadRole";
			  }
		  }
   	
		return null;
	}

	private String archiveStateChecker(Archive archive, User user, String action){

		if(action.equals("suspend")){
			if(coreService.isUserHasRoleOf("ROLE_MANAGE_ARCHIVE" , user) && ( archive.getStatus() == "REGISTERED" ) ){
				return "ok";
			}else{
				if( archive.getStatus() != "REGISTERED" )
					return "koBadState";
				if(!coreService.isUserHasRoleOf("ROLE_MANAGE_ARCHIVE" , user))
					return "koBadRole";
			}
		}else if(action.equals("validate")){
			if(coreService.isUserHasRoleOf("ROLE_ARCHIVE_CONTROLLER" , user) && ( archive.getStatus() == "REGISTERED"  ) )
				return "ok";
			else{
				if( archive.getStatus() != "REGISTERED" )
					return "koBadState";
				if(!coreService.isUserHasRoleOf("ROLE_ARCHIVE_CONTROLLER" , user))
					return "koBadRole";
			}
		}else if(action.equals("invalidate")){
			if(coreService.isUserHasRoleOf("ROLE_ARCHIVE_CONTROLLER" , user) && ( archive.getStatus() == "VALIDATED"  || archive.getStatus() == "SUSPENDED"  ) )
				return "ok";
			else{
				if( archive.getStatus() != "VALIDATED"  || archive.getStatus() != "SUSPENDED" )
					return "koBadState";
				if(!coreService.isUserHasRoleOf("ROLE_ARCHIVE_CONTROLLER" , user))
					return "koBadRole";
			}
		}
		else if(action.equals("delete")){
			if(coreService.isUserHasRoleOf("ROLE_MANAGE_ARCHIVE" , user) && archive.getStatus().equals("REGISTERED") )
				return "ok";
			else{
				if( !archive.getStatus().equals("REGISTERED"))
					return "koBadState";
				if(!coreService.isUserHasRoleOf("ROLE_MANAGE_ARCHIVE" , user))
					return "koBadRole";
			}
		}

		return null;

	}
	
	private String applicationStateChecker(Application application, User user, String action) {
		if(action.equals("process")){
			if(coreService.isUserHasRoleOf("ROLE_PROCESS_FILE", user)){
				return "ok";
			}else{
				return "koBadRole";
			}
		}else if(action.equals("reject")){
			if(coreService.isUserHasRoleOf("ROLE_PROCESS_FILE", user) && (application.getApplicationStatus().getId() == 1 || application.getApplicationStatus().getId()== 3)){
				return "ok";
			}else{
				if( application.getApplicationStatus().getId() != 1 || application.getApplicationStatus().getId()!= 3)
					return "koBadState";
				if(!coreService.isUserHasRoleOf("ROLE_PROCESS_FILE" , user))
					return "koBadRole";
			}
		
		}else if(action.equals("reset")){
			if(coreService.isUserHasRoleOf("ROLE_PROCESS_FILE", user) || coreService.isUserHasRoleOf("ROLE_PRINT_APPLICATION", user) ){
				if(coreService.isUserHasRoleOf("ROLE_PROCESS_FILE", user) && (application.getApplicationStatus().getId() == 2 || application.getApplicationStatus().getId()== 3)) {				
					return "ok";				
				}else if(coreService.isUserHasRoleOf("ROLE_PRINT_APPLICATION", user) && (application.getApplicationStatus().getId() == 4 || application.getApplicationStatus().getId()==7)){		
					return "ok";
				}else{
					return "koBadState";
				}
			}else{
				return "koBadRole";
			}
		
		}else if(action.equals("cancel")){
			if(coreService.isUserHasRoleOf("ROLE_PROCESS_FILE", user) && (application.getApplicationStatus().getId() == 1 || application.getApplicationStatus().getId()== 2)){
				return "ok";
			}else{
				if( application.getApplicationStatus().getId() != 1 || application.getApplicationStatus().getId()!= 2)
					return "koBadState";
				if(!coreService.isUserHasRoleOf("ROLE_PROCESS_FILE" , user))
					return "koBadRole";
			}
			
		}else if(action.equals("confirm")){
			if(coreService.isUserHasRoleOf("ROLE_CONFIRM_PROCESS", user)) {
				if (application.getApplicationStatus().getId() == 1){
					return "ok";
				}else{
					return "koBadState";
				}
			}else{
				return "koBadRole";
			}
			
		}else if(action.equals("print")){
			if(coreService.isUserHasRoleOf("ROLE_PRINT_APPLICATION", user)) {
				if (application.getApplicationStatus().getId() == 4){
					return "ok";
				}else{
					return "koBadState";
				}
			}else{
				return "koBadRole";
			}
			
		}else if(action.equals("print_report")){
			if(coreService.isUserHasRoleOf("ROLE_PRINT_APPLICATION", user)) {
				if (application.getApplicationStatus().getId() == 5){
					return "ok";
				}else{
					return "koBadState";
				}
			}else{
				return "koBadRole";
			}
			
		}else if(action.equals("request_reprint")){
			if(coreService.isUserHasRoleOf("ROLE_PRINT_APPLICATION", user)) {
				if (application.getApplicationStatus().getId() == 8){
					return "ok";
				}else{
					return "koBadState";
				}
			}else{
				return "koBadRole";
			}
			
		}else if(action.equals("confirm_reject")){
			if(coreService.isUserHasRoleOf("ROLE_CONFIRM_PROCESS", user)) {
				if (application.getApplicationStatus().getId() == 2){
					return "ok";
				}else{
					return "koBadState";
				}
			}else{
				return "koBadRole";
			}
		}else if(action.equals("deliver_with_reject")){
			
			if (application.getApplicationStatus().getId() == 7){
				return "ok";
			}else{
				return "koBadState";
			}
			
		}else if(action.equals("edit")){
			
			if (application.getApplicationStatus().getId() == 1){
				return "ok";
			}else{
				return "koBadState";
			}
			
		}
		else if(action.equals("grant_reprint")){
			if(coreService.isUserHasRoleOf("ROLE_AUTHORIZE_REPRINT", user)) {
				if (application.getApplicationStatus().getId() == 11){
					return "ok";
				}else{
					return "koBadState";
				}
			}else{
				return "koBadRole";
			}
		}	
		return null;
	}
	
	/*
	 *@uthor MPA
	 *this function helps to checks user role prequisites and entrance eligiblecenter state for a given action 
	 */
	
	private String entranceEligibleCenterStateChecker(EntranceEligibleCenter eligiblecenter, User user, String action){
		
		  if(action.equals("reset")){
  			if(coreService.isUserHasRoleOf("ROLE_PV_CONTROLLER" , user) && ( eligiblecenter.getEntranceEligibleCenterStatus().getId() == 3 ) ){
  				return "ok";
  			}else{
  				if( eligiblecenter.getEntranceEligibleCenterStatus().getId() != 3)
  					return "koBadState";
  				if(!coreService.isUserHasRoleOf("ROLE_PV_CONTROLLER" , user))
  					return "koBadRole";
  			}
  	   }else if(action.equals("close")){
  		   if(coreService.isUserHasRoleOf("ROLE_PV_CONTROLLER" , user) && ( eligiblecenter.getEntranceEligibleCenterStatus().getId() == 2 ) )
 					return "ok";
 			   else{
     				if( eligiblecenter.getEntranceEligibleCenterStatus().getId() != 2)
  					return "koBadState";
  				if(!coreService.isUserHasRoleOf("ROLE_PV_CONTROLLER" , user))
  					return "koBadRole";
 			   }
  	   }else if(action.equals("validate")){
  		   if(coreService.isUserHasRoleOf("ROLE_PV_CONTROLLER" , user) && ( eligiblecenter.getEntranceEligibleCenterStatus().getId() == 5 ) )
 				return "ok";
 			   else{
     				if( eligiblecenter.getEntranceEligibleCenterStatus().getId() != 5)
  					return "koBadState";
  				if(!coreService.isUserHasRoleOf("ROLE_PV_CONTROLLER" , user))
  					return "koBadRole";
			   }
  	   }else if(action.equals("open")){
  		   if(coreService.isUserHasRoleOf("ROLE_MANAGE_PV" , user) && ( eligiblecenter.getEntranceEligibleCenterStatus().getId() == 1 ) )
 				return "ok";
 			   else{
     				if( eligiblecenter.getEntranceEligibleCenterStatus().getId() != 1)
  					return "koBadState";
  				if(!coreService.isUserHasRoleOf("ROLE_MANAGE_PV" , user))
  					return "koBadRole";
			   }
  	   }else if(action.equals("generate")){
  		   if(coreService.isUserHasRoleOf("ROLE_GENERATE_PV" , user) && ( eligiblecenter.getEntranceEligibleCenterStatus().getId() == 3 ) ){
  			   return "ok";
  		   }else{
     				if( eligiblecenter.getEntranceEligibleCenterStatus().getId() != 3)
  					return "koBadState";
  				if(!coreService.isUserHasRoleOf("ROLE_GENERATE_PV" , user))
  					return "koBadRole";
			   }
  	   }else if(action.equals("reset_pv")){
    		   if(coreService.isUserHasRoleOf("ROLE_GENERATE_PV" , user) && ( eligiblecenter.getEntranceEligibleCenterStatus().getId() == 5 ) ){
    			 return "ok";
    		   }else{
       				if( eligiblecenter.getEntranceEligibleCenterStatus().getId() != 5)
    					return "koBadState";
    				if(!coreService.isUserHasRoleOf("ROLE_GENERATE_PV" , user))
    					return "koBadRole";
  			   }
    	   }
		  return null;
	}

}
