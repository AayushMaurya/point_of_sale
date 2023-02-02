function getAdminUrl(){
    var baseUrl = $("meta[name=baseUrl]").attr("content")
     	return baseUrl + "/api/admin/user";
}

function getUserList(){
    var callParams = {};
    callParams.Type = "GET";
    callParams.Url = getAdminUrl();

    ajaxCall(callParams, null, displayUserList);
}

function displayUserList(data){
    	var $tbody = $('#user-table').find('tbody');
    	$tbody.empty();
    	var index = 0;
    	for(var i in data){
    		var e = data[i];
    		index++;
            var buttonHtml = '<button class="btn btn-primary" onclick="deleteUser('+e.id+')">Delete</button>';
    		var row = '<tr>'
    		+ '<td>' + index + '</td>'
    		+ '<td>' + e.email + '</td>'
    		+ '<td>'  + e.role + '</td>'
    		+ '<td>' + buttonHtml + '</td>'
    		+ '</tr>';
            $tbody.append(row);
    	}
}

function deleteUser(id){
    var callParams = {};
    callParams.Type = "DELETE";
    callParams.Url = getAdminUrl() + "/" + id;

    function callback(data){
        handleSuccess("User deleted Successfully");
        getUserList();
    }

    ajaxCall(callParams, null, callback);
}

function addUser(){
    var $form = $("#user-form");
    var dataParams = toJson($form);
    var callParams = {};
    callParams.Type = "POST";
    callParams.Url = getAdminUrl();

    function callback(data){
        getUserList();
        handleSuccess("User added successfully");
        document.getElementById("user-form").reset();
    }

    ajaxCall(callParams, dataParams, callback);

    return false;
}

function init(){
    $('#add-user').click(addUser);
}

$(document).ready(init);
$(document).ready(getUserList);