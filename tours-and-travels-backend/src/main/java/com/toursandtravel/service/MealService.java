package com.toursandtravel.service;

import java.util.List;

import com.toursandtravel.entity.Meal;
import com.toursandtravel.entity.Tour;

public interface MealService {

	List<Meal> addAllMeals(List<Meal> meals);

	List<Meal> updateAllMeals(List<Meal> meals);

	List<Meal> getByTour(Tour tour);

	Meal addMeal(Meal meal);

	Meal getById(int mealId);

	void delete(Meal meal);

}
