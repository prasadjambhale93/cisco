package com.cisco.assignment.controller;

import com.cisco.assignment.dto.PhoneDto;
import com.cisco.assignment.service.PhoneService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/phone")
public class PhoneController {

    @Autowired
    PhoneService phoneService;

    @ApiOperation(value ="Add Phone", notes = "Adds phone to the particular user in the system")
    @PostMapping("/add")
    public String addPhone(@RequestBody PhoneDto phoneDto) {
        phoneService.savePhone(phoneDto);
        return "userService.saveUser(userDetails)";
    }
}
