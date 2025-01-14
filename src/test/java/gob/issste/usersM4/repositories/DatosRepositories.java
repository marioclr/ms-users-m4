package gob.issste.usersM4.repositories;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import gob.issste.usersM4.entities.PermissionM4;
import gob.issste.usersM4.entities.ProfileM4;
import gob.issste.usersM4.entities.UserM4;

public class DatosRepositories {

	public final static List<PermissionM4> PERMISOS = Arrays.asList(
			new PermissionM4(1L, "ALTAS_EMPLEADOS", "Registros de movimientos para dar de alta a los empleados", null),
			new PermissionM4(2L, "BAJAS_EMPLEADOS", "Registros de movimientos para dar de baja a los empleados", null));

	public final static Optional<ProfileM4> PERFIL = Optional.of(new ProfileM4(1L, "MOVPERS", "MOVIMIENTOS DE PERSONAL", null, PERMISOS));

	public final static Optional<UserM4> USUARIO = Optional.of(new UserM4(null, "mclr", "1234", "357819", true, null));

}
