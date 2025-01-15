package gob.issste.usersM4.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import gob.issste.usersM4.entities.PermissionM4;
import gob.issste.usersM4.entities.ProfileM4;

@DataJpaTest
public class IntegracionRepositoriesJPATest {

	@Autowired
	ProfileM4Repository profileM4Repository;
	@Autowired
	PermissionM4Repository permissionM4Repository;

	@Test
	@Tag("repositorioJPA")
	@DisplayName("Test para validar la consulta de perfiles, mediante el repositorio en base de datos")
	void testFindById() {
		Optional<ProfileM4> perfil = profileM4Repository.findById(1L);
		assertTrue(perfil.isPresent());
		assertEquals("MOVPERS", perfil.orElseThrow().getProfileCode());

	}

    @Test
	@Tag("repositorioJPA")
	@DisplayName("Test para validar la consulta personalizada de perfiles, mediante el repositorio en base de datos")
    void testFindByPersona() {
    	String description = "PERSONAL";
        Optional<ProfileM4> perfil = profileM4Repository.findByDescription(description);
        assertTrue(perfil.isPresent());
        assertEquals("MOVPERS", perfil.orElseThrow().getProfileCode());
        assertEquals("ALTAS_EMPLEADOS", perfil.orElseThrow().getPermisos().get(0).getNombre());
    }

    @Test
	@Tag("repositorioJPA")
	@DisplayName("Test para validar exepción en la consulta personalizada de perfiles, mediante el repositorio en base de datos")
    void testFindByPersonaThrowException() {
    	String description = "EMPLEADOS";
        Optional<ProfileM4> perfil = profileM4Repository.findByDescription(description);
        assertThrows(NoSuchElementException.class, perfil::orElseThrow);
        assertFalse(perfil.isPresent());
    }

    @Test
	@Tag("repositorioJPA")
	@DisplayName("Test para validar la consulta de todos los permisos, mediante el repositorio en base de datos")
    void testFindAll() {
        List<PermissionM4> permisos = permissionM4Repository.findAll();
        assertFalse(permisos.isEmpty());
        assertEquals(2, permisos.size());
    }

    @Test
	@Tag("repositorioJPA")
    @DisplayName("Test para validar la inserción de un permiso, mediante el repositorio en base de datos")
    void testSave() {

    	// Given
        PermissionM4 permisoRegistroDeVacaciones = new PermissionM4(null, "VAVACIONES", "Registra vacaciones de los empleados", null);

        // When
        PermissionM4 permiso = permissionM4Repository.save(permisoRegistroDeVacaciones);

        // Then
        assertEquals("VAVACIONES", permiso.getNombre());

    }

    @Test
	@Tag("repositorioJPA")
    @DisplayName("Test para validar la actualización de un permiso, mediante el repositorio en base de datos")
    void testUpdate() {

        // Given
    	PermissionM4 permisoRegistroDeVacaciones = new PermissionM4(null, "VAVACIONES", "Registra vacaciones de los empleados", null);

        // When
    	PermissionM4 permiso = permissionM4Repository.save(permisoRegistroDeVacaciones);

        // Then
    	assertEquals("VAVACIONES", permiso.getNombre());

    	// When
    	permiso.setNombre("REG_VACACIONES");
    	PermissionM4 permisoActualizado = permissionM4Repository.save(permiso);

        // Then
        assertEquals("REG_VACACIONES", permisoActualizado.getNombre());

    }

    @Test
	@Tag("repositorioJPA")
    @DisplayName("Test para validar la eliminaación de un permiso, mediante el repositorio en base de datos")
    void testDelete() {
    	PermissionM4 permiso = permissionM4Repository.findById(2L).orElseThrow();
        assertEquals("BAJAS_EMPLEADOS", permiso.getNombre());

        permissionM4Repository.delete(permiso);

        assertThrows(NoSuchElementException.class, () -> {
        	permissionM4Repository.findById(2L).orElseThrow();
        });
        assertEquals(1, permissionM4Repository.findAll().size());
    }

}
