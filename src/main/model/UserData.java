package main.model;

import javax.persistence.*;

@Entity
@Table(name = "user_data")
public class UserData {
    private Integer id;
    private User user;
    private String passportSeries;
    private String passportNumber;
    private String passportValidity;
    private String passportRegistration;
    private String phone;
    private String passportUrl;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "passport_series", length = 20, nullable = false)
    public String getPassportSeries() {
        return passportSeries;
    }

    public void setPassportSeries(String passportSeries) {
        this.passportSeries = passportSeries;
    }

    @Column(name = "passport_number", length = 20, nullable = false)
    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    @Column(name = "passport_validity", length = 50, nullable = false)
    public String getPassportValidity() {
        return passportValidity;
    }

    public void setPassportValidity(String passportValidity) {
        this.passportValidity = passportValidity;
    }

    @Column(name = "passport_registration", length = 100)
    public String getPassportRegistration() {
        return passportRegistration;
    }

    public void setPassportRegistration(String passportRegistration) {
        this.passportRegistration = passportRegistration;
    }

    @Column(name = "phone", length = 20)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "passport_url", length = 200)
    public String getPassportUrl() {
        return passportUrl;
    }

    public void setPassportUrl(String passportUrl) {
        this.passportUrl = passportUrl;
    }
}
