package cl.sarayar.gestorTareasRest.config.auth;

import cl.sarayar.gestorTareasRest.entities.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDetailsImplTest {

    private UserDetailsImpl userDetails;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        this.userDetails = new UserDetailsImpl(usuario);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAuthorities() {
        List<GrantedAuthority> authoritiesFake = new ArrayList<>();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) userDetails.getAuthorities();
        assertEquals(authoritiesFake,authorities);
    }

    /* @Test
    void getUsuario() {
    } */

    @Test
    void getPassword() {
        this.usuario.setClave("1234");

        String clave = userDetails.getPassword();

        assertEquals("1234", clave);
    }

    @Test
    void getUsername() {
        usuario.setCorreo("1234");

        String correo = userDetails.getUsername();

        assertEquals("1234", correo);
    }

    @Test
    void isAccountNonExpired() {
        boolean nonExpired = userDetails.isAccountNonExpired();

        assertTrue(nonExpired);
    }

    @Test
    void isAccountNonLocked() {
        boolean nonLocked = userDetails.isAccountNonLocked();

        assertTrue(nonLocked);
    }

    @Test
    void isCredentialsNonExpired() {
        boolean nonExpired = userDetails.isCredentialsNonExpired();

        assertTrue(nonExpired);
    }

    @Test
    void isEnabled() {
        boolean enabled = userDetails.isEnabled();

        assertTrue(enabled);
    }

    @Test
    void testEqualsSame() {
        assertTrue(userDetails.equals(userDetails));
    }

    @Test
    void testEqualsNull() {
        assertFalse(userDetails.equals(null));
    }

    @Test
    void testEqualsNotSameClass() {
        String notTheSameClass = "deff not";
        assertFalse(userDetails.equals(notTheSameClass));
    }
}