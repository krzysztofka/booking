package com.kali.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

public abstract class AbstractRestController {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

}
