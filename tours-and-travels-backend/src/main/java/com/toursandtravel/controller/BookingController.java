package com.toursandtravel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toursandtravel.dto.BookingRequestDto;
import com.toursandtravel.dto.BookingResponseDto;
import com.toursandtravel.dto.CommonApiResponse;
import com.toursandtravel.resource.BookingResource;

@RestController
@RequestMapping("api/tour/booking")
@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {

	@Autowired
	private BookingResource bookingResource;

	@PostMapping("add")
	public ResponseEntity<CommonApiResponse> addEvent(@RequestBody BookingRequestDto request) {
		return this.bookingResource.addBooking(request);
	}

	@GetMapping("fetch/all")
	public ResponseEntity<BookingResponseDto> fetchAllBookings() {
		return this.bookingResource.fetchAllBookings();
	}

	@GetMapping("fetch/tour-wise")
	public ResponseEntity<BookingResponseDto> fetchAllBookingsByTour(@RequestParam("tourId") Integer tourId) {
		return this.bookingResource.fetchAllBookingsByTour(tourId);
	}

	@GetMapping("fetch/customer-wise")
	public ResponseEntity<BookingResponseDto> fetchAllBookingsByCustomer(
			@RequestParam("customerId") Integer customerId) {
		return this.bookingResource.fetchAllBookingsByCustomer(customerId);
	}

	@GetMapping("fetch/guide-wise")
	public ResponseEntity<BookingResponseDto> fetchAllBookingsByTourGuideId(
			@RequestParam("tourGuideId") Integer tourGuideId) {
		return this.bookingResource.fetchAllBookingsByTourGuideId(tourGuideId);
	}

}
