package cl.sarayar.gestorTareasRest.controllers;

import cl.sarayar.gestorTareasRest.services.UsuariosService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

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
    }

    @Test
    void registerUser() {
    }

    @Test
    void actualizarUsuario() {
    }

    @Test
    void getAll() {
    }
}