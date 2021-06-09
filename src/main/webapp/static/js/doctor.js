const iconColors = ["#EB8F00", "#D24794", "#242424", "#2DAD4F", "#4864ED", "#ED7770", "#57925B", "#984646", "#CFB53B", "#D22C2C"];
const scoringColors = ["#4f413a", "#007f5f", "#2b9348", "#55a630", "#80b918", "#99b800"];

function getDoctorId() {
    const urlParams = new URLSearchParams(window.location.search);
    var fetchedDoctorId = urlParams.get("id");
    if (fetchedDoctorId) doctorId = fetchedDoctorId;
    else window.location.replace("/404");
}

function getDoctorData() {
    var url = getBaseUrl() + "/doctors/" + doctorId;
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            populatePage(doctorId, data);
        },
        error: function(data) {
            window.location.replace("/404");
        }
    });
}

function populatePage(doctorId, data) {
    doctorData = populateDoctorTile(data);
    $('#doctor-tile-container').empty();
    $('#doctor-tile-container').html(doctorData);

    getDoctorReviewData(doctorId);
}

function populateDoctorTile(data) {

    const randomColor = iconColors[Math.floor(Math.random() * iconColors.length)];

    const ReviewTemplate = ({ name, city, state, phone, gender, ageRange, ratingCount, overallRating,
        anonymityScore, professionalScore, objectiveScore, respectfulScore, bgColor, iconLetter, inclusiveScore,
         telNo, officeHours, paymentForms, consultationFees, officeAddress}) => `
        <div class="doctor-tile">
            <div class="row doctor-tile-margin-main">
                <div class="col-md-6">
                    <div class="row">
                        <div class="col-sm-12 flexbox-left-row">
                            <div>
                                <div class="name-circle" style="background-color:${bgColor}">
                                    <div class="name-circle-letter">${iconLetter}</div>
                                </div>
                            </div>
                            <div class="col-md-6 doctor-tile-name flexbox-left text-left">
                                <a href="#" class="browse-doctor-name">
                                ${name}
                                </a>
                            </div>
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
                            ${telNo}
                        </div>
                    </div>
                </div>
            </div>
            <div class="row doctor-tile-margin-main mt-2">
                <div class="col-md-3">
                    <div class="row">
                    <div class="col-sm-12">
                        <div class="doctor-tile-rating">
                            Overall Rating <span></span>
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
                    </div>
                    <div class="row mt-3">
                        <div class="col-sm-12">Gender: <span class="font-weight-bold">${gender}</span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12">Age-Range: <span class="font-weight-bold">${ageRange}</span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12">Office Phone: <span
                                class="font-weight-bold">${telNo}</span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12">Office Hours: <span class="font-weight-bold">${officeHours}</span>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="row mt-3">
                        <div class="col-sm-12">Payment Mode: <span
                                class="font-weight-bold">${paymentForms}</span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12">Fees: <span class="font-weight-bold">${consultationFees}</span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12">Full Address: <span class="font-weight-bold">${officeAddress}</span>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 text-center">
                    <div class="row">
                        <div class="col-md-4 tile-rating tile-rating-doctor">
                            <div class="tile-rating-score" style="color:${scoringColors[Math.round(overallRating)]}">${overallRating}</div>
                            <div class="small tile-rating-field">Overall</div>
                        </div>
                        <div class="col-md-4 tile-rating tile-rating-doctor">
                            <div class="tile-rating-score" style="color:${scoringColors[Math.round(professionalScore)]}">${professionalScore}</div>
                            <div class="small tile-rating-field">Professional</div>
                        </div>
                        <div class="col-md-4 tile-rating tile-rating-doctor">
                            <div class="tile-rating-score" style="color:${scoringColors[Math.round(objectiveScore)]}">${objectiveScore}</div>
                            <div class="small tile-rating-field">Objective</div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4 tile-rating tile-rating-doctor">
                            <div class="tile-rating-score" style="color:${scoringColors[Math.round(respectfulScore)]}">${respectfulScore}</div>
                            <div class="small tile-rating-field">Respectful</div>
                        </div>
                        <div class="col-md-4 tile-rating tile-rating-doctor">
                            <div class="tile-rating-score" style="color:${scoringColors[Math.round(inclusiveScore)]}">${inclusiveScore}</div>
                            <div class="small tile-rating-field">Inclusive</div>
                        </div>
                        <div class="col-md-4 tile-rating tile-rating-doctor">
                            <div class="tile-rating-score" style="color:${scoringColors[Math.round(anonymityScore)]}">${anonymityScore}</div>
                            <div class="small tile-rating-field">Anonymity</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `;


    console.log("XXXXXXXXXXX: " + data.consultationFees);
    var loader = [{
        name: data.name,
        city: data.city,
        state: data.state,
        phone: data.telNo,
        gender: data.gender,
        ageRange: data.ageRange,
        ratingCount: data.ratingCount,
        overallRating: roundN(data.overallRating, 1),
        anonymityScore: getScore(data.anonymityScore, data.anonymityScoreCount),
        professionalScore: getScore(data.professionalismScore, data.professionalismScoreCount),
        objectiveScore: getScore(data.objectivityScore, data.objectivityScoreCount),
        respectfulScore: getScore(data.respectfulnessScore, data.respectfulnessScoreCount),
        inclusiveScore: getScore(data.inclusivityScore, data.inclusivityScoreCount),
        bgColor: randomColor,
        iconLetter: data.name[0].toUpperCase(),
        telNo: data.telNo,
        officeHours: data.officeHours,
        paymentForms: data.paymentForms,
        consultationFees: data.consultationFees,
        officeAddress: data.officeAddress
    }].map(ReviewTemplate);

    return loader;
}

function getScore(score, count) {
    if (count == 0) return "-";
    return roundN(score / count, 1);
}

