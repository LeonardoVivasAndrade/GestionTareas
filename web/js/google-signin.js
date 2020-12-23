$(document).ready(function () {
    $("#btn-perfil").hide();
});


function onLogout() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut();
    auth2.disconnect();
    $("#btn-perfil").hide();
    location.href = "/";
}

function onSignIn(googleUser) {
    var profile = googleUser.getBasicProfile();
    var userEmail = profile.getEmail();
    var userName = profile.getName();

    var params = {
        userEmail: userEmail,
        userName: userName
    };

    $.get("/Login.do", params, function (responseJson) {
        $.each(responseJson, function (index, usuario) {
            $("#btn-perfil").show();
        });
    });
}

function fail(error) {
    alert("Fallo inicio de sesión google: Error(" + error + ")");
}







