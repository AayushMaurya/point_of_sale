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
	   error: handleAjaxError(response);
	});
}

function displayBrandList(data){
	var $tbody = $('#brand-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = ' <button class="btn btn-primary" data-toggle="modal"'
        + 'data-target="#exampleModalCenter" onclick="fillUpdateFields('
		+ e.id +')" >edit</button>';
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>'  + e.category + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
	pagination();
}

function addBrand(event)
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
    	   		getBrandList();
    	   		setStatus(response);
    	   },
    	   error: handleAjaxError(response);
    	});
    	return false;
}

function fillUpdateFields(id)
{
    document.getElementById("inputUpdateBrandId").value = id;
}

function updateBrand()
{
    var id = document.getElementById("inputUpdateBrandId").value;
    var $form = $("#updateBrandForm");
    var json = toJson($form);
    var url = getStoreUrl() + "/" + id ;

    $.ajax({
        	   url: url,
        	   type: 'PUT',
        	   data: json,
        	   headers: {
               	'Content-Type': 'application/json'
               },
        	   success: function(response) {
        	   		getBrandList();
        	   		setStatus(response);
        	   },
        	   error: handleAjaxError(response);

        	});
        	return false;
}

function setStatus(message)
{
    document.getElementById("status").innerHTML = "status: " + message;
}

function pagination(){
  $('#brand-table').DataTable({
    "pagingType": "simple" // false to disable pagination (or any other option)
  });
  $('.dataTables_length').addClass('bs-select');
}

function init()
{
    $('#add-brand').click(addBrand);
    $('#update-brand').click(updateBrand);
}

$(document).ready(getBrandList);
$(document).ready(init);