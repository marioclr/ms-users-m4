package gob.issste.usersM4.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gob.issste.usersM4.entities.User;
import gob.issste.usersM4.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@CrossOrigin(origins="http://localhost:4200", originPatterns = "*")
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	@Operation(summary = "Obtener usuarios que realizaran operaciones en el registro de Usuarios de Meta4", description = "Método para obtener usuarios que realizaran operaciones en el registro de Usuarios de Meta4", tags = { "Users" })
	@GetMapping
	public List<User> list() {
		return userService.findAll();
	}

	@Operation(summary = "Agregar un nuevo usuario con perfil de administrador para operar el registro de Usuarios de Meta4", description = "Método para agregar un nuevo usuario con perfil de administrador para operar el registro de Usuarios de Meta4", tags = { "Users" })
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {
		if (result.hasFieldErrors()) {
			return validation(result);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
	}

	@Operation(summary = "Agregar un nuevo usuario para operar el registro de Usuarios de Meta4", description = "Método para agregar un nuevo usuario para operar el registro de Usuarios de Meta4", tags = { "Users" })
	@PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user, BindingResult result) {
        user.setAdmin(false);
        return create(user, result);
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
