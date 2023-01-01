function getStoreUrl(){
 	var baseUrl = $("meta[name=baseUrl]").attr("content")
 	return baseUrl + "/api/order-item";
 }

function getOrderItemList(){
	var url = getStoreUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   console.log(data);
	   		displayOrderItemList(data);
	   },
//	   error: handleAjaxError
	});
}

function displayOrderItemList(data){
	var $tbody = $('#orderItem-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = ' <button >edit</button>'
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.orderId + '</td>'
		+ '<td>'  + e.productId + '</td>'
		+ '<td>'  + e.quantity + '</td>'
		+ '<td>'  + e.sellingPrice + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

function addOrderItem(event)
{
    var $form = $("#brand-form");
    var json = toJson($form);
    var url = getStoreUrl();

    $.ajax({
    	   url: url,
    	   type: 'POST',
    	   data: json,
    	   headers: {
           	'Content-Type': 'application/json'
           },
    	   success: function(response) {
    	   		getOrderItemList();
    	   		setStatus(response);
    	   },
//    	   error: handleAjaxError
//            error: setStatus(response)

    	});
    	return false;
}

function setStatus(message)
{
    document.getElementById("status").innerHTML = "status: " + message;
}

function init()
{
    $('#add-item').click(addOrderItem);
}

$(document).ready(getOrderItemList);
$(document).ready(init);