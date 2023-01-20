package ppp.simt.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.AccessDeniedException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import ppp.simt.util.Constants;
import ppp.simt.util.Logger_;

@ControllerAdvice
public class GlobalExceptionHandlerController  {
	
@Autowired 
private Logger_ logger_;
@ExceptionHandler(Throwable.class)
@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
public String handleAll(Throwable ex,Model model)
 {
	StringWriter error = new StringWriter();
	ex.printStackTrace(new PrintWriter(error));
	logger_.log(Constants.EXCEPTION_LOG_DIR, error.toString());
	model.addAttribute("error",600);
	ex.printStackTrace();
    return "exception";
 }
@ExceptionHandler(NoHandlerFoundException.class)
@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
public String notFoundHandle(NoHandlerFoundException ex,Model model)
 {
	StringWriter error = new StringWriter();
	ex.printStackTrace(new PrintWriter(error));
	logger_.log(Constants.EXCEPTION_LOG_DIR, error.toString());	
	model.addAttribute("error", 500);
	return "exception";
 }

@ExceptionHandler(NullPointerException.class)
@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
public String nullPointedrException(NullPointerException ex,Model model)
 {
	StringWriter error = new StringWriter();
	ex.printStackTrace(new PrintWriter(error));
	logger_.log(Constants.EXCEPTION_LOG_DIR, error.toString());
	model.addAttribute("error", 100);
	return "exception";
 }

@ExceptionHandler(IndexOutOfBoundsException.class)
@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
public String indexOut(IndexOutOfBoundsException ex,Model model)
 {
	StringWriter error = new StringWriter();
	ex.printStackTrace(new PrintWriter(error));
	logger_.log(Constants.EXCEPTION_LOG_DIR, error.toString());
	model.addAttribute("error", 200);
	return "exception";
 }
@ExceptionHandler(PersistenceException.class)
@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
public String persistence(PersistenceException ex,Model model)
 {
	StringWriter error = new StringWriter();
	ex.printStackTrace(new PrintWriter(error));
	logger_.log(Constants.EXCEPTION_LOG_DIR, error.toString());	
	model.addAttribute("error", 300);
	return "exception";
 }

@ExceptionHandler(ArithmeticException.class)
@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
public String arithmetic(ArithmeticException ex,Model model)
 {
	StringWriter error = new StringWriter();
	ex.printStackTrace(new PrintWriter(error));
	logger_.log(Constants.EXCEPTION_LOG_DIR, error.toString());	
	model.addAttribute("error", 400);
	return "exception";
 }

@ExceptionHandler(AccessDeniedException.class)
@ResponseStatus(value=HttpStatus.NOT_FOUND)
public String access(AccessDeniedException ex,Model model)
 {
	StringWriter error = new StringWriter();
	ex.printStackTrace(new PrintWriter(error));
	logger_.log(Constants.EXCEPTION_LOG_DIR, error.toString());
	model.addAttribute("error", 000);
	return "exception";
 }

@InitBinder
protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
    final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
}
}
