<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="row">
		<div class="col-sm-3">
			<label><span>ID:</span></label>
			<span><h5>${person.id}</h5></span>
		</div>
		<div class="col-sm-3">
			<label><span><fmt:message key="consult.more.personal.info.surname" />:</span></label>
			<span><h5>${person.surName}</h5></span>
		</div>
		<div class="col-sm-3">
			<label><span><fmt:message key="consult.more.personal.info.givenName" />:</span></label>
			<span><h5>${person.givenName}</h5></span>
		</div>
		<div class="col-sm-3">
			<label><span><fmt:message key="consult.more.personal.info.dob" />:</span></label>
			<span><h5>${person.dob}</h5></span>
		</div>
		<div class="col-sm-3">
			<label><span><fmt:message key="consult.more.personal.info.pob" />:</span></label>
			<span><h5>${person.pob}</h5></span>
		</div>
		<div class="col-sm-3">
			<label><span><fmt:message key="consult.more.personal.info.gender" />:</span></label>
			<span><h5>${person.gender}</h5></span>
		</div>
		<div class="col-sm-3">
					<label><span><fmt:message key="person.licenseNum" /> : </span></label>
					<span><h5>${fn:toUpperCase(person.licenseNum)}</h5></span>
		</div>
		<div class="col-sm-3">
					<label><span><fmt:message key="catb.session.date" />: </span></label>
					<span><h5>${CatB_Date}</h5></span>
		</div>
		<div class="col-sm-3">
					<label><span>Matricule : </span></label>
					<span><h5>${person.matricule}</h5></span>
		</div>
		<div class="col-sm-3">
					<label><span ><fmt:message key="consult.more.personal.info.categories" /> : </span></label>
					<span><h3 >${categories}</h3></span>
		</div>
		<div class="col-sm-3">
					<label><span><fmt:message key="catb.session.date" />: </span></label>
					<span><h5>${CatB_Date}</h5></span>
		</div>
		<div class="col-sm-3">
			<label><span><fmt:message key="consult.more.personal.info.nationality" />:</span></label>
			<span><h5>${person.nationality.countryName}</h5></span>
		</div>

</div>
