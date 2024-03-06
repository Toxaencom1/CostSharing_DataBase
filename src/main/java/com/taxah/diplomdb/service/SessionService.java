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

    //    private ProductUsingUserRepository productUsingUserRepository;
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

    public List<ProductUsing> addProductUsing(Long checkId, String productName, Double cost, List<TempUser> tempUsers) {
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
            return check.getProductUsingList();
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
        Optional<User> optional = userRepository.findById(Math.toIntExact(id));
        return optional.map(User::getId).orElse(null);
    }

    public TempUser addTempUser(TempUser tempUser) {
        return tempUserRepository.save(tempUser);
    }
}
