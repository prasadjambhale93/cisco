package com.cisco.assignment.controller;

import com.cisco.assignment.dto.UserDetails;
import com.cisco.assignment.dto.UserDto;
import com.cisco.assignment.exception.ApiRequestException;
import com.cisco.assignment.model.Phone;
import com.cisco.assignment.model.User;
import com.cisco.assignment.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @ApiOperation(value ="Add User", notes = "Adds user to the system")
    @PostMapping("/add")
    public User addUser(@RequestBody UserDetails userDetails) {
        return userService.saveUser(userDetails);
    }

    @ApiOperation(value ="Get all Users", notes = "Gets all the users from the system")
    @GetMapping("/all")
    public List<User> getAllUsers() throws ApiRequestException {
        logger.info("Get Users Accessed");
        return userService.getAllUsers();
    }

    @ApiOperation(value ="Get All User Details", notes = "Gets all the details with phone of a user")
    @GetMapping("/all/details")
    public List<UserDto> getUserDetails() {
        logger.info("Get aLL Users Accessed");
        return userService.getUserDetails();
    }

    @ApiOperation(value ="Get User's phone", notes = "Gets phone details of the specified user in the system")
    @GetMapping("/{userId}/phone")
    public List<Phone> getUserPhones(@PathVariable UUID userId) throws Exception {
        logger.info("Get Users Phone Accessed");
        return userService.getUserPhones(userId);
    }

    @ApiOperation(value ="Update preferred number", notes = "Updates preferred number of the user")
    @PutMapping("/{userId}/phone/update-preferred-phonenumber/{preferredPhoneNumber}")
    public void updatePreferredPhoneNumber(@PathVariable UUID userId, @PathVariable String preferredPhoneNumber) throws Exception {
        logger.info("Update Users Preferred Phone Number");
        userService.updatePreferredPhoneNumber(userId, preferredPhoneNumber);
    }

    @ApiOperation(value ="Delete User", notes = "Deletes user from the system")
    @DeleteMapping("/{userId}/delete")
    public void deleteUser(@PathVariable UUID userId) throws Exception {
        logger.info("Delete User");
        userService.deleteUser(userId);
    }

    @ApiOperation(value ="Delete Phone", notes = "Deletes phone of user from the system")
    @DeleteMapping("/{userId}/phone/{phoneNumber}/delete")
    public void deletePhone(@PathVariable UUID userId, @PathVariable String phoneNumber) throws Exception {
        logger.info("Delete Users Phone");
        userService.deletePhone(userId, phoneNumber);
    }

}
