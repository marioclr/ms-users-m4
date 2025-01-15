package gob.issste.usersM4.repositories;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import gob.issste.usersM4.entities.PermissionM4;
import gob.issste.usersM4.entities.ProfileM4;

@ExtendWith(MockitoExtension.class)
public class RepositoriesM4Test {

	@Mock
	PermissionM4Repository permissionM4Repository;
	@Mock
	ProfileM4Repository profileM4Repository;

    @Test
    @Tag("repositorio")
	@DisplayName("Test para validar la consulta de permisos del repositorio")
    void findPermissionPorNombre() {

        when(permissionM4Repository.findAll()).thenReturn(DatosRepositories.PERMISOS);
        Optional<PermissionM4> permiso = permissionM4Repository.findAll().stream()
        		.filter(e -> e.getNombre().contains("ALTAS_EMPLEADOS"))
                .findFirst();

        assertTrue(permiso.isPresent());
        assertEquals(1L, permiso.orElseThrow().getId());
        assertEquals("Registros de movimientos para dar de alta a los empleados", permiso.get().getDescripcion());
    }

    @Test
    @Tag("repositorio")
	@DisplayName("Test para validar la consulta de permisos del repositorio")
    void findPermissionPorNombreListaVacia() {
    	List<PermissionM4> permisos = Collections.emptyList();

    	when(permissionM4Repository.findAll()).thenReturn(permisos);
    	Optional<PermissionM4> permiso = permissionM4Repository.findAll().stream()
        		.filter(e -> e.getNombre().contains("ALTAS_EMPLEADOS"))
                .findFirst();
    	assertFalse(permiso.isPresent());
    }

    @Test
    @Tag("repositorio")
	@DisplayName("Test para validar la consulta de permisos asociados a un perfil")
    void testPermisosDePerfil() {
        when(permissionM4Repository.findAll()).thenReturn(DatosRepositories.PERMISOS);
        List<PermissionM4> permisos = permissionM4Repository.findAll();
        assertEquals(2, permisos.size());
        assertTrue(permisos.contains(DatosRepositories.PERMISOS.get(0)));
    }

    @Test
    @Tag("repositorio")
	@DisplayName("Test para verificar llamada al método findAll en la consulta de permisos asociados a un perfil")
    void testPermisosDePerfilVerify() {
        when(permissionM4Repository.findAll()).thenReturn(DatosRepositories.PERMISOS);
        List<PermissionM4> permisos = permissionM4Repository.findAll();
        assertEquals(2, permisos.size());
        assertTrue(permisos.contains(DatosRepositories.PERMISOS.get(0)));
        verify(permissionM4Repository).findAll();
    }

    @Test
    @Tag("repositorio")
	@DisplayName("Test para validar el auto incremento del ID en la función para guardar repositorios")
    void testGuardarExamen() {
        // Given
        ProfileM4 nuevoPerfil = DatosRepositories.PERFIL.get();
        nuevoPerfil.setPermisos(DatosRepositories.PERMISOS);

        when(profileM4Repository.save(any(ProfileM4.class))).then(new Answer<ProfileM4>(){

            Long secuencia = 2L;

            @Override
            public ProfileM4 answer(InvocationOnMock invocation) throws Throwable {
            	ProfileM4 perfil = invocation.getArgument(0);
                perfil.setId(secuencia++);
                return perfil;
            }
        });

        // When
        ProfileM4 perfil = profileM4Repository.save(nuevoPerfil);

        // Then
        assertNotNull(perfil.getId());
        assertEquals(2L, perfil.getId());
        assertEquals("MOVPERS", perfil.getProfileCode());
        verify(profileM4Repository).save(any(ProfileM4.class));
        ProfileM4 perfil2 = profileM4Repository.save(nuevoPerfil);
        assertEquals(3L, perfil2.getId());

    }
}
