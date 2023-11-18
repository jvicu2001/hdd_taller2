package cl.sarayar.gestorTareasRest.controllers;

import cl.sarayar.gestorTareasRest.config.auth.dto.MessageResponse;
import cl.sarayar.gestorTareasRest.entities.Usuario;
import cl.sarayar.gestorTareasRest.services.UsuariosService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UsuariosControllerTest {

    @Mock
    private UsuariosService usuariosService;

    private UsuariosController usuariosController;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        this.usuariosController = new UsuariosController(usuariosService);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void authenticateUser() {
        Usuario usuarioFake = new Usuario();

        ResponseEntity<?> response = this.usuariosController.authenticateUser(usuarioFake);

        Usuario usuario = (Usuario) response.getBody();

        assertEquals(usuarioFake, usuario);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void registerUserAlreadyExists() {
        Usuario usuarioFake = new Usuario();

        when(this.usuariosService.existsByCorreo(usuarioFake.getCorreo())).thenReturn(true);

        ResponseEntity<MessageResponse> response
                = (ResponseEntity<MessageResponse>) this.usuariosController.registerUser(usuarioFake);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void registerUserOk() {
        Usuario usuarioFake = new Usuario();

        when(this.usuariosService.existsByCorreo(usuarioFake.getCorreo())).thenReturn(false);
        when(this.usuariosService.save(any(Usuario.class))).thenReturn(usuarioFake);

        ResponseEntity<Usuario> response =
                (ResponseEntity<Usuario>) this.usuariosController.registerUser(usuarioFake);

        Usuario usuario = response.getBody();

        assertEquals(1, usuario.getEstado());
        assertEquals(usuarioFake, usuario);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void actualizarUsuarioConflicto() {
        Usuario usuarioFake = new Usuario();
        Usuario usuarioFake2 = new Usuario();

        usuarioFake.setCorreo("1");
        usuarioFake.setId("1");
        usuarioFake2.setCorreo("2");
        usuarioFake2.setId("2");

        when(this.usuariosService.findById(anyString())).thenReturn(usuarioFake);
        when(this.usuariosService.findByCorreo(anyString())).thenReturn(usuarioFake2);

        ResponseEntity<MessageResponse> response =
                (ResponseEntity<MessageResponse>) this.usuariosController.actualizarUsuario(usuarioFake);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void actualizarUsuarioOk() {
        Usuario usuarioFake = new Usuario();

        usuarioFake.setCorreo("1");
        usuarioFake.setId("1");

        when(this.usuariosService.findById(anyString())).thenReturn(usuarioFake);
        when(this.usuariosService.findByCorreo(anyString())).thenReturn(usuarioFake);
        when(this.usuariosService.save(any(Usuario.class))).thenReturn(usuarioFake);

        ResponseEntity<Usuario> response =
                (ResponseEntity<Usuario>) this.usuariosController.actualizarUsuario(usuarioFake);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuarioFake, response.getBody());
    }

    @Test
    void getAll() {
        List<Usuario> usuarioListFake = new ArrayList<Usuario>();

        when(this.usuariosService.getAll()).thenReturn(usuarioListFake);

        List<Usuario> usuarioList = this.usuariosController.getAll();

        assertEquals(usuarioListFake, usuarioList);
    }
}