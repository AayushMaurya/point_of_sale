package com.increff.store.postConstruct;

import com.increff.store.api.ApiException;
import com.increff.store.dto.ReportDto;
import com.increff.store.dto.UserDto;
import com.increff.store.model.form.UserForm;
import com.increff.store.pojo.UserPojo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PilotUser {

    @Value("${pilot.email}")
    String pilotEmail;

    @Value("${pilot.password}")
    String pilotPassword;

    @Autowired
    UserDto userDto;
    private static Logger logger = Logger.getLogger(ReportDto.class);

    @PostConstruct
    public void initializePilotUser() {
        UserForm userForm = new UserForm();
        userForm.setEmail(pilotEmail);
        userForm.setPassword(pilotPassword);
        userForm.setRole("supervisor");
        try {
            logger.info("Created pilot user");
            userDto.addUser(userForm);
        } catch (ApiException e) {
            logger.info("Pilot user already exists");
        }
    }
}
