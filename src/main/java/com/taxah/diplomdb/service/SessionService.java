package com.taxah.diplomdb.service;

import com.taxah.diplomdb.model.*;

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



    public Session writeSession(Session session,Long admin) {
        Session session1 = sessionRepository.save(new Session());
        session.setId(session1.getId());

        session1.setAdminId(admin);
        Long sessionId = session1.getId();
        System.out.println("session members = " + session.getMembersList());
        System.out.println("session id = " + sessionId);

        for (PayFact pf: session.getPayFact()){
            PayFact payFact = payFactRepository.save(new PayFact());
            pf.setId(payFact.getId());
            pf.setSession(session);
            System.out.println("Payfact id = " + payFact.getId() +" "+ pf);
//            payFactRepository.save(pf);
        }
        List<Check> checks = session.getCheckList();
        for (Check c : checks){
            Check check = checkRepository.save(new Check());
            c.setId(check.getId());
            c.setSession(session);
            System.out.println("Check id = " + check.getId()+" "+c);
            for (ProductUsing p : c.getProductUsingList()){
                System.out.println("Start");
                ProductUsing productUsing = productUsingRepository.save(new ProductUsing());
                p.setId(productUsing.getId());
                p.setCheck(c);
                System.out.println("ProductUsing id = " + productUsing.getId()+" "+p);
                System.out.println("End");
            }
        }
        System.out.println("Session = " + session);
        return sessionRepository.save(session);
    }

    public Session getSession(Long id){
        Optional<Session> optional = sessionRepository.findById( id.intValue());
        return optional.orElse(null);
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public Long myId(Long id){
        Optional<User> optional = userRepository.findById(Math.toIntExact(id));
        return optional.map(User::getId).orElse(null);
    }
}

//public Session session() {
//        Session session = new Session();
//
//        // Создаем пользователя
//        User user1 = new User();
//        user1.setFirstname("Alex"); user1.setLastname("Starikov");
//        User user2 = new User();
//        user2.setFirstname("Anton"); user2.setLastname("Takhaev");
//        User user3 = new User();
//        user3.setFirstname("Andrey"); user3.setLastname("Tkachenko");
//        User user4 = new User();
//        user4.setFirstname("Artem"); user4.setLastname("Zhukov");
//        User user5 = new User();
//        user5.setFirstname("Ruslan"); user5.setLastname("Zelentcov");
//
//        List<User> users = new ArrayList<>();
//        users.add(user1);
//        users.add(user2);
//        users.add(user3);
//        users.add(user4);
//        users.add(user5);
//        userRepository.saveAll(users);
//
//        session.setAdminId(user2.getId());
//        session.setMembersList(users);
//        System.out.println(sessionRepository.save(session));
//
//        // Создаем факт оплаты
//        PayFact payFact1 = new PayFact();
//        payFact1.setUserData(user1.getFirstname()+" "+user1.getLastname());
//        payFact1.setSessionId(session.getId());
//        payFact1.setUserId(user1.getId());
//        payFact1.setAmount(10000.0);
//        PayFact payFact2 = new PayFact();
//        payFact2.setUserData(user4.getFirstname()+" "+user4.getLastname());
//        payFact2.setSessionId(session.getId());
//        payFact2.setUserId(user4.getId());
//        payFact2.setAmount(20000.0);
//        List<PayFact> payFacts = new ArrayList<>();
//        payFacts.add(payFact1);
//        payFacts.add(payFact2);
//
//        System.out.println(payFactRepository.saveAll(payFacts));
//        session.setPayFact(payFacts);
//        // Создаем чеки
//        Check check1 = new Check();
//        check1.setSessionId(session.getId());
//        check1.setName("Alcohol");
//        Check check2 = new Check();
//        check2.setSessionId(session.getId());
//        check2.setName("food");
//        List<Check> checks = new ArrayList<>();
//        checks.add(check1);
//        checks.add(check2);
//
//        System.out.println(checkRepository.saveAll(checks));
//
//        // Создаем записи использования продуктов
//        ProductUsing productUsing1 = new ProductUsing();
//        productUsing1.setCheckId(check1.getId());
//        productUsing1.setProductName("Beer");
//        productUsing1.setCost(10000.0);
//        productUsing1.setUsers(List.of(user1,user3,user4,user5));
//
//        ProductUsing productUsing2 = new ProductUsing();
//        productUsing2.setCheckId(check2.getId());
//        productUsing2.setProductName("Food");
//        productUsing2.setCost(20000.0);
//        productUsing2.setUsers(List.of(user1,user2,user3,user4,user5));
//
//        List<ProductUsing> productUsingList1 = new ArrayList<>();
//        productUsingList1.add(productUsing1);
//        List<ProductUsing> productUsingList2 = new ArrayList<>();
//        productUsingList2.add(productUsing2);
//        System.out.println(productUsingRepository.saveAll(productUsingList1));
//        System.out.println(productUsingRepository.saveAll(productUsingList2));
//        check1.setProductUsingList(productUsingList1);
//        check2.setProductUsingList(productUsingList2);
//
//
//        System.out.println(checkRepository.saveAll(checks));
//        session.setCheckList(checks);
//        return session;
//    }
