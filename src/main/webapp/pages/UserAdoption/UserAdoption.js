/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */

/* perform any action on widgets/variables within this block */
Page.onReady = function() {
    /*
     * variables can be accessed through 'Page.Variables' property here
     * e.g. to get dataSet in a staticVariable named 'loggedInUser' use following script
     * Page.Variables.loggedInUser.getData()
     *
     * widgets can be accessed through 'Page.Widgets' property here
     * e.g. to get value of text widget named 'username' use following script
     * 'Page.Widgets.username.datavalue'
     */
};

Page.createAnimalPictureForm1Beforesubmit = function($event, widget, $data) {
    debugger;
    if (Page.Variables.FileServiceUploadFile1.dataSet.length > 0) {
        $data.picurl =
            Page.Variables.FileServiceUploadFile1.dataSet[0].path;
    }



};
Page.AnimalLiveForm1Beforeservicecall = function($event, $operation, $data, options) {
    debugger;
    if (Page.Variables.FileServiceUploadFile2.dataSet.length > 0) {
        $data.imagePath =
            Page.Variables.FileServiceUploadFile2.dataSet[0].path;
    }


};
Page.button3Click = function($event, widget) {
    if (navigator.geolocation) {
        // Get the user's current location
        navigator.geolocation.getCurrentPosition(function(position) {
            // Create a new Geocoder object
            let geocoder = new google.maps.Geocoder();

            // Use the Geocoder to get the address for the user's location
            geocoder.geocode({
                'location': {
                    lat: position.coords.latitude,
                    lng: position.coords.longitude
                }
            }, function(results, status) {
                // If the Geocoder was successful, populate the address input field
                if (status === 'OK') {
                    console.log(results[0].formatted_address);
                    // Widgets.
                    Page.Widgets.address.datavalue = results[0].formatted_address;
                } else {
                    alert('Geocoder failed due to: ' + status);
                }
            });
        }, function() {
            alert('Unable to get your location.');
        });
    } else {
        alert('Geolocation is not supported by this browser.');
    }
};
Page.button4Click = function($event, widget) {
    let userLat = Page.Variables.svGetSearchCoord.dataSet.results[0].geometry.location.lat;
    let userLong = Page.Variables.svGetSearchCoord.dataSet.results[0].geometry.location.lng;
    console.log(Page.Variables.svGetSearchCoord.dataSet.results[0].geometry.location);


    function toRad(value) {
        return value * Math.PI / 180;
    }

    function calcDistance(lat1, lon1, lat2, lon2) {
        const R = 6371; // radius of the earth in km
        const dLat = toRad(lat2 - lat1);
        const dLon = toRad(lon2 - lon1);
        const a =
            Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
            Math.sin(dLon / 2) * Math.sin(dLon / 2);
        const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        const distance = R * c; // distance in km
        return distance;
    }

    let animalList = Page.Variables.Wepet2AnimalData1.dataSet;
    debugger;
    let nearByAnimal = [];
    console.log(animalList);
    for (var i = 0; i < animalList.length; i++) {

        var animal = animalList[2];
        var animalLat = animal.latitude;
        var animalLng = animal.longitude;

        let d = calcDistance(userLat, userLong, animalLat, animalLng);
        console.log(d);
        if (d < 30) {
            console.log('Done');
            nearByAnimal.push(animal);
            // console.log(getAllHomechefs);
            console.log(animal);
        }
        // console.log(App.Widgets.showdistance.caption);

        // Page.Widgets.showdistance.caption.value = d;

    }
    console.log(nearByAnimal);
    // Page.Variables.Wepet2AnimalData1.dataSet = nearByAnimal;
    Page.Widgets.AnimalFilter2.dataset = nearByAnimal;
};