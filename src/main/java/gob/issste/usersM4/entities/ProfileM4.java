package gob.issste.usersM4.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class ProfileM4 {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id; 
	private String profileCode;
	private String description;
	@JsonIgnore
	@OneToOne(mappedBy = "perfil")
	private UserM4 userM4;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "profile", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
	private List<PermissionM4> permisos;

	public ProfileM4() {
		super();
		permisos = new ArrayList<>();
	}

	public void addPermission(List<PermissionM4> permissions) {
		this.permisos = permissions;
		this.permisos.forEach(p -> p.setProfile(this));
	}

	public void addPermision(PermissionM4 permission) {
		permisos.add(permission);
		permission.setProfile(this);
	}

}
