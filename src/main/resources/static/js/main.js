var baseURL = new URL(window.location.origin);
var map = L.map('mapid', {}).locate({setView: true});
L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
    attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>',
    maxZoom: 18,
    id: 'shenacity.0lmkco6n',
    accessToken: 'pk.eyJ1Ijoic2hlbmFjaXR5IiwiYSI6ImNpcW4ya25nMDAwd2ZnbmpmcDl0bWc2engifQ.F34m_4vs0I6zM6gwaCqzVg'
}).addTo(map);
var markerGroup = L.layerGroup();

document.addEventListener('DOMContentLoaded', function () {
	$('.input-daterange').datepicker({
	    format: "yyyy-mm-dd"
	});
	linkForm();
})

var linkForm = function () {
		var form = document.getElementById('search-form');
		var startDateInput = document.getElementById('startDate');
		var endDateInput = document.getElementById('endDate');
		form.addEventListener('submit', function (e) {
		  e.preventDefault();
		  buildURL(startDateInput.value, endDateInput.value);
		});
}

var fetchFoodtruckSchedules = function (url) {
  fetch(url).then(function(resp) {
    return resp.json();
  }).then(function (data) {
    displayQueryStatus(displayResponseCount(Object.keys(data).length));
    displayMapMarkers(data);
  })
}

var buildURL = function (startDate, endDate) {
  var url = new URL('/foodtrucks-schedule', baseURL);
  url.searchParams.append('from', startDate);
  url.searchParams.append('to', endDate);
  fetchFoodtruckSchedules(url);
}

var displayQueryStatus = function (queryStatusHTML) {
	document.getElementById('queryStatus').innerHTML = queryStatusHTML;
}

var displayResponseCount = function (count) {
	return '<span>'+ count +' schedule' + (count !== 1 ? 's' : '') + ' found' + '</span>';
}

var displayMapMarkers = function (data) {
	markerGroup.clearLayers();
	data.forEach(function(schedule) {
    	var marker = L.marker([schedule.latitude, schedule.longitude]).addTo(map)
	    .bindPopup('<span>Name: '+schedule.truckName+'</span><br/>'
	    	+ '<span>Location: '+schedule.location+'</span><br/>'
	    	+ '<span>Start: '+moment(schedule.startDate).format("dddd, MMMM Do YYYY, HH:mm")+'</span><br/>'
	    	+ '<span>Start: '+moment(schedule.endDate).format("dddd, MMMM Do YYYY, HH:mm")+'</span>');
	    markerGroup.addLayer(marker);
	});
	this.map.addLayer(markerGroup);
}