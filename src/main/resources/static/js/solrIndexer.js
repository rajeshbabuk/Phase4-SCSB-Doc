/**
 * Created by SheikS on 6/20/2016.
 */
var intervalID;

jQuery(document).ready(function ($) {
    $("#fullIndex-form").submit(function (event) {
        event.preventDefault();
        fullIndex();
    });

    $("#partialIndex-form").submit(function (event) {
        event.preventDefault();
        partialIndex();
    });

    $("#reports-form").submit(function (event) {
        event.preventDefault();
        generateReport();
    });

    $("#requestResubmit-form").submit(function (event) {
        event.preventDefault();
        resubmitRequests();
    });

    $('#dateFrom').datetimepicker({
        format: "dd-mm-yyyy hh:ii"
    });

    $('#createdDate').datepicker({
        format: "yyyy/mm/dd"
    });

    $('#todate').datepicker({
        format: "yyyy/mm/dd"
    });
    
    $('#matchingAlgoDate').datepicker({
        format: "yyyy/mm/dd"
    });

    $('#fromDate').datepicker({
        format: "yyyy/mm/dd"
    });

    $('#ongoingMatchFromDateId').datepicker({
        format: "yyyy/mm/dd"
    });

    $('#partialIndexFromDate').datetimepicker({
        format: "dd-mm-yyyy hh:ii"
    });

    $('#partialIndexToDate').datetimepicker({
        format: "dd-mm-yyyy hh:ii"
    });

    $('#RequestFromDate').datetimepicker({
        format: "dd-mm-yyyy hh:ii"
    });

    $('#RequetToDate').datetimepicker({
        format: "dd-mm-yyyy hh:ii"
    });

    showDateField();
    populateInstitutionForFullIndex();
});


function refresh() {
    var autoRefresh = $('#autoRefresh').is(':checked');
    if(autoRefresh) {
        intervalID= setInterval(function () {
            updateStatus();
        }, 5000);
    } else {
        clearInterval(intervalID);
    }
}

function fullIndex() {
    
    if($('#clean').is(':checked')) {
        $('#deleteConfirmationModal').modal('show');
    } else {
        proceedIndex();
    }
}

function proceedIndex() {
    $('#deleteConfirmationModal').modal('hide');
    var $form = $('#fullIndex-form');
    $("#submit").attr('disabled', 'disabled');
    $.ajax({
        url: $form.attr('action'),
        type: 'post',
        data: $form.serialize(),
        success: function (response) {
            $("#submit").removeAttr('disabled');
            document.getElementById("fullIndexingStatus").value = response;
        }
    });
    setTimeout(function(){
    }, 2000);
    updateFullIndexStatus();
}

function partialIndex() {

    var $form = $("#partialIndex-form");
    $("#submit").attr('disabled', 'disabled');
    $.ajax({
        url: $form.attr('action'),
        type: 'post',
        data: $form.serialize(),
        success: function (response) {
            $("#submit").removeAttr('disabled');
            document.getElementById("partialIndexingStatus").value = response;
        }
    });
    setTimeout(function(){
    }, 2000);
    updatePartialIndexStatus();
}


function updateFullIndexStatus() {
    var request = $.ajax({
        url: "solrIndexer/report",
        type: "GET",
        contentType: "application/json"
    });
    request.done(function (msg) {
        document.getElementById("fullIndexingStatus").value = msg;
    });
}

function updatePartialIndexStatus() {
    var request = $.ajax({
        url: "solrIndexer/report",
        type: "GET",
        contentType: "application/json"
    });
    request.done(function (msg) {
        document.getElementById("partialIndexingStatus").value = msg;
    });
}

function saveReport() {
    $("#saveReport").attr('disabled', 'disabled');
    document.getElementById("matchingAlgorithmStatus").value = '';
    var criteria = $('#matchingCriteria').val();
    var matchingAlgoDate = $('#matchingAlgoDate').val();
    var url = '';
    if(criteria === 'ALL') {
        url = "/matchingAlgorithm/full?matchingAlgoDate="+matchingAlgoDate;
    } else if(criteria === 'MatchingAndReports') {
        url = "/matchingAlgorithm/findMatchingAndSaveReports";
    } else if (criteria === 'Reports') {
        url = "/matchingAlgorithm/reports";
    } else if (criteria === 'UpdateMonographCGDInDB') {
        url = "/matchingAlgorithm/updateMonographCGDInDB";
    } else if (criteria === 'UpdateSerialCGDInDB') {
        url = "/matchingAlgorithm/updateSerialCGDInDB";
    } else if (criteria === 'UpdateMvmCGDInDB') {
        url = "/matchingAlgorithm/updateMvmCGDInDB";
    } else if (criteria === 'UpdateCGDInSolr') {
        url = "/matchingAlgorithm/updateCGDInSolr?matchingAlgoDate="+matchingAlgoDate;
    } else if (criteria === 'PopulateDataForDataDump') {
        url = "/matchingAlgorithm/populateDataForDataDump";
    }
    if(url !== '') {
        var request = $.ajax({
            url: url,
            type: 'post'
        });
        request.done(function (msg) {
            document.getElementById("matchingAlgorithmStatus").value = msg;
            $("#saveReport").removeAttr('disabled');
        })
    }
}

