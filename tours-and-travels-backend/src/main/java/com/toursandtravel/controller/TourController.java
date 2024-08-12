package com.toursandtravel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toursandtravel.dto.ActivityOrMealResponseDto;
import com.toursandtravel.dto.AddTourActivityOrMeal;
import com.toursandtravel.dto.AddTourRequest;
import com.toursandtravel.dto.CommonApiResponse;
import com.toursandtravel.dto.TourResponseDto;
import com.toursandtravel.dto.UpdateTourDetailRequestDto;
import com.toursandtravel.resource.TourResource;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/tour")
@CrossOrigin(origins = "http://localhost:3000")
public class TourController {

	@Autowired
	private TourResource tourResource;

	@PostMapping("add")
	public ResponseEntity<CommonApiResponse> addTour(AddTourRequest request) {
		return this.tourResource.addTour(request);
	}

	@PutMapping("update/detail")
	public ResponseEntity<CommonApiResponse> updateTourDetail(@RequestBody UpdateTourDetailRequestDto request) {
		return this.tourResource.updateTour(request);
	}

	@PutMapping("update/images")
	public ResponseEntity<CommonApiResponse> updateTourImages(AddTourRequest request) {
		return this.tourResource.updateTourImages(request);
	}

	@DeleteMapping("delete")
	public ResponseEntity<CommonApiResponse> deleteTour(@RequestParam("tourId") Integer tourId) {
		return this.tourResource.deleteTour(tourId);
	}

	@PostMapping("activity/add")
	public ResponseEntity<ActivityOrMealResponseDto> addTourActivity(@RequestBody AddTourActivityOrMeal request) {
		return this.tourResource.addTourActivity(request);
	}

	@DeleteMapping("activity/delete")
	public ResponseEntity<ActivityOrMealResponseDto> deleteTourActivity(
			@RequestParam("activityId") Integer activityId) {
		return this.tourResource.deleteTourActivity(activityId);
	}

	@PostMapping("meal/add")
	public ResponseEntity<ActivityOrMealResponseDto> addTourMeal(@RequestBody AddTourActivityOrMeal request) {
		return this.tourResource.addTourMeal(request);
	}

	@DeleteMapping("meal/delete")
	public ResponseEntity<ActivityOrMealResponseDto> deleteTourMeal(@RequestParam("mealId") Integer mealId) {
		return this.tourResource.deleteTourMeal(mealId);
	}

	@GetMapping("fetch/all/active")
	public ResponseEntity<TourResponseDto> fetchActiveTours() {
		return this.tourResource.fetchActiveTours();
	}

	// for admin side
	@GetMapping("fetch/all")
	public ResponseEntity<TourResponseDto> fetchAllTours(@RequestParam("status") String status) {
		return this.tourResource.fetchAllTours(status);
	}

	@GetMapping("fetch/location-wise")
	public ResponseEntity<TourResponseDto> fetchActiveToursByLocation(
			@RequestParam("fromLocationId") Integer fromLocationId,
			@RequestParam("toLocationId") Integer toLocationId) {
		return this.tourResource.fetchActiveToursByLocations(fromLocationId, toLocationId);
	}

	@GetMapping("fetch/name-wise")
	public ResponseEntity<TourResponseDto> searchActiveToursByName(@RequestParam("tourName") String tourName) {
		return this.tourResource.searchActiveToursByName(tourName);
	}

	@GetMapping("fetch/guide-wise")
	public ResponseEntity<TourResponseDto> getAllToursByTourGuide(@RequestParam("tourGuideId") Integer tourGuideId) {
		return this.tourResource.getAllToursByTourGuide(tourGuideId);
	}

	@GetMapping("fetch")
	public ResponseEntity<TourResponseDto> fetchTourByTourId(@RequestParam("tourId") Integer tourId) {
		return this.tourResource.fetchTourByTourId(tourId);
	}

	@GetMapping(value = "/{tourImageName}", produces = "image/*")
	public void fetchTourImage(@PathVariable("tourImageName") String tourImageName, HttpServletResponse resp) {
		this.tourResource.fetchTourImage(tourImageName, resp);
	}

}
