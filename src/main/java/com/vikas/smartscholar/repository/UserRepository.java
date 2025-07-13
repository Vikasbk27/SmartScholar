package com.vikas.smartscholar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vikas.smartscholar.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
