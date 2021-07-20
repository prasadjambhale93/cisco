package com.cisco.assignment.mapper;

import com.cisco.assignment.dto.PhoneDto;
import com.cisco.assignment.model.Phone;

public class PhoneMapper {

    public static Phone phoneDtoToModel(PhoneDto phoneDto) {
        return Phone.builder().userId(phoneDto.getUserId()).phoneName(phoneDto.getPhoneName())
                .phoneNumber(phoneDto.getPhoneNumber())
                .phoneModel(phoneDto.getPhoneModel()).build();
    }
}
