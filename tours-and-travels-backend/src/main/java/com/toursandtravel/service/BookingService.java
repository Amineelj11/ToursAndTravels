package com.toursandtravel.service;

import java.util.List;

import com.toursandtravel.entity.Booking;
import com.toursandtravel.entity.Tour;
import com.toursandtravel.entity.User;

public interface BookingService {

	Booking addBooking(Booking booking);

	Booking updateBooking(Booking booking);

	Booking getById(int bookingId);

	Booking findByBookingId(String bookingId);

	List<Booking> getBookingByCustomer(User customer);

	List<Booking> getByTourGuide(User tourGuide);

	List<Booking> getByTour(Tour tour);

	List<Booking> getAllBookings();

}
