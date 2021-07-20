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
public class PhoneDto {

    private UUID userId;
    private String userName;
    private String phoneName;
    private String phoneNumber;
    private String phoneModel;
}
