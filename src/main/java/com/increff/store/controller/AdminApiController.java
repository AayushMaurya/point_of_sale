package com.increff.store.controller;

import com.increff.store.model.UserData;
import com.increff.store.model.UserForm;
import com.increff.store.api.ApiException;
import com.increff.store.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class AdminApiController {

    @Autowired
    private UserDto dto;

    @ApiOperation(value = "Adds a user")
    @RequestMapping(path = "/api/admin/user", method = RequestMethod.POST)
    public void addUser(@RequestBody UserForm form) throws ApiException {
        dto.addUser(form);
    }

    @ApiOperation(value = "Deletes a user")
    @RequestMapping(path = "/api/admin/user/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable Integer id) throws ApiException {
        dto.deleteUser(id);
    }

    @ApiOperation(value = "Gets list of all users")
    @RequestMapping(path = "/api/admin/user", method = RequestMethod.GET)
    public List<UserData> getAllUser() {
        return dto.getAllUserData();
    }
}
