package org.safegyn.model.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Getter
@Setter
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LoginMessageData implements Serializable {

    private static final long serialVersionUID = 1L;
    private String message;
    private String username;

    public LoginMessageData() {
        message = "";
        username = "No username";
    }

}

