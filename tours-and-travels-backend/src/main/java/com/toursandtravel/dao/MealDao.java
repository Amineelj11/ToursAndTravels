package com.toursandtravel.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toursandtravel.entity.Meal;
import com.toursandtravel.entity.Tour;

@Repository
public interface MealDao extends JpaRepository<Meal, Integer> {
	
	List<Meal> findByTour(Tour tour);

}
