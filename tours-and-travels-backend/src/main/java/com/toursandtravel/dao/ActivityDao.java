package com.toursandtravel.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toursandtravel.entity.Activity;
import com.toursandtravel.entity.Tour;

@Repository
public interface ActivityDao extends JpaRepository<Activity, Integer> {

	List<Activity> findByTour(Tour tour);
	
}
