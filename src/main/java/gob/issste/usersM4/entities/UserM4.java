package gob.issste.usersM4.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserM4 {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userName;
    private String password;
    private String numEmpleado;
    private Boolean activo;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="perfil_id")
    private ProfileM4 perfil;

    public void addperfil(ProfileM4 perfil) {
    	setPerfil(perfil);
    	perfil.setUserM4(this);
    }

}
