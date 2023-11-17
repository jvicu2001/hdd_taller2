package cl.sarayar.gestorTareasRest.services;

import cl.sarayar.gestorTareasRest.entities.Tarea;
import cl.sarayar.gestorTareasRest.repositories.TareasRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito.*;
import org.mockito.internal.matchers.Any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TareasServiceImplTest {

    @Mock
    private TareasRepository tareasRepositoryMock;

    private AutoCloseable autoCloseable;

    private TareasService tareasService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        tareasService = new TareasServiceImpl(tareasRepositoryMock);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void findAll() {
        List<Tarea> tareaListFake = new ArrayList<>();
        when(this.tareasRepositoryMock.findAll()).thenReturn(tareaListFake);

        List<Tarea> tareaList = this.tareasService.findAll();
        assertEquals(tareaList, tareaListFake);
    }

    @Test
    void save() {
        Tarea tareaFake = new Tarea();
        when(this.tareasRepositoryMock.save(any(Tarea.class))).thenReturn(tareaFake);

        Tarea tarea = this.tareasService.save(tareaFake);
        assertEquals(tarea, tareaFake);
    }

    @Test
    void removeSuccessful() {
        doNothing().when(this.tareasRepositoryMock).deleteById(anyString());

        assertTrue(this.tareasService.remove(anyString()));
    }

    @Test
    void removeFailure() {
        doThrow(IllegalArgumentException.class).when(this.tareasRepositoryMock).deleteById(anyString());

        assertFalse(this.tareasService.remove(anyString()));
    }

    @Test
    void findByIdOk() {
        Tarea tareaFake = new Tarea();
        doReturn(Optional.of(tareaFake)).when(this.tareasRepositoryMock).findById(anyString());

        Tarea tarea = this.tareasService.findById(anyString());
        assertEquals(tarea, tareaFake);
    }

    @Test
    void findByIdNull() {
        when(this.tareasRepositoryMock.findById(anyString())).thenReturn(Optional.empty());

        assertNull(this.tareasService.findById(anyString()));
    }
}