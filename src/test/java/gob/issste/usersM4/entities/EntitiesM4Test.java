package gob.issste.usersM4.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;

public class EntitiesM4Test {

	PermissionM4 permiso;
	ProfileM4 perfil;
	UserM4 usuario;

    private TestInfo testInfo;
    private TestReporter testReporter;

    @BeforeEach
    void initMetodoTest(TestInfo testInfo, TestReporter testReporter) {
    	permiso = new PermissionM4();
		permiso.setNombre("ALTAS_EMPLEADOS");
		permiso.setDescripcion("Registros de movimientos para dar de alta a los empleados");
    	perfil = new ProfileM4();
    	perfil.setProfileCode("MOVPERS");
    	perfil.setDescription("MOVIMIENTOS DE PERSONAL");
    	usuario = new UserM4(1L, "mclr", "1234", "357819", true, null);

        this.testInfo = testInfo;
        this.testReporter = testReporter;

        System.out.println("Iniciando el método de prueba.");
        testReporter.publishEntry(" ejecutando: " + testInfo.getDisplayName() + " " + testInfo.getTestMethod().orElse(null).getName()
                + " con las etiquetas " + testInfo.getTags());
    }

    @AfterEach
    void tearDown() {
        System.out.println("Finalizando el método de prueba.");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("Inicializando los tests");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Finalizando los tests");
    }

	@Test
	@Tag("modelo")
	@DisplayName("Test para validar los valores de los atributos del objeto para permisos")
	void testPermissionM4() {
		PermissionM4 permiso = new PermissionM4();
		permiso.setNombre("ALTAS_EMPLEADOS");
		permiso.setDescripcion("Registros de movimientos para dar de alta a los empleados");
		String nombreEsperado = "ALTAS_EMPLEADOS";
		String descEsperada = "Registros de movimientos para dar de alta a los empleados";
		String nombreReal = permiso.getNombre();
		String descReal = permiso.getDescripcion();

		assertNotNull(nombreReal, () -> "El nombre del permiso no puede ser nulo");
		assertNotNull(nombreReal, () -> "La descripcion del permiso no puede ser nula");
		assertEquals(nombreEsperado, nombreReal, () -> "El nombre del permiso no es el esperado");
		assertEquals(descEsperada, descReal, () -> "La descripcion del permiso no es la esperada");
	}

	@Test
	@Tag("modelo")
	@DisplayName("Test para validar los valores de los atributos del objeto para perfiles")
	void testProfileM4() {
		String codeEsperado = "MOVPERS";
		String descEsperada = "MOVIMIENTOS DE PERSONAL";
		String codeReal = perfil.getProfileCode();
		String descReal = perfil.getDescription();

		assertNotNull(codeReal, () -> "El código del perfil no puede ser nulo");
		assertNotNull(descReal, () -> "La descripción del perfil no puede ser nula");
		assertEquals(codeEsperado, codeReal, () -> "El código del perfil no es el esperado");
		assertEquals(descEsperada, descReal, () -> "La descripción del perfil no es la esperada");
	}

	@Test
	@Tag("modelo")
	@DisplayName("Test para validar los valores de los atributos del objeto para usuarios")
	void testUserM4() {
		UserM4 usuario = new UserM4();
		usuario.setUserName("mclr");
		usuario.setPassword("1234");
		usuario.setNumEmpleado("357819");
		usuario.setActivo(true);

		String userNameEsperado = "mclr";
		String passwordEsperado = "1234";
		String numEmpleadoEsperado = "357819";
		Boolean activoEsperado = true;
		String userNameReal = usuario.getUserName();
		String passwordReal = usuario.getPassword();
		String numEmpleadoReal = usuario.getNumEmpleado();
		Boolean activoReal = usuario.getActivo();

		assertNotNull(userNameReal, () -> "El nombre del usuario no puede ser nulo");
		assertNotNull(passwordReal, () -> "El password del usuario no puede ser nulo");
		assertNotNull(numEmpleadoReal, () -> "El número de empleado del usuario no puede ser nulo");
		assertNotNull(activoReal, () -> "El estatus del usuario no puede ser nulo");
		assertEquals(userNameEsperado, userNameReal, () -> "El nombre del usuario no es el esperado");
		assertEquals(passwordEsperado, passwordReal, () -> "El password del usuario no es el esperado");
		assertEquals(numEmpleadoEsperado, numEmpleadoReal, () -> "El número de empleado del usuario no es el esperado");
		assertEquals(activoEsperado, activoReal, () -> "El estatus del usuario no es el esperado");
	}

	@Test
	@Tag("modelo")
	@Tag("relaciones")
	@DisplayName("Test para validar las asociaciones uno a muchos de los objetos de perfiles con sus permisos relacionados")
	void testRelacionProfilesPermissions() {
		PermissionM4 permiso1 = new PermissionM4(1L, "ALTAS_EMPLEADOS", "Registros de movimientos para dar de alta a los empleados", null);
		PermissionM4 permiso2 = new PermissionM4(2L, "BAJAS_EMPLEADOS", "Registros de movimientos para dar de baja a los empleados", null);
		perfil.addPermision(permiso1);
		perfil.addPermision(permiso2);

		assertAll(
				() -> assertEquals(2, perfil.getPermisos().size(), 
						() -> "El perfil no tiene los permisos esperados"),
				() -> assertEquals("MOVPERS", permiso1.getProfile().getProfileCode(), 
						() -> "El código del perfil asociado al permiso no es el esperado"),
				() -> assertEquals("ALTAS_EMPLEADOS", perfil.getPermisos().stream()
						.filter(c -> c.getNombre().equals("ALTAS_EMPLEADOS"))
						.findFirst()
						.get().getNombre(), 
						() -> "El perfil no tiene el permiso asociado con el nombre esperado"),
				() -> assertTrue(perfil.getPermisos().stream()
						.filter(c -> c.getNombre().equals("ALTAS_EMPLEADOS"))
						.findFirst().isPresent(), 
						() -> "El perfil no tiene el permiso asociado esperado")
		);
	}

	@Test
	@Tag("modelo")
	@Tag("relaciones")
	@DisplayName("Test para validar las asociaciones uno a uno de los objetos de usuarios con sus perfiles relacionados")
	void testRelacionUsersProfiles() {
		usuario.addperfil(perfil);

		assertAll(
				() -> assertNotNull(usuario.getPerfil(), 
						() -> "El perfil del usuario no puede ser nullo"),
				() -> assertNotNull(perfil.getUserM4(), 
						() -> "El usuario del perfil no puede ser nullo"),
				() -> assertEquals("MOVPERS", usuario.getPerfil().getProfileCode(), 
						() -> "El código del perfil asociado al usuario no es el esperado"),
				() -> assertEquals("357819", perfil.getUserM4().getNumEmpleado(), 
						() -> "El número de empleado asociado al usuario no es el esperado")
		);
	}

	@Test
	@DisplayName("Test creado solo para validar el método fail(), y la anotación @Disabled, de JUnit")
	@Disabled
	void testForzarFallo() {
        testReporter.publishEntry(testInfo.getTags().toString());
        if (testInfo.getTags().contains("modelo")) {
            testReporter.publishEntry("Realizar alguna operación relacionada a la etiqueta modelo");
        }
		fail();
	}

}
