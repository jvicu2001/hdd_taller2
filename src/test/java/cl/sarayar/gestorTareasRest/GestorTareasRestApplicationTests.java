package cl.sarayar.gestorTareasRest;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import cl.sarayar.gestorTareasRest.entities.Usuario;
import cl.sarayar.gestorTareasRest.services.UsuariosService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class GestorTareasRestApplicationTests {

	@Mock
	private UsuariosService usuariosService;

	@InjectMocks
	private GestorTareasRestApplication gestorTareasRestApplication;


	private AutoCloseable autoCloseable;

	@BeforeEach
	void setUp(){
		this.gestorTareasRestApplication = new GestorTareasRestApplication();
		autoCloseable = MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception{
		autoCloseable.close();
	}

	@Test
	void contextLoads() {
	}

	@Test
	void runListEmpty() throws Exception {
		List<Usuario> usuarioListFake = new ArrayList<>();

		Usuario admin = new Usuario();
		admin.setNombre("Admin");
		admin.setCorreo("sarayar@skynux.cl");


		when(usuariosService.getAll()).thenReturn(usuarioListFake);
		when(usuariosService.save(any(Usuario.class))).thenAnswer(invocationOnMock -> {
            Usuario usuarioCapturado = invocationOnMock.getArgument(0);
            usuarioListFake.add(usuarioCapturado);
            return null;
        });
		gestorTareasRestApplication.run();

		assertEquals(admin.getCorreo(), usuarioListFake.get(0).getCorreo());
		assertEquals(admin.getNombre(), usuarioListFake.get(0).getNombre());

	}

	@Test
	void runThrowException() throws Exception {
		// https://stackoverflow.com/a/52229629
		// Creamos un logger con ch.qos.logback
		Logger logger = (Logger) LoggerFactory.getLogger(GestorTareasRestApplication.class);

		// Creamos un Appender para capturar los mensajes del Logger de la clase
		ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
		listAppender.start();

		logger.addAppender(listAppender);

		when(usuariosService.save(any(Usuario.class))).thenThrow(new NullPointerException());

		gestorTareasRestApplication.run();

		// Pasamos los logs capturados a una lista y realizamos nuestros asserts
		List<ILoggingEvent> loggingEventList = listAppender.list;

		assertEquals(Level.ERROR, loggingEventList.get(0).getLevel());
		assertEquals("Error al definir usuario por defecto:null",
				loggingEventList.get(0).getMessage());
	}

}
