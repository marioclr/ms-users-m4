package gob.issste.usersM4.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import gob.issste.usersM4.repositories.PermissionM4Repository;

@RestController
@RequestMapping("permissions")
public class PermissionM4Controller {

	@Autowired
	PermissionM4Repository permissionRepository;

	@GetMapping()
	public List<PermissionM4> findAll() {
		return permissionRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable("id") long id) {
		Optional<PermissionM4> permission = permissionRepository.findById(id);
		if(permission.isPresent()) {
			return new ResponseEntity<>(permission.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<?> post(@RequestBody PermissionM4 permission) {
		PermissionM4 per = permissionRepository.save(permission);
		return ResponseEntity.ok(per);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> put(@PathVariable("id") long id, @RequestBody PermissionM4 permission) {
		Optional<PermissionM4> optionalPermission = permissionRepository.findById(id);
		if (optionalPermission.isPresent()) {
			PermissionM4 per = optionalPermission.get();
			per.setNombre(permission.getNombre());
			per.setDescripcion(permission.getDescripcion());
			PermissionM4 save = permissionRepository.save(per);
			return new ResponseEntity<>(save, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		permissionRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}