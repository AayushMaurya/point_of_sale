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
	   error: handleAjaxError
	});
}

function displayBrandList(data){
	var $tbody = $('#brand-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = ' <button class="btn btn-primary" data-toggle="modal"'
        + 'data-target="#exampleModalCenter" onclick="fillUpdateFields('
		+ e.id +')" >Edit</button>';
		var index = i+1;
		var row = '<tr>'
		+ '<td>' + index + '</td>'
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
    	   		handleSuccess("Brand added successfully");
    	   },
    	   error: handleAjaxError
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
        	   		handleSuccess("Brand Updated");
        	   },
        	   error: handleAjaxError

        	});
        	return false;
}


// functions for uploading TSV

var fileData = [];
var errorData = [];
var processCount = 0;

function processData(){
	var file = $('#brandFile')[0].files[0];
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
	fileData = results.data;
	uploadRows();
}

function uploadRows(){
	//Update progress
	updateUploadDialog();
	//If everything processed then return
	if(processCount==fileData.length){
		return;
	}

	//Process next row
	var row = fileData[processCount];
	processCount++;

	var json = JSON.stringify(row);
	var url = getStoreUrl();

	//Make ajax call
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		uploadRows();
	   		getBrandList();

	   },
	   error: function(response){
	   		row.error=response.responseText
	   		errorData.push(row);
	   		uploadRows();
	   }
	});

	console.log("Finallllyy uploaded");

}

function downloadErrors(){
	writeFileData(errorData);
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#brandFile');
	$file.val('');
	$('#brandFileName').html("Choose File");
	//Reset various counts
	processCount = 0;
	fileData = [];
	errorData = [];
	//Update counts
	updateUploadDialog();
}

function updateUploadDialog(){
	$('#rowCount').html("" + fileData.length);
	$('#processCount').html("" + processCount);
	$('#errorCount').html("" + errorData.length);
}

function updateFileName(){
	var $file = $('#brandFile');
	var fileName = $file.val();
	$('#brandFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog();
	$('#upload-brand-modal').modal('toggle');
}

function pagination(){
  $('#brand-table').DataTable();
  $('.dataTables_length').addClass('bs-select');
}

function init()
{
    $('#add-brand').click(addBrand);
    $('#update-brand').click(updateBrand);
    $('#upload-data').click(displayUploadData);
    $('#process-data').click(processData);
    $('#download-errors').click(downloadErrors);
    $('#brandFile').on('change', updateFileName);
}

$(document).ready(getBrandList);
$(document).ready(init);