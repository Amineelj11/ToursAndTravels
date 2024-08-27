package com.toursandtravel.ressource;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import com.toursandtravel.resource.TransportResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.toursandtravel.dto.CommonApiResponse;
import com.toursandtravel.dto.TransportResponseDto;
import com.toursandtravel.entity.Transport;
import com.toursandtravel.exception.TransportSaveFailedException;
import com.toursandtravel.service.TransportService;
import com.toursandtravel.utility.Constants.ActiveStatus;

public class TransportResourceTest {

    @InjectMocks
    private TransportResource transportResource;

    @Mock
    private TransportService transportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddTransport_Success() {
        Transport transport = new Transport();
        transport.setStatus(ActiveStatus.ACTIVE.value());

        when(transportService.add(any(Transport.class))).thenReturn(transport);

        ResponseEntity<CommonApiResponse> response = transportResource.addTransport(transport);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Transport Added Successful", response.getBody().getResponseMessage());
    }

    @Test
    public void testAddTransport_NullTransport() {
        ResponseEntity<CommonApiResponse> response = transportResource.addTransport(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("missing input", response.getBody().getResponseMessage());
    }

    @Test
    public void testAddTransport_SaveFailed() {
        Transport transport = new Transport();
        when(transportService.add(any(Transport.class))).thenReturn(null);

        assertThrows(TransportSaveFailedException.class, () -> transportResource.addTransport(transport));
    }

    @Test
    public void testUpdateTransport_Success() {
        Transport transport = new Transport();
        transport.setId(1);
        transport.setStatus(ActiveStatus.ACTIVE.value());

        when(transportService.update(any(Transport.class))).thenReturn(transport);

        ResponseEntity<CommonApiResponse> response = transportResource.updateTransport(transport);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Transport Updated Successful", response.getBody().getResponseMessage());
    }

    @Test
    public void testUpdateTransport_NullTransport() {
        ResponseEntity<CommonApiResponse> response = transportResource.updateTransport(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("missing input", response.getBody().getResponseMessage());
    }

    @Test
    public void testUpdateTransport_MissingId() {
        Transport transport = new Transport();
        transport.setId(0);

        ResponseEntity<CommonApiResponse> response = transportResource.updateTransport(transport);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("missing transport Id", response.getBody().getResponseMessage());
    }

    @Test
    public void testUpdateTransport_SaveFailed() {
        Transport transport = new Transport();
        transport.setId(1);

        when(transportService.update(any(Transport.class))).thenReturn(null);

        assertThrows(TransportSaveFailedException.class, () -> transportResource.updateTransport(transport));
    }

    @Test
    public void testFetchAllTransport_Success() {
        List<Transport> transports = new ArrayList<>();
        transports.add(new Transport());

        when(transportService.getAllByStatus(ActiveStatus.ACTIVE.value())).thenReturn(transports);

        ResponseEntity<TransportResponseDto> response = transportResource.fetchAllTransport();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Transport fetched successful", response.getBody().getResponseMessage());
    }

    @Test
    public void testFetchAllTransport_NoTransportsFound() {
        when(transportService.getAllByStatus(ActiveStatus.ACTIVE.value())).thenReturn(new ArrayList<>());

        ResponseEntity<TransportResponseDto> response = transportResource.fetchAllTransport();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("No Transports found", response.getBody().getResponseMessage());
    }

    @Test
    public void testDeleteTransport_Success() {
        Transport transport = new Transport();
        transport.setId(1);
        transport.setStatus(ActiveStatus.ACTIVE.value());

        when(transportService.getById(1)).thenReturn(transport);
        when(transportService.update(any(Transport.class))).thenReturn(transport);

        ResponseEntity<CommonApiResponse> response = transportResource.deleteTransport(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Transport Deleted Successful", response.getBody().getResponseMessage());
    }

    @Test
    public void testDeleteTransport_MissingId() {
        ResponseEntity<CommonApiResponse> response = transportResource.deleteTransport(0);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("missing transport Id", response.getBody().getResponseMessage());
    }

    @Test
    public void testDeleteTransport_TransportNotFound() {
        when(transportService.getById(1)).thenReturn(null);

        ResponseEntity<CommonApiResponse> response = transportResource.deleteTransport(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("transport not found", response.getBody().getResponseMessage());
    }

    @Test
    public void testDeleteTransport_SaveFailed() {
        Transport transport = new Transport();
        transport.setId(1);

        when(transportService.getById(1)).thenReturn(transport);
        when(transportService.update(any(Transport.class))).thenReturn(null);

        assertThrows(TransportSaveFailedException.class, () -> transportResource.deleteTransport(1));
    }
}
