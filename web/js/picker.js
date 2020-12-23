
var developerKey = 'AIzaSyA1HfTzEYkHB64emt2Zr1XTXM0N1SWeKbg';
var clientId = "190238485681-a7f3s6ucvs1k374ln04itv2h8c0a5gqk.apps.googleusercontent.com";
var appId = "1234567890";
var scope = ['https://www.googleapis.com/auth/drive.file'];
var pickerApiLoaded = false;
var oauthToken;

function loadPicker() {
    gapi.load('auth', {'callback': onAuthApiLoad});
    gapi.load('picker', {'callback': onPickerApiLoad});
}

function onAuthApiLoad() {
    window.gapi.auth.authorize(
            {
                'client_id': clientId,
                'scope': scope,
                'immediate': false
            },
            handleAuthResult);
}

function onPickerApiLoad() {
    pickerApiLoaded = true;
    createPicker();
}

function handleAuthResult(authResult) {
    if (authResult && !authResult.error) {
        oauthToken = authResult.access_token;
        createPicker();
    }
}

// Create and render a Picker object for searching images.
function createPicker() {
    if (pickerApiLoaded && oauthToken) {
        var view = new google.picker.View(google.picker.ViewId.FOLDERS);
        view.setMimeTypes("image/png,image/jpeg,image/jpg");

        var picker = new google.picker.PickerBuilder()
                .enableFeature(google.picker.Feature.NAV_HIDDEN)
                .enableFeature(google.picker.Feature.MULTISELECT_ENABLED)
                .setAppId(appId)
                .setOAuthToken(oauthToken)
                .addView(view)
                .setLocale('es')
                .addView(new google.picker.DocsUploadView())
                .setDeveloperKey(developerKey)
                .setCallback(pickerCallback)
                .setTitle('Tus Archivos de Google Drive')
                .build();
        picker.setVisible(true);
    }
}

// A simple callback implementation.
function pickerCallback(data) {
    if (data.action == google.picker.Action.PICKED) {

        var params = new Array(data.length);
        
        for (var i = 0; i < data.docs.length; i++) {            

            var id = data.docs[i].id;
            var titulo = data.docs[i].name;
            var url = data.docs[i].url;
            var iconUrl = data.docs[i].iconUrl;
            var embedUrl = data.docs[i].embedUrl;
            var sizeBytes = data.docs[i].sizeBytes;            

            var doc = {
                id : id,
                titulo: titulo,
                url: url,
                iconUrl: iconUrl,
                embedUrl : embedUrl,
                sizeBytes : sizeBytes,
                materia: ""
            };
            
            params[i] = doc;
        }
        drawInsert(params);
    }
}



