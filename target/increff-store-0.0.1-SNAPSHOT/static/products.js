function getStoreUrl(){
 	var baseUrl = $("meta[name=baseUrl]").attr("content")
 	return baseUrl + "/api/product";
 }

function getBrandUrl()
{
    var baseUrl = $("meta[name=baseUrl]").attr("content")
     	return baseUrl + "/api/brand";
}

function getProductList(){
	var url = getStoreUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   console.log(data);
	   		displayProductList(data);
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
		var buttonHtml = ' <button >edit</button>'
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>'  + e.brandCategory + '</td>'
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
    var newBrands = {};
    var newCategory = {};
    for(var i in data)
    {
        var a = data[i].brand;
        var b = data[i].category;
        Object.assign(newBrands, {[a]:[]});
        Object.assign(newCategory, {[b]:[]});
    }

    var $elB = $("#inputBrandName");
    var $elC = $("#inputBrandCategory");

    $elB.empty();
    $elC.empty();

    $.each(newBrands, function(key,value) {
          $elB.append($("<option></option>")
             .attr("value", value).text(key));
        });

    $.each(newCategory, function(key,value) {
              $elC.append($("<option></option>")
                 .attr("value", value).text(key));
            });
}

displayCategoryList(e)
{
    console.log(e);
    }

function setStatus(message)
{
    document.getElementById("status").innerHTML = "status: " + message;
}

function init()
{
    $('#add-product').click(addBrand);
    $('#inputBrandName').onchange(displayCategoryList(e));
}

$(document).ready(getProductList);
$(document).ready(init);
$(document).ready(getBrandsList);