function getStoreUrl(){
 	var baseUrl = $("meta[name=baseUrl]").attr("content")
 	return baseUrl + "/api/inventory";
 }

function getInventoryList(){
	var url = getStoreUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   console.log(data);
	   		displayInventoryList(data);
	   },
//	   error: handleAjaxError
	});
}

function displayInventoryList(data){
	var $tbody = $('#inventory-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button >delete</button>'
		buttonHtml += ' <button >edit</button>'
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

$(document).ready(getInventoryList);