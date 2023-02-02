var newBrands = {};

var productData;

function getStoreUrl(){
 	var baseUrl = $("meta[name=baseUrl]").attr("content")
 	return baseUrl + "/api/product";
 }

function getBrandUrl()
{
    var baseUrl = $("meta[name=baseUrl]").attr("content")
     	return baseUrl + "/api/brand";
}

function getBrandOption() {
        selectElement = document.querySelector('#inputBrandName');
        output = selectElement.options[selectElement.selectedIndex].value;
        return output;
}

function getProductList(){
	var url = getStoreUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   console.log(data);
	   		displayProductList(data);
	   		productData = data;
	   },
	   error: handleAjaxError
	});
}

function displayProductList(data){
    $('#product-table').DataTable().destroy();
	var $tbody = $('#product-table').find('tbody');
	$tbody.empty();
	var index = 0;
	for(var i in data){
		var e = data[i];
		index++;
		var buttonHtml = ' <button class="btn btn-primary" data-toggle="modal" '
		+ 'data-target="#exampleModalCenter" onclick="fillFields('+ i +')">Edit</button>'
		var row = '<tr>'
		+ '<td>' + index + '</td>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>'  + e.brand + '</td>'
		+ '<td>'  + e.category + '</td>'
		+ '<td>'  + e.name + '</td>'
		+ '<td>'  + e.mrp + '</td>'
		+ '<td class="supervisor-view">' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
     if($("meta[name=role]").attr("content") == "operator")
            hideSupervisorView();
	pagination();
}

function addProduct(event)
{
    var $form = $("#product-form");
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
    	   		getProductList();
    	   		handleSuccess("Product Added");
    	   		document.getElementById("product-form").reset();
    	   },
    	   error: handleAjaxError

    	});
    	return false;
}

function getBrandsList()
{
    var url = getBrandUrl();
    $.ajax({
    	   url: url,
    	   type: 'GET',
    	   success: function(data) {
    	   		console.log(data);
    	   		displayBrandsList(data);
    	   }
    	});
   }


function displayBrandsList(data)
{
    for(var i in data)
    {
        var a = data[i].brand;
        var b = data[i].category;
        if(!newBrands.hasOwnProperty(a))
            Object.assign(newBrands, {[a]:[]});
        newBrands[a].push(b);
    }

    console.log(newBrands);

    var $elB = $("#inputBrandName");

    $elB.empty();

    $.each(newBrands, function(key,value) {
          $elB.append($("<option></option>")
             .attr("value", key).text(key));
        });

    displayCategoryList();
}

function displayCategoryList()
{
    var $elC = $("#inputBrandCategory");

    $elC.empty();

    console.log("this is it");
    var a = getBrandOption();

    console.log(newBrands[a]);

    for(var i=0; i<newBrands[a].length; i++)
    {
        $elC.append($("<option></option>")
            .attr("value", newBrands[a][i]).text(newBrands[a][i]));
    }
}

function fillFields(index)
{
    document.getElementById("inputUpdateName").value = productData[index].name;
    document.getElementById("inputUpdateMrp").value = productData[index].mrp;
    document.getElementById("inputUpdateId").value = productData[index].id;

}

function updateProduct()
{
    var id = document.getElementById("inputUpdateId").value;
    var $form = $("#updateProductForm");
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
            	   		getProductList();
            	   		handleSuccess("Product Updated");
            	   		$('#exampleModalCenter').modal('hide');
            	   },
            	   error: handleAjaxError

            	});
            	return false;

}

function pagination(){
  $('#product-table').DataTable();
  $('.dataTables_length').addClass('bs-select');
}

// methods for upload by tsv

var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#productFile')[0].files[0];
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
	console.log(json);


	console.log(json);
	var url = getAdminProductUrl();

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
	   		getProductList();

	   },
	   error: function(response){
	   		row.error=response.responseText
	   		errorData.push(row);
	   		uploadRows();
	   }
	});

	console.log("Finally uploaded");

}

function downloadErrors(){
	writeFileData(errorData);
}


function resetUploadDialog(){
	//Reset file name
	var $file = $('#productFile');
	$file.val('');
	$('#productFileName').html("Choose File");
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
	var $file = $('#productFile');
	var fileName = $file.val();
	$('#productFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog();
	$('#upload-product-modal').modal('toggle');
}

function init()
{
    $('#add-product').click(addProduct);
    $('#inputBrandName').change(displayCategoryList);
    $('#update-product').click(updateProduct);
    $('#inputUpdateBrand').change(displayCategoryList);
    $('#upload-data').click(displayUploadData);
    $('#process-data').click(processData);
    $('#download-errors').click(downloadErrors);
    $('#productFile').on('change', updateFileName);
    if($("meta[name=role]").attr("content") == "operator")
            document.getElementById('supervisor-view').style.display = "none";
}

$(document).ready(getProductList);
$(document).ready(getBrandsList);
$(document).ready(init);