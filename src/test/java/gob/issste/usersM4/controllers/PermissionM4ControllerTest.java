package gob.issste.usersM4.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import gob.issste.usersM4.entities.PermissionM4;
import gob.issste.usersM4.repositories.PermissionM4Repository;

@ExtendWith(MockitoExtension.class)
public class PermissionM4ControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PermissionM4Repository permissionRepository;

    @InjectMocks
    private PermissionM4Controller permissionController;

    @Test
	@Tag("controller")
	@DisplayName("Test para validar el método GET de permisos del controlador")
    public void testObtenerPermisos() throws Exception {
    	PermissionM4 permiso = new PermissionM4(1L, "ALTAS_EMPLEADOS", "Registros de movimientos para dar de alta a los empleados", null);
        when(permissionRepository.findById(anyLong())).thenReturn(Optional.of(permiso));

        mockMvc = MockMvcBuilders.standaloneSetup(permissionController).build();

        mockMvc.perform(get("/permissions/2"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1L))
               .andExpect(jsonPath("$.nombre").value("ALTAS_EMPLEADOS"));
    }

    @Test
	@Tag("controller")
	@DisplayName("Test para validar el método POST de permisos del controlador")
    public void testCrearPermiso() throws Exception {
        PermissionM4 permiso = new PermissionM4(4L, "CALCULO_NOMINA", "Permiso para ejecutar el cálculo de la nómina", null);
        when(permissionRepository.save(any(PermissionM4.class))).thenReturn(permiso);

        mockMvc = MockMvcBuilders.standaloneSetup(permissionController).build();

        final ResultActions result = mockMvc.perform(post("/permissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1, \"nombre\":\"CALCULO_NOMINA\", \"descripcion\":\"Permiso para ejecutar el cálculo de la nómina\"}"));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(4));
        result.andExpect(jsonPath("$.nombre").value("CALCULO_NOMINA"));
    }

    @Test
	@Tag("controller")
	@DisplayName("Test para validar el método PUT de permisos del controlador")
    public void testActualizarPermiso() throws Exception {
        PermissionM4 permisoExistente = new PermissionM4(1L, "ALTAS_EMPLEADOS", "Registros de movimientos para dar de alta a los empleados", null);
        PermissionM4 permisoActualizado = new PermissionM4(1L, "CALCULO_NOMINA", "Permiso para ejecutar el cálculo de la nómina", null);
        when(permissionRepository.findById(anyLong())).thenReturn(Optional.of(permisoExistente));
        when(permissionRepository.save(permisoActualizado)).thenReturn(permisoActualizado);

        mockMvc = MockMvcBuilders.standaloneSetup(permissionController).build();

        final ResultActions result = mockMvc.perform(put("/permissions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1, \"nombre\":\"CALCULO_NOMINA\", \"descripcion\":\"Permiso para ejecutar el cálculo de la nómina\"}"));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(1L));
        result.andExpect(jsonPath("$.nombre").value("CALCULO_NOMINA"));
    }

    @Test
	@Tag("controller")
	@DisplayName("Test para validar el método DELETE de permisos del controlador")
    public void testEliminarPermiso() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(permissionController).build();

        final ResultActions result = mockMvc.perform(delete("/permissions/1")
        		.contentType(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());

        verify(permissionRepository, times(1)).deleteById(anyLong());
    }

}