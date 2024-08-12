package com.toursandtravel.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.toursandtravel.entity.Booking;
import com.toursandtravel.entity.Tour;
import com.toursandtravel.entity.User;

@Repository
public interface BookingDao extends JpaRepository<Booking, Integer> {

	Booking findByBookingId(String bookingId);

	List<Booking> findByCustomer(User customer);

	@Query("SELECT b FROM Booking b WHERE b.tour.guide = :guide")
	List<Booking> findAllBookingsByTourGuide(@Param("guide") User guide);

	List<Booking> findByTour(Tour tour);

}
