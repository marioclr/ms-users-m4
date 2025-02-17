package gob.issste.usersM4.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import gob.issste.usersM4.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByUsername(String userName);
	Optional<User> findByUsername(String userName);
	Page<User> findAll(Pageable pageable);

}
