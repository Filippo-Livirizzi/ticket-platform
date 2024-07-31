package it.spring.milestone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.spring.milestone.Model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String Username);
}
