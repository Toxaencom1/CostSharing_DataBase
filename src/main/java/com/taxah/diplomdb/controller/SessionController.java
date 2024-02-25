package com.taxah.diplomdb.controller;

import com.taxah.diplomdb.model.Session;
import com.taxah.diplomdb.model.User;
import com.taxah.diplomdb.service.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class SessionController {
    private SessionService service;

    @GetMapping("/session/{id}")
    public Session getSession(@PathVariable Long id){
        return service.getSession(id);
    }

    @PostMapping("/write/session")
    public Session write(@RequestBody Session session){
        return service.writeSession(session);
    }
}
