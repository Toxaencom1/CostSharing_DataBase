package com.taxah.diplomdb.service;

import com.taxah.diplomdb.model.*;

import com.taxah.diplomdb.model.abstractClasses.Account;
import com.taxah.diplomdb.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Service for working with the session, check, pay_fact, product_using, and temp_user entities in the database.
 */
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


    //region Session
    /**
     * Creates a session with adds members to it. The admin of the session is also added to the session.
     * <p>
     * This is debugging method.
     * @param tempMembers List of members to add to the session.
     * @param admin       The admin of the session.
     * @return The id of the created session.
     */
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

    /**
     * Finds a session by its name. Ignores case.
     * @param sessionName The name of the session to find.
     * @return List of sessions with the specified name.
     */
    public List<Session> findByName(String sessionName) {
        return sessionRepository.findByNameContainingIgnoreCase(sessionName);
    }

    /**
     * Creates a session with the specified name and adds the admin to it.
     * @param firstname   The first name of the admin.
     * @param lastname    The last name of the admin.
     * @param sessionName The name of the session.
     * @return The created session.
     */
    public Session createSession(String firstname, String lastname, String sessionName) {
        Session session = sessionRepository.save(new Session());
        TempUser tempUser = tempUserRepository.save(new TempUser(session.getId(), firstname, lastname));
        session.setName(sessionName);
        session.setAdminId(tempUser.getId());
        session.getMembersList().add(tempUser);
        return sessionRepository.save(session);
    }

    /**
     * Finds a session by its id.
     * @param id - type long, id of the session.
     * @return null if the session is not found.
     */
    public Session getSession(Long id) {
        Optional<Session> optional = sessionRepository.findById(id);
        return optional.orElse(null);
    }
//endregion

    //region Check

    /**
     * Finds a check by its id.
     * @param id - type long, id of the check.
     * @return null if the check is not found.
     */
    public Check getCheck(Long id) {
        Optional<Check> optionalCheck = checkRepository.findById(id);
        return optionalCheck.orElse(null);
    }

    /**
     * Creates a check with the specified name and adds it to the session.
     * @param sessionId - type long, id of the session.
     * @param name - type String, name of the check.
     * @return id of the created check.
     */
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

    /**
     * Deletes a check by its id. Manual relationship detach.
     * @param id - type long, id of the check.
     * @return id of the session to which the check belonged.
     */
    public Long deleteCheck(Long id) {
        Optional<Check> optionalCheck = checkRepository.findById(id);
        if (optionalCheck.isPresent()) {
            Check check = optionalCheck.get();
            if (check.getPayFact() != null) {
                PayFact payFact = check.getPayFact();
                check.setPayFact(null);
                payFactRepository.delete(payFact);
            }
            if (check.getProductUsingList() != null) {
                List<ProductUsing> productUsingList = check.getProductUsingList();
                check.setProductUsingList(null);
                for (ProductUsing productUsing : productUsingList) {
                    productUsing.setCheck(null);
                    for (TempUser user : productUsing.getUsers()) {
                        user.getProductUsingList().removeIf(p -> p.equals(productUsing));
                    }
                    productUsingRepository.delete(productUsing);
                }
            }
            Long sessionId = check.getSession().getId();
            check.setSession(null);
            checkRepository.deleteById(check.getId());
            return sessionId;
        }
        return null;
    }
