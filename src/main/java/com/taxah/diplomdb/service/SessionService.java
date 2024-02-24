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
        // Создаем факт оплаты
        PayFact payFact = new PayFact();
//        payFact.setSession(session);
        payFact.setUsers(payFactUsersList); // Здесь предполагается, что один пользователь сделал оплату
        payFact.setAmount(10000.0);
        List<PayFact> payFacts = new ArrayList<>();
        payFacts.add(payFact);

        System.out.println(payFactRepository.save(payFact));
        session.setPayFact(payFacts);
        // Создаем чек
        Check check = new Check();
//        check.setSession(session);
        check.setName("Alcohol");


        System.out.println(checkRepository.save(check));

        // Создаем записи использования продуктов
        ProductUsing productUsing1 = new ProductUsing();
//        productUsing1.setCheck(check);
        productUsing1.setProductName("Alcohol");
        productUsing1.setCost(10000.0);
        productUsing1.setUsers(List.of(user1,user3,user4,user5));
        List<ProductUsing> productUsingList = new ArrayList<>();
        productUsingList.add(productUsing1);
        System.out.println(productUsingRepository.saveAll(productUsingList));
        check.setProductUsingList(productUsingList);


        List<Check> checks = new ArrayList<>();
        checks.add(check);
        System.out.println(checkRepository.save(check));
        session.setCheckList(checks);
        return sessionRepository.save(session);
    }
}
