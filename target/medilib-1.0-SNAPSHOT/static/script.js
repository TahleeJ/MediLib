function goPatient() {
    location.replace("patientportal.html");
}

function goDoctor() {
    location.replace("doctorportal.html");
}

function goEntry() {
    location.replace("entryportal.html");
}

// Attempt at solving cookie issue
// document.cookie = "HSID=27000; SSID=27000; APISID=27000; SAPISID=27000; SID=27000; SIDCC=27000; ACCOUNT_CHOOSER=27000; LSID=27000; Host_GAPS; Secure";

function init() {
  gapi.load('auth2', function() {
    /* Ready. Make a call to gapi.auth2.init or some other API */
    auth2 = gapi.auth2.init({client_id: '101678558796-he7teotdh66mk6s4hrdea62u8e4lj8hk.apps.googleusercontent.com'})
  });
}

async function onSignIn(googleUser) {
    var profile = googleUser.getBasicProfile();
    console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
    console.log('Name: ' + profile.getName());
    console.log('Image URL: ' + profile.getImageUrl());
    console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.

    var id_token = googleUser.getAuthResponse().id_token;

    call = await fetch("/?idtoken=" + id_token, {method: "POST"});
    response = await call.json();

    console.log("test");
    console.log("test" + response);    
    
    // var xhr = new XMLHttpRequest();
    // xhr.open('POST', 'https://yourbackend.example.com/tokensignin');
    // xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    // xhr.onload = function() {
    // console.log('Signed in as: ' + xhr.responseText);
    // };
    // xhr.send('idtoken=' + id_token);
}