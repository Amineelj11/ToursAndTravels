package com.toursandtravel.service;

import java.util.List;

import com.toursandtravel.entity.Location;
import com.toursandtravel.entity.Tour;
import com.toursandtravel.entity.User;

public interface TourService {

	Tour addTour(Tour tour);

	Tour updateTour(Tour tour);

	Tour getById(int tourId);

	List<Tour> getAllStatusAndStartDate(String status, String startDate);

	List<Tour> getAllByStatusAndFromAndToLocationAndStartDate(String status, Location fromLocation, Location toLocation,
			String startDate);

	List<Tour> getAllStatusAndTourNameAndStartDate(String status, String tourName, String startDate);

	List<Tour> getAllByGuide(User guide);

	List<Tour> getAllByStatus(String status);
	
}
