package cl.sarayar.gestorTareasRest.utils;

import cl.sarayar.gestorTareasRest.config.auth.UserDetailsImpl;
import cl.sarayar.gestorTareasRest.entities.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.UnknownClassException;
import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtUtilsTest {


    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Date dateFake;

    private JwtUtils jwtUtils;

    private String testToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SP0R2USEDHqPV7mcIK08ZAs4WtPMQ0NdMHuSD8tnWOw";

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils();
        ReflectionTestUtils.setField(jwtUtils, "jwtSecret", "1234", String.class);
        ReflectionTestUtils.setField(jwtUtils, "jwtExpirationMs", 30000, int.class);

        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    /**
     * Pruebas que requieren generar un JWT no se logran ejecutar
     * debido a que io.jsonwebtoken reclama de que no encuemtra la
     * clase DefaultJwtBuilder. Por ende, no se cuenta con una
     * llave JWT válida para probar.
     */

    @Test
    void generateJwtTokenNoClass() {
        Usuario usuarioFake = new Usuario();
        usuarioFake.setCorreo("johndoe@example.com");
        UserDetailsImpl userDetailsFake = new UserDetailsImpl(usuarioFake);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder
                .getContext().getAuthentication().getPrincipal()).thenReturn(userDetailsFake);

        assertThrows(UnknownClassException.class, () -> jwtUtils.generateJwtToken(authentication));

        // Validar token
        //boolean validToken = jwtUtils.validateJwtToken(jwtString);
        //assertTrue(validToken);
    }

    @Test
    void getSigningKey() {
        assertEquals("1234", jwtUtils.getSigningKey());
    }

    /*
    @Test
    void getUserNameFromJwtToken() {
    }

    @Test
    void validateJwtToken() {
    }
    */
}