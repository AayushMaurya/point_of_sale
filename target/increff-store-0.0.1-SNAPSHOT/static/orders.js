function getStoreUrl(){
 	var baseUrl = $("meta[name=baseUrl]").attr("content")
 	return baseUrl + "/api/order";
 }

function getOrderList(){
	var url = getStoreUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   console.log(data);
	   		displayOrderList(data);
	   },
	   error: handleAjaxError
	});
	return false;
}

function getOrderListByDateFilter()
{
console.log("filtered orders");
    var url = getStoreUrl() + "/date-filter";
    var $form = $("#date-filter-form");
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
    	   		displayOrderList(data);
    	   },
    	   error: handleAjaxError
    	});
    	return false;
}

function displayOrderList(data){
 	var $tbody = $('#order-table').find('tbody');
 	$tbody.empty();
 	var index = 0;
 	for(var i in data){
 		var e = data[i];
 		index++;
 		var buttonHtml = ' <button class="btn btn-secondary" onClick=redirect("'+ e.id +'")>Open</button>'
 		var row = '<tr>'
 		+ '<td>' + index + '</td>'
 		+ '<td>' + e.customerName + '</td>'
 		+ '<td>' + e.createdDataTime + '</td>'
 		+ '<td>' + e.status + '</td>'
 		+ '<td>' + e.placedDataTime + '</td>'
 		+ '<td>' + buttonHtml + '</td>'
 		+ '</tr>';
         $tbody.append(row);
 	}

 	pagination();
 }

function redirect(id)
{
    console.log("redirecting to :" + url);
    var url = $("meta[name=baseUrl]").attr("content") + "/ui/order-item/" + id;
    window.location.href = url;
}

 function createOrder(event)
 {
     var $form = $("#order-form");
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
     	   		getOrderList();
     	   		console.log("this should be order id: " + response);
     	   		redirect(response);
     	   },
     	   error: handleAjaxError

     	});
     	return false;
 }

 function pagination(){
   $('#order-table').DataTable();
   $('.dataTables_length').addClass('bs-select');
 }

 function init()
 {
    console.log("initializing");
    $('#create-order').click(createOrder);
    $('#apply-date-filter').click(getOrderListByDateFilter);
 }

 $(document).ready(init);
 $(document).ready(getOrderList);