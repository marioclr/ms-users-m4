package gob.issste.usersM4.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import gob.issste.usersM4.entities.User;
import gob.issste.usersM4.models.UserRequest;

public interface UserService {

	List<User> findAll();

	Page<User> findAll(Pageable pageable);

	Optional<User> findById(@NonNull Long id);

	User save(User user);

	Optional<User> update(UserRequest user, Long id);

	boolean existsByUsername(String username);

	void deleteById(Long id);

}
