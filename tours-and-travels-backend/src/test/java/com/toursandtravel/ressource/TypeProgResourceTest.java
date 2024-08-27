package com.toursandtravel.ressource;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import com.toursandtravel.resource.TypeProgResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.toursandtravel.dto.CommonApiResponse;
import com.toursandtravel.dto.TypeResponseDto;
import com.toursandtravel.entity.Type;
import com.toursandtravel.exception.TransportSaveFailedException;
import com.toursandtravel.service.TypeService;
import com.toursandtravel.utility.Constants;

public class TypeProgResourceTest {

    @InjectMocks
    private TypeProgResource typeProgResource;

    @Mock
    private TypeService typeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddTransport_Success() {
        Type type = new Type();
        type.setStatus(Constants.ActiveStatus.ACTIVE.value());

        when(typeService.add(any(Type.class))).thenReturn(type);

        ResponseEntity<CommonApiResponse> response = typeProgResource.addTransport(type);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Type Added Successful", response.getBody().getResponseMessage());
    }

    @Test
    public void testAddTransport_NullType() {
        ResponseEntity<CommonApiResponse> response = typeProgResource.addTransport(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("missing input", response.getBody().getResponseMessage());
    }

    @Test
    public void testAddTransport_SaveFailed() {
        Type type = new Type();
        when(typeService.add(any(Type.class))).thenReturn(null);

        assertThrows(TransportSaveFailedException.class, () -> typeProgResource.addTransport(type));
    }

    @Test
    public void testUpdateTransport_Success() {
        Type type = new Type();
        type.setId(1);
        type.setStatus(Constants.ActiveStatus.ACTIVE.value());

        when(typeService.update(any(Type.class))).thenReturn(type);

        ResponseEntity<CommonApiResponse> response = typeProgResource.updateTransport(type);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Type Updated Successful", response.getBody().getResponseMessage());
    }

    @Test
    public void testUpdateTransport_NullType() {
        ResponseEntity<CommonApiResponse> response = typeProgResource.updateTransport(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("missing input", response.getBody().getResponseMessage());
    }

    @Test
    public void testUpdateTransport_MissingId() {
        Type type = new Type();
        type.setId(0);

        ResponseEntity<CommonApiResponse> response = typeProgResource.updateTransport(type);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("missing Type Id", response.getBody().getResponseMessage());
    }

    @Test
    public void testUpdateTransport_SaveFailed() {
        Type type = new Type();
        type.setId(1);

        when(typeService.update(any(Type.class))).thenReturn(null);

        assertThrows(TransportSaveFailedException.class, () -> typeProgResource.updateTransport(type));
    }

    @Test
    public void testFetchAllTransport_Success() {
        List<Type> types = new ArrayList<>();
        types.add(new Type());

        when(typeService.getAllByStatus(Constants.ActiveStatus.ACTIVE.value())).thenReturn(types);

        ResponseEntity<TypeResponseDto> response = typeProgResource.fetchAllTransport();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Type fetched successful", response.getBody().getResponseMessage());
    }

    @Test
    public void testFetchAllTransport_NoTypesFound() {
        when(typeService.getAllByStatus(Constants.ActiveStatus.ACTIVE.value())).thenReturn(new ArrayList<>());

        ResponseEntity<TypeResponseDto> response = typeProgResource.fetchAllTransport();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("No Types found", response.getBody().getResponseMessage());
    }

    @Test
    public void testDeleteTransport_Success() {
        Type type = new Type();
        type.setId(1);
        type.setStatus(Constants.ActiveStatus.ACTIVE.value());

        when(typeService.getById(1)).thenReturn(type);
        when(typeService.update(any(Type.class))).thenReturn(type);

        ResponseEntity<CommonApiResponse> response = typeProgResource.deleteTransport(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Type Deleted Successful", response.getBody().getResponseMessage());
    }

    @Test
    public void testDeleteTransport_MissingId() {
        ResponseEntity<CommonApiResponse> response = typeProgResource.deleteTransport(0);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("missing Type Id", response.getBody().getResponseMessage());
    }

    @Test
    public void testDeleteTransport_TypeNotFound() {
        when(typeService.getById(1)).thenReturn(null);

        ResponseEntity<CommonApiResponse> response = typeProgResource.deleteTransport(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Type not found", response.getBody().getResponseMessage());
    }

    @Test
    public void testDeleteTransport_SaveFailed() {
        Type type = new Type();
        type.setId(1);

        when(typeService.getById(1)).thenReturn(type);
        when(typeService.update(any(Type.class))).thenReturn(null);

        assertThrows(TransportSaveFailedException.class, () -> typeProgResource.deleteTransport(1));
    }
}
