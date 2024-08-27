package com.toursandtravel.ressource;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.toursandtravel.resource.BookingResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.toursandtravel.dto.BookingRequestDto;
import com.toursandtravel.dto.BookingResponseDto;
import com.toursandtravel.dto.CommonApiResponse;
import com.toursandtravel.entity.Booking;
import com.toursandtravel.entity.Payment;
import com.toursandtravel.entity.Tour;
import com.toursandtravel.entity.User;
import com.toursandtravel.service.BookingService;
import com.toursandtravel.service.PaymentService;
import com.toursandtravel.service.TourService;
import com.toursandtravel.service.UserService;
import com.toursandtravel.utility.Constants.BookingStatus;

class BookingResourceTest {

    @Mock
    private BookingService bookingService;

    @Mock
    private TourService tourService;

    @Mock
    private UserService userService;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private BookingResource bookingResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddBooking_Success() {
        BookingRequestDto request = new BookingRequestDto();
        request.setTourId(1);
        request.setCustomerId(1);
        request.setNoOfTickets(2);
        request.setCardNo("1234567890123456");
        request.setCvv("123");
        request.setExpiryDate(String.valueOf(LocalDate.now().plusYears(1)));
        request.setNameOnCard("John Doe");

        User customer = new User();
        Tour tour = new Tour();
        tour.setAvailableTickets(5);
        tour.setTicketPrice(BigDecimal.valueOf(100));

        Payment payment = new Payment();
        Booking booking = new Booking();

        when(userService.getUserById(request.getCustomerId())).thenReturn(customer);
        when(tourService.getById(request.getTourId())).thenReturn(tour);
        when(paymentService.addPayment(any(Payment.class))).thenReturn(payment);
        when(bookingService.addBooking(any(Booking.class))).thenReturn(booking);

        ResponseEntity<CommonApiResponse> response = bookingResource.addBooking(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
    }

    @Test
    void testAddBooking_MissingInput() {
        BookingRequestDto request = new BookingRequestDto();

        ResponseEntity<CommonApiResponse> response = bookingResource.addBooking(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("missing input or invalid details", response.getBody().getResponseMessage());
    }

    @Test
    void testFetchAllBookings_Success() {
        Booking booking = new Booking();
        List<Booking> bookings = Arrays.asList(booking);

        when(bookingService.getAllBookings()).thenReturn(bookings);

        ResponseEntity<BookingResponseDto> response = bookingResource.fetchAllBookings();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertFalse(response.getBody().getBookings().isEmpty());
    }

    @Test
    void testFetchAllBookings_NoBookingsFound() {
        when(bookingService.getAllBookings()).thenReturn(Collections.emptyList());

        ResponseEntity<BookingResponseDto> response = bookingResource.fetchAllBookings();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Bookings not found", response.getBody().getResponseMessage());
    }

    @Test
    void testFetchAllBookingsByTour_Success() {
        Integer tourId = 1;
        Tour tour = new Tour();
        Booking booking = new Booking();
        List<Booking> bookings = Arrays.asList(booking);

        when(tourService.getById(tourId)).thenReturn(tour);
        when(bookingService.getByTour(tour)).thenReturn(bookings);

        ResponseEntity<BookingResponseDto> response = bookingResource.fetchAllBookingsByTour(tourId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertFalse(response.getBody().getBookings().isEmpty());
    }

    @Test
    void testFetchAllBookingsByTour_NoBookingsFound() {
        Integer tourId = 1;
        Tour tour = new Tour();

        when(tourService.getById(tourId)).thenReturn(tour);
        when(bookingService.getByTour(tour)).thenReturn(Collections.emptyList());

        ResponseEntity<BookingResponseDto> response = bookingResource.fetchAllBookingsByTour(tourId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertTrue(response.getBody().getBookings().isEmpty());
        assertEquals("Bookings not found", response.getBody().getResponseMessage());
    }

    @Test
    void testFetchAllBookingsByCustomer_Success() {
        Integer customerId = 1;
        User customer = new User();
        Booking booking = new Booking();
        List<Booking> bookings = Arrays.asList(booking);

        when(userService.getUserById(customerId)).thenReturn(customer);
        when(bookingService.getBookingByCustomer(customer)).thenReturn(bookings);

        ResponseEntity<BookingResponseDto> response = bookingResource.fetchAllBookingsByCustomer(customerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertFalse(response.getBody().getBookings().isEmpty());
    }

    @Test
    void testFetchAllBookingsByCustomer_CustomerNotFound() {
        Integer customerId = 1;

        when(userService.getUserById(customerId)).thenReturn(null);

        ResponseEntity<BookingResponseDto> response = bookingResource.fetchAllBookingsByCustomer(customerId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("customer not found!!!", response.getBody().getResponseMessage());
    }

    @Test
    void testFetchAllBookingsByCustomer_NoBookingsFound() {
        Integer customerId = 1;
        User customer = new User();

        when(userService.getUserById(customerId)).thenReturn(customer);
        when(bookingService.getBookingByCustomer(customer)).thenReturn(Collections.emptyList());

        ResponseEntity<BookingResponseDto> response = bookingResource.fetchAllBookingsByCustomer(customerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertTrue(response.getBody().getBookings().isEmpty());
        assertEquals("Bookings not found", response.getBody().getResponseMessage());
    }

    @Test
    void testFetchAllBookingsByTourGuideId_Success() {
        Integer tourGuideId = 1;
        User tourGuide = new User();
        Booking booking = new Booking();
        List<Booking> bookings = Arrays.asList(booking);

        when(userService.getUserById(tourGuideId)).thenReturn(tourGuide);
        when(bookingService.getByTourGuide(tourGuide)).thenReturn(bookings);

        ResponseEntity<BookingResponseDto> response = bookingResource.fetchAllBookingsByTourGuideId(tourGuideId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertFalse(response.getBody().getBookings().isEmpty());
    }

    @Test
    void testFetchAllBookingsByTourGuideId_TourGuideNotFound() {
        Integer tourGuideId = 1;

        when(userService.getUserById(tourGuideId)).thenReturn(null);

        ResponseEntity<BookingResponseDto> response = bookingResource.fetchAllBookingsByTourGuideId(tourGuideId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("tour guide not found!!!", response.getBody().getResponseMessage());
    }

    @Test
    void testFetchAllBookingsByTourGuideId_NoBookingsFound() {
        Integer tourGuideId = 1;
        User tourGuide = new User();

        when(userService.getUserById(tourGuideId)).thenReturn(tourGuide);
        when(bookingService.getByTourGuide(tourGuide)).thenReturn(Collections.emptyList());

        ResponseEntity<BookingResponseDto> response = bookingResource.fetchAllBookingsByTourGuideId(tourGuideId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertTrue(response.getBody().getBookings().isEmpty());
        assertEquals("Bookings not found", response.getBody().getResponseMessage());
    }

    // Additional tests can be added for other methods
}
