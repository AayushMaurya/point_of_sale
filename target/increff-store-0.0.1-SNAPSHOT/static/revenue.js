function getRevenueUrl(){
 	var baseUrl = $("meta[name=baseUrl]").attr("content")
 	return baseUrl + "/api/revenue";
 }

function getRevenueList()
{
    getProductRevenueList();
    getBrandRevenueList();
    getCategoryRevenueList();
}

function getProductRevenueList(){
	var url = getRevenueUrl() + "-product";
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
//	   error: handleAjaxError
	});
}

function getBrandRevenueList()
{
    var url = getRevenueUrl() + "-brand";
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
    	   		displayRevenueBrandList(data);
    	   },
    //	   error: handleAjaxError
    	});
}

function getCategoryRevenueList()
{
    var url = getRevenueUrl() + "-category";
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
    	   		displayRevenueCategoryList(data);
    	   },
    //	   error: handleAjaxError
    	});
}

function displayRevenueProductList(data)
{
    var $tbody = $('#product-revenue-list-table').find('tbody');
    $tbody.empty();
    for(var i in data){
    		var e = data[i];
    		var row = '<tr>'
    		+ '<td>' + e.id + '</td>'
    		+ '<td>' + e.barcode + '</td>'
    		+ '<td>'  + e.name + '</td>'
    		+ '<td>'  + e.mrp + '</td>'
    		+ '<td>'  + e.quantity + '</td>'
    		+ '<td>'  + e.total + '</td>'
    		+ '</tr>';
            $tbody.append(row);
    	}
}

function displayRevenueBrandList(data)
{
    var $tbody = $('#brand-revenue-list-table').find('tbody');
        $tbody.empty();
        for(var i in data){
        		var e = data[i];
        		var row = '<tr>'
        		+ '<td>' + e.brand + '</td>'
        		+ '<td>'  + e.quantity + '</td>'
        		+ '<td>'  + e.total + '</td>'
        		+ '</tr>';
                $tbody.append(row);
        	}
}

function displayRevenueCategoryList(data)
{
    var $tbody = $('#category-revenue-list-table').find('tbody');
        $tbody.empty();
        for(var i in data){
        		var e = data[i];
        		var row = '<tr>'
        		+ '<td>' + e.category + '</td>'
        		+ '<td>'  + e.quantity + '</td>'
        		+ '<td>'  + e.total + '</td>'
        		+ '</tr>';
                $tbody.append(row);
        	}
}

function showBrandView()
{

}

function showCategoryView()
{

}

function showProductView()
{

}

function init()
{
    $('#show-revenue').click(getRevenueList);
    $('#product-view').click(showProductView);
    $('#brand-view').click(showBrandView);
    $('#category-view').click(showCategoryView);

    $('#category-div').style.display = "none";
    $('#product-div').style.display = "none";
    $('#brand-div').style.display = "block";
}

$(document).ready(init);
