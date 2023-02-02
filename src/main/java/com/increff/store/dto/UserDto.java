package com.increff.store.dto;

import com.increff.store.model.data.UserData;
import com.increff.store.model.form.UserForm;
import com.increff.store.pojo.UserPojo;
import com.increff.store.api.ApiException;
import com.increff.store.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("#{'${supervisor.email}'.split(',')}")
    private List<String> supervisorEmail;

    public Integer addUser(UserForm form) throws ApiException {
        UserPojo pojo = convertUserFormToUserPojo(form);
        return service.add(pojo);
    }

    public void deleteUser(Integer id) throws ApiException {
        UserPojo pojo = service.getById(id);
        for(String str: supervisorEmail)
        {
            if (Objects.equals(pojo.getEmail(), str))
                throw new ApiException("Cannot delete a supervisor");
        }
        service.delete(id);
    }

    public List<UserData> getAllUserData() {
        List<UserPojo> list = service.getAll();
        List<UserData> list2 = new ArrayList<UserData>();
        for (UserPojo p : list) {
            UserData data = convertUserPojoToUserData(p);
            for(String str: supervisorEmail)
            {
                if(Objects.equals(data.getEmail(), str))
                {
                    data.setRole("supervisor");
                    break;
                }
            }
            list2.add(data);
        }
        return list2;
    }
}
