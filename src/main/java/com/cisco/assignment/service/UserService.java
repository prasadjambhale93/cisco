package com.cisco.assignment.service;

import com.cisco.assignment.constants.Exception;
import com.cisco.assignment.dto.UserDetails;
import com.cisco.assignment.dto.UserDto;
import com.cisco.assignment.exception.ApiRequestException;
import com.cisco.assignment.model.Phone;
import com.cisco.assignment.model.User;
import com.cisco.assignment.repository.PhoneRepository;
import com.cisco.assignment.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PhoneRepository phoneRepository;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public User saveUser(UserDetails userDetails) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userDetails.getUser().setPassword(passwordEncoder.encode(userDetails.getUser().getPassword()));
        return userRepository.save(userDetails.getUser());
    }

    public List<User> getAllUsers() throws ApiRequestException {
        List<User> users = userRepository.findAll();
        if (null == users || users.isEmpty()) {
            logger.error("Users Not found");
            throw new ApiRequestException(Exception.NO_USERS);
        }
        return users;
    }

    public List<UserDto> getUserDetails() {
        return userRepository.getUserDetails();
    }

    public List<Phone> getUserPhones(UUID userId) throws java.lang.Exception {
        Optional<User> user = userRepository.findById(userId);
        checkIfUserNotFound(user);
        List<Phone> phones = phoneRepository.findAllByUserId(userId);
        if (phones == null || phones.isEmpty()) {
            logger.error("Phone Not found");
            throw new ApiRequestException(Exception.EXCEPTION_PHONE_NOT_FOUND);
        } else {
            return phones;
        }
    }

    @Transactional
    public void updatePreferredPhoneNumber(UUID userId, String preferredPhoneNumber) throws ApiRequestException {
        Optional<User> user = userRepository.findById(userId);
        checkIfUserNotFound(user);
        Optional<Phone> phone = phoneRepository.findByPhoneNumber(preferredPhoneNumber);
        if (!phone.isPresent()) {
            logger.error("Phone Not found");
            throw new ApiRequestException(Exception.EXCEPTION_PHONE_NOT_FOUND_UPDATE);
        }
        User user1 = user.get();
        user1.setPreferredPhoneNumber(preferredPhoneNumber);
        userRepository.save(user1);
    }

    public void deleteUser(UUID userId) throws ApiRequestException {
        Optional<User> user = userRepository.findById(userId);
        checkIfUserNotFound(user);

        user.ifPresent(u -> {
            userRepository.deleteById(userId);
        });
    }

    public void deletePhone(UUID userId, String phoneNumber) throws ApiRequestException {
        Optional<User> user = userRepository.findById(userId);
        checkIfUserNotFound(user);

        user.ifPresent(u -> {
            Optional<Phone> phone = phoneRepository.findByPhoneNumber(phoneNumber);
            phone.ifPresent(p -> {
                phoneRepository.deleteById(phone.get().getPhoneId());
            });
        });
    }

    private void checkIfUserNotFound(Optional<User> user) throws ApiRequestException {
        if (!user.isPresent()) {
            logger.error("Users Not found");
            throw new ApiRequestException(Exception.EXCEPTION_USER_NOT_FOUND);
        }
    }

}
