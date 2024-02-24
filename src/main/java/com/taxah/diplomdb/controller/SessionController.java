package com.taxah.diplomdb.controller;

import com.taxah.diplomdb.model.Session;
import com.taxah.diplomdb.service.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class SessionController {
    private SessionService service;

    @GetMapping("/execute")
    public Session get(){
        return service.session();
    }
}
