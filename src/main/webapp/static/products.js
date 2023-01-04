var newBrands = {};

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
            .attr("value", newBrands[a]).text(newBrands[a][i]));
    }
}

function setStatus(message)
{
    document.getElementById("status").innerHTML = "status: " + message;
}

function init()
{
    $('#add-product').click(addBrand);
    $('#inputBrandName').change(displayCategoryList);
}

$(document).ready(getProductList);
$(document).ready(getBrandsList);
$(document).ready(init);