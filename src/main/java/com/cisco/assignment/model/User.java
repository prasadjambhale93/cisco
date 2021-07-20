package com.cisco.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "TB_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;
    private String userName;
    private String password;
    private String emailAddress;
    @JoinColumn(name = "phoneNumber", referencedColumnName = "phoneNumber")
    @Column(unique = true)
    private String preferredPhoneNumber;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Phone.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "phone_fk", referencedColumnName = "userId")
    private List<Phone> phones;

    public User(String userName, String password, String emailAddress, String preferredPhoneNumber, List<Phone> phones) {
        this.userName = userName;
        this.password = password;
        this.emailAddress = emailAddress;
        this.preferredPhoneNumber = preferredPhoneNumber;
        this.phones = phones;
    }

    public User(String userName, String password, String emailAddress, String preferredPhoneNumber) {
        this.userName = userName;
        this.password = password;
        this.emailAddress = emailAddress;
        this.preferredPhoneNumber = preferredPhoneNumber;
        this.phones = phones;
    }
}
