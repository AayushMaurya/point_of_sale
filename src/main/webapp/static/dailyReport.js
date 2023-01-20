function getDailyReportUrl()
{
    var baseUrl = $("meta[name=baseUrl]").attr("content")
     	return baseUrl + "/api/admin/daily-report";
}

function getDailyReport()
{
    var url = getDailyReportUrl();
    $.ajax({
    	   url: url,
    	   type: 'GET',
    	   success: function(data) {
    	   console.log(data);
    	   		displayDailyReport(data);
    	   },
    	   error: handleAjaxError
    	});
}

function displayDailyReport(data){
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

$(document).ready(getDailyReport);