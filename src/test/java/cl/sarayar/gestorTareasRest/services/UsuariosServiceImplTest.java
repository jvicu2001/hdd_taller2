package cl.sarayar.gestorTareasRest.services;

import cl.sarayar.gestorTareasRest.config.auth.UserDetailsImpl;
import cl.sarayar.gestorTareasRest.entities.Usuario;
import cl.sarayar.gestorTareasRest.repositories.UsuariosRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

class UsuariosServiceImplTest {

    @Mock
    private UsuariosRepository usuariosRepositoryMock;

    private UsuariosService usuariosService;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        usuariosService = new UsuariosServiceImpl(usuariosRepositoryMock);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void save() {
        Usuario usuarioFake = new Usuario();
        when(this.usuariosRepositoryMock.save(any(Usuario.class))).thenReturn(usuarioFake);

        Usuario usuario = this.usuariosService.save(usuarioFake);
        assertEquals(usuario, usuarioFake);
    }

    @Test
    void getAll() {
        List<Usuario> usuarioListFake = new ArrayList<>();
        when(this.usuariosRepositoryMock.findAll()).thenReturn(usuarioListFake);

        List<Usuario> usuarioList = this.usuariosService.getAll();
        assertEquals(usuarioList, usuarioListFake);
    }

    @Test
    void findByCorreoOk() {
        Usuario usuarioFake = new Usuario();
        doReturn(Optional.of(usuarioFake)).when(this.usuariosRepositoryMock).findByCorreo(anyString());

        Usuario usuario = this.usuariosService.findByCorreo(anyString());
        assertEquals(usuario, usuarioFake);
    }

    @Test
    void findByCorreoNull() {
        doReturn(Optional.empty()).when(this.usuariosRepositoryMock).findByCorreo(anyString());
        assertNull(this.usuariosService.findByCorreo(anyString()));
    }

    @Test
    void findByIdOk() {
        Usuario usuarioFake = new Usuario();
        doReturn(Optional.of(usuarioFake)).when(this.usuariosRepositoryMock).findById(anyString());

        Usuario usuario = this.usuariosService.findById(anyString());
        assertEquals(usuario, usuarioFake);
    }

    @Test
    void findByIdNull() {
        doReturn(Optional.empty()).when(this.usuariosRepositoryMock).findById(anyString());
        assertNull(this.usuariosService.findById(anyString()));
    }

    @Test
    void loadUserByUsernameOk() {
        Usuario usuarioFake = new Usuario();
        UserDetails userDetailsFake = new UserDetailsImpl(usuarioFake);
        doReturn(Optional.of(usuarioFake)).when(this.usuariosRepositoryMock).findByCorreo(anyString());

        Usuario usuario = this.usuariosService.findByCorreo(anyString());
        UserDetails userDetails = this.usuariosService.loadUserByUsername(anyString());
        assertEquals(userDetails, userDetailsFake);
    }

    @Test
    void loadUserByUsernameNull() {
        doReturn(Optional.empty()).when(this.usuariosRepositoryMock).findByCorreo(anyString());

        Usuario usuario = this.usuariosService.findByCorreo(anyString());
        UserDetails userDetails = this.usuariosService.loadUserByUsername(anyString());
        assertNull(userDetails);
    }

    @Test
    void existsByCorreoTrue() {
        when(this.usuariosRepositoryMock.existsByCorreo(anyString())).thenReturn(true);

        boolean exists = this.usuariosService.existsByCorreo(anyString());
        assertTrue(exists);
    }

    @Test
    void existsByCorreoFalse() {
        when(this.usuariosRepositoryMock.existsByCorreo(anyString())).thenReturn(false);

        boolean exists = this.usuariosService.existsByCorreo(anyString());
        assertFalse(exists);
    }
}