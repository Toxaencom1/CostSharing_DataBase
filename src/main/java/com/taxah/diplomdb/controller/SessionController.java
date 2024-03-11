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

    @GetMapping("/check/{id}")
    public Check getCheck(@PathVariable Long id){
        return service.getCheck(id);
    }

    @GetMapping("/payfact/{id}")
    public PayFact getPayFact(@PathVariable Long id) {
        return service.getPayFact(id);
    }

    @GetMapping("/session/tempuser/{id}")
    public TempUser getTempUser(@PathVariable Long id) {
        return service.getTempUser(id);
    }

    @GetMapping("/product/{id}")
    public ProductUsing getProductUsing(@PathVariable Long id){
        return  service.getProductUsing(id);
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
    public ProductUsing addProductUsing(@RequestBody ProductUsingDTO p) {
        return service.addProductUsingList(p.getCheckId(), p.getProductName(), p.getCost(), p.getTempUsers());
    }

    @PostMapping("/users/add")
    public User addUser(@RequestBody User user) {
        return service.addUser(user);
    }

    @PostMapping("/users/add/temp_user")
    public TempUser addGuestMember(@RequestBody TempUser tempUser) {
        return service.addTempUser(tempUser);
    }

    @PostMapping("/productusing/add/{productUsingId}")
    public Long addTempUserToProduct(@PathVariable Long productUsingId,@RequestBody TempUser tempUser){
        service.addTempUserToProduct(tempUser,productUsingId);
        return tempUser.getSessionId();
    }

    @DeleteMapping("/users/delete/{id}")
    public Long deleteMember(@PathVariable Long id) {
        return service.deleteMember(id);
    }

    @DeleteMapping("/payfact/delete/{id}")
    public Long deletePayFact(@PathVariable Long id) {
        Check check = service.deletePayFact(id);
        return check.getSession().getId();
    }
    @DeleteMapping("/check/delete/{id}")
    public Long deleteCheck(@PathVariable Long id){
        return service.deleteCheck(id);
    }

    @DeleteMapping("/productusing/delete/{id}")
    public void deleteProductUsing(@PathVariable(name = "id") Long productUsingId){
        service.deleteProduct(productUsingId);
    }

    @DeleteMapping("/productusing/delete/user/{productUsingId}")
    public void deleteTempUserFromProduct(@PathVariable Long productUsingId,
                                          @RequestBody TempUser tempUser){
        service.deleteTempUserFromProduct(tempUser,productUsingId);
    }

    @PutMapping("/payfact/update")
    public PayFact updatePayFact(@RequestBody PayFact payFact) {
        return service.updatePayFact(payFact);
    }

    @PutMapping("/product/update")
    public ProductUsing updateProductUsing(@RequestBody ProductUsing productUsing){
        return service.updateProductUsing(productUsing);
    }
}