function generateReport() {
    var $form = $('#reports-form');
    $("#report").attr('disabled', 'disabled');
    document.getElementById("reportStatus").value = '';
    var processType = $('#processType').val();
    var url = '';
    if(processType === 'SolrIndex' || processType === 'DeAccession' || processType ==='Accession'  || processType ==='SubmitCollection') {
        url = "/reportGeneration/generateReports";
    }
    if(url !== '') {
        var request = $.ajax({
            url: url,
            type: 'post',
            data: $form.serialize(),
            success: function (response) {
                $("#report").removeAttr('disabled');
            }
        });
        request.done(function (msg) {
            document.getElementById("reportStatus").value = msg;
        })
    }
}

function resubmitRequests() {
    var $form = $('#requestResubmit-form');
    $("#requestSubmit").attr('disabled', 'disabled');
    $.ajax({
        url: $form.attr('action'),
        type: 'post',
        data: $form.serialize(),
        success: function (response) {
            $("#requestSubmit").removeAttr('disabled');
            document.getElementById("resubmitRequestStatus").value = response;
        }
    });
}

function showDateField() {
    var criteria = $('#matchingCriteria').val();
    if(criteria === 'UpdateCGDInSolr' || criteria === 'ALL') {
        $('#matchingAlgoDateDiv').show();
    } else {
        $('#matchingAlgoDateDiv').hide();
    }
}

function showBibIdList(){
    $("#BibIdListView").show();
    $("#BibIdRangeView").hide();
    $("#DateRangeView").hide();
}

function showBibIdRange(){
    $("#BibIdListView").hide();
    $("#BibIdRangeView").show();
    $("#DateRangeView").hide();
}

function showBibIdDateRange(){
    $("#BibIdListView").hide();
    $("#BibIdRangeView").hide();
    $("#DateRangeView").show();
}

function showRequest() {
    if ($('#RequestStatus').is(':checked')){
        $("#RequestStatusView").show();
        $("#requestSubmitBtnDiv").show();
        $("#RequestIdListView").hide();
        $("#RequestIdRangeView").hide();
        $("#RequestDateRangeView").hide();
    }if ($('#RequestId').is(':checked')){
        $("#RequestStatusView").show();
        $("#RequestIdListView").show();
        $("#requestSubmitBtnDiv").show();
        $("#RequestIdRangeView").hide();
        $("#RequestDateRangeView").hide();
    }if ($('#RequestIdRange').is(':checked')){
        $("#RequestStatusView").show();
        $("#RequestIdListView").hide();
        $("#RequestIdRangeView").show();
        $("#requestSubmitBtnDiv").show();
        $("#RequestDateRangeView").hide();
    }if ($('#RequestDateRange').is(':checked')){
        $("#RequestStatusView").show();
        $("#RequestIdListView").hide();
        $("#RequestIdRangeView").hide();
        $("#RequestDateRangeView").show();
        $("#requestSubmitBtnDiv").show();
    }
}

function populateInstitutionForFullIndex() {
       getInstitutions('institutionCode');
}
function populateInstitutionForReports() {
       getInstitutions('reportInstitutionName');
}
function getInstitutions(selectId) {
       $('#'+selectId).empty();
       var request = $.ajax({
            url: "/solrIndexer/institutions",
            type: "GET",
            contentType: "application/json"
        });
        request.done(function (response) {
             $('#'+selectId).append('<option value="">ALL</option>');
             $.each(response , function(index, val) {
               $('#'+selectId).append('<option value="' + val + '">' + val+ '</option>');
             });
        });
}
function showOngoingMatchFromDate(){
    $("#OngoingMatchFromDateView").show();
    $("#OngoingMatchBibIdRangeView").hide();
}
function showOngoingMatchBibIdRange(){
    $("#OngoingMatchFromDateView").hide();
    $("#OngoingMatchBibIdRangeView").show();
}