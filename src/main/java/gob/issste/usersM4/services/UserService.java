package gob.issste.usersM4.services;

import java.util.List;

import gob.issste.usersM4.entities.User;

public interface UserService {

	List<User> findAll();

	User save(User user);

	boolean existsByUsername(String username);

}
