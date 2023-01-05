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
		var buttonHtml = '<button onclick="fillFields('+ e.id +')">edit</button>';
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

function fillFields(id)
{
    console.log("this will fill the fields");

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
    $('#add-inventory').click(addInventory);
}

$(document).ready(getInventoryList);
$(document).ready(init);