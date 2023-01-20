<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="row">
	<input id="action" type="hidden" value="${action}"/>
	<textarea id="comment" rows="8" cols="100"></textarea>
</div>
<hr>
<div class="col-lg-3 kt-margin-b-10-tablet-and-mobile pull-right">
	<center>
	    <button style="width: 50%; margin-top:20px; background-color: rgb(1, 168, 249);" type="button" class="btn btn-success" title="SaveComment" onClick="saveApplicationComment(${application.id });">
	     <fmt:message key="add.comment.save" /> 
	    </button>
	</center>
</div>

</body>
</html>