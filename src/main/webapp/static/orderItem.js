var orderId;
var orderCode
var status;
var customerName;
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
	url += "/order-code/" + orderCode;
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
		var index = i+1;
		var buttonHtml = ' <button class="btn-disable btn btn-primary" onclick="deleteOrderItem('
		+ e.id + ')">delete</button>'
		+ ' <button onclick="fillFields(' + e.id + ','
		+ e.orderId + ',' + e.productId + ',' + e.quantity + ','
		+ e.sellingPrice + ')" class="btn-disable btn btn-primary" data-toggle="modal"'
		+ 'data-target="#exampleModalCenter">Edit</button>';
		var row = '<tr>'
		+ '<td>' + index + '</td>'
		+ '<td>' + e.productName + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + e.sellingPrice + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
	if(status === "Placed")
	    disableEditing();
	if(data.length == 0)
	    document.getElementById('place-order').disabled = true;
	else
	    document.getElementById('place-order').disabled = false;
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
    	   		handleSuccess("Item added");
    	   },
    	   error: handleAjaxError

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
        	   		alert(response);
        	   		location.reload();
        	   		handleSuccess("Order Placed");
        	   },
        	   error: handleAjaxError

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
    	   		handleSuccess("Item deleted");
    	   },
    	   error: handleAjaxError
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
//
function updateOrderItem()
{
    console.log("this function will update order item");
    var $form = $("#editOrderItemForm");
    var json = toJson($form);
    var url = getStoreUrl();

    if((JSON.parse(json).quantity) == 0)
    {
        deleteOrderItem(JSON.parse(json).id);
        return;
    }

    $.ajax({
        	   url: url,
        	   type: 'PUT',
        	   data: json,
        	   headers: {
               	'Content-Type': 'application/json'
               },
        	   success: function(response) {
        	   		getOrderItemList();
        	   		handleSuccess("Item Updated");
        	   },
        	   error: handleAjaxError

        	});
}

function disableEditing()
{
    const buttons = document.getElementsByClassName("btn-disable");
    for (let i = 0; i < buttons.length; i++)
      buttons[i].disabled=true;

    document.getElementById('add-Item').disabled = true;
    document.getElementById('place-order').disabled = true;
    $('#download-invoice').disabled = true;
}

function downloadInvoice()
{
    console.log("This will start downloading invoice");
}

function init()
{
    orderId = $("meta[name=orderId]").attr("content");
    orderCode = $("meta[name=orderCode]").attr("content");
    customerName = $("meta[name=customerName]").attr("content");
    document.getElementById("inputOrderId").value = orderId;
    document.getElementById("customer-name").innerHTML = customerName;

    status = $("meta[name=status]").attr("content");

    $('#add-Item').click(addOrderItem);
    $('#place-order').click(placeOrder);
    $('#update-orderItem').click(updateOrderItem);
    $('#download-invoice').click(downloadInvoice).disabled = true;
}

$(document).ready(init);
$(document).ready(getOrderItemList);