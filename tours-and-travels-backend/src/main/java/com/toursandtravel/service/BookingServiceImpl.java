package com.toursandtravel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toursandtravel.dao.BookingDao;
import com.toursandtravel.entity.Booking;
import com.toursandtravel.entity.Tour;
import com.toursandtravel.entity.User;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingDao bookingDao;

	@Override
	public Booking addBooking(Booking booking) {
		// TODO Auto-generated method stub
		return bookingDao.save(booking);
	}

	@Override
	public Booking updateBooking(Booking booking) {
		// TODO Auto-generated method stub
		return bookingDao.save(booking);
	}

	@Override
	public Booking getById(int bookingId) {
		Optional<Booking> optionalBooking = bookingDao.findById(bookingId);

		if (optionalBooking.isPresent()) {
			return optionalBooking.get();
		} else {
			return null;
		}

	}

	@Override
	public Booking findByBookingId(String bookingId) {
		// TODO Auto-generated method stub
		return this.bookingDao.findByBookingId(bookingId);
	}

	@Override
	public List<Booking> getBookingByCustomer(User customer) {
		// TODO Auto-generated method stub
		return bookingDao.findByCustomer(customer);
	}

	@Override
	public List<Booking> getByTourGuide(User tourGuide) {
		// TODO Auto-generated method stub
		return bookingDao.findAllBookingsByTourGuide(tourGuide);
	}

	@Override
	public List<Booking> getByTour(Tour tour) {
		// TODO Auto-generated method stub
		return bookingDao.findByTour(tour);
	}

	@Override
	public List<Booking> getAllBookings() {
		// TODO Auto-generated method stub
		return bookingDao.findAll();
	}

}
