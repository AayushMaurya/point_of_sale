function getStoreUrl(){
 	var baseUrl = $("meta[name=baseUrl]").attr("content")
 	return baseUrl + "/api/inventory";
 }

function getProductUrl(){
 	var baseUrl = $("meta[name=baseUrl]").attr("content")
 	return baseUrl + "/api/product";
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
	   error: handleAjaxError
	});
}

function displayInventoryList(data){
	var $tbody = $('#inventory-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter"'
		+ 'onclick="fillFields('
		+ e.id +')">edit</button>';
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}

	pagination();
}

function fillFields(id)
{
    console.log("this will fill the fields: " + id);
    var url = getProductUrl() + "/" + id;
    $.ajax({
    	   url: url,
    	   type: 'GET',
    	   success: function(data) {
    	   document.getElementById("inputUpdateBarcode").value = data.barcode;
    	   },
    	   error: handleAjaxError
    	});

}

function addInventory(event)
{
console.log("anything");
    var $form = $("#inventory-form");
    var json = toJson($form);
    console.log(json);
    var url = getStoreUrl();

    $.ajax({
    	   url: url,
    	   type: 'POST',
    	   data: json,
    	   headers: {
           	'Content-Type': 'application/json'
           },
    	   success: function(response) {
    	   		getInventoryList();
    	   		setStatus(response);
    	   },
    	   error: handleAjaxError

    	});
    	return false;
}

function updateInventoryAdd()
{
    console.log("this will update inventory Add");
    var $form = $("#updateInventoryForm");
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
        getInventoryList()
        setStatus(response);
        },
           error: handleAjaxError

        });
}

function updateInventoryRemove()
{
    console.log("this will update inventory Remove");
    var $form = $("#updateInventoryForm");
    var json = toJson($form);
    var url = getStoreUrl();

    $.ajax({
        url: url,
        type: 'PUT',
        data: json,
        headers: {
        'Content-Type': 'application/json'
        },
        success: function(response) {
        getInventoryList()
        setStatus(response);
        },
        error: handleAjaxError

        });
}

function setStatus(message)
{
    document.getElementById("status").innerHTML = "status: " + message;
}

function pagination(){
  $('#inventory-table').DataTable();
  $('.dataTables_length').addClass('bs-select');
}

function init()
{
    $('#add-inventory').click(addInventory);
    $('#update-inventory-add').click(updateInventoryAdd);
    $('#update-inventory-remove').click(updateInventoryRemove);
}

$(document).ready(getInventoryList);
$(document).ready(init);