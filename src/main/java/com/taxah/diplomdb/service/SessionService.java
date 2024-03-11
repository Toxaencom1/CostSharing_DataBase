package com.taxah.diplomdb.service;

import com.taxah.diplomdb.model.*;

import com.taxah.diplomdb.model.abstractClasses.Account;
import com.taxah.diplomdb.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SessionService {
    private ProductUsingUserRepository productUsingUserRepository;
    private SessionRepository sessionRepository;
    private UserRepository userRepository;
    private PayFactRepository payFactRepository;
    private CheckRepository checkRepository;
    private ProductUsingRepository productUsingRepository;
    private TempUserRepository tempUserRepository;

    public Long createSessionAndMembers(List<TempUser> tempMembers, Long admin) {
        Session session = sessionRepository.save(new Session());
        Long sessionId = session.getId();
        List<TempUser> tempMemberList = new ArrayList<>();
        for (Account a : tempMembers) {
            TempUser tempUser = tempUserRepository.save(new TempUser());
            tempUser.setFirstname(a.getFirstname());
            tempUser.setLastname(a.getLastname());
            tempUser.setSessionId(sessionId);
            tempMemberList.add(tempUser);
        }
        session.setMembersList(tempMemberList);
        session.setAdminId(admin);
        session = sessionRepository.save(session);
        return session.getId();
    }


    public PayFact addPayFact(Long checkId, Long tempUserId, Double amount) {
        Optional<Check> optionalCheck = checkRepository.findById(checkId);
        Optional<TempUser> optionalTempUser = tempUserRepository.findById(tempUserId);
        PayFact payFact = null;
        if (optionalCheck.isPresent() && optionalTempUser.isPresent()) {
            Check check = optionalCheck.get();
            payFact = payFactRepository.save(new PayFact());
            payFact.setTempUser(optionalTempUser.get());
            payFact.setAmount(amount);
            payFact.setCheck(check);
            check.setPayFact(payFact);
            checkRepository.save(check);
        }
        return payFact;
    }

    public Long createCheck(Long sessionId, String name) {
        Check check = checkRepository.save(new Check());
        Optional<Session> optionalSession = sessionRepository.findById(sessionId);
        if (optionalSession.isPresent()) {
            check.setSession(optionalSession.get());
            check.setName(name);
            checkRepository.save(check);
            return check.getId();
        }
        return null;
    }

    public ProductUsing addProductUsingList(Long checkId, String productName, Double cost, List<TempUser> tempUsers) {
        Optional<Check> optionalCheck = checkRepository.findById(checkId);
        ProductUsing productUsing = productUsingRepository.save(new ProductUsing());

        if (optionalCheck.isPresent()) {
            Check check = optionalCheck.get();
            productUsing.setCheck(check);
            productUsing.setProductName(productName);
            productUsing.setCost(cost);
            productUsing.setUsers(tempUsers);
            check.addProductUsing(productUsing);
            checkRepository.save(check);
            return productUsing;
        }
        return null;
    }

    public Session getSession(Long id) {
        Optional<Session> optional = sessionRepository.findById(id);
        return optional.orElse(null);
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public Long myId(Long id) {
        Optional<User> optional = userRepository.findById(id);
        return optional.map(User::getId).orElse(null);
    }

    public TempUser addTempUser(TempUser tempUser) {
        return tempUserRepository.save(tempUser);
    }

    public Long deleteMember(Long id) {
        Optional<TempUser> optionalTempUser = tempUserRepository.findById(id);
        if (optionalTempUser.isPresent()) {
            TempUser tempUser = optionalTempUser.get();
            for (ProductUsing p:tempUser.getProductUsingList()){
                p.getUsers().removeIf(u->u.equals(tempUser));
            }
            tempUser.setProductUsingList(null);
            tempUserRepository.saveAndFlush(tempUser);
            tempUserRepository.deleteById(id);
            return optionalTempUser.get().getSessionId();
        }
        return null;
    }

    public Check deletePayFact(Long id) {
        Optional<PayFact> optionalPayFact = payFactRepository.findById(id);
        if (optionalPayFact.isPresent()) {
            Check check = optionalPayFact.get().getCheck();
            check.setPayFact(null);
            payFactRepository.deleteById(id);
            return check;
        }
        return null;
    }

    public PayFact updatePayFact(PayFact newPayFact) {
        return payFactRepository.save(newPayFact);
    }

    public ProductUsing updateProductUsing(ProductUsing newProductUsing){
        Optional<ProductUsing> optionalProductUsing = productUsingRepository.findById(newProductUsing.getId());
        if (optionalProductUsing.isPresent()){
            ProductUsing productUsing = optionalProductUsing.get();
            productUsing.setProductName(newProductUsing.getProductName());
            productUsing.setCost(newProductUsing.getCost());
            productUsingRepository.save(productUsing);
            return productUsing;
        }
        return null;
    }

    public PayFact getPayFact(Long id) {
        Optional<PayFact> optionalPayFact = payFactRepository.findById(id);
        return optionalPayFact.orElse(null);
    }

    public TempUser getTempUser(Long id) {
        Optional<TempUser> optionalTempUser = tempUserRepository.findById(id);
        return optionalTempUser.orElse(null);
    }

    public Long deleteCheck(Long id) {
        Optional<Check> optionalCheck = checkRepository.findById(id);
        if (optionalCheck.isPresent()) {
            Check check = optionalCheck.get();
            System.out.println(check);
            if (check.getPayFact() != null) {
                PayFact payFact = check.getPayFact();
                check.setPayFact(null);
                payFactRepository.delete(payFact);
            }
            if (check.getProductUsingList() != null) {
                List<ProductUsing> productUsingList = check.getProductUsingList();
                check.setProductUsingList(null);
                for (ProductUsing productUsing:productUsingList){
                    productUsing.setCheck(null);
                    for (TempUser user : productUsing.getUsers()){
                        user.getProductUsingList().removeIf(p->p.equals(productUsing));
                    }
                    productUsingRepository.delete(productUsing);
                }
            }
            Long sessionId = check.getSession().getId();
            check.setSession(null);
            checkRepository.deleteById(check.getId());
            checkRepository.delete(check);
            return sessionId;
        }
        return null;
    }

    public void deleteProduct(Long productUsingId){
        Optional<ProductUsing> optionalProductUsing = productUsingRepository.findById(productUsingId);
        if (optionalProductUsing.isPresent()){
            ProductUsing productUsing = optionalProductUsing.get();
            for (TempUser tempUser:productUsing.getUsers()){
                tempUser.getProductUsingList().removeIf(p->p.equals(productUsing));
            }
            productUsing.setUsers(null);
            productUsingRepository.delete(productUsing);
        }
    }

    public TempUser addTempUserToProduct(TempUser tempUser, Long productUsingId){
        Optional<ProductUsing> optionalProductUsing = productUsingRepository.findById(productUsingId);
        if (optionalProductUsing.isPresent()){
            ProductUsing productUsing = optionalProductUsing.get();
            productUsing.addTempUser(tempUser);
            productUsingRepository.save(productUsing);
        }
        return null;

    }

    public ProductUsing getProductUsing(Long id) {
        Optional<ProductUsing> optionalProductUsing = productUsingRepository.findById(id);
        return optionalProductUsing.orElse(null);
    }

    public Check getCheck(Long id) {
        Optional<Check> optionalCheck = checkRepository.findById(id);
        return optionalCheck.orElse(null);
    }

    public void deleteTempUserFromProduct(TempUser tempUser1, Long productUsingId) {
        productUsingUserRepository.deleteByTempUserIdAndProductUsingId(tempUser1.getId(),productUsingId);
    }
}
