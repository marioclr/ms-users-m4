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

import gob.issste.usersM4.entities.UserM4;
import gob.issste.usersM4.repositories.UserM4Repository;

@ExtendWith(MockitoExtension.class)
public class UserM4ControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserM4Repository userRepository;

    @InjectMocks
    private UsersM4Controller userController;

    @Test
	@Tag("controller")
	@DisplayName("Test para validar el método GET de usuarios del controlador")
    public void testObtenerPermisos() throws Exception {
    	UserM4 usuario = new UserM4(1L, "mclr", "1234", "357819", true, null);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(usuario));

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        mockMvc.perform(get("/usersM4/2"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1L))
               .andExpect(jsonPath("$.userName").value("mclr"));
    }

    @Test
	@Tag("controller")
	@DisplayName("Test para validar el método POST de usuarios del controlador")
    public void testCrearPermiso() throws Exception {
        UserM4 usuario = new UserM4(2L, "mclr", "1234", "357819", true, null);
        when(userRepository.save(any(UserM4.class))).thenReturn(usuario);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        final ResultActions result = mockMvc.perform(post("/usersM4")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1, \"userName\":\"mclr\", \"password\":\"1234\", \"numEmpleado\":\"357819\"}"));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(2));
        result.andExpect(jsonPath("$.userName").value("mclr"));
    }

    @Test
	@Tag("controller")
	@DisplayName("Test para validar el método PUT de usuarios del controlador")
    public void testActualizarPermiso() throws Exception {
        UserM4 usuarioExistente = new UserM4(3L, "mclr", "1234", "357819", true, null);
        UserM4 usuarioActualizado = new UserM4(3L, "lrmc", "4321", "357819", true, null);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(usuarioExistente));
        when(userRepository.save(usuarioActualizado)).thenReturn(usuarioActualizado);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        final ResultActions result = mockMvc.perform(put("/usersM4/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1, \"userName\":\"lrmc\", \"password\":\"4321\", \"numEmpleado\":\"357819\"}"));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(3L));
        result.andExpect(jsonPath("$.userName").value("lrmc"));
    }

    @Test
	@Tag("controller")
	@DisplayName("Test para validar el método DELETE de usuarios del controlador")
    public void testEliminarPermiso() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        final ResultActions result = mockMvc.perform(delete("/usersM4/1")
        		.contentType(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());

        verify(userRepository, times(1)).deleteById(anyLong());
    }

}