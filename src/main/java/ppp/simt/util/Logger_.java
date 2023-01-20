package ppp.simt.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.service.user.UserService;
import ppp.simt.util.Constants;

@Service("logger_")
public  class Logger_  {
    private static String PathToFile="";
    private static String MainLogFolder="";
    private static String SubLogFolders="";
    @Autowired
	private  UserService userService;
	
	public  void log(String destination,String message,HttpServletRequest httpServletRequest)
	{
	  MainLogFolder=getLogDir(destination);
	  if(message==""){
		 MainLogFolder=Constants.ROOT_LOOG_DIR+Constants.EXCEPTION_LOG_DIR;
	     message="Empty log information";
	  } 
	  SubLogFolders=Calendar.getInstance().get(Calendar.YEAR)+"/"+(Calendar.getInstance().get(Calendar.MONTH)+1);
	  mkdir();
	  PathToFile=MainLogFolder+SubLogFolders+"/"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+".txt";
	  writeToFile(PathToFile, message,httpServletRequest);
	  
	}
	public  void log(String destination,String message)
	{
	  MainLogFolder=getLogDir(destination);
	  if(message==""){
		 MainLogFolder=Constants.ROOT_LOOG_DIR+Constants.EXCEPTION_LOG_DIR;
	     message="Empty log information";
	  } 
	  SubLogFolders=Calendar.getInstance().get(Calendar.YEAR)+"/"+(Calendar.getInstance().get(Calendar.MONTH)+1);
	  mkdir();
	  PathToFile=MainLogFolder+SubLogFolders+"/"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+".txt";
	  writeToFile(PathToFile, message,null);
	  
	}
	
	private  String  getLogDir(String destination){
       switch (destination){
       case Constants.ACCESS_DENIED_LOG_DIR:
    	   MainLogFolder=Constants.ROOT_LOOG_DIR+Constants.ACCESS_DENIED_LOG_DIR;
    	   break;
       case Constants.EXCEPTION_LOG_DIR:
    	   MainLogFolder=Constants.ROOT_LOOG_DIR+Constants.EXCEPTION_LOG_DIR;
    	   break;
       case Constants.FAILED_CONNEXION_LOG_DIR:
    	   MainLogFolder=Constants.ROOT_LOOG_DIR+Constants.FAILED_CONNEXION_LOG_DIR;
    	   break;
       case Constants.NORMAL_LOG_DIR:
    	   MainLogFolder=Constants.ROOT_LOOG_DIR+Constants.NORMAL_LOG_DIR;
    	   break;
       default:
    	   MainLogFolder=Constants.ROOT_LOOG_DIR+Constants.EXCEPTION_LOG_DIR;
       }
       return MainLogFolder;
	}
	
	private   void writeToFile(String filename,String message,HttpServletRequest httpServletRequest){
	  
		try(FileWriter fw = new FileWriter(filename, true);
		    BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw))
			{
			    if (httpServletRequest!=null)
			    out.println(userService.getLogedUser(httpServletRequest).getUsername()+": " +new SimpleDateFormat("dd MMM yyyy HH:mm:ss").format(Calendar.getInstance().getTime())+" ==> "+message);
			    else  out.println(new SimpleDateFormat("dd MMM yyyy HH:mm:ss").format(Calendar.getInstance().getTime())+" ==> "+message);
			    out.close();
			} catch (IOException e) {
			    //exception handling left as an exercise for the reader
			}
	 }
	
	private static  void mkdir(){
	File file = new File(MainLogFolder+SubLogFolders);
	file.mkdirs();
	} 

}
