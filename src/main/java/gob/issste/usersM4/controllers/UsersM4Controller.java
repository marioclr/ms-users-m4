package gob.issste.usersM4.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gob.issste.usersM4.entities.PermissionM4;
import gob.issste.usersM4.entities.ProfileM4;
import gob.issste.usersM4.entities.UserM4;
import gob.issste.usersM4.repositories.ProfileM4Repository;
import gob.issste.usersM4.repositories.UserM4Repository;

@RestController
@RequestMapping("usersM4")
public class UsersM4Controller {

	@Autowired
    UserM4Repository userM4Repository;
	@Autowired
	ProfileM4Repository profileM4Repository;

    @GetMapping()
    public List<UserM4> findAll() {
        return userM4Repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") long id) {
        Optional<UserM4> customer = userM4Repository.findById(id);
        if (customer.isPresent()) {
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody UserM4 userM4) {
        UserM4 uM4 = userM4Repository.save(userM4);
        return ResponseEntity.ok(uM4);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable("id") long id, @RequestBody UserM4 input) {
         Optional<UserM4> optionalUser = userM4Repository.findById(id);
        if (optionalUser.isPresent()) {
			UserM4 newUser = optionalUser.get();
			newUser.setUserName(input.getUserName());
			newUser.setPassword(input.getPassword());
			newUser.setNumEmpleado(input.getNumEmpleado());
			UserM4 save = userM4Repository.save(newUser);
			return new ResponseEntity<>(save, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
    	userM4Repository.deleteById(id);
    	return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "inicializa", produces = MediaType.TEXT_PLAIN_VALUE)
    public String inicializa() {

    	UserM4 usuario = new UserM4();
    	usuario.setUserName("mclr");
    	usuario.setPassword("1234");
    	usuario.setNumEmpleado("357819");
    	usuario.setActivo(true);
    	
		ProfileM4 profile = new ProfileM4();
		profile.setProfileCode("MOVPERS");
		profile.setDescription("MOVIMIENTOS DE PERSONAL");

		List<PermissionM4> permisos =  new ArrayList<PermissionM4>();

		PermissionM4 permission1 = new PermissionM4();

		permission1.setNombre("ALTAS_EMPLEADOS");
		permission1.setDescripcion("Registros de movimientos para dar de alta a los empleados");
		permission1.setProfile(profile);

		permisos.add(permission1);

		PermissionM4 permission2 = new PermissionM4();
		permission2.setNombre("BAJAS_EMPLEADOS");
		permission2.setDescripcion("Registros de movimientos para dar de baja a los empleados");
		permission2.setProfile(profile);

		permisos.add(permission2);

		profile.setPermisos(permisos);
		
		usuario.setPerfil(profile);

		profileM4Repository.save(profile);
		userM4Repository.save(usuario);

		return "Inicialización OK... Saludos de MCLR.";
    }

}