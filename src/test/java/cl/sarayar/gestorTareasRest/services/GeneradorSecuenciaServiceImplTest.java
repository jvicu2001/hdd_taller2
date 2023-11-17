package cl.sarayar.gestorTareasRest.services;

import cl.sarayar.gestorTareasRest.entities.Secuencia;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class GeneradorSecuenciaServiceImplTest {

    @Mock
    private MongoOperations mongoOperationsMock;

    private GeneradorSecuenciaService generadorSecuenciaService;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        generadorSecuenciaService = new GeneradorSecuenciaServiceImpl(mongoOperationsMock);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void generadorSecuenciaOk() {
        Secuencia secuenciaFake = new Secuencia();

        when(this.mongoOperationsMock.findAndModify(any(Query.class), any(UpdateDefinition.class), any(FindAndModifyOptions.class), eq(Secuencia.class))).thenReturn(secuenciaFake);

        long secuencia = this.generadorSecuenciaService.generadorSecuencia(""); // Me tira error con anyString()
        assertEquals(secuencia, secuenciaFake.getSeq());
    }

    @Test
    void generadorSecuenciaNull() {

        long secuencia = this.generadorSecuenciaService.generadorSecuencia("");
        assertEquals(1, secuencia);
    }
}