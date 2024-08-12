package com.toursandtravel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toursandtravel.dao.TourDao;
import com.toursandtravel.entity.Location;
import com.toursandtravel.entity.Tour;
import com.toursandtravel.entity.User;

@Service
public class TourServiceImpl implements TourService {

	@Autowired
	private TourDao tourDao;

	@Override
	public Tour addTour(Tour tour) {
		// TODO Auto-generated method stub
		return tourDao.save(tour);
	}

	@Override
	public Tour updateTour(Tour tour) {
		// TODO Auto-generated method stub
		return tourDao.save(tour);
	}

	@Override
	public Tour getById(int tourId) {

		Optional<Tour> optionalTour = this.tourDao.findById(tourId);

		if (optionalTour.isEmpty()) {
			return null;
		}

		return optionalTour.get();
	}

	@Override
	public List<Tour> getAllStatusAndStartDate(String status, String startDate) {
		// TODO Auto-generated method stub
		return this.tourDao.findByStatusAndStartDateGreaterThan(status, startDate);
	}

	@Override
	public List<Tour> getAllByStatusAndFromAndToLocationAndStartDate(String status, Location fromLocation,
			Location toLocation, String startDate) {
		// TODO Auto-generated method stub
		return this.tourDao.findByStatusAndFromLocationAndToLocationAndStartDateGreaterThan(status, fromLocation,
				toLocation, startDate);
	}

	@Override
	public List<Tour> getAllStatusAndTourNameAndStartDate(String status, String tourName, String startDate) {
		// TODO Auto-generated method stub
		return this.tourDao.findByStatusAndNameContainingIgnoreCaseAndStartDateGreaterThan(status, tourName, startDate);
	}

	@Override
	public List<Tour> getAllByGuide(User guide) {
		// TODO Auto-generated method stub
		return this.tourDao.findByGuideOrderByIdDesc(guide);
	}

	@Override
	public List<Tour> getAllByStatus(String status) {
		// TODO Auto-generated method stub
		return this.tourDao.findByStatusOrderByIdDesc(status);
	}

}
