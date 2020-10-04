async function goPatient() {
    // location.replace("patientportal.html");
    call = await fetch("/patientportal?init=false&switch=false&delete=false", {method: "GET"});
        // get = await fetch("/patientportal", {method: "GET"});
        response = await call.json();
        loadFiles(response);
        
        console.log(response);   
}

function loadFiles(files) {
    const fileList = document.getElementById("files");
    // fileList.innerHTML = '';

    files.forEach(file => {
            var element = document.createElement("div");
            element.id = "fileBox"
            var p = document.createElement("p");
            p.id = "fileName";
            p.innerHTML = file.fileName;
            element.appendChild(p);

            var hideButton = document.createElement("button");

            if (file.visible == "true") {
                hideButton.id = "hideFile"
                hideButton.innerHTML = "Hide File";
            } else {
                hideButton.id = "showFile"
                hideButton.innerHTML = "Show File";
            }

            // hideButton.onclick = switchVisibility(file.fileName, file.visible);

            var deleteButton = document.createElement("button");
            deleteButton.id = "deleteButton";

            // deleteButton.onclick = deleteFile(file.fileName, file.visible);

            element.appendChild(hideButton);
            element.appendChild(deleteButton);

            fileList.appendChild(element);
        });
}

function goDoctor() {
    location.replace("doctorportal.html?init=false");
}

function goEntry() {
    location.replace("entryportal.html?init=false");
}

// Attempt at solving cookie issue
// document.cookie = "HSID=27000; SSID=27000; APISID=27000; SAPISID=27000; SID=27000; SIDCC=27000; ACCOUNT_CHOOSER=27000; LSID=27000; Host_GAPS; Secure";

// function init() {
//   gapi.load('auth2', function() {
//     /* Ready. Make a call to gapi.auth2.init or some other API */
//     auth2 = gapi.auth2.init({client_id: '101678558796-he7teotdh66mk6s4hrdea62u8e4lj8hk.apps.googleusercontent.com'})
//   });
// }

// async function onSignIn(googleUser) {
//     var profile = googleUser.getBasicProfile();
//     console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
//     console.log('Name: ' + profile.getName());
//     console.log('Image URL: ' + profile.getImageUrl());
//     console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.

//     var id_token = googleUser.getAuthResponse().id_token;

//     call = await fetch("/?idtoken=" + id_token, {method: "POST"});
//     response = await call.json();

//     console.log("test");
//     console.log("test" + response);    
    
//     // var xhr = new XMLHttpRequest();
//     // xhr.open('POST', 'https://yourbackend.example.com/tokensignin');
//     // xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
//     // xhr.onload = function() {
//     // console.log('Signed in as: ' + xhr.responseText);
//     // };
//     // xhr.send('idtoken=' + id_token);
// }

function displayUpload() {
    const uploadBox = document.getElementById("upload-box");
    uploadBox.style.display = "block";
}

async function submitInfo() {
    var userType;

    if (document.getElementById("patient").checked == true) {
        userType = "Patient";
    } else {
        userType = "Doctor";
    }

    const firstName = document.getElementById("first-name").value;
    const lastName = document.getElementById("last-name").value;

    console.log(document.getElementById("patient").checked == true);
    console.log(userType);

    if (userType == "Patient") {
        location.replace("patientportal.html");
        call = await fetch("/patientportal?init=true&first=" + firstName + "&last=" + lastName, {method: "GET"});

    } else {
        call = await fetch("/doctorportal?init=true&first=" + firstName + "&last=" + lastName, {method: "POST"});
        location.replace("doctorportal.html");
        get = await fetch("/doctorportal", {method: "GET"});
        response = await get.json();
        console.log("enter");
        
    } 
}

async function switchVisibility(fileName, fileVisibility) {
    call = await fetch("/patientportal?init=false&switch=true&file-name=" + fileName + "&visibility=" + fileVisibility, {method: "GET"});
    response = await call.json();

    loadFiles(response);
}

async function deleteFile(fileName, fileVisibility) {
    call = await fetch("/patientportal?init=false&switch=false&delete=true&file-name=" + fileName + "&visibility=" + fileVisibility, {method: "GET"});
    response = await call.json();

    loadFiles(response);
}