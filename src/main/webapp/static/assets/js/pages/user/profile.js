function getUserProfile(url) {
	
	var theObject   =   document.getElementById('kt_content');
	var xmlHttp = getXMLHttp();
	xmlHttp.open("GET", url, true);
	
	xmlHttp.setRequestHeader("Content-Type",
		"application/x-www-form-urlencoded; charset=UTF-8");
	xmlHttp.setRequestHeader("X-Requested-With", "XMLHttpRequest");
	xmlHttp.onreadystatechange = function() {
		
		if (xmlHttp.status !== 200) {
			customAlert("Something went wrong on the server!");
			theObject.innerHTML = xmlHttp.responseText;
			return;
		}
		
		if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
			theObject.innerHTML = xmlHttp.responseText;
			}
	};
	xmlHttp.send(null);
}


/**
 * 
 * @returns {ActiveXObject|XMLHttpRequest|Boolean}
 * 
 * Returns the XMLHttp DOM Object
 */
function getXMLHttp(){
	var xmlHttp = false;
	try{
		//try to get request object for IE 5 or later
		xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
	}catch(e){
		//get request object for earlier versions of IE.
		try{
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		}catch(e){
			
		}
	}	
	
	if(!xmlHttp && typeof XMLHttpRequest !== 'undefined'){
		xmlHttp = new XMLHttpRequest();
	}
	
	return xmlHttp;
}
