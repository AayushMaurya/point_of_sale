package com.increff.store.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class InfoData {
    private String message;
    private String email;

    InfoData()
    {
        message = "no message";
        email = "no email";
    }
}
