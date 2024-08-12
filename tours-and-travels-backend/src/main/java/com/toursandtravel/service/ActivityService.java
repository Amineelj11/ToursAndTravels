package com.toursandtravel.service;

import java.util.List;

import com.toursandtravel.entity.Activity;
import com.toursandtravel.entity.Tour;

public interface ActivityService {

	List<Activity> addAllActivities(List<Activity> activities);

	List<Activity> updateAllActivities(List<Activity> activities);

	List<Activity> getByTour(Tour tour);

	Activity addActivity(Activity activity);
	
	Activity getById(int activityId);
	
	void delete(Activity activity);

}
