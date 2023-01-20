package ppp.simt.service.core;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.user.Group;
import ppp.simt.entity.user.GroupRole;
import ppp.simt.entity.user.Role;
import ppp.simt.entity.user.User;
import ppp.simt.service.user.RoleService;



@Service
public class CoreServiceImpl implements CoreService {
	
	@Autowired
	private RoleService roleService;
			
		
	@Override
	public java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }
	
	@Override
	public java.util.Date convertSqlToUtil(java.sql.Date sDate)  {
        java.util.Date uDate = new java.util.Date(sDate.getTime());
        return uDate;
    }
	
	
	/*
	 * this function check if an user has appropriate role
	 */
	@Override
    public boolean isUserHasRoleOf(String role , User user){   	
    	Role roleChecked= roleService.findByName(role);
	    Set<Role>  userRoles = user.getRoles();	
	    List<Role> roles=new ArrayList<Role>();
	    Group group=user.getGroup();
	    if(group!=null) {
	    	Set<GroupRole> groupRoles=group.getGroupRoles();
	    	List<GroupRole> groles=new ArrayList<>();
	    	groles.addAll(groupRoles);
	    	for(int i=0; i<groles.size();i++) {
	    		roles.add(groles.get(i).getRoleId());
	    	}
	    	userRoles.addAll(roles);
	    }
		if(userRoles.contains(roleChecked)){
			return true;
		}
    	return false;
    }
	
	/*
	 * this function check convert a string to sql Date
	 */
	public java.sql.Date stringToDate(String dateEntered) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date parsed = sdf.parse(dateEntered);
		return new java.sql.Date(parsed.getTime()) ;
	}
    
	
	@Override
	public Map<Integer,Float> constructMapFromStringJSON(String stringJSON){
        Map<Integer, Float> hMapData = new HashMap<Integer, Float>();
        if(stringJSON.indexOf(",")>-1){
	        String parts[] = stringJSON.split(",");
	        splittingMapPairs( parts ,  hMapData , null);  
	     }else{
	    	 splittingMapPairs( null ,  hMapData , stringJSON); 
	     }
        return hMapData;
	}
	
	@Override
	public void splittingMapPairs(String[] mapPairs , Map<Integer, Float> hashMap , String pair){
		if(pair == null){
		 for(String part : mapPairs){
			 if(part.indexOf(":") > -1 ){
	            String strPair[] = part.split(":");         
	            String strKey = strPair[0].trim();
	            String strValue = strPair[1].trim();
	            hashMap.put(Integer.valueOf(strKey), Float.valueOf(strValue));
		     }
	      }
		}else{
			if(pair.indexOf(":") > -1 ){
	            String strPair[] = pair.split(":");         
	            String strKey = strPair[0].trim();
	            String strValue = strPair[1].trim();
	            hashMap.put(Integer.valueOf(strKey), Float.valueOf(strValue));
		     }
			
		}
		
	}

	
	
	
	
	

	
}
