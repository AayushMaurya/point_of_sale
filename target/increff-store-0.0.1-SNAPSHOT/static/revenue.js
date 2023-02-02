var newBrands = {};
var salesReportData;

function getRevenueUrl(){
 	var baseUrl = $("meta[name=baseUrl]").attr("content")
 	return baseUrl + "/api/admin/sales-report";
 }

function getBrandUrl()
{
    var baseUrl = $("meta[name=baseUrl]").attr("content")
     	return baseUrl + "/api/brand";
}

function getBrandOption() {
        selectElement = document.querySelector('#inputFilterBrand');
        output = selectElement.options[selectElement.selectedIndex].value;
        return output;
}

function getCategoryOption() {
        selectElement = document.querySelector('#inputFilterCategory');
        output = selectElement.options[selectElement.selectedIndex].value;
        return output;
}

function getRevenueList(){
	var url = getRevenueUrl();
	var $form = $("#filter-date-form");
    var json = toJson($form);
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
            'Content-Type': 'application/json'
       },
	   success: function(data) {
	   console.log(data);
	   		displayRevenueProductList(data);
	   },
	   error: handleAjaxError
	});
	return false;
}

function displayRevenueProductList(data)
{
    salesReportData = data;
    $('#product-revenue-list-table').DataTable().destroy();
    var $tbody = $('#product-revenue-list-table').find('tbody');
    $tbody.empty();
    var total = 0;
    var index = 0;
    for(var i in data){
    		var e = data[i];
            total = total + e.total;
    		index++;
    		var row = '<tr>'
    		+ '<td>' + index + '</td>'
    		+ '<td>' + e.brand + '</td>'
    		+ '<td>'  + e.category + '</td>'
    		+ '<td>'  + e.quantity + '</td>'
    		+ '<td>'  + e.total + '</td>'
    		+ '</tr>';
            $tbody.append(row);
    	}
    	var row = '<tr>'
            		+ '<td></td>'
            		+ '<td></td>'
            		+ '<td></td>'
            		+ '<td>' + "Total" + '</td>'
            		+ '<td>'  + total + '</td>'
            		+ '</tr>';
                    $tbody.append(row);
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
       Object.assign(newBrands, {"All":[]});
       for(var i in data)
       {
           var a = data[i].brand;
           var b = data[i].category;
           if(!newBrands.hasOwnProperty(a))
               Object.assign(newBrands, {[a]:[]});
           newBrands[a].push(b);
           newBrands["All"].push(b);
       }

       newBrands["All"] = removeDuplicates(newBrands["All"]);

       console.log(newBrands);

       var $elB = $("#inputFilterBrand");

       $elB.empty();

       $.each(newBrands, function(key,value) {
             $elB.append($("<option></option>")
                .attr("value", key).text(key));
           });

       displayCategoryList();
   }

function displayCategoryList()
{
    var $elC = $("#inputFilterCategory");

    $elC.empty();

    console.log("this is it");
    var a = getBrandOption();

    console.log(newBrands[a]);

    $elC.append($("<option></option>")
                    .attr("value", "All").text("All"));

    for(var i=0; i<newBrands[a].length; i++)
    {
        $elC.append($("<option></option>")
            .attr("value", newBrands[a][i]).text(newBrands[a][i]));
    }
}

function removeDuplicates(arr) {
        return arr.filter((item,
            index) => arr.indexOf(item) === index);
}

function downloadSalesReport()
{
    if(salesReportData.length == 0)
        return;
    salesReportData.forEach(function(v){ delete v.id });
    writeFileData(salesReportData);
}

function init()
{
    $('#show-revenue').click(getRevenueList);
    $('#download-sales-report').click(downloadSalesReport);
    $('#inputFilterBrand').change(displayCategoryList);
}

$(document).ready(init);
$(document).ready(getBrandsList);