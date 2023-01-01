var orderId;

function getStoreUrl(){
 	var baseUrl = $("meta[name=baseUrl]").attr("content")
 	return baseUrl + "/api/order-item";
 }

function getOrderUrl(){
var baseUrl = $("meta[name=baseUrl]").attr("content")
 	return baseUrl + "/api/order/place";
}

function getOrderItemList(){
	var url = getStoreUrl();
	url += "/" + orderId;
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
		buttonHtml += ' <button onclick="deleteOrderItem(' + e.id + ')">edit</button>''
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
    var $form = $("#orderItem-form");
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

function placeOrder()
{
    var url = getOrderUrl() + "/" + orderId;
    $.ajax({
        	   url: url,
        	   type: 'PUT',
        	   headers: {
               	'Content-Type': 'application/json'
               },
        	   success: function(response) {
        	   console.log("order placed");
        	   		setStatus(response);
        	   },
    //    	   error: handleAjaxError
    //            error: setStatus(response)

        	});
}

function deleteOrderItem(int id)
{
    var url = getStoreUrl() + "/" + id;

    	$.ajax({
    	   url: url,
    	   type: 'DELETE',
    	   success: function(data) {
    	   		getOrderItemList();
    	   		setStatus(response);
    	   },
//    	   error: handleAjaxError
    	});
}

function setStatus(message)
{
    document.getElementById("status").innerHTML = "status: " + message;
}

function init()
{
    orderId = $("meta[name=orderId]").attr("content");
    document.getElementById("inputOrderId").value = orderId;
    $('#add-Item').click(addOrderItem);
    $('#place-order').click(placeOrder);
}

$(document).ready(init);
$(document).ready(getOrderItemList);