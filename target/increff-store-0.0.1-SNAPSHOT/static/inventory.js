function getStoreUrl(){
 	var baseUrl = $("meta[name=baseUrl]").attr("content")
 	return baseUrl + "/api/inventory";
 }

 function getUpdateInventoryUrl(){
  	var baseUrl = $("meta[name=baseUrl]").attr("content")
  	return baseUrl + "/api/inventory-update";
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
    $('#inventory-table').DataTable().destroy();
	var $tbody = $('#inventory-table').find('tbody');
	var index = 0;
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		index++;
		var buttonHtml = '<button class="btn btn-primary" data-toggle="modal"'
		+ 'data-target="#exampleModalCenter"'
		+ 'onclick="fillFields('
		+ e.id +')">Edit</button>';
		var row = '<tr>'
		+ '<td>' + index+ '</td>'
		+ '<td>' + e.barcode+ '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td class="supervisor-view">' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
    if($("meta[name=role]").attr("content") == "operator")
        hideSupervisorView();
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
    	   		handleSuccess("Inventory Added");
    	   		document.getElementById("inventory-form").reset();
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
        handleSuccess("Inventory Added");
        $('#exampleModalCenter').modal('hide');
        document.getElementById('updateInventoryForm').reset();
        },
           error: handleAjaxError
        });
}

function updateInventoryRemove()
{
    var $form = $("#updateInventoryForm");
    var json = toJson($form);
    var url = getAdminInventoryUrl();

    $.ajax({
        url: url,
        type: 'PUT',
        data: json,
        headers: {
        'Content-Type': 'application/json'
        },
        success: function(response) {
        getInventoryList();
        handleSuccess("Inventory reduced");
            document.getElementById('updateInventoryForm').reset();
        },
        error: handleAjaxError
        });
}

function pagination(){
  $('#inventory-table').DataTable();
  $('.dataTables_length').addClass('bs-select');
}

// upload by TSV methods

var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#inventoryFile')[0].files[0];
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
	fileData = results.data;
	if(fileData.length > 5000)
    	{
    	    document.getElementById('status-message').innerHTML = "Data length cannot be grater than 500";
                document.getElementById('status').style.backgroundColor = "red";
               	$('.toast').toast('show');
            return false;
    	}
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
	var url = getAdminInventoryUrl();

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
	   		getInventoryList();

	   },
	   error: function(response){
	   		row.error=response.responseText
	   		errorData.push(row);
	   		uploadRows();
	   }
	});

}

function downloadErrors(){
	writeFileData(errorData);
}


function resetUploadDialog(){
	//Reset file name
	var $file = $('#inventoryFile');
	$file.val('');
	$('#inventoryFileName').html("Choose File");
	//Reset various counts
	processCount = 0;
	processCount = 0;
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
	var $file = $('#inventoryFile');
	var fileName = $file.val();
	$('#inventoryFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog();
	$('#upload-inventory-modal').modal('toggle');
}

function updateInventoryEdit()
{
        var $form = $("#updateInventoryForm");
        var json = toJson($form);
        var url = getUpdateInventoryUrl();

        $.ajax({
            url: url,
            type: 'POST',
            data: json,
            headers: {
            'Content-Type': 'application/json'
            },
            success: function(response) {
            getInventoryList()
                handleSuccess("Inventory Added");
                $('#exampleModalCenter').modal('hide');
                document.getElementById('updateInventoryForm').reset();
            },
               error: handleAjaxError
            });
}

function init()
{
    $('#add-inventory').click(addInventory);
    $('#update-inventory-add').click(updateInventoryAdd);
    $('#update-inventory-remove').click(updateInventoryRemove);
    $('#update-inventory-edit').click(updateInventoryEdit);
    $('#upload-data').click(displayUploadData);
    $('#process-data').click(processData);
    $('#download-errors').click(downloadErrors);
    $('#inventoryFile').on('change', updateFileName)
    if($("meta[name=role]").attr("content") == "operator")
            document.getElementById('supervisor-view').style.display = "none";
}

$(document).ready(getInventoryList);
$(document).ready(init);