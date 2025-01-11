package gob.issste.usersM4.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class UserM4 {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String userName;
    private String password;
    private String numEmpleado;
    private Boolean activo;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="perfil_id")
    private ProfileM4 perfil;
}
