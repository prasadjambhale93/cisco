package com.cisco.assignment.service;

import com.cisco.assignment.dto.PhoneDto;
import com.cisco.assignment.mapper.PhoneMapper;
import com.cisco.assignment.model.Phone;
import com.cisco.assignment.repository.PhoneRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class PhoneService {

    @Autowired
    PhoneRepository phoneRepository;

    public Phone savePhone(PhoneDto phoneDto) {
        return phoneRepository.save(PhoneMapper.phoneDtoToModel(phoneDto));
    }
}
