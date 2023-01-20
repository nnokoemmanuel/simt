/**
 * this class contains services called by others classes independently from entities services
 */
package ppp.simt.service.core;


import java.text.ParseException;
import java.util.Map;

import ppp.simt.entity.user.User;

public interface CoreService {
	public java.sql.Date convertUtilToSql(java.util.Date uDate);
	public java.util.Date convertSqlToUtil(java.sql.Date sDate);
    public boolean isUserHasRoleOf(String role , User user);
    public java.sql.Date stringToDate(String dateEntered)  throws ParseException ;
    public Map<Integer,Float>  constructMapFromStringJSON(String stringJSON);
    public void splittingMapPairs(String[] mapPairs , Map<Integer,Float>  hashMap , String pair);

}
