function getIndexData() {
    var url = getBaseUrl() + "/index";
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            displayIndexData(data);
        },
        error: handleAjaxError
    });
}

function displayIndexData(data) {
    $('#index-city-count').empty();
    $('#index-city-count').html(data.indexCityCount);

    $('#index-review-count').empty();
    $('#index-review-count').html(data.indexReviewCount);

    $('#index-doctor-count').empty();
    $('#index-doctor-count').html(data.indexDoctorCount);
}

function getCityData() {
    var url = getBaseUrl() + "/cities";
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            displayCitySearchDropdown(data);
        },
        error: handleAjaxError
    });
}

function displayCitySearchDropdown(data) {
    var $dropdown = $('#banner-search-select')
    for (var entry in data) {
        var cityOption = `<option value="` + data[entry].toLowerCase() + `">` + data[entry].toUpperCase() + `</option>`;
        $dropdown.append(cityOption);
    }
}

function searchByCity() {
    var city = $("#banner-search-select").find(":selected").val();
    if (!city) getErrorSnackbar("Please provide a City");
    else {
        var url = "/browse?city=" + city;
        url = encodeURI(url);
        window.location.replace(url);
    }
}

function init() {
    $('#search-doctor-btn').click(searchByCity);
}

$(document).ready(init);
$(document).ready(getCityData);