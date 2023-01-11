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

function getBrandOption2() {
        selectElement = document.querySelector('#inputUpdateBrand');
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
//	   error: handleAjaxError
	});
}

function displayProductList(data){
	var $tbody = $('#product-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
//		var buttonHtml = '<button >delete</button>'
		var buttonHtml = ' <button class="btn btn-primary" data-toggle="modal" '
		+ 'data-target="#exampleModalCenter" onclick="fillFields('+ i +')">edit</button>'
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>'  + e.brand + '</td>'
		+ '<td>'  + e.category + '</td>'
		+ '<td>'  + e.name + '</td>'
		+ '<td>'  + e.mrp + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

function addBrand(event)
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
    	   		setStatus(response);
    	   },
//    	   error: handleAjaxError
//            error: setStatus(response)

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
    var $elB2 = $("#inputUpdateBrand");

    $elB.empty();
    $elB2.empty();

    $.each(newBrands, function(key,value) {
          $elB.append($("<option></option>")
             .attr("value", key).text(key));
             $elB2.append($("<option></option>")
                          .attr("value", key).text(key));
        });

    displayCategoryList();
}

function displayCategoryList()
{
    var $elC = $("#inputBrandCategory");
    var $elC2 = $("#inputUpdateCategory");

    $elC.empty();
    $elC2.empty();

    console.log("this is it");
    var a = getBrandOption();
    var b = getBrandOption2();

    console.log(newBrands[a]);

    for(var i=0; i<newBrands[a].length; i++)
    {
        $elC.append($("<option></option>")
            .attr("value", newBrands[a][i]).text(newBrands[a][i]));
        $elC2.append($("<option></option>")
                    .attr("value", newBrands[b][i]).text(newBrands[b][i]));
    }
}

function fillFields(index)
{
    document.getElementById("inputUpdateId").value = productData[index].id;
    document.getElementById("updateProductForm").reset();
    document.getElementById("inputUpdateBarcode").value = productData[index].barcode;
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

function pagination(){
  $('#product-table').DataTable({
    "pagingType": "simple" // false to disable pagination (or any other option)
  });
  $('.dataTables_length').addClass('bs-select');
}

function init()
{
    $('#add-product').click(addBrand);
    $('#inputBrandName').change(displayCategoryList);
    $('#update-product').click(updateProduct);
    $('#inputUpdateBrand').change(displayCategoryList);
}

$(document).ready(getProductList);
$(document).ready(getBrandsList);
$(document).ready(init);