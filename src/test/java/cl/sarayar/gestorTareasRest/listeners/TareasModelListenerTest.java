package cl.sarayar.gestorTareasRest.listeners;

import cl.sarayar.gestorTareasRest.entities.Tarea;
import cl.sarayar.gestorTareasRest.services.GeneradorSecuenciaService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TareasModelListenerTest {

    @Mock
    private GeneradorSecuenciaService generadorSecuenciaService;

    private TareasModelListener listener;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        listener = new TareasModelListener(generadorSecuenciaService);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void onBeforeConvert() {
        Tarea tareaFake = new Tarea();
        BeforeConvertEvent<Tarea> event = new BeforeConvertEvent<Tarea>(tareaFake, anyString());

        when(this.generadorSecuenciaService.generadorSecuencia("")).thenReturn(2L);

        this.listener.onBeforeConvert(event);

        assertEquals(2L, event.getSource().getIdentificador());
    }
}