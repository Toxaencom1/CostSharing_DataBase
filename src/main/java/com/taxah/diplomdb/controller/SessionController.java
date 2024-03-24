package com.taxah.diplomdb.controller;

import com.taxah.diplomdb.model.*;
import com.taxah.diplomdb.model.dto.PayFactDTO;
import com.taxah.diplomdb.model.dto.ProductUsingDTO;
import com.taxah.diplomdb.service.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller that handles requests to the database.
 * <p>
 * Methods:
 * - getSession(Long id) - get session by id
 * - findByName(String sessionName) - find session by name
 * - createNewSession(List<TempUser> accounts, Long id) - create session with members
 * - createSession(String firstname, String lastname, String sessionName) - create session
 * - addUser(User user) - add user
 * <p>
 * - getCheck(Long id) - get check by id
 * - createCheck(String name, Long sessionId) - create check
 * - deleteCheck(Long checkId) - delete check
 * <p>
 * - getPayFact(Long id) - get pay fact by id
 * - addPayFact(PayFactDTO p) - add pay fact
 * - deletePayFact(Long id) - delete pay fact
 * - updatePayFact(PayFact payFact) - update pay fact
 * <p>
 * - getProductUsing(Long id) - get product using by id
 * - addProductUsing(ProductUsingDTO p) - add product to check
 * - deleteProductUsing(Long productUsingId) - delete product from check
 * - updateProductUsing(ProductUsing productUsing) - update product
 * <p>
 * - getTempUser(Long id) - get temp user by id
 * - addMember(TempUser tempUser) - add temp user
 * - deleteMember(Long id) - delete temp user
 * - updateMember(Long id, TempUser tempUser) - update temp user
 * - addTempUserToProduct(Long productUsingId, TempUser tempUser) - add temp user to product
 * - addAllMembersToProduct(Long productUsingId, Long sessionId) - add all members to product
 * - deleteTempUserFromProduct(Long productUsingId, TempUser tempUser) - delete temp user from product
 */
@AllArgsConstructor
@RestController
@RequestMapping("/db")
public class SessionController {
    private SessionService service;

    // region Session
    @GetMapping("/session/{id}")
    public Session getSession(@PathVariable Long id) {
        return service.getSession(id);
    }

    @GetMapping("/session/findByName")
    public List<Session> findByName(@RequestParam("sessionName") String sessionName) {
        return service.findByName(sessionName);
    }

    @PostMapping("/session/create/{id}")
    public Long createNewSession(@RequestBody List<TempUser> accounts, @PathVariable Long id) {
        return service.createSessionAndMembers(accounts, id);
    }

    @PostMapping("/session/create")
    public Session createSession(@RequestParam("firstname") String firstname,
                                 @RequestParam("lastname") String lastname,
                                 @RequestParam("sessionName") String sessionName) {
        return service.createSession(firstname, lastname, sessionName);
    }

    @PostMapping("/users/add")
    public User addUser(@RequestBody User user) {
        return service.addUser(user);
    }
//endregion

    // region Check
    @GetMapping("/check/{id}")
    public Check getCheck(@PathVariable Long id) {
        return service.getCheck(id);
    }

    @PostMapping("/check/add/{sessionId}")
    public Long createCheck(@RequestParam String name, @PathVariable Long sessionId) {
        return service.createCheck(sessionId, name);
    }

    @DeleteMapping("/check/delete/{checkId}")
    public Long deleteCheck(@PathVariable Long checkId) {
        return service.deleteCheck(checkId);
    }
//endregion

    // region Pay fact
    @GetMapping("/payFact/{id}")
    public PayFact getPayFact(@PathVariable Long id) {
        return service.getPayFact(id);
    }

    @PostMapping("/payFact/add")
    public PayFact addPayFact(@RequestBody PayFactDTO p) {
        return service.addPayFact(p.getCheckId(), p.getTempUserId(), p.getAmount());
    }

    @DeleteMapping("/payFact/delete/{id}")
    public Long deletePayFact(@PathVariable Long id) {
        Check check = service.deletePayFact(id);
        return check.getSession().getId();
    }

    @PutMapping("/payFact/update")
    public PayFact updatePayFact(@RequestBody PayFact payFact) {
        return service.updatePayFact(payFact);
    }

//endregion

    // region Product using
    @GetMapping("/productUsing/{id}")
    public ProductUsing getProductUsing(@PathVariable Long id) {
        return service.getProductUsing(id);
    }

    @PostMapping("/productUsing/add")
    public ProductUsing addProductUsing(@RequestBody ProductUsingDTO p) {
        return service.addProductUsingList(p.getCheckId(), p.getProductName(), p.getCost(), p.getTempUsers());
    }

    @DeleteMapping("/productUsing/delete/{id}")
    public void deleteProductUsing(@PathVariable(name = "id") Long productUsingId) {
        service.deleteProduct(productUsingId);
    }

    @PutMapping("/productUsing/update")
    public ProductUsing updateProductUsing(@RequestBody ProductUsing productUsing) {
        return service.updateProductUsing(productUsing);
    }
//endregion

    // region Temp user and Product using / Temp user
    @GetMapping("/tempUser/{id}")
    public TempUser getTempUser(@PathVariable Long id) {
        return service.getTempUser(id);
    }

    @PostMapping("/tempUser/add")
    public TempUser addMember(@RequestBody TempUser tempUser) {
        return service.addTempUser(tempUser);
    }

    @DeleteMapping("/tempUser/member/delete/{id}")
    public Long deleteMember(@PathVariable Long id) {
        return service.deleteMember(id);
    }

    @PutMapping("/tempUser/member/update/{id}")
    public Long updateMember(@PathVariable Long id, @RequestBody TempUser tempUser) {
        return service.updateMember(id, tempUser);
    }

    @PostMapping("/tempUser/add/{productUsingId}")
    public Long addTempUserToProduct(@PathVariable Long productUsingId, @RequestBody TempUser tempUser) {
        service.addTempUserToProduct(tempUser, productUsingId);
        return tempUser.getSessionId();
    }

    @PostMapping("/tempUser/addAll/{productUsingId}")
    public Long addAllMembersToProduct(@PathVariable Long productUsingId,
                                       @RequestParam("sessionId") Long sessionId) {
        service.addAllMembersToProduct(productUsingId, sessionId);
        return sessionId;
    }

    @DeleteMapping("/tempUser/delete/{productUsingId}")
    public void deleteTempUserFromProduct(@PathVariable Long productUsingId,
                                          @RequestBody TempUser tempUser) {
        service.deleteTempUserFromProduct(tempUser, productUsingId);
    }
    //endregion

}
