package gob.issste.usersM4.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gob.issste.usersM4.entities.ProfileM4;
import gob.issste.usersM4.repositories.ProfileM4Repository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("profile")
@Tag(name = "Perfiles", description = "Métodos HTTP para la gestión de Perfiles del sistema Meta4")
public class ProfileM4Controller {

	@Autowired
    ProfileM4Repository profileRepository;

	@Operation(summary = "Listado de todos los p1erfiles del sistema Meta4 registrados", description = "Método para obtener el listado de todos los perfiles del sistema Meta4 registrados", tags = { "Perfiles" })
    @GetMapping()
    public List<ProfileM4> findAll() {
        return profileRepository.findAll();
    }

	@Operation(summary = "Obtener perfil del sistema Meta4 registrado mediante su ID", description = "Método para obtener un perfil del sistema Meta4 registrado, mediante su identificador interno", tags = {	"Perfiles" })
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@Parameter(description = "ID del perfil del que se desea obtener la información", required = true) @PathVariable("id") long id) {
         Optional<ProfileM4> customer = profileRepository.findById(id);
        if (customer.isPresent()) {
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

	@Operation(summary = "Agregar un nuevo perfil del sistema Meta4", description = "Método para agregar un nuevo perfil del sistema Meta4", tags = {	"Perfiles" })
    @PostMapping
    public ResponseEntity<?> post(@Parameter(description = "Objeto con datos del perfil a crearse en el Sistema") @RequestBody ProfileM4 profile) {
    	profile.addPermission(profile.getPermisos());
    	ProfileM4 prof = profileRepository.save(profile);
        return ResponseEntity.ok(prof);
    }

	@Operation(summary = "Modificar perfil del sistema Meta4", description = "Método para modificar un perfil del sistema Meta4 ya registrado", tags = { "Perfiles" })
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@Parameter(description = "ID del perfil que se desea modificar", required = true) @PathVariable("id") long id,
    		@Parameter(description = "Objeto con datos del perfil a modificarse en el Sistema") @RequestBody ProfileM4 input) {
        Optional<ProfileM4> optionalProf = profileRepository.findById(id);
        if (optionalProf.isPresent()) {
        	ProfileM4 newProf = optionalProf.get();
        	newProf.setProfileCode(input.getProfileCode());
        	newProf.setDescription(input.getDescription());
			ProfileM4 save = profileRepository.save(newProf);
			return new ResponseEntity<>(save, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

	@Operation(summary = "Eliminar perfil del sistema Meta4", description = "Método para eliminar un perfil del sistema Meta4 ya registrado", tags = { "Perfiles" })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@Parameter(description = "ID del perfil que se desea eliminar", required = true) @PathVariable("id") long id) {
    	profileRepository.deleteById(id);
    	return new ResponseEntity<>(HttpStatus.OK);
    }

}