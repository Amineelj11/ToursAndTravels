package com.toursandtravel.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toursandtravel.entity.Location;
import com.toursandtravel.entity.Tour;
import com.toursandtravel.entity.User;

@Repository
public interface TourDao extends JpaRepository<Tour, Integer> {

	List<Tour> findByStatusOrderByIdDesc(String status);

	List<Tour> findByStatusAndStartDateGreaterThan(String status, String startDate);

	List<Tour> findByStatusAndFromLocationAndToLocationAndStartDateGreaterThan(String status, Location fromLocation,
			Location toLocation, String startDate);

	List<Tour> findByStatusAndNameContainingIgnoreCaseAndStartDateGreaterThan(String status, String name,
			String startDate);

	List<Tour> findByGuideOrderByIdDesc(User guide);

}
