package gob.issste.usersM4.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gob.issste.usersM4.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(String name);

}
