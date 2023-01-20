/*
 *Ce fichier permet gerer le poll genirique des notifications vers l utilisateur
 */


countNoifications = 0;
function pollingNotifications(){
	   var request = window.indexedDB.open('marineDB',15);
	   
	   request.onupgradeneeded = function(event){
			var db = event.target.result;
		}
	   
	   request.onsuccess = function(){
		   db = request.result;
		   var transaction = db.transaction(["push-notifications"], "readonly");
		   var pushNotifications = transaction.objectStore("push-notifications");
		   
		   transaction.onerror = function(){

			   console.log('Impossible d\'accéder à la base de donnée ');

		    };
		    
		   transaction.oncomplete = function(){
		        console.log('ouverture de la transaction en lecture terminee !!');
		    };
	        
		    pushNotifications.openCursor().onsuccess = function(event) {
			      var cursor = event.target.result;
			     
			      if (cursor) {
			    	  var isNotified =cursor.value.isNotified;
			    	  //alert(document.getElementsByClassName("nav-link show active")[0].innerText);
			    	  if(isNotified.localeCompare("NO")==0){
				        	if(document.getElementsByClassName("kt-notification kt-margin-t-10 kt-margin-b-10 kt-scroll ps").length > 0){
					        	var alertMessagesDiv = document.getElementsByClassName("kt-notification kt-margin-t-10 kt-margin-b-10 kt-scroll ps")[0];
					        	var message = cursor.value.message;
					        	var pushTime = cursor.value.pushTime;
					        	var pushUser = cursor.value.pushUser;
					        	if(countNoifications==0){
					        	 alertMessagesDiv.innerHTML= toggleNotifications(message,pushTime,pushUser);
					        	}else{
					        	 alertMessagesDiv.innerHTML+= toggleNotifications(message,pushTime,pushUser);
					        	}
				        	}
				        	 countNoifications+=1;
			         }
			         cursor.continue();
			      } else {
			    	 document.getElementById("notifications-counter").innerText=countNoifications+ " new ";
			         console.log("No more entries from marineDB database !");
			      }
		   };
     };
}

function toggleNotifications(notificationMessage,notificationPushTime,notificationPushUser){
		var alertHTML = '<a href="#" class="kt-notification__item"><div class="kt-notification__item-icon"><i class="flaticon2-line-chart kt-font-success"></i></div><div class="kt-notification__item-details"><div class="kt-notification__item-title">'+notificationMessage+'</div><div class="kt-notification__item-time">'+ notificationPushTime +'=>'+ notificationPushUser+'</div></div></a><div class="ps__rail-x" style="left: 0px; bottom: 0px;"><div class="ps__thumb-x" tabindex="0" style="left: 0px; width: 0px;"></div></div><div class="ps__rail-y" style="top: 0px; right: 0px;"><div class="ps__thumb-y" tabindex="0" style="top: 0px; height: 0px;"></div></div>';
	    return alertHTML;

	
}
