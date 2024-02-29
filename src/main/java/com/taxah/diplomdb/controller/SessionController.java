package com.taxah.diplomdb.controller;

import com.taxah.diplomdb.model.PayFact;
import com.taxah.diplomdb.model.ProductUsing;
import com.taxah.diplomdb.model.Session;
//import com.taxah.diplomdb.model.User;
import com.taxah.diplomdb.model.TempUser;
import com.taxah.diplomdb.model.abstractClasses.Account;
import com.taxah.diplomdb.model.dto.PayFactDTO;
import com.taxah.diplomdb.model.dto.ProductUsingDTO;
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

    @PostMapping("/session/create/{id}")
    public Long createNewSession(@RequestBody List<TempUser> accounts, @PathVariable Long id){
        return service.createSessionAndMembers(accounts,id);
    }

    @PostMapping("/session/add/payfact")
    public List<PayFact> addPayFact(@RequestBody PayFactDTO p){
        return service.addPayFact(p.getTempUserId(),p.getAmount(),p.getSessionId());
    }

    @PostMapping("/session/add/check/{id}")
    public Long createCheck(@RequestParam String name, @PathVariable(name = "id") Long sessionId){
        return service.createCheck(sessionId, name);
    }

    @PostMapping("/session/add/productusing")
    public List<ProductUsing> addProductUsing(@RequestBody ProductUsingDTO p){
        return service.addProductUsing(p.getCheckId(),p.getProductName(),p.getCost(),p.getTempUsers());

    }

//    @PostMapping("/user/add")
//    public User addUser(@RequestBody User user){
//        return service.addUser(user);
//    }
}
