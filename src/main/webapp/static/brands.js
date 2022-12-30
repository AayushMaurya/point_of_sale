function getStoreUrl(){
 	var baseUrl = $("meta[name=baseUrl]").attr("content")
 	return baseUrl + "/api/brand";
 }

function getBrandList(){
	var url = getStoreUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   console.log(data);
	   		displayBrandList(data);
	   },
//	   error: handleAjaxError
	});
}

function displayBrandList(data){
	var $tbody = $('#brand-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button >delete</button>'
		buttonHtml += ' <button >edit</button>'
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>'  + e.category + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

$(document).ready(getBrandList);