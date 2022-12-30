function getStoreUrl(){
 	var baseUrl = $("meta[name=baseUrl]").attr("content")
 	return baseUrl + "/api/product";
 }

function getProductList(){
	var url = getStoreUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   console.log(data);
	   		displayProductList(data);
	   },
//	   error: handleAjaxError
	});
}

function displayProductList(data){
	var $tbody = $('#product-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button >delete</button>'
		buttonHtml += ' <button >edit</button>'
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>'  + e.brandCategory + '</td>'
		+ '<td>'  + e.name + '</td>'
		+ '<td>'  + e.mrp + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

$(document).ready(getProductList);