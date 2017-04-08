package com.kali.booking.controller.hateoas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

public abstract class HateoasRestController {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

}
