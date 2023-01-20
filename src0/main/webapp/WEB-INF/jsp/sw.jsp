<%@ page language="java" contentType="application/javascript; charset=UTF-8" pageEncoding="UTF-8"%>


var staticCacheName = 'smart_dl-worker-v2022';


var allCaches = [staticCacheName];
self.addEventListener('install', function(event) {
  event.waitUntil(
    caches.open(staticCacheName).then(function(cache) {
      return cache.addAll([
       ]);
    })
  );
});
/*
self.addEventListener('activate', function(event) {
console.log('we detect caches');
  event.waitUntil(

    caches.keys().then(function(cacheNames) {
      return Promise.all(
        cacheNames.filter(function(cacheName) {
          return cacheName.startsWith('smart_dl-worker-') &&
                 !allCaches.includes(cacheName);
        }).map(function(cacheName) {
          console.log(cacheName);
          return caches.delete(cacheName);
        })
      );
    })
  );
}); */

self.addEventListener('activate', function(event) {
  var cacheWhitelist = ['smart_dl-worker-v'];

  event.waitUntil(
    caches.keys().then(function(keyList) {
      return Promise.all(keyList.map(function(key) {
        if (cacheWhitelist.indexOf(key) === -1) {
          return caches.delete(key);
        }
      }));
    })
  );
});

self.addEventListener('fetch', function(event) {

    event.respondWith(
   caches.open(staticCacheName).then(function(cache) {
      return cache.match(event.request).then(function(response){
          if(response) return response;
              return fetch(event.request).then(function(response){
                  if(event.request.url.endsWith("simt/login") || event.request.url.endsWith(".css") ||event.request.url.endsWith("login") ||event.request.url.endsWith(".jpg") || event.request.url.endsWith(".png") ||event.request.url.endsWith(".js"))
                  cache.put(event.request,response.clone());
                  return response;
          })
      })
   }
   )
  );
});

self.addEventListener('message', function(event) {
  if (event.data.action === 'skipWaiting') {
    self.skipWaiting();
  }
});




