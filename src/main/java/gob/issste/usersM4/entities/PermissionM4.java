package gob.issste.usersM4.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class PermissionM4 {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String nombre;
	private String descripcion;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = ProfileM4.class)
	@JoinColumn(name = "profile_id", nullable = true)
	private ProfileM4 profile;
}
