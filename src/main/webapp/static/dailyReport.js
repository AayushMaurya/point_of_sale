var dailyReportDate;

function getDailyReportUrl(){
    var baseUrl = $("meta[name=baseUrl]").attr("content")
     	return baseUrl + "/api/admin/daily-report";
}

function getDailyReport(){
    var callParams = {};
    callParams.Url = getDailyReportUrl();
    callParams.Type = "GET";

    ajaxCall(callParams, null, displayDailyReport);
}

function displayDailyReport(data){
    dailyReportDate = data;
	var $tbody = $('#daily-report-table').find('tbody');
	var index = 0;
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		index++;
		var row = '<tr>'
		+ '<td>' + index+ '</td>'
		+ '<td>' + e.date + '</td>'
		+ '<td>' + e.invoicedOrderCount + '</td>'
		+ '<td>' + e.invoicedItemsCount + '</td>'
		+ '<td>' + e.totalRevenue + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

function getDailyReportByDateFilter()
{
    var callParams = {};
    callParams.Url = getDailyReportUrl();
    callParams.Type = "POST";
    dataParams = toJson($form);

    ajaxCall(callParams, dataParams, displayDailyReport);
    return false;
}

function downloadDailyReport(){
    writeFileData(dailyReportDate);
}

function init(){
    $('#apply-date-filter').click(getDailyReportByDateFilter);
    $('#download-daily-report').click(downloadDailyReport);
}

$(document).ready(init);
$(document).ready(getDailyReport);