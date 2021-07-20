package com.cisco.assignment.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "TB_PHONE")
@Data
@Builder
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID phoneId;
    private String phoneName;
    private String phoneNumber;
    private String phoneModel;
    @Column(name = "phone_fk")
    private UUID userId;

    public Phone(UUID phoneId, String phoneName, String phoneNumber, String phoneModel) {
        this.phoneId = phoneId;
        this.phoneName = phoneName;
        this.phoneNumber = phoneNumber;
        this.phoneModel = phoneModel;
    }

    public Phone(String phoneName, String phoneNumber, String phoneModel) {
        this.phoneName = phoneName;
        this.phoneNumber = phoneNumber;
        this.phoneModel = phoneModel;
    }


}
