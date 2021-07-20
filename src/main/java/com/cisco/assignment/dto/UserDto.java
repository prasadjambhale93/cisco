package com.cisco.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {

    private UUID userId;
    private String userName;
    private String emailAddress;
    private String preferredPhoneNumber;
}
