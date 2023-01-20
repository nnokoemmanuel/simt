
/**
 * 
 * @MPA
 */
function registerServiceWorker () {
	detectPrivateMode(function (isPrivateMode) {
	    console.log('is private mode: ' + isPrivateMode)
	});
  if (!navigator.serviceWorker) ;
  navigator.serviceWorker.register('/smart-dl/sw.js').then(function(reg) {
	  console.log('Service Worker is regsitered', reg);
	  if(window.indexedDB){
			var request = window.indexedDB.open('marineDB',15);
			request.onupgradeneeded = function(event){
				var db = event.target.result;
				db.createObjectStore('push-notifications', {keyPath: 'id', autoIncrement: true});
			}
			
			//start adding static push-message
			  request.onsuccess = function(){
			    db = request.result;
			    let transaction = db.transaction('push-notifications', 'readwrite');
			    
			    transaction.oncomplete = function(){
			    	console.log('Transaction terminée');
			    };

			    let pushNotifications = transaction.objectStore('push-notifications');
			    
			    let pushNotification = {
			        id: 1,
			        isNotified: 'NO',
			        message: 'user created !',
			        pushUser: 'MANDENGUE PAULE AGGY',
			        pushTime: new Date()
			    };
			    
			    let pushNotification2 = {
				        id: 2,
				        isNotified: 'NO',
				        message: 'user created 2 !',
				        pushUser: 'MANDENGUE PAULE AGGY',
				        pushTime: new Date()
				   };
			    
			    let ajout = pushNotifications.put(pushNotification);
			    let ajout2 = pushNotifications.put(pushNotification2);

			    ajout = pushNotifications.put(pushNotification2);
			    ajout.onsuccess = function(){ 
			        console.log('Utilisateur ajouté avec la clef ' + ajout.result);
			    };
			    ajout2.onsuccess = function(){ 
			    	console.log('Utilisateur 2 ajouté avec la clef ' + ajout2.result);
			    };
			    
			    ajout.onerror = function(){
			    	console.log('Erreur : ' + ajout.error);
			    };
			    
			    let lire = pushNotifications.get(1);
			    lire.onsuccess = function(){ 
			    	console.log('Message notifie 1 : ' + lire.result.message + ' pushed from server at '+  lire.result.pushTime + ' by the user '+  lire.result.pushUser);
			       
			    };
			    
			    lire.onerror = function(){
			    	console.log('Erreur : ' + lire.error);
			    };
			};
			//end adding static push-message
	}
    if (!navigator.serviceWorker.controller) {
      return;
    }

    if (reg.waiting) {
      updateReady(reg.waiting);
      return;
    }

    if (reg.installing) {
       trackInstalling(reg.installing);
      return;
    }

    reg.addEventListener('updatefound', function() {
       trackInstalling(reg.installing);
    });
   
  }).catch(err => console.log('service worker registration failed !', err));

  var refreshing;
  navigator.serviceWorker.addEventListener('controllerchange', function() {
    if (refreshing) return;
    window.location.reload();
    refreshing = true;
  });
}

function trackInstalling (worker) {
  worker.addEventListener('statechange', function() {
    if (worker.state === 'installed') {
     updateReady(worker);
    }
  });
}

function updateReady (worker) {
 
    if(confirm("Please we have some update, accept it; /Des mises à  jour sont disponibles, cliquez sur Ok"))
    worker.postMessage({action: 'skipWaiting'}); 
    else  worker.postMessage({action: 'skipWaiting'}); 

 }


function detectPrivateMode(cb) {
    var db,
    on = cb.bind(null, true),
    off = cb.bind(null, false)

    function tryls() {
        try {
            localStorage.length ? off() : (localStorage.x = 1, localStorage.removeItem("x"), off());
        } catch (e) {
            // Safari only enables cookie in private mode
            // if cookie is disabled then all client side storage is disabled
            // if all client side storage is disabled, then there is no point
            // in using private mode
            navigator.cookieEnabled ? on() : off();
        }
    }

    // Blink (chrome & opera)
    window.webkitRequestFileSystem ? webkitRequestFileSystem(0, 0, off, on)
    // FF
    : "MozAppearance" in document.documentElement.style ? (db = indexedDB.open("test"), db.onerror = on, db.onsuccess = off)
    // Safari
    : /constructor/i.test(window.HTMLElement) || window.safari ? tryls()
    // IE10+ & edge
    : !window.indexedDB && (window.PointerEvent || window.MSPointerEvent) ? on()
    // Rest
    : off()
}