//endregion

    //region PayFact
    /**
     * Finds a pay fact by its id.
     * @param id - type long, id of the pay fact.
     * @return null if the pay fact is not found.
     */
    public PayFact getPayFact(Long id) {
        Optional<PayFact> optionalPayFact = payFactRepository.findById(id);
        return optionalPayFact.orElse(null);
    }

    /**
     * Adds a pay fact to the check.
     * @param checkId - type long, id of the check.
     * @param tempUserId - type long, id of the user who paid.
     * @param amount - type double, amount of money.
     * @return null if the check or user is not found.
     */
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

    /**
     * Deletes a pay fact by its id. Manual relationship detach.
     * @param id - type long, id of the pay fact.
     * @return id of the check to which the pay fact belonged.
     */
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

    /**
     * Updates a pay fact.
     * @param newPayFact - type PayFact, new pay fact.
     * @return updated pay fact.
     */
    public PayFact updatePayFact(PayFact newPayFact) {
        return payFactRepository.save(newPayFact);
    }

    //endregion

    //region ProductUsing
    /**
     * Finds a product using by its id.
     * @param id - type long, id of the product using.
     * @return null if the product using is not found.
     */
    public ProductUsing getProductUsing(Long id) {
        Optional<ProductUsing> optionalProductUsing = productUsingRepository.findById(id);
        return optionalProductUsing.orElse(null);
    }

    /**
     * Adds a product using to the check.
     * @param checkId - type long, id of the check.
     * @param productName - type String, product name.
     * @param cost - type double, product cost.
     * @param tempUsers - type List<TempUser>, list of users who used the product.
     * @return null if the check is not found.
     */
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

    /**
     * Updates a product using.
     * @param newProductUsing - type ProductUsing, new product using.
     * @return updated product using.
     */
    public ProductUsing updateProductUsing(ProductUsing newProductUsing) {
        Optional<ProductUsing> optionalProductUsing = productUsingRepository.findById(newProductUsing.getId());
        if (optionalProductUsing.isPresent()) {
            ProductUsing productUsing = optionalProductUsing.get();
            productUsing.setProductName(newProductUsing.getProductName());
            productUsing.setCost(newProductUsing.getCost());
            productUsingRepository.save(productUsing);
            return productUsing;
        }
        return null;
    }

    /**
     * Deletes a product using by its id. Manual relationship detach.
     * @param productUsingId - type long, id of the product using.
     */
    public void deleteProduct(Long productUsingId) {
        Optional<ProductUsing> optionalProductUsing = productUsingRepository.findById(productUsingId);
        if (optionalProductUsing.isPresent()) {
            ProductUsing productUsing = optionalProductUsing.get();
            for (TempUser tempUser : productUsing.getUsers()) {
                tempUser.getProductUsingList().removeIf(p -> p.equals(productUsing));
            }
            productUsing.setUsers(null);
            productUsingRepository.delete(productUsing);
        }
    }

    /**
     * Deletes a user from the product using list.
     * @param tempUser - type TempUser, user to delete.
     * @param productUsingId - type long, id of the product using.
     */
    public void deleteTempUserFromProduct(TempUser tempUser, Long productUsingId) {
        productUsingUserRepository.deleteByTempUserIdAndProductUsingId(tempUser.getId(), productUsingId);
    }

    /**
     * Adds a user to the product using list.
     * @param tempUser - type TempUser, user to add.
     * @param productUsingId - type long, id of the product using.
     */
    public void addTempUserToProduct(TempUser tempUser, Long productUsingId) {
        Optional<ProductUsing> optionalProductUsing = productUsingRepository.findById(productUsingId);
        if (optionalProductUsing.isPresent()) {
            ProductUsing productUsing = optionalProductUsing.get();
            if (!productUsing.getUsers().contains(tempUser)) {
                productUsing.addTempUser(tempUser);
                productUsingRepository.save(productUsing);
            }
        }
    }

    /**
     * Adds all members of the session to the product using list.
     * @param productUsingId - type long, id of the product using.
     * @param sessionId - type long, id of the session.
     */
    public void addAllMembersToProduct(Long productUsingId, Long sessionId) {
        Optional<ProductUsing> optionalProductUsing = productUsingRepository.findById(productUsingId);
        Optional<Session> optionalSession = sessionRepository.findById(sessionId);
        if (optionalProductUsing.isPresent() && optionalSession.isPresent()) {
            ProductUsing productUsing = optionalProductUsing.get();
            Session session = optionalSession.get();
            for (TempUser t : session.getMembersList()) {
                if (!productUsing.getUsers().contains(t)) {
                    productUsing.addTempUser(t);
                }
            }
            productUsingRepository.save(productUsing);
        }
    }

    //region ProductUsing / TempUser
    /**
     * Finds a temp user by its id.
     * @param id - type long, id of the temp user.
     * @return null if the temp user is not found.
     */
    public TempUser getTempUser(Long id) {
        Optional<TempUser> optionalTempUser = tempUserRepository.findById(id);
        return optionalTempUser.orElse(null);
    }

    /**
     * Adds a temp user to the session.
     * @param tempUser - type TempUser, temp user to add.
     * @return added temp user.
     */
    public TempUser addTempUser(TempUser tempUser) {
        return tempUserRepository.save(tempUser);
    }

    /**
     * Deletes a temp user by its id. Manual relationship detach.
     * @param id - type long, id of the temp user.
     * @return id of the session to which the temp user belonged.
     */
    public Long deleteMember(Long id) {
        Optional<TempUser> optionalTempUser = tempUserRepository.findById(id);
        if (optionalTempUser.isPresent()) {
            TempUser tempUser = optionalTempUser.get();
            Optional<Session> optionalSession = sessionRepository.findById(tempUser.getId());
            if (optionalSession.isPresent()) {
                if (Objects.equals(optionalSession.get().getAdminId(), tempUser.getId())) {
                    return null;
                }
            }
            for (ProductUsing p : tempUser.getProductUsingList()) {
                p.getUsers().removeIf(u -> u.equals(tempUser));
            }
            tempUser.setProductUsingList(null);
            tempUserRepository.saveAndFlush(tempUser);
            tempUserRepository.deleteById(id);
            return optionalTempUser.get().getSessionId();
        }
        return null;
    }

    /**
     * Updates a temp user.
     * @param id - type long, id of the temp user.
     * @param newTempUser - type TempUser, new temp user.
     * @return id of the session to which the temp user belonged.
     */
    public Long updateMember(Long id, TempUser newTempUser) {
        Optional<TempUser> optionalTempUser = tempUserRepository.findById(id);
        if (optionalTempUser.isPresent()) {
            TempUser oldTempUser = optionalTempUser.get();
            oldTempUser.setFirstname(newTempUser.getFirstname());
            oldTempUser.setLastname(newTempUser.getLastname());
            tempUserRepository.save(oldTempUser);
            return oldTempUser.getSessionId();
        }
        return null;
    }
    //endregion
//endregion

    /**
     * Adds a user to the database.
     * @param user - type User, user to add.
     * @return added user.
     */
    public User addUser(User user) {
        return userRepository.save(user);
    }
}
