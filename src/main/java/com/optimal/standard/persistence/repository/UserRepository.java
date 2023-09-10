package com.optimal.standard.persistence.repository;

import com.optimal.standard.persistence.model.RegisteredUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<RegisteredUser, Long> {

  Optional<RegisteredUser> findByUsername(String username);

}
