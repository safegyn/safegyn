function login(event) {
    var json = JSON.parse(toJson($("#login-form")));
    $.ajax({
        url: getBaseUrl() + "/admin/login",
        type: "POST",
        data: {
            "username": json['username'],
            "password": json['password']
        },
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        success: function(response) {
            getSuccessSnackbar("Logged in");
        },
        error: handleAjaxError
    });
    return false;
}

function init() {
    $('#login-form').submit(login);
}

$(document).ready(function() {
    init();
});