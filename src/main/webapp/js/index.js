function getIndexData() {
    console.log(getBaseUrl());
    var url = getBaseUrl() + "/reviews/index";
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
    doctorData = populateDoctorTile(data);
    $('#doctor-tile-container').empty();
    $('#doctor-tile-container').html(doctorData);

    getDoctorReviewData();
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

function init() {
    getIndexData();
}

$(document).ready(init);
$(document).ready(getCityData);