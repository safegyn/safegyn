function login(event) {
    var json = JSON.parse(toJson($("#login-form")));
    $.ajax({
        url: "http://localhost:9090/safegyn/api/admin/login",
        type: "POST",
        data: {
            "username": json['username'],
            "password": json['password']
        },
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        success: function(response) {
            console.log("Success");
        },
        error: function(response) {
            console.log("Failure");
        }
    });
    return false;
}

function init() {
    $('#login-form').submit(login);
}

$(document).ready(function() {
    init();
});