package gob.issste.usersM4.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins="http://localhost:4200", originPatterns = "*")
@RestController
@RequestMapping("permissions")
@Tag(name = "Permisos", description = "Métodos HTTP para la gestión de Permisos del sistema Meta4")
public class PermissionM4Controller {

	@Autowired
	PermissionM4Repository permissionRepository;

	@Operation(summary = "Listado de todos los permisos del sistema Meta4 registrados", description = "Método para obtener el listado de todos los permisos del sistema Meta4 registrados", tags = { "Permisos" })
	@GetMapping()
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public List<PermissionM4> findAll() {
		return permissionRepository.findAll();
	}

	@Operation(summary = "Obtener permiso del sistema Meta4 registrado mediante su ID", description = "Método para obtener un permiso del sistema Meta4 registrado, mediante su identificador interno", tags = {	"Permisos" })
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<?> get(@Parameter(description = "ID del permiso del que se desea obtener la información", required = true) @PathVariable("id") long id) {
		Optional<PermissionM4> permission = permissionRepository.findById(id);
		if(permission.isPresent()) {
			return new ResponseEntity<>(permission.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Agregar un nuevo permiso del sistema Meta4", description = "Método para agregar un nuevo permiso del sistema Meta4", tags = {	"Permisos" })
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> post(@Parameter(description = "Objeto con datos del permiso a crearse en el Sistema") @RequestBody PermissionM4 permission) {
		PermissionM4 per = permissionRepository.save(permission);
		return ResponseEntity.ok(per);
	}

	@Operation(summary = "Modificar permiso del sistema Meta4", description = "Método para modificar un permiso del sistema Meta4 ya registrado", tags = { "Permisos" })
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> put(@Parameter(description = "ID del permiso que se desea modificar", required = true) @PathVariable("id") long id,
			@Parameter(description = "Objeto con datos del permiso a modificarse en el Sistema") @RequestBody PermissionM4 permission) {
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

	@Operation(summary = "Eliminar permiso del sistema Meta4", description = "Método para eliminar un permiso del sistema Meta4 ya registrado", tags = { "Permisos" })
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@Parameter(description = "ID del permiso que se desea eliminar", required = true) @PathVariable("id") long id) {
		permissionRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}