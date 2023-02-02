var brandData;

function getStoreUrl(){
 	var baseUrl = $("meta[name=baseUrl]").attr("content")
 	return baseUrl + "/api/brand";
 }

function getBrandList(){
    var callParams = {};
    callParams.Type = "GET";
    callParams.Url = getStoreUrl();
    function callback(data)
    {
        displayBrandList(data);
        brandData = data;
    }
    ajaxCall(callParams, null, callback);
}

function displayBrandList(data){
    $('#brand-table').DataTable().destroy();
	var $tbody = $('#brand-table').find('tbody');
	$tbody.empty();
	var index = 0;
	for(var i in data){
		var e = data[i];
		index++;
		var buttonHtml = ' <button class="btn btn-primary" data-toggle="modal"'
        + 'data-target="#exampleModalCenter" onclick="fillUpdateFields('
		+ i +')" >Edit</button>';
		var row = '<tr>'
		+ '<td>' + index + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>'  + e.category + '</td>'
		+ '<td class="supervisor-view">' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
	if($("meta[name=role]").attr("content") == "operator")
        hideSupervisorView();
	pagination();
}

function addBrand()
{
    var $form = $("#brand-form");
    var dataParams = toJson($form);
    var callParams = {};
    callParams.Type = "POST";
    callParams.Url = getStoreUrl();

    function callback(data){
        getBrandList();
        handleSuccess("Brand added successfully");
        document.getElementById("brand-form").reset();
    }

    ajaxCall(callParams, dataParams, callback);

    return false;
}

function addBrand()
{
    var callParams = {};
    callParams.Type = "POST";
    callParams.Url = getStoreUrl();
    var $form = $("#brand-form");
    var dataParams = toJson($form);

    function callback(data)
    {
        getBrandList();
        handleSuccess("Brand added successfully");
        document.getElementById("brand-form").reset();
    }

    ajaxCall(callParams, dataParams, callback)
}

function fillUpdateFields(i)
{
    document.getElementById("inputUpdateBrandId").value = brandData[i].id;
    document.getElementById("inputUpdateBrand").value = brandData[i].brand;
    document.getElementById("inputUpdateCategory").value = brandData[i].category;
}

function updateBrand()
{
    var id = document.getElementById("inputUpdateBrandId").value;
    var $form = $("#updateBrandForm");
    var dataParams = toJson($form);
    var callParams = {};
    callParams.Url = getStoreUrl() + "/" + id ;
    callParams.Type = "PUT";

    function callback(data){
        getBrandList();
        handleSuccess("Brand Updated");
        $('#exampleModalCenter').modal('hide');
    }

    ajaxCall(callParams, dataParams, callback);

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
	if(fileData.length > 5000){
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
	   		row.error=JSON.parse(response.responseText).message;
	   		errorData.push(row);
	   		uploadRows();
	   }
	});
}

function downloadErrors(){
    if(errorData.length >= 0)
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
	console.log($file);
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
    if($("meta[name=role]").attr("content") == "operator")
        document.getElementById('supervisor-view').style.display = "none";
}

$(document).ready(getBrandList);
$(document).ready(init);