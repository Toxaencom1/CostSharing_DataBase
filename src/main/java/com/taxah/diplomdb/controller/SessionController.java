package com.taxah.diplomdb.controller;

import com.taxah.diplomdb.model.Session;
import com.taxah.diplomdb.model.User;
import com.taxah.diplomdb.service.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


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
        // TODO переделать получение своего ID
        Long id = service.myId(2L);
        return service.writeSession(session,id);
    }

    @PostMapping("/user/add")
    public User addUser(@RequestBody User user){
        return service.addUser(user);
    }
}
