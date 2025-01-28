package gob.issste.usersM4.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gob.issste.usersM4.services.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class ExistsByUsernameValidation implements ConstraintValidator<ExistsByUsername, String> {

	@Autowired
	private UserService userService;

	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {

		if (userService == null) {
            return true;
        }

		return !userService.existsByUsername(username);
	}
}
