var inventoryData;
var newBrands = {};

function getInventoryReportUrl(){
 	var baseUrl = $("meta[name=baseUrl]").attr("content")
 	return baseUrl + "/api/inventory-report";
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

function getInventoryReportList(){
    var url = getInventoryReportUrl();

    $.ajax({
    	   url: url,
    	   type: 'GET',
    	   success: function(data) {
    	   console.log(data);
    	   inventoryData = data;
    	   		displayInventoryReport(data);
    	   		displayBrandList(data);
    	   },
    	   error: handleAjaxError
    	});
}

function displayBrandList(data)
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

    var a = getBrandOption();

    $elC.append($("<option></option>")
        .attr("value", "All").text("All"));

    for(var i=0; i<newBrands[a].length; i++)
    {
        $elC.append($("<option></option>")
            .attr("value", newBrands[a][i]).text(newBrands[a][i]));
    }
}


function displayInventoryReport(data)
{
    var $tbody = $('#inventory-report-table').find('tbody');
    $tbody.empty();
    var total_quantity = 0;
    for(var i in data)
    {
        var e = data[i];
        var row = '<tr>'
                + '<td>' + i + '</td>'
        		+ '<td>' + e.brand + '</td>'
        		+ '<td>' + e.category + '</td>'
        		+ '<td>' + e.quantity + '</td>'
        		+ '</tr>';
                $tbody.append(row);
        total_quantity = total_quantity + e.quantity;
    }
    var row = '<tr>'
            + '<td></td>'
            + '<td></td>'
            + '<td>Total</td>'
            + '<td>' + total_quantity + '</td>'
            + '</tr>';
            $tbody.append(row);

    pagination();
}

function applyBrandCategoryFilter()
{
    var brandFilter = getBrandOption();
    var categoryFilter = getCategoryOption();
    var data = [];

    for(var i = 0; i<inventoryData.length; i++){
            if(check(inventoryData[i].brand, brandFilter) && check(inventoryData[i].category, categoryFilter))
                data.push(inventoryData[i]);
    }
    displayInventoryReport(data);
}

function check(a, b)
{
    if(b=="All" || a==b)
        return true;
    return false;
}

function removeDuplicates(arr) {
        return arr.filter((item,
            index) => arr.indexOf(item) === index);
}

function pagination(){
  $('#inventory-report-table').DataTable();
  $('.dataTables_length').addClass('bs-select');
}

function init()
{
    $('#inputFilterBrand').change(displayCategoryList);
    $('#apply-brand-category-filter').click(applyBrandCategoryFilter);
}

$(document).ready(getInventoryReportList);
$(document).ready(init);