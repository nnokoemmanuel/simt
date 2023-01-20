  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
     <div class="row">
     <c:if test="${totalStudents == 1}">
          <div class="kt-widget__media col-sm-12" style="border-radius: 40px" >
          <img class="kt-widget__img col-sm-12" src="${pageContext.request.contextPath}/file/download?fileName=transcript_${studentSessionId}-1.jpg&folderName=transcript.preview.folder" width="650" height="770" alt="image">
           <!--  <img class="kt-widget__img col-sm-12" src="${pageContext.request.contextPath}/file/download?fileName=transcript_${studentSessionId}-1.jpg&folderName=transcript.preview.folder" alt="image"> -->

          </div>
          <div class="pull-left  col-sm-12">
			<button  style="margin-top:5%" type="button" class="btn btn-primary"  onclick="productionPrintTranscript('${pageContext.request.contextPath}','${studentSessionId}','${studentSession.eligibleCenter.id}','${applicationId}');active_link('file-management');return false;"  ><fmt:message key="transcript.preview.print" /></button>
		  </div>
     </c:if>

     </div>