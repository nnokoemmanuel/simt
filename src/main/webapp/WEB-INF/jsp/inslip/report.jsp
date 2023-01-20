<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script src="${pageContext.request.contextPath}/js/inslip/list_inslip.js" type="text/javascript"></script>


<br/>
<div class="row col-sm-11 page-title"><fmt:message key="inslip.number" /> ${inSlip.referenceNumber}</div>
<input id="slip" type="hidden" value="${inSlip.id }">
<br/>
 <div class=" row col-sm-11">
       ${user.firstName} <fmt:message key="choose.action.lable" /> 
</div>
<br/>

<div class="row col-sm-11 clear-both pull-right">
     <input type= "radio"  name="radio" value="2" id="report_wrong" checked onchange="switch_box_message('message');"/>
     <fmt:message key="bad.report.label" />
</div>
<div class="row col-sm-11 clear-both pull-right">
         <input type= "radio" name="radio" value="1"  id="report_good"   onchange="switch_box_message('message');"/>
        	<fmt:message key="good.report.label" />
</div>
<input type="hidden" id="checked" name="caheck" Value="1"/>
<br/>
<br/>
<br/>
<div  class=" row col-sm-11 clear-both center-block">
    <textarea  class="display-none" name="ameliorer" id="message" rows="2" cols="50" onmouseup="deleteError()"></textarea>
</div>
<div  id="error" class=" error display-none clear-both col-sm-11" ></div>
<br/>
<br/>
<div class="row col-sm-3 pull-right clear-both">
	<input type="button" value="<fmt:message key="register.lable" />" name="valider" onClick="checkform('${pageContext.request.contextPath}/marine/inSlip/RegisterMakeReport?id=${inSlip.id}');"/>
</div>