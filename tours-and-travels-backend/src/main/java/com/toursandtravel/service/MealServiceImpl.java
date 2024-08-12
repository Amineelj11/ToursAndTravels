package com.toursandtravel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toursandtravel.dao.MealDao;
import com.toursandtravel.entity.Activity;
import com.toursandtravel.entity.Meal;
import com.toursandtravel.entity.Tour;

@Service
public class MealServiceImpl implements MealService {

	@Autowired
	private MealDao mealDao;

	@Override
	public List<Meal> addAllMeals(List<Meal> meals) {
		// TODO Auto-generated method stub
		return mealDao.saveAll(meals);
	}

	@Override
	public List<Meal> updateAllMeals(List<Meal> meals) {
		// TODO Auto-generated method stub
		return mealDao.saveAll(meals);
	}

	@Override
	public List<Meal> getByTour(Tour tour) {
		// TODO Auto-generated method stub
		return mealDao.findByTour(tour);
	}

	@Override
	public Meal addMeal(Meal meal) {
		// TODO Auto-generated method stub
		return mealDao.save(meal);
	}

	@Override
	public Meal getById(int mealId) {
		Optional<Meal> optionalMeal = mealDao.findById(mealId);

		if (optionalMeal.isPresent()) {
			return optionalMeal.get();
		} else {
			return null;
		}

	}

	@Override
	public void delete(Meal meal) {
		// TODO Auto-generated method stub
		mealDao.delete(meal);
	}

}
