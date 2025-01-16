package gob.issste.usersM4.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
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

import gob.issste.usersM4.entities.ProfileM4;
import gob.issste.usersM4.repositories.ProfileM4Repository;

@ExtendWith(MockitoExtension.class)
public class ProfileM4ControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProfileM4Repository profileRepository;

    @InjectMocks
    private ProfileM4Controller profileController;

    @Test
	@Tag("controller")
	@DisplayName("Test para validar el método GET de perfiles del controlador")
    public void testObtenerPermisos() throws Exception {
    	ProfileM4 perfil = new ProfileM4(1L, "MOVPERS", "MOVIMIENTOS DE PERSONAL", null, new ArrayList<>());
        when(profileRepository.findById(anyLong())).thenReturn(Optional.of(perfil));

        mockMvc = MockMvcBuilders.standaloneSetup(profileController).build();

        mockMvc.perform(get("/profile/2"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1L))
               .andExpect(jsonPath("$.description").value("MOVIMIENTOS DE PERSONAL"));
    }

    @Test
	@Tag("controller")
	@DisplayName("Test para validar el método POST de perfiles del controlador")
    public void testCrearPermiso() throws Exception {
        ProfileM4 perfil = new ProfileM4(4L, "PROCESO", "PROCESOS DE PAGO", null, null);
        when(profileRepository.save(any(ProfileM4.class))).thenReturn(perfil);

        mockMvc = MockMvcBuilders.standaloneSetup(profileController).build();

        final ResultActions result = mockMvc.perform(post("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1, \"profileCode\":\"PROCESO\", \"descripcion\":\"PROCESOS DE PAGO\", \"userM4\":\"null\", \"permisos\":[]}"));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(4));
        result.andExpect(jsonPath("$.profileCode").value("PROCESO"));
    }

    @Test
	@Tag("controller")
	@DisplayName("Test para validar el método PUT de perfiles del controlador")
    public void testActualizarPermiso() throws Exception {
        ProfileM4 perfilExistente = new ProfileM4(1L, "MOVPERS", "MOVIMIENTOS DE PERSONAL", null, new ArrayList<>());
        ProfileM4 perfilActualizado = new ProfileM4(1L, "PROCESO", "PROCESOS DE PAGO", null, new ArrayList<>());
        when(profileRepository.findById(anyLong())).thenReturn(Optional.of(perfilExistente));
        when(profileRepository.save(any(ProfileM4.class))).thenReturn(perfilActualizado);

        mockMvc = MockMvcBuilders.standaloneSetup(profileController).build();

        final ResultActions result = mockMvc.perform(put("/profile/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1, \"profileCode\":\"PROCESO\", \"descripcion\":\"PROCESOS DE PAGO\", \"userM4\":\"null\", \"permisos\":[]}"));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(1L));
        result.andExpect(jsonPath("$.profileCode").value("PROCESO"));
        result.andExpect(jsonPath("$.description").value("PROCESOS DE PAGO"));
        result.andExpect(jsonPath("$.permisos").isEmpty());
    }

    @Test
	@Tag("controller")
	@DisplayName("Test para validar el método DELETE de perfiles del controlador")
    public void testEliminarPermiso() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(profileController).build();

        final ResultActions result = mockMvc.perform(delete("/profile/1")
        		.contentType(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());

        verify(profileRepository, times(1)).deleteById(anyLong());
    }

}