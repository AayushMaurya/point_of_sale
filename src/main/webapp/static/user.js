function getAdminUrl()
{
    var baseUrl = $("meta[name=baseUrl]").attr("content")
     	return baseUrl + "/api/admin/user";
}

function getUserList(){
	var url = getAdminUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   console.log(data);
	   		displayUserList(data);
	   },
	   error: handleAjaxError
	});
}

function displayUserList(data)
{
//    $('#user-table').DataTable().destroy();
    	var $tbody = $('#user-table').find('tbody');
    	$tbody.empty();
    	var index = 0;
    	for(var i in data){
    		var e = data[i];
    		index++;
//    		var buttonHtml = ' <button class="btn btn-primary" data-toggle="modal"'
//            + 'data-target="#exampleModalCenter" onclick="fillUpdateFields('
//    		+ i +')" >Edit</button>';
            var buttonHtml = '<button class="btn btn-primary" onclick="deleteUser('+e.id+')">Delete</button>';
    		var row = '<tr>'
    		+ '<td>' + index + '</td>'
    		+ '<td>' + e.email + '</td>'
    		+ '<td>'  + e.role + '</td>'
    		+ '<td>' + buttonHtml + '</td>'
    		+ '</tr>';
            $tbody.append(row);
    	}
//    	pagination();
}

function deleteUser(id)
{
    var url = getAdminUrl() + "/" + id;
    	$.ajax({
    	   url: url,
    	   type: 'DELETE',
    	   success: function(data) {
    	   handleSuccess("User deleted Successfully");
    	   getUserList();
    	   },
    	   error: handleAjaxError
    	});
}

function addUser()
{
    var $form = $("#user-form");
    var json = toJson($form);
    var url = getAdminUrl();

    $.ajax({
        	   url: url,
        	   type: 'POST',
        	   data: json,
        	   headers: {
               	'Content-Type': 'application/json'
               },
        	   success: function(response) {
        	   		getUserList();
        	   		handleSuccess("User added successfully");
        	   		document.getElementById("user-form").reset();
        	   },
        	   error: handleAjaxError
        	});
        	return false;
}

function init()
{
    $('#add-user').click(addUser);
}

$(document).ready(init);
$(document).ready(getUserList);