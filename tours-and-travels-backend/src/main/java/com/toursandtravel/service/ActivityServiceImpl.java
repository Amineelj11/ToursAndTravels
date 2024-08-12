package com.toursandtravel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toursandtravel.dao.ActivityDao;
import com.toursandtravel.entity.Activity;
import com.toursandtravel.entity.Address;
import com.toursandtravel.entity.Tour;

@Service
public class ActivityServiceImpl implements ActivityService {

	@Autowired
	private ActivityDao activityDao;
	
	@Override
	public List<Activity> addAllActivities(List<Activity> activities) {
		return activityDao.saveAll(activities);
	}

	@Override
	public List<Activity> updateAllActivities(List<Activity> activities) {
		return activityDao.saveAll(activities);
	}

	@Override
	public List<Activity> getByTour(Tour tour) {
		// TODO Auto-generated method stub
		return activityDao.findByTour(tour);
	}

	@Override
	public Activity addActivity(Activity activity) {
		// TODO Auto-generated method stub
		return activityDao.save(activity);
	}

	@Override
	public Activity getById(int activityId) {
		Optional<Activity> optionalAddress = activityDao.findById(activityId);

		if (optionalAddress.isPresent()) {
			return optionalAddress.get();
		} else {
			return null;
		}

	}

	@Override
	public void delete(Activity activity) {
		// TODO Auto-generated method stub
		this.activityDao.delete(activity);
	}

}
