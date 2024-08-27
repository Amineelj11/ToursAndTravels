package com.toursandtravel.dto;

import java.util.ArrayList;
import java.util.List;

import com.toursandtravel.entity.Activity;
import com.toursandtravel.entity.Meal;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
@Data
public class ActivityOrMealResponseDto extends CommonApiResponse {

	private List<Activity> activities = new ArrayList<>();

	private List<Meal> meals = new ArrayList<>();

}
