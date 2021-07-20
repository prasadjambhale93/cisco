package com.cisco.assignment.repository;

import com.cisco.assignment.dto.UserDto;
import com.cisco.assignment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT new com.cisco.assignment.dto.UserDto(u.userId, u.userName, u.emailAddress, u.preferredPhoneNumber) FROM User u")
    public List<UserDto> getUserDetails();

}
