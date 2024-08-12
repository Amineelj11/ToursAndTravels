package com.toursandtravel.resource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

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
import com.toursandtravel.utility.Helper;

@Component
public class BookingResource {

	private final Logger LOG = LoggerFactory.getLogger(BookingResource.class);

	@Autowired
	private BookingService bookingService;

	@Autowired
	private TourService tourService;

	@Autowired
	private UserService userService;

	@Autowired
	private PaymentService paymentService;

	public ResponseEntity<CommonApiResponse> addBooking(BookingRequestDto request) {

		LOG.info("request received for adding customer booking");

		CommonApiResponse response = new CommonApiResponse();

		String bookingTime = String
				.valueOf(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

		if (request == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getTourId() == 0 || request.getCustomerId() == 0 || request.getNoOfTickets() == 0
				|| request.getCvv() == null || request.getExpiryDate() == null || request.getNameOnCard() == null
				|| request.getCardNo() == null) {

			response.setResponseMessage("missing input or invalid details");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);

		}

		User customer = this.userService.getUserById(request.getCustomerId());

		if (customer == null) {
			response.setResponseMessage("customer not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		Tour tour = this.tourService.getById(request.getTourId());

		if (tour == null) {
			response.setResponseMessage("tour not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		int tourAvailableTickets = tour.getAvailableTickets();

		int noOfTicketsToBook = request.getNoOfTickets();

		if (noOfTicketsToBook > tourAvailableTickets) {
			response.setResponseMessage("Only " + tourAvailableTickets + " left for the Tour!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		BigDecimal tourTicketPrice = tour.getTicketPrice();

		BigDecimal totalAmountToPay = tourTicketPrice.multiply(new BigDecimal(noOfTicketsToBook));

		String bookingId = Helper.generateTourBookingId();
		String paymentBookingId = Helper.generateBookingPaymentId();

		Payment payment = new Payment();
		payment.setCardNo(request.getCardNo());
		payment.setBookingId(bookingId);
		payment.setAmount(totalAmountToPay);
		payment.setCustomer(customer);
		payment.setCvv(request.getCvv());
		payment.setExpiryDate(request.getExpiryDate());
		payment.setNameOnCard(request.getNameOnCard());
		payment.setPaymentId(paymentBookingId);

		Payment savedPayment = this.paymentService.addPayment(payment);

		if (savedPayment == null) {
			response.setResponseMessage("Failed to Book Event, Payment Failure!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		Booking booking = new Booking();
		booking.setBookingId(bookingId);
		booking.setPayment(savedPayment);
		booking.setAmount(totalAmountToPay);
		booking.setBookingTime(bookingTime);
		booking.setCustomer(customer);
		booking.setTour(tour);
		booking.setNoOfTickets(noOfTicketsToBook);
		booking.setStatus(BookingStatus.CONFIRMED.value());

		Booking savedBooking = this.bookingService.addBooking(booking);

		if (savedBooking == null) {
			response.setResponseMessage("Failed to Book Event, Internal Error");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		tour.setAvailableTickets(tour.getAvailableTickets() - noOfTicketsToBook);
		this.tourService.updateTour(tour);

		response.setResponseMessage("Your Booking is Confirmed, Booking ID: " + bookingId);
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);

	}

	public ResponseEntity<BookingResponseDto> fetchAllBookings() {

		BookingResponseDto response = new BookingResponseDto();

		List<Booking> bookings = this.bookingService.getAllBookings();

		if (CollectionUtils.isEmpty(bookings)) {
			response.setResponseMessage("Bookings not found");
			response.setSuccess(false);

			return new ResponseEntity<BookingResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		response.setBookings(bookings);
		response.setResponseMessage("Booking fetched successful!!");
		response.setSuccess(true);

		return new ResponseEntity<BookingResponseDto>(response, HttpStatus.OK);
	}

	public ResponseEntity<BookingResponseDto> fetchAllBookingsByTour(Integer tourId) {

		BookingResponseDto response = new BookingResponseDto();

		if (tourId == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<BookingResponseDto>(response, HttpStatus.BAD_REQUEST);

		}

		Tour tour = this.tourService.getById(tourId);

		if (tour == null) {
			response.setResponseMessage("tour not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<BookingResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		List<Booking> bookings = this.bookingService.getByTour(tour);

		if (CollectionUtils.isEmpty(bookings)) {
			response.setResponseMessage("Bookings not found");
			response.setSuccess(false);

			return new ResponseEntity<BookingResponseDto>(response, HttpStatus.OK);
		}

		response.setBookings(bookings);
		response.setResponseMessage("Booking fetched successful!!");
		response.setSuccess(true);

		return new ResponseEntity<BookingResponseDto>(response, HttpStatus.OK);
	}

	public ResponseEntity<BookingResponseDto> fetchAllBookingsByCustomer(Integer customerId) {

		BookingResponseDto response = new BookingResponseDto();

		if (customerId == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<BookingResponseDto>(response, HttpStatus.BAD_REQUEST);

		}

		User customer = this.userService.getUserById(customerId);

		if (customer == null) {
			response.setResponseMessage("customer not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<BookingResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		List<Booking> bookings = this.bookingService.getBookingByCustomer(customer);

		if (CollectionUtils.isEmpty(bookings)) {
			response.setResponseMessage("Bookings not found");
			response.setSuccess(false);

			return new ResponseEntity<BookingResponseDto>(response, HttpStatus.OK);
		}

		response.setBookings(bookings);
		response.setResponseMessage("Booking fetched successful!!");
		response.setSuccess(true);

		return new ResponseEntity<BookingResponseDto>(response, HttpStatus.OK);
	}

	public ResponseEntity<BookingResponseDto> fetchAllBookingsByTourGuideId(Integer tourGuideId) {

		BookingResponseDto response = new BookingResponseDto();

		if (tourGuideId == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<BookingResponseDto>(response, HttpStatus.BAD_REQUEST);

		}

		User tourGuide = this.userService.getUserById(tourGuideId);

		if (tourGuide == null) {
			response.setResponseMessage("tour guide not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<BookingResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		List<Booking> bookings = this.bookingService.getByTourGuide(tourGuide);

		if (CollectionUtils.isEmpty(bookings)) {
			response.setResponseMessage("Bookings not found");
			response.setSuccess(false);

			return new ResponseEntity<BookingResponseDto>(response, HttpStatus.OK);
		}

		response.setBookings(bookings);
		response.setResponseMessage("Booking fetched successful!!");
		response.setSuccess(true);

		return new ResponseEntity<BookingResponseDto>(response, HttpStatus.OK);
	}

}