function getDoctorReviewData(doctorId) {
    var url = getBaseUrl() + "/reviews?doctorId=" + doctorId;
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            console.log("DISPLAING");
            console.log("DISPLAING");
            displayDoctorReviewData(data);
            displayDoctorReviewTest(data);
        },
        error: handleAjaxError
    });
}

function displayDoctorReviewTest(data) {
    const reviewCount = data.length;
    var reviewRows = [];
    for (i = 0; i < reviewCount; i++) {
        const submissionId = data[i].submissionId;
        const answersCount = data[i].answers.length;
        var contentDict = {};
        for (j = 0; j < answersCount; j++) {
            var reviewCategory = data[i].answers[j].category;
            if (!(reviewCategory in contentDict)) { contentDict[reviewCategory] = []; }
            contentDict[reviewCategory].push(
                `
                <div class="col-md-6" style="margin-bottom:10px;">
                    <div class="row review-text-header" style="color:#526e72; padding-right:5px; font-size:0.9em; font-weight:800; color: ">
                        <span>• ${data[i].answers[j].title}</span>
                    </div>
                    <div class="row" style="padding-right:5px;">
                        <span>${data[i].answers[j].answer}</span>
                    </div>
                </div>
                `
            );
        }


        $('#reviewCollapse-' + data[i].submissionId).empty();
        if ("INFO" in contentDict) {
            $('#reviewCollapse-' + submissionId).append(
                `
                <div class="review-section">
                    <div class="row">
                        <div class="review-text-header">ABOUT THE DOCTOR</div>
                    </div>
                    <div class="row inner-review-text">
                        ${contentDict["INFO"].join('')}
                    </div>
                </div>
                `
            );
        }
        for (var sectionReviewCategory in contentDict) {
            if (sectionReviewCategory == "INFO") continue;
            $('#reviewCollapse-' + submissionId).append(
                `
                <div class="review-section">
                    <div class="row">
                        <div class="review-text-header">${sectionReviewCategory}</div>
                    </div>
                    <div class="row inner-review-text">
                        ${contentDict[sectionReviewCategory].join('')}
                    </div>
                </div>
                `
            );
        }
    }
}

function displayDoctorReviewData(data) {
    const reviewCount = data.length;
    var reviewRows = [];
    console.log(data);
    for (i = 0; i < reviewCount; i++) {
        reviewRows.push(populateGetDoctorReviewTile(data[i]))
    }

    $('#doctor-review-container').empty();
    $('#doctor-review-container').html(reviewRows.join(''));
}


function populateGetDoctorReviewTile(data) {
    const ReviewTemplate = ({ submissionId, overallRating, professionalScore, objectiveScore, respectfulScore, inclusiveScore, anonymityScore }) =>
        `
        <div class="col-md-12 review-tile-container">
            <div class="doctor-review container mb-2">
                <div class="row">
                    <div class="col-sm-3 flexbox-left" style="text-align: left;">
                        <div style="font-size: 1.2em;"><i class="fa fa-user"></i>&nbsp;&nbsp;&nbsp;Submission
                            #${submissionId}</div>
                        <div>Overall Rating</div>
                        <div class="rating-wrap flexbox-row">

                            <ul class="rating-stars">
                                <li style="width: ${overallRating / 5 * 100}%" class="stars-active">
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
                    <div class="col-sm-3"></div>
                    <div class="col-sm-1 flexbox inner-review-score">
                        <div class="tile-rating-score" style="color:${scoringColors[Math.round(professionalScore)]}">${professionalScore}</div>
                        <div class="small tile-rating-field">Professional</div>
                    </div>
                    <div class="col-sm-1 flexbox inner-review-score">
                        <div class="tile-rating-score" style="color:${scoringColors[Math.round(objectiveScore)]}">${objectiveScore}</div>
                        <div class="small tile-rating-field">Objective</div>
                    </div>
                    <div class="col-sm-1 flexbox inner-review-score">
                        <div class="tile-rating-score" style="color:${scoringColors[Math.round(respectfulScore)]}">${respectfulScore}</div>
                        <div class="small tile-rating-field">Respectful</div>
                    </div>
                    <div class="col-sm-1 flexbox inner-review-score">
                        <div class="tile-rating-score" style="color:${scoringColors[Math.round(inclusiveScore)]}">${inclusiveScore}</div>
                        <div class="small tile-rating-field">Inclusive</div>
                    </div>
                    <div class="col-sm-1 flexbox inner-review-score">
                    <div class="tile-rating-score" style="color:${scoringColors[Math.round(anonymityScore)]}">${anonymityScore}</div>
                    <div class="small tile-rating-field">Anonymity</div>
                </div>
                    <div class="col-sm-1 flexbox-left review-expand-button">
                        <a data-toggle="collapse" href="#reviewCollapse-${submissionId}" role="button">
                            <i class="fa fa-angle-down review-expand-button"></i>
                        </a>
                    </div>
                </div>

                <div class="collapse" id="reviewCollapse-${submissionId}">
                </div>
            </div>
        </div>
        `;

    var loader = [{
        name: data.name,
        submissionId: data.submissionId,
        overallRating: roundN(data.averageRating, 1),
        anonymityScore: getScore(data.anonymityScore, data.anonymityScoreCount),
        professionalScore: getScore(data.professionalismScore, data.professionalismScoreCount),
        objectiveScore: getScore(data.objectivityScore, data.objectivityScoreCount),
        respectfulScore: getScore(data.respectfulnessScore, data.respectfulnessScoreCount),
        inclusiveScore: getScore(data.inclusivityScore, data.inclusivityScoreCount),
    }].map(ReviewTemplate);

    return loader;
}

function init() {
    getDoctorId();
    getDoctorData();
}

$(document).ready(init);