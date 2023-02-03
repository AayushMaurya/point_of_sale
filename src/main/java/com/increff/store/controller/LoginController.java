package com.increff.store.controller;

import com.increff.store.model.data.InfoData;
import com.increff.store.model.form.LoginForm;
import com.increff.store.pojo.UserPojo;
import com.increff.store.api.ApiException;
import com.increff.store.api.UserService;
import com.increff.store.util.SecurityUtil;
import com.increff.store.util.UserPrincipal;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class LoginController {

    @Autowired
    private UserService service;
    @Autowired
    private InfoData info;

    @ApiOperation(value = "Logs in a user")
    @RequestMapping(path = "/session/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView login(HttpServletRequest req, LoginForm f) throws ApiException {
        UserPojo pojo = service.getByEmail(f.getEmail());
        boolean authenticated = (pojo != null && Objects.equals(pojo.getPassword(), f.getPassword()));
        if (!authenticated) {
            info.setMessage("Invalid username or password");
            return new ModelAndView("redirect:/site/login");
        }

        // Create authentication object
        Authentication authentication = convert(pojo);
        // Create new session
        HttpSession session = req.getSession(true);
        // Attach Spring SecurityContext to this new session
        SecurityUtil.createContext(session);
        // Attach Authentication object to the Security Context
        SecurityUtil.setAuthentication(authentication);

        return new ModelAndView("redirect:/ui/home");

    }

    @RequestMapping(path = "/session/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return new ModelAndView("redirect:/site/logout");
    }

    private Authentication convert(UserPojo pojo) {
        // Create principal
        UserPrincipal principal = new UserPrincipal();
        principal.setEmail(pojo.getEmail());
        principal.setId(pojo.getId());
        principal.setRole(pojo.getRole());

        // Create Authorities
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();

        authorities.add(new SimpleGrantedAuthority(pojo.getRole()));
        // you can add more roles if required

        // Create Authentication
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, null,
                authorities);
        return token;
    }
}
