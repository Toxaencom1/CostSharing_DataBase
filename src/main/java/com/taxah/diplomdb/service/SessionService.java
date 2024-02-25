package com.taxah.diplomdb.service;

import com.taxah.diplomdb.model.*;
import com.taxah.diplomdb.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class SessionService {

    private ProductUsingUserRepository productUsingUserRepository;
    private SessionRepository sessionRepository;
    private UserRepository userRepository;
    private PayFactRepository payFactRepository;
    private CheckRepository checkRepository;
    private ProductUsingRepository productUsingRepository;

    public Session session() {
        Session session = new Session();

        // Создаем пользователя
        User user1 = new User();
        user1.setFirstname("Alex"); user1.setLastname("Starikov");
        User user2 = new User();
        user2.setFirstname("Anton"); user2.setLastname("Takhaev");
        User user3 = new User();
        user3.setFirstname("Andrey"); user3.setLastname("Tkachenko");
        User user4 = new User();
        user4.setFirstname("Artem"); user4.setLastname("Zhukov");
        User user5 = new User();
        user5.setFirstname("Ruslan"); user5.setLastname("Zelentcov");

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        userRepository.saveAll(users);

        session.setAdminId(user2.getId());
        session.setMembersList(users);
//        userRepository.save(user1);
        System.out.println(sessionRepository.save(session));

        List<User> payFactUsersList = new ArrayList<>();
        payFactUsersList.add(user1);
        payFactUsersList.add(user4);
        // Создаем факт оплаты
        PayFact payFact1 = new PayFact();
        payFact1.setUserData(user1.getFirstname()+" "+user1.getLastname());
        payFact1.setSessionId(session.getId());
        payFact1.setUserId(user1.getId());
        payFact1.setAmount(10000.0);
        PayFact payFact2 = new PayFact();
        payFact2.setUserData(user4.getFirstname()+" "+user4.getLastname());
        payFact2.setSessionId(session.getId());
        payFact2.setUserId(user4.getId());
        payFact2.setAmount(20000.0);
        List<PayFact> payFacts = new ArrayList<>();
        payFacts.add(payFact1);
        payFacts.add(payFact2);

        System.out.println(payFactRepository.saveAll(payFacts));
        session.setPayFact(payFacts);
        // Создаем чеки
        Check check1 = new Check();
        check1.setSessionId(session.getId());
        check1.setName("Alcohol");
        Check check2 = new Check();
        check2.setSessionId(session.getId());
        check2.setName("food");
        List<Check> checks = new ArrayList<>();
        checks.add(check1);
        checks.add(check2);

        System.out.println(checkRepository.saveAll(checks));

        // Создаем записи использования продуктов
        ProductUsing productUsing1 = new ProductUsing();
        productUsing1.setCheckId(check1.getId());
        productUsing1.setProductName("Beer");
        productUsing1.setCost(10000.0);
        productUsing1.setUsers(List.of(user1,user3,user4,user5));
        ProductUsing productUsing2 = new ProductUsing();
        productUsing2.setCheckId(check2.getId());
        productUsing2.setProductName("Food");
        productUsing2.setCost(20000.0);
        productUsing2.setUsers(List.of(user1,user2,user3,user4,user5));

        List<ProductUsing> productUsingList1 = new ArrayList<>();
        productUsingList1.add(productUsing1);
        List<ProductUsing> productUsingList2 = new ArrayList<>();
        productUsingList2.add(productUsing2);
        System.out.println(productUsingRepository.saveAll(productUsingList1));
        System.out.println(productUsingRepository.saveAll(productUsingList2));
        check1.setProductUsingList(productUsingList1);
        check2.setProductUsingList(productUsingList2);


        System.out.println(checkRepository.saveAll(checks));
        session.setCheckList(checks);
        return sessionRepository.save(session);
    }
}
