package cl.sarayar.gestorTareasRest.controllers;

import cl.sarayar.gestorTareasRest.entities.Tarea;
import cl.sarayar.gestorTareasRest.services.GeneradorSecuenciaServiceImpl;
import cl.sarayar.gestorTareasRest.services.TareasService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class TareasControllerTest {

    @Mock
    private TareasService tareasService;

    private TareasController tareasController;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        tareasController = new TareasController(tareasService);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void getAll() {
        List<Tarea> tareaListFake = new ArrayList<>();
        when(this.tareasService.findAll()).thenReturn(tareaListFake);

        List<Tarea> tareaList = this.tareasController.getAll();
        assertEquals(tareaListFake, tareaList);
    }

    @Test
    void save() {
        Tarea tareaFake = new Tarea();

        when(this.tareasService.save(any(Tarea.class))).thenReturn(tareaFake);

        ResponseEntity<Tarea> response = this.tareasController.save(tareaFake);

        Tarea tarea = response.getBody();

        assertEquals(tareaFake, tarea);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateOk() {
        Tarea tareaFake = new Tarea();
        tareaFake.setId("a");
        when(this.tareasService.findById(anyString())).thenReturn(tareaFake);
        when(this.tareasService.save(any(Tarea.class))).thenReturn(tareaFake);


        ResponseEntity<Tarea> response = this.tareasController.update(tareaFake);

        Tarea tarea = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tareaFake, tarea);
    }

    @Test
    void updateFail() {
        Tarea tareaFake = new Tarea();
        when(this.tareasService.findById(anyString())).thenReturn(null);

        ResponseEntity<Tarea> response = this.tareasController.update(tareaFake);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deleteOk() {
        Tarea tareaFake = new Tarea();

        when(this.tareasService.remove(anyString())).thenReturn(true);

        ResponseEntity<Boolean> response = this.tareasController.delete(tareaFake.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Boolean.TRUE, response.getBody());
    }

    @Test
    void deleteException() {
        Tarea tareaFake = new Tarea();
        tareaFake.setId("a");

        when(this.tareasService.remove(anyString())).thenThrow(new NullPointerException());

        ResponseEntity<Boolean> response = this.tareasController.delete(tareaFake.getId());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(Boolean.FALSE, response.getBody());

    }
}