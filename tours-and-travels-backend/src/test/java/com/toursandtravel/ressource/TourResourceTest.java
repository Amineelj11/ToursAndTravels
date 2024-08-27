package com.toursandtravel.ressource;

import com.toursandtravel.dto.AddTourRequest;
import com.toursandtravel.dto.CommonApiResponse;
import com.toursandtravel.entity.*;
import com.toursandtravel.resource.TourResource;
import com.toursandtravel.service.*;
import com.toursandtravel.utility.Constants.ActiveStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TourResourceTest {

    @InjectMocks
    private TourResource tourResource;

    @Mock
    private LocationService locationService;

    @Mock
    private TourService tourService;

    @Mock
    private TransportService transportService;

    @Mock
    private LodgingService lodgingService;

    @Mock
    private UserService userService;

    @Mock
    private StorageService storageService;

    @Mock
    private ActivityService activityService;

    @Mock
    private MealService mealService;

    @Mock
    private TypeService typeProgService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

  /*  @Test
    public void testAddTour_Success() {
        AddTourRequest request = new AddTourRequest();
        request.setFromLocationId(1);
        request.setToLocationId(2);
        request.setStartDate("1700000000000");  // Future date
        request.setEndDate("1700000000001");
        request.setTransportId(1);
        request.setTypeProgId(1);
        request.setLodgingId(1);
        request.setGuideId(1);
        request.setTotalTickets(10);

        Location fromLocation = new Location();
        fromLocation.setId(1);
        fromLocation.setStatus(ActiveStatus.ACTIVE.value());

        Location toLocation = new Location();
        toLocation.setId(2);
        toLocation.setStatus(ActiveStatus.ACTIVE.value());

        Transport transport = new Transport();
        transport.setId(1);
        transport.setStatus(ActiveStatus.ACTIVE.value());

        Type typeProg = new Type();
        typeProg.setId(1);
        typeProg.setStatus(ActiveStatus.ACTIVE.value());

        Lodging lodging = new Lodging();
        lodging.setId(1);
        lodging.setStatus(ActiveStatus.ACTIVE.value());

        User guide = new User();
        guide.setId(1);
        guide.setStatus(ActiveStatus.ACTIVE.value());

        when(locationService.getById(1)).thenReturn(fromLocation);
        when(locationService.getById(2)).thenReturn(toLocation);
        when(transportService.getById(1)).thenReturn(transport);
        when(typeProgService.getById(1)).thenReturn(typeProg);
        when(lodgingService.getById(1)).thenReturn(lodging);
        when(userService.getUserById(1)).thenReturn(guide);

        when(storageService.store(any())).thenReturn("image1.jpg");
        when(tourService.addTour(any(Tour.class))).thenReturn(new Tour());

        ResponseEntity<CommonApiResponse> response = tourResource.addTour(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Tour Added Successful!!!", response.getBody().getResponseMessage());
    }*/

    @Test
    public void testAddTour_MissingRequestBody() {
        ResponseEntity<CommonApiResponse> response = tourResource.addTour(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("missing request body", response.getBody().getResponseMessage());
    }

    @Test
    public void testAddTour_InvalidLocation() {
        AddTourRequest request = new AddTourRequest();
        request.setFromLocationId(1);
        request.setToLocationId(1);  // Same location

        ResponseEntity<CommonApiResponse> response = tourResource.addTour(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("From & To Loction should be different!!!", response.getBody().getResponseMessage());
    }



    // Add more tests for other edge cases and service method calls as needed
}
