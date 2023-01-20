   <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
     <div class="row">
         <div class="kt-widget__media col-sm-12" style="border-radius: 40px" >
                  <img class="kt-widget__img col-sm-12" src="${pageContext.request.contextPath}/file/download?fileName=merge_capacity_${id}.jpg&folderName=capacity.preview.folder" width="650" height="300" alt="image">
               
          </div>

         <div class="pull-left  col-sm-12">
			<button  style="margin-top:5%" type="button" class="btn btn-primary"  onclick="printCapacity('${id}');"  ><fmt:message key="capacity.preview.print" /></button>
		</div>
     </div>