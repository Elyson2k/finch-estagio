package com.finchproject.estagio.controller.exceptions;

import com.finchproject.estagio.service.exceptions.ProductExceptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ResourceExceptionHandlerTest {

    @InjectMocks
    private ResourceExceptionHandler resourceExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // =======================================================================================================================
    // =================================================T E S T E 1 =======================================================
    // =======================================================================================================================

    @Test
    public void testMethodTypeMismatch() {

        ResponseEntity<StandardError> response = resourceExceptionHandler
                .methodTypeMismatch(new MethodArgumentTypeMismatchException(null, null, null, null, null), new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertEquals("Error: Marketplace not fined", response.getBody().getMsg());
    }

    // =======================================================================================================================
    // =================================================T E S T E 2 =======================================================
    // =======================================================================================================================

    @Test
    @DisplayName("Product não encontrado")
    void empty() {
        ResponseEntity<StandardError> response = resourceExceptionHandler
                .empty(new ProductExceptions("Error: The product sought was not found") , new MockHttpServletRequest());
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals("Error: The product sought was not found", response.getBody().getMsg());
    }

    // =======================================================================================================================
    // =================================================T E S T E 3 =======================================================
    // =======================================================================================================================

    @Test
    public void testNoSuchBean() {
        ResponseEntity<StandardError> response = resourceExceptionHandler
                .noSuchBean(new NoSuchBeanDefinitionException("Text") , new MockHttpServletRequest());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertEquals("Error: Marketplace not fined", response.getBody().getMsg());
    }

    // =======================================================================================================================
    // =================================================T E S T E 4 =======================================================
    // =======================================================================================================================



    @Test
    public void testMissingServletRequest() {
        ResponseEntity<StandardError> response = resourceExceptionHandler
                .missingServletRequest(new MissingServletRequestParameterException(null, null), new MockHttpServletRequest());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertEquals("Error: Some URL field that was to be a given is wrong.", response.getBody().getMsg());
    }

}