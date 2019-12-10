function(){
var apiKey = 'YOUR_API_KEY';
var map;
var drawingManager;
var placeIdArray = [];
var polylines = [];
var snappedCoordinates = [];

function initialize() {
  var mapOptions = {
    zoom: 17,
    center: {lat: -33.8667, lng: 151.1955}
  };
  map = new google.maps.Map(document.getElementById('map'), mapOptions);

  // Adds a Places search box. Searching for a place will center the map on that
  // location.
  map.controls[google.maps.ControlPosition.RIGHT_TOP].push(
      document.getElementById('bar'));
  var autocomplete = new google.maps.places.Autocomplete(
      document.getElementById('autoc'));
  autocomplete.bindTo('bounds', map);
  autocomplete.addListener('place_changed', function() {
    var place = autocomplete.getPlace();
    if (place.geometry.viewport) {
      map.fitBounds(place.geometry.viewport);
    } else {
      map.setCenter(place.geometry.location);
      map.setZoom(17);
    }
  });

  // Enables the polyline drawing control. Click on the map to start drawing a
  // polyline. Each click will add a new vertice. Double-click to stop drawing.
  drawingManager = new google.maps.drawing.DrawingManager({
    drawingMode: google.maps.drawing.OverlayType.POLYLINE,
    drawingControl: true,
    drawingControlOptions: {
      position: google.maps.ControlPosition.TOP_CENTER,
      drawingModes: [
        google.maps.drawing.OverlayType.POLYLINE
      ]
    },
    polylineOptions: {
      strokeColor: '#696969',
      strokeWeight: 2
    }
  });
  drawingManager.setMap(map);

  // Snap-to-road when the polyline is completed.
  drawingManager.addListener('polylinecomplete', function(poly) {
    var path = poly.getPath();
    polylines.push(poly);
    placeIdArray = [];
    runSnapToRoad(path);
  });
}

$(window).load(initialize);
}