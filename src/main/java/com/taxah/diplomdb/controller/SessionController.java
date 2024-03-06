package com.taxah.diplomdb.controller;

import com.taxah.diplomdb.model.*;
import com.taxah.diplomdb.model.dto.PayFactDTO;
import com.taxah.diplomdb.model.dto.ProductUsingDTO;
import com.taxah.diplomdb.service.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/db")
public class SessionController {
    private SessionService service;

    @GetMapping("/session/{id}")
    public Session getSession(@PathVariable Long id) {
        return service.getSession(id);
    }

    @PostMapping("/session/create/{id}")
    public Long createNewSession(@RequestBody List<TempUser> accounts, @PathVariable Long id) {
        return service.createSessionAndMembers(accounts, id);
    }

    @PostMapping("/session/add/payfact")
    public PayFact addPayFact(@RequestBody PayFactDTO p) {
        return service.addPayFact(p.getCheckId(), p.getTempUserId(), p.getAmount());
    }

    @PostMapping("/session/add/check/{sessionId}")
    public Long createCheck(@RequestParam String name, @PathVariable Long sessionId) {
        return service.createCheck(sessionId, name);
    }

    @PostMapping("/session/add/productusing")
    public List<ProductUsing> addProductUsing(@RequestBody ProductUsingDTO p) {
        return service.addProductUsing(p.getCheckId(), p.getProductName(), p.getCost(), p.getTempUsers());

    }

    @PostMapping("/users/add")
    public User addUser(@RequestBody User user) {
        return service.addUser(user);
    }

    @PostMapping("/users/add/temp_user")
    public TempUser addGuestMember(@RequestBody TempUser tempUser) {
        return service.addTempUser(tempUser);
    }
}
