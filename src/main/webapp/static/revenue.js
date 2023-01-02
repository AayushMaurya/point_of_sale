function getRevenueUrl(){
 	var baseUrl = $("meta[name=baseUrl]").attr("content")
 	return baseUrl + "/api/revenue";
 }

function getProductRevenueList(){
	var url = getRevenueUrl();
	var $form = $("#filter-date-form");
    var json = toJson($form);
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
            'Content-Type': 'application/json'
       },
	   success: function(data) {
	   console.log(data);
	   		displayRevenueProductList(data);
	   },
//	   error: handleAjaxError
	});
}

function displayRevenueProductList(data)
{
    var $tbody = $('#revenue-list-table').find('tbody');
    $tbody.empty();
    for(var i in data){
    		var e = data[i];
    		var row = '<tr>'
    		+ '<td>' + e.id + '</td>'
    		+ '<td>' + e.barcode + '</td>'
    		+ '<td>'  + e.name + '</td>'
    		+ '<td>'  + e.mrp + '</td>'
    		+ '<td>'  + e.quantity + '</td>'
    		+ '<td>'  + e.total + '</td>'
    		+ '</tr>';
            $tbody.append(row);
    	}
}

function init()
{
    $('#show-revenue').click(getProductRevenueList);
}

$(document).ready(init);
