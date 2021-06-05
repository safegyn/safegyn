const iconColors = ["#EB8F00", "#D24794", "#242424", "#2DAD4F", "#4864ED", "#ED7770", "#57925B", "#984646", "#CFB53B", "#D22C2C"];
const scoringColors = ["#4f413a", "#007f5f", "#2b9348", "#55a630", "#80b918", "#99b800"];

// TODO: This should come from main.js
function getBaseUrl() {
    return "http://localhost:3000";
}

function getReviewData() {
    console.log(getBaseUrl());
    var url = getBaseUrl() + "/doctors";
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            displayReviewData(data);
        },
        error: handleAjaxError
    });
}

function getReviewDataForCity() {
    var city = $("#banner-search-select").find(":selected").text();
    var url = getBaseUrl() + "/doctors?reviews.city=" + city.toLowerCase();
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            console.log(data);
            displayReviewData(data)
        },
        error: handleAjaxError
    });
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

function displayReviewData(data) {

    var $doctorCount = $('#browse-doctor-count');
    $doctorCount.empty();
    $doctorCount.append(data.total);

    var $browseLocation = $('#browse-location');
    $browseLocation.empty();
    $browseLocation.append(data.state);

    const reviewCount = data.reviews.length;
    var reviewRows = [];
    for (i = 0; i < Math.floor(reviewCount / 2); i++) {
        reviewRows.push(`<div class="row style="width:100%">` +
            populateGetReviewTile(data.reviews[i]) + populateGetReviewTile(data.reviews[i + 1]) + `</div>`)
    }

    if (reviewCount % 2 == 1) {
        reviewRows.push(`<div class="row">` + populateGetReviewTile(data.reviews[reviewCount - 1]) + `</div>`)
    }

    $('#browse-review-container').empty();
    $('#browse-review-container').html(reviewRows.join(''));

}

function populateGetReviewTile(data) {

    const randomColor = iconColors[Math.floor(Math.random() * iconColors.length)];

    const ReviewTemplate = ({ name, city, state, phone, gender, ageRange, ratingCount, overallRating,
        anonymityScore, professionalScore, objectiveScore, respectfulScore, bgColor, iconLetter }) => `
        <div class="col-md-6 doctor-tile-container">
            <div class="doctor-tile">
                <div class="row doctor-tile-margin">
                    <div class="col-md-6">
                        <div class="row">
                            <div class="col-md-4 flexbox">
                                <div class="name-circle" style="background-color:${bgColor}">
                                    <div class="name-circle-letter">${iconLetter}</div>
                                </div>
                            </div>
                            <div class="col-md-8 doctor-tile-name flexbox-left text-left">
                                <a href="#" id="browse-doctor-name">
                                    ${name}
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 flexbox">
                        <div class="doctor-tile-address flexbox-right">
                            <div style="line-height: 1.2em;">
                                <span>${city}</span><br />
                                <span>${state}</span>
                            </div>
                            <div class="doctor-tile-phone">
                                ${phone}
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row doctor-tile-margin">
                    <div class="col-md-6">
                        <div class="row mt-3">
                            <div class="col-sm-12">Gender: <span class="font-weight-bold">${gender}</span>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12">Age-Range: <span
                                    class="font-weight-bold">${ageRange}</span></div>
                        </div>
                        <div class="doctor-tile-rating pt-3 mt-3">
                            Overall Rating <span>(${ratingCount})</span>
                            <div class="rating-wrap flexbox-row">
                                <ul class="rating-stars">
                                    <li style="width:${overallRating / 5 * 100}%" class="stars-active">
                                        <i class="fa fa-star"></i><i class="fa fa-star"></i><i
                                            class="fa fa-star"></i><i class="fa fa-star"></i><i
                                            class="fa fa-star"></i>
                                    </li>
                                    <li>
                                        <i class="fa fa-star"></i><i class="fa fa-star"></i><i
                                            class="fa fa-star"></i><i class="fa fa-star"></i><i
                                            class="fa fa-star"></i>
                                    </li>
                                </ul>
                                <span>&nbsp;${overallRating}</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 text-center">
                        <div class="row">
                            <div class="col-md-6 tile-rating">
                                <div class="tile-rating-score" style="color:${scoringColors[Math.round(anonymityScore)]}">${anonymityScore}</div>
                                <div class="small tile-rating-field">Anonymity</div>
                            </div>
                            <div class="col-md-6 tile-rating">
                                <div class="tile-rating-score" style="color:${scoringColors[Math.round(professionalScore)]}">${professionalScore}</div>
                                <div class="small tile-rating-field">Professional</div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 tile-rating">
                                <div class="tile-rating-score" style="color:${scoringColors[Math.round(objectiveScore)]}">${objectiveScore}</div>
                                <div class="small tile-rating-field">Objective</div>
                            </div>
                            <div class="col-md-6 tile-rating">
                                <div class="tile-rating-score" style="color:${scoringColors[Math.round(respectfulScore)]}">${respectfulScore}</div>
                                <div class="small tile-rating-field">Respectful</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `;

    var loader = [{
        name: data.name,
        city: data.city,
        state: data.state,
        phone: data.phone,
        gender: data.gender,
        ageRange: data.ageRange,
        ratingCount: data.ratingCount,
        overallRating: data.averageRating,
        anonymityScore: data.anonymity,
        professionalScore: data.professional,
        objectiveScore: data.objective,
        respectfulScore: data.respectful,
        bgColor: randomColor,
        iconLetter: data.name[0].toUpperCase()
    }].map(ReviewTemplate);

    return loader;
}

function handleAjaxError(response) {
    console.log("ERROR: " + response.status);
    console.log(response);
}

function init() {
    getReviewData();
}

$(document).ready(function () {
    $('#city-search-btn').click(getReviewDataForCity);
});


$(document).ready(init);
$(document).ready(getCityData);