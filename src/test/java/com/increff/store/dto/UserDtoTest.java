package com.increff.store.dto;

import com.increff.store.model.data.UserData;
import com.increff.store.model.form.UserForm;
import com.increff.store.pojo.UserPojo;
import com.increff.store.api.ApiException;
import com.increff.store.api.UserService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserDtoTest extends AbstractUnitTest {
    @Autowired
    UserDto dto;
    @Autowired
    UserService service;
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void addUserTest() throws ApiException {
        UserForm form = new UserForm();
        form.setEmail("test@email.com");
        form.setPassword("testPassword");

        Integer id = dto.addUser(form);

        UserPojo pojo = service.getById(id);

        assertEquals("test@email.com", pojo.getEmail());
        assertEquals("testPassword", pojo.getPassword());
        assertEquals("operator", pojo.getRole());

        exceptionRule.expect(ApiException.class);
        exceptionRule.expectMessage("User with given email already exists");
        dto.addUser(form);
    }

    @Test
    public void deleteUserTest() throws ApiException {
        UserForm form = new UserForm();
        form.setEmail("test@email.com");
        form.setPassword("testPassword");

        Integer id = dto.addUser(form);

        dto.deleteUser(id);

        exceptionRule.expect(ApiException.class);
        UserPojo pojo = service.getById(id);
    }

    @Test
    public void getALlUsersTest() throws ApiException {
        UserForm form = new UserForm();
        form.setEmail("test2@email.com");
        form.setPassword("testPassword");
        dto.addUser(form);

        form.setEmail("test@email.com");
        form.setPassword("testPassword");
        dto.addUser(form);

        List<UserData> list = dto.getAllUserData();

        assertEquals(2, list.size());
    }
}
