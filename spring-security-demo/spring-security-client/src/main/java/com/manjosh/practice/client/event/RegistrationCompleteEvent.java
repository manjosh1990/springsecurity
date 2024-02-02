package com.manjosh.practice.client.event;

import com.manjosh.practice.client.entity.CustomUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private  CustomUser user;
    private String applicationUrl;
    public RegistrationCompleteEvent(CustomUser user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}
