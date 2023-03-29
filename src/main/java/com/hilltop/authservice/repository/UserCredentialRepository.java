package com.hilltop.authservice.repository;

import com.hilltop.authservice.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserCredentialRepository extends JpaRepository<UserCredential, String> {


    Optional<UserCredential> findByName(String userName);
}
