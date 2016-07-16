var baseURL = new URL(window.location.origin);
var map = L.map('mapid', {}).locate({setView: true});
L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
    attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>',
    maxZoom: 18,
    id: 'your.mapbox.project.id',
    accessToken: 'your.mapbox.public.access.token'
}).addTo(map);
var markerGroup = L.layerGroup();
var bixiGroup = L.layerGroup();
var bixiRadius;
var bixiIcon = L.AwesomeMarkers.icon({
    icon: 'bold',
    markerColor: 'red'
  });

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
		clearMap();
    	displayQueryStatus('queryStatus', displayResponseCount(Object.keys(data).length));
    	displayMapMarkers(data);
	})
}

var fetchBixiStations = function (event) {
	var url = new URL('/bixi-stations', baseURL);
	fetch(url).then(function(resp) {
    	return resp.json();
	}).then(function (data) {
    	displayBixiMarkers(data, event.latlng);
    })
}

var clearMap = function () {
	markerGroup.clearLayers();
	bixiGroup.clearLayers();
	displayQueryStatus('bixiQueryStatus', '');
	if(bixiRadius) {
		map.removeLayer(bixiRadius); 
		bixiRadius = undefined;
	}
}

var buildURL = function (startDate, endDate) {
	var url = new URL('/foodtrucks-schedule', baseURL);
	url.searchParams.append('from', startDate);
	url.searchParams.append('to', endDate);
	fetchFoodtruckSchedules(url);
}

var displayQueryStatus = function (divId, queryStatusHTML) {
	document.getElementById(divId).innerHTML = queryStatusHTML;
}

var displayResponseCount = function (count) {
	return '<span>'+ count +' schedule' + (count !== 1 ? 's' : '') + ' found' + '</span>';
}

var displayBixiCountInRadius = function (count) {
	return '<span>'+ count +' Bixi station' + (count !== 1 ? 's' : '') + ' found within 200 meters radius' + '</span>';
}

var displayMapMarkers = function (data) {
	data.forEach(function(schedule) {
    	var marker = L.marker([schedule.latitude, schedule.longitude]).addTo(map)
	    .bindPopup('<span>Name: '+schedule.truckName+'</span><br/>'
	    	+ '<span>Location: '+schedule.location+'</span><br/>'
	    	+ '<span>Start: '+moment(schedule.startDate).format("dddd, MMMM Do YYYY, HH:mm")+'</span><br/>'
	    	+ '<span>End: '+moment(schedule.endDate).format("dddd, MMMM Do YYYY, HH:mm")+'</span>'
	    );
	    marker.addEventListener('click', fetchBixiStations);
	    markerGroup.addLayer(marker);
	});
	this.map.addLayer(markerGroup);
}

var displayBixiMarkers = function (data, markerLatLng) {
	bixiGroup.clearLayers();
	setupBixiRadius(markerLatLng);

	data.forEach(function (station) {
		var stationLatLng = L.latLng(station.latitude, station.longitude);
		if(stationLatLng.distanceTo(markerLatLng) <= Number.parseFloat("200.0")) {
			var bixiMarker = L.marker(stationLatLng, {icon: bixiIcon}).addTo(map)
		    .bindPopup('<span>Name: '+station.stationName+'</span><br/>'
		    	+ '<span>Number of Bixis available: '+station.availableBikes+'</span><br/>'
		    	+ '<span>Last updated on: '+moment(station.lastUpdateTime).format("dddd, MMMM Do YYYY, HH:mm")+'</span>'
		    );
		    bixiGroup.addLayer(bixiMarker);
		}
	});
	this.map.addLayer(bixiGroup);
	displayQueryStatus('bixiQueryStatus', displayBixiCountInRadius(bixiGroup.getLayers().length));
}

var setupBixiRadius = function (markerLatLng) {
	if(bixiRadius) {
		bixiRadius.setLatLng(markerLatLng);
	} else {
		bixiRadius = L.circle(markerLatLng, 200).addTo(map);
	}
}