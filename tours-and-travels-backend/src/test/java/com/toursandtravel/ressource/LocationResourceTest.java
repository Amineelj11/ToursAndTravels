package com.toursandtravel.ressource;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toursandtravel.resource.LocationResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.toursandtravel.dto.CommonApiResponse;
import com.toursandtravel.dto.LocationResponseDto;
import com.toursandtravel.entity.Location;
import com.toursandtravel.exception.LocationSaveFailedException;
import com.toursandtravel.service.LocationService;
import com.toursandtravel.utility.Constants.ActiveStatus;

import java.util.Collections;
import java.util.List;

public class LocationResourceTest {

    @InjectMocks
    private LocationResource locationResource;

    @Mock
    private LocationService locationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddLocation_Success() {
        Location location = new Location();
        location.setStatus(ActiveStatus.ACTIVE.value());

        when(locationService.add(any(Location.class))).thenReturn(location);

        ResponseEntity<CommonApiResponse> responseEntity = locationResource.addLocation(location);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isSuccess());
        assertEquals("Location Added Successful", responseEntity.getBody().getResponseMessage());
    }

    @Test
    void testAddLocation_Failure_NullLocation() {
        ResponseEntity<CommonApiResponse> responseEntity = locationResource.addLocation(null);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertFalse(responseEntity.getBody().isSuccess());
        assertEquals("missing input", responseEntity.getBody().getResponseMessage());
    }

    @Test
    void testAddLocation_Failure_SaveFailed() {
        Location location = new Location();
        when(locationService.add(any(Location.class))).thenReturn(null);

        assertThrows(LocationSaveFailedException.class, () -> {
            locationResource.addLocation(location);
        });
    }

    @Test
    void testUpdateLocation_Success() {
        Location location = new Location();
        location.setId(1);
        location.setStatus(ActiveStatus.ACTIVE.value());

        when(locationService.update(any(Location.class))).thenReturn(location);

        ResponseEntity<CommonApiResponse> responseEntity = locationResource.updateLocation(location);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isSuccess());
        assertEquals("Location Updated Successful", responseEntity.getBody().getResponseMessage());
    }

    @Test
    void testUpdateLocation_Failure_NullLocation() {
        ResponseEntity<CommonApiResponse> responseEntity = locationResource.updateLocation(null);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertFalse(responseEntity.getBody().isSuccess());
        assertEquals("missing input", responseEntity.getBody().getResponseMessage());
    }

    @Test
    void testUpdateLocation_Failure_MissingId() {
        Location location = new Location();

        ResponseEntity<CommonApiResponse> responseEntity = locationResource.updateLocation(location);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertFalse(responseEntity.getBody().isSuccess());
        assertEquals("missing location Id", responseEntity.getBody().getResponseMessage());
    }

    @Test
    void testUpdateLocation_Failure_SaveFailed() {
        Location location = new Location();
        location.setId(1);

        when(locationService.update(any(Location.class))).thenReturn(null);

        assertThrows(LocationSaveFailedException.class, () -> {
            locationResource.updateLocation(location);
        });
    }

    @Test
    void testFetchAllLocation_Success() {
        Location location = new Location();
        List<Location> locations = Collections.singletonList(location);

        when(locationService.getAllByStatus(ActiveStatus.ACTIVE.value())).thenReturn(locations);

        ResponseEntity<LocationResponseDto> responseEntity = locationResource.fetchAllLocation();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isSuccess());
        assertEquals(locations, responseEntity.getBody().getLocations());
        assertEquals("Location fetched successful", responseEntity.getBody().getResponseMessage());
    }

    @Test
    void testFetchAllLocation_Failure_NoLocations() {
        when(locationService.getAllByStatus(ActiveStatus.ACTIVE.value())).thenReturn(Collections.emptyList());

        ResponseEntity<LocationResponseDto> responseEntity = locationResource.fetchAllLocation();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertFalse(responseEntity.getBody().isSuccess());
        assertEquals("No Locations found", responseEntity.getBody().getResponseMessage());
    }

    @Test
    void testDeleteLocation_Success() {
        Location location = new Location();
        location.setId(1);
        location.setStatus(ActiveStatus.ACTIVE.value());

        when(locationService.getById(anyInt())).thenReturn(location);
        when(locationService.update(any(Location.class))).thenReturn(location);

        ResponseEntity<CommonApiResponse> responseEntity = locationResource.deleteLocation(1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isSuccess());
        assertEquals("Location Deleted Successful", responseEntity.getBody().getResponseMessage());
    }

    @Test
    void testDeleteLocation_Failure_MissingId() {
        ResponseEntity<CommonApiResponse> responseEntity = locationResource.deleteLocation(0);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertFalse(responseEntity.getBody().isSuccess());
        assertEquals("missing location Id", responseEntity.getBody().getResponseMessage());
    }

    @Test
    void testDeleteLocation_Failure_LocationNotFound() {
        when(locationService.getById(anyInt())).thenReturn(null);

        ResponseEntity<CommonApiResponse> responseEntity = locationResource.deleteLocation(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertFalse(responseEntity.getBody().isSuccess());
        assertEquals("location not found", responseEntity.getBody().getResponseMessage());
    }

    @Test
    void testDeleteLocation_Failure_SaveFailed() {
        Location location = new Location();
        location.setId(1);

        when(locationService.getById(anyInt())).thenReturn(location);
        when(locationService.update(any(Location.class))).thenReturn(null);

        assertThrows(LocationSaveFailedException.class, () -> {
            locationResource.deleteLocation(1);
        });
    }
}
