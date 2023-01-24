package com.increff.store.dto;

import com.increff.store.model.UserData;
import com.increff.store.model.UserForm;
import com.increff.store.pojo.UserPojo;
import com.increff.store.service.ApiException;
import com.increff.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.increff.store.dto.DtoUtils.convertUserFormToUserPojo;
import static com.increff.store.dto.DtoUtils.convertUserPojoToUserData;

@Service
public class UserDto {
    @Autowired
    UserService service;

    public void addUser(UserForm form) throws ApiException
    {
        UserPojo p = convertUserFormToUserPojo(form);
        service.add(p);
    }

    public void deleteUser(Integer id) throws ApiException
    {
        UserPojo pojo = service.getById(id);
        if(Objects.equals(pojo.getRole(), "supervisor"))
            throw new ApiException("Cannot delete a supervisor");
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
