var orderId;
var status;
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
		var buttonHtml = ' <button class="btn btn-primary" onclick="deleteOrderItem(' + e.id + ')">delete</button>'
		+ ' <button onclick="fillFields(' + e.id + ','
		+ e.orderId + ',' + e.productId + ',' + e.quantity + ','
		+ e.sellingPrice + ')" class="btn btn-primary" data-toggle="modal"'
		+ 'data-target="#exampleModalCenter">edit</button>'
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

function deleteOrderItem(id)
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

function fillFields(id, orderId, productId, quantity, sellingPrice)
{
console.log("filling the update order item form fields");
    document.getElementById("inputUpdateOrderItemId").value = id;
    document.getElementById("inputUpdateOrderId").value = orderId;
    document.getElementById("inputUpdateProductId").value = productId;
    document.getElementById("inputUpdateQuantity").value = quantity;
    document.getElementById("inputUpdateMrp").value = sellingPrice;
}

function updateOrderItem()
{
    console.log("this function will update order item");
    var $form = $("#editOrderItemForm");
    var json = toJson($form);
    var url = getStoreUrl();
    console.log(json);

    $.ajax({
        	   url: url,
        	   type: 'PUT',
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
    orderId = $("meta[name=orderId]").attr("content");
    document.getElementById("inputOrderId").value = orderId;

    $('#add-Item').click(addOrderItem);
    $('#place-order').click(placeOrder);
    $('#update-orderItem').click(updateOrderItem);
}

$(document).ready(init);
$(document).ready(getOrderItemList);