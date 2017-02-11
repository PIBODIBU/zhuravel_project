package main.model;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

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
    private String companyName;
    private String bonusCardNumber;
    private List<PassportFile> passportFiles;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Column(name = "company", length = 100)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String company) {
        this.companyName = company;
    }

    @Column(name = "bonus_card_number", length = 100)
    public String getBonusCardNumber() {
        return bonusCardNumber;
    }

    public void setBonusCardNumber(String bonusCardNumber) {
        this.bonusCardNumber = bonusCardNumber;
    }

    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true,
            mappedBy = "userData", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    public List<PassportFile> getPassportFiles() {
        return passportFiles;
    }

    public void setPassportFiles(List<PassportFile> passportFiles) {
        this.passportFiles = passportFiles;
    }
}
