package com.increff.store.model.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@Scope(value="session", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class InfoData {
    private String message;
    private String email;
    private String role;

}
