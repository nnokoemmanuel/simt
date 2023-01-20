   <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
     <div class="row">
         <div class="kt-widget__media col-sm-12" style="border-radius: 40px" >
                  <img class="kt-widget__img col-sm-12" src="${pageContext.request.contextPath}/file/download?fileName=certificate${studentSessionId}.jpg&folderName=certificate.preview.folder" width="585" height="814" alt="image">
               
          </div>

         <div class="pull-right  col-sm-12">
			<button  style="margin-top:2%" type="button" class="pull-right btn btn-primary"  onclick="productionPrintCertificate('${studentSessionId}','${eligibleCenterId}','${applicationId}');"  ><fmt:message key="capacity.preview.print" /></button>
		</div>
     </div>