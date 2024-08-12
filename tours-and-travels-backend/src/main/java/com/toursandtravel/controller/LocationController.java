package com.toursandtravel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toursandtravel.dto.CommonApiResponse;
import com.toursandtravel.dto.LocationResponseDto;
import com.toursandtravel.entity.Location;
import com.toursandtravel.resource.LocationResource;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api/location")
@CrossOrigin(origins = "http://localhost:3000")
public class LocationController {
	
	@Autowired
	private LocationResource locationResource;
	
	@PostMapping("/add")
	@Operation(summary = "Api to add location")
	public ResponseEntity<CommonApiResponse> addLocation(@RequestBody Location location) {
		return locationResource.addLocation(location);
	}
	
	@PutMapping("/update")
	@Operation(summary = "Api to update location")
	public ResponseEntity<CommonApiResponse> updateLocation(@RequestBody Location location) {
		return locationResource.updateLocation(location);
	}
	
	@GetMapping("/fetch/all")
	@Operation(summary = "Api to fetch all location")
	public ResponseEntity<LocationResponseDto> fetchAllLocation() {
		return locationResource.fetchAllLocation();
	}
	
	@DeleteMapping("/delete")
	@Operation(summary = "Api to delete location all its events")
	public ResponseEntity<CommonApiResponse> deleteLocation(@RequestParam("locationId") int locationId) {
		return locationResource.deleteLocation(locationId);
	}

}
