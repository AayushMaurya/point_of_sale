function getStoreUrl(){
 	var baseUrl = $("meta[name=baseUrl]").attr("content")
 	return baseUrl + "/api/order";
 }

function getOrderList()
{
    var callParams = {};
    callParams.Type = "GET";
    callParams.Url = getStoreUrl();

    ajaxCall(callParams, null, displayOrderList);
}

function getOrderListByDateFilter()
{
    var callParams = {};
    callParams.Url = getStoreUrl() + "/date-filter";
    callParams.Type = "POST";
    var $form = $("#date-filter-form");
    var dataParams = toJson($form);

    ajaxCall(callParams, dataParams, displayOrderList);

    return false;
}

function displayOrderList(data){
    $('#order-table').DataTable().destroy();
 	var $tbody = $('#order-table').find('tbody');
 	$tbody.empty();
 	var index = 0;
 	for(var i in data){
 		var e = data[i];
 		index++;
 		var buttonHtml = ' <button class="btn btn-secondary" onClick=redirect("'+ e.orderCode +'")>Open</button>'
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
     var dataParams = toJson($form);
     var callParams = {};
     callParams.Url = getStoreUrl();
     callParams.Type = "POST";

     function callback(data){
        getOrderList();
        redirect(data);
     }

     ajaxCall(callParams, dataParams, callback);

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