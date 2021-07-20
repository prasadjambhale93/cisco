package com.cisco.assignment.service;

import com.cisco.assignment.controller.UserController;
import com.cisco.assignment.dto.UserDetails;
import com.cisco.assignment.dto.UserDto;
import com.cisco.assignment.exception.ApiRequestException;
import com.cisco.assignment.model.Phone;
import com.cisco.assignment.model.User;
import com.cisco.assignment.repository.PhoneRepository;
import com.cisco.assignment.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PhoneRepository phoneRepository;
    private UserService userServiceMock;
    Logger logger = LoggerFactory.getLogger(UserController.class);


    @BeforeEach
    void setUp() {
        userServiceMock = new UserService(userRepository, phoneRepository, logger);
    }

    @Test
    void canSaveUser() {
        Phone phone = new Phone("Prasad Phone", "9004883316", "Nokia");
        List<Phone> phones = new ArrayList<>();
        phones.add(phone);
        User user = new User("Prasad", "pass123", "prasadjambhale93@gmail.com", "9004883316", phones);
        UserDetails userDetails = new UserDetails(user);

        //when
        userServiceMock.saveUser(userDetails);

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    void canGetAllUsers() throws ApiRequestException {
        User user = new User("Prasad", "pass123", "prasadjambhale93@gmail.com", "9004883316");
        List<User> users = new ArrayList<>();
        users.add(user);
        //when
        Mockito.when(userRepository.findAll()).thenReturn(users);
        userServiceMock.getAllUsers();
        //then
        verify(userRepository).findAll();

    }

    @Test
    void willThrowExceptionIfNoUsers() {
        //when
        List<User> users = new ArrayList<>();
        Mockito.when(userRepository.findAll()).thenReturn(users);
        //then
        assertThatThrownBy(() -> userServiceMock.getAllUsers())
                .isInstanceOf(ApiRequestException.class)
                .hasMessageContaining("No Users at the moment");
        verify(userRepository).findAll();
    }

    @Test
    void canGetUserDetails() {

        UUID uuid = UUID.randomUUID();
        List<UserDto> userDto = new ArrayList<>();
        userDto.add(new UserDto(uuid, "Prasad", "prasad@gmail.com", "9004883316"));

        //when
        Mockito.when(userRepository.getUserDetails()).thenReturn(userDto);
        //then
        userServiceMock.getUserDetails();
        verify(userRepository).getUserDetails();
    }

    @Test
    void canGetUserPhones() throws Exception {
        Optional<User> user = Optional.of(new User("Prasad", "pass123", "prasadjambhale93@gmail.com", "9004883316"));
        List<Phone> phones = new ArrayList<>();
        UUID userId = UUID.randomUUID();
        phones.add(new Phone("Prasad Phone", "9004883316", "Nokia"));

        //when
        Mockito.when(userRepository.findById(userId)).thenReturn(user);
        Mockito.when(phoneRepository.findAllByUserId(userId)).thenReturn(phones);
        //then
        userServiceMock.getUserPhones(userId);
        verify(phoneRepository).findAllByUserId(userId);
    }

    @Test
    void willThrowExceptionIfPhoneNotFound() {
        Optional<User> user = Optional.of(new User("Prasad", "pass123", "prasadjambhale93@gmail.com", "9004883316"));
        UUID userId = UUID.randomUUID();
        List<Phone> phones = new ArrayList<>();
        //when
        Mockito.when(userRepository.findById(userId)).thenReturn(user);
        Mockito.when(phoneRepository.findAllByUserId(userId)).thenReturn(phones);
        //then
        assertThatThrownBy(() -> userServiceMock.getUserPhones(userId))
                .isInstanceOf(ApiRequestException.class)
                .hasMessageContaining("Phone Number doesn't exist");
        verify(phoneRepository).findAllByUserId(userId);
    }

    @Test
    void willThrowExceptionIfUserNotFound() {
        UUID userId = UUID.randomUUID();
        assertThatThrownBy(() -> userServiceMock.getUserPhones(userId))
                .isInstanceOf(ApiRequestException.class)
                .hasMessageContaining("User Doesnt Exist");
    }

    @Test
    void canUpdatePreferredPhoneNumber() throws ApiRequestException {
        Optional<User> user = Optional.of(new User("Prasad", "pass123", "prasadjambhale93@gmail.com", "9004883315"));
        Phone phone = new Phone("Prasad Phone", "9004883316", "Nokia");
        UUID userId = UUID.randomUUID();
        String phoneNumber = "9004883316";
        User updatedUser = new User("Prasad", "pass123", "prasadjambhale93@gmail.com", "9004883316");

        //when
        Mockito.when(userRepository.findById(userId)).thenReturn(user);
        Mockito.when(phoneRepository.findByPhoneNumber(phoneNumber)).thenReturn(Optional.of(phone));
        userServiceMock.updatePreferredPhoneNumber(userId, phoneNumber);

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(updatedUser);
    }

    @Test
    void throwExceptionIfPhoneNumberNotFound() {
        Optional<User> user = Optional.of(new User("Prasad", "pass123", "prasadjambhale93@gmail.com", "9004883315"));
        UUID userId = UUID.randomUUID();
        String phoneNumber = "9004883316";

        //when
        Mockito.when(userRepository.findById(userId)).thenReturn(user);
        //userServiceMock.updatePreferredPhoneNumber(userId, phoneNumber);

        //then
        assertThatThrownBy(() -> userServiceMock.updatePreferredPhoneNumber(userId, phoneNumber))
                .isInstanceOf(ApiRequestException.class)
                .hasMessageContaining("Phone Number doesn't exist, try updating the phone from existing entries");
    }

    @Test
    void throwExceptionIfUserNotPresentToUpdatePhoneNumber() {
        UUID userId = UUID.randomUUID();
        String phoneNumber = "9004883316";
        assertThatThrownBy(() -> userServiceMock.updatePreferredPhoneNumber(userId, phoneNumber))
                .isInstanceOf(ApiRequestException.class)
                .hasMessageContaining("User Doesnt Exist");
    }

    @Test
    void canDeleteUser() throws ApiRequestException {
        Optional<User> user = Optional.of(new User("Prasad", "pass123", "prasadjambhale93@gmail.com", "9004883315"));
        User user1 = new User("Prasad", "pass123", "prasadjambhale93@gmail.com", "9004883315");
        UUID userId = UUID.randomUUID();

        //when
        Mockito.when(userRepository.findById(userId)).thenReturn(user);
        userServiceMock.deleteUser(userId);

        //then

        ArgumentCaptor<UUID> userIdCapture = ArgumentCaptor.forClass(UUID.class);
        verify(userRepository).deleteById(userIdCapture.capture());
        UUID capturedUserId = userIdCapture.getValue();
        assertThat(capturedUserId).isEqualTo(userId);
    }

    @Test
    void canDeletePhone() throws ApiRequestException {
        Optional<User> user = Optional.of(new User("Prasad", "pass123", "prasadjambhale93@gmail.com", "9004883315"));
        UUID userId = UUID.randomUUID();
        UUID phoneId = UUID.randomUUID();
        Optional<Phone> phone = Optional.of(new Phone(phoneId, "Prasad Phone", "9004883316", "Nokia"));
        String phoneNumber = "9004883316";

        //when
        Mockito.when(userRepository.findById(userId)).thenReturn(user);
        Mockito.when(phoneRepository.findByPhoneNumber(phoneNumber)).thenReturn(phone);
        userServiceMock.deletePhone(userId, phoneNumber);

        //then

        ArgumentCaptor<UUID> phoneIdCapture = ArgumentCaptor.forClass(UUID.class);
        verify(phoneRepository).deleteById(phoneIdCapture.capture());
        UUID capturedPhoneId = phoneIdCapture.getValue();
        assertThat(capturedPhoneId).isEqualTo(phoneId);
    }

}