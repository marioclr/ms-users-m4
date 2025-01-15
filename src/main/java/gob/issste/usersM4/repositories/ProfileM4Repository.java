package gob.issste.usersM4.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gob.issste.usersM4.entities.ProfileM4;

public interface ProfileM4Repository extends JpaRepository<ProfileM4, Long> {

	@Query("SELECT p FROM ProfileM4 p WHERE p.description LIKE %:description%")
	Optional<ProfileM4> findByDescription(@Param("description") String description);

}
