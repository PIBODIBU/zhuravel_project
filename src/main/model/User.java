package main.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    private Integer id;
    private String name;
    private String surname;
    private String middleName;
    private String email;
    private String username;
    private String password;
    private List<UserRole> userRoles;
    private List<Order> ordersAsAgent;
    private List<Order> ordersAsBuyer;
    private UserData userData;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name", length = 50, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "surname", length = 50, nullable = false)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Column(name = "middle_name", length = 50, nullable = false)
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Column(name = "email", length = 50, unique = true, nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "username", length = 30, unique = true, nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", length = 30, nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "agent", orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    @OrderBy("id DESC")
    public List<Order> getOrdersAsAgent() {
        return ordersAsAgent;
    }

    public void setOrdersAsAgent(List<Order> ordersAsAgent) {
        this.ordersAsAgent = ordersAsAgent;
    }

    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "buyer", orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    @OrderBy("id DESC")
    public List<Order> getOrdersAsBuyer() {
        return ordersAsBuyer;
    }

    public void setOrdersAsBuyer(List<Order> ordersAsBuyer) {
        this.ordersAsBuyer = ordersAsBuyer;
    }

    @OneToOne(cascade = CascadeType.PERSIST, mappedBy = "user",
            fetch = FetchType.EAGER, orphanRemoval = true)
    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
