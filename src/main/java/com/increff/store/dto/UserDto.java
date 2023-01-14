package com.increff.store.dto;

import com.increff.store.model.UserData;
import com.increff.store.model.UserForm;
import com.increff.store.pojo.UserPojo;
import com.increff.store.service.ApiException;
import com.increff.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.increff.store.dto.DtoUtils.convertUserFormToUserPojo;
import static com.increff.store.dto.DtoUtils.convertUserPojoToUserData;

public class UserDto {
    @Autowired
    UserService service;

    public void addUser(UserForm form) throws ApiException
    {
        UserPojo p = convertUserFormToUserPojo(form);
        service.add(p);
    }

    public void deleteUser(Integer id)
    {
        service.delete(id);
    }

    public List<UserData> getAllUserData(){
        List<UserPojo> list = service.getAll();
        List<UserData> list2 = new ArrayList<UserData>();
        for (UserPojo p : list) {
            list2.add(convertUserPojoToUserData(p));
        }
        return list2;
    }
}
