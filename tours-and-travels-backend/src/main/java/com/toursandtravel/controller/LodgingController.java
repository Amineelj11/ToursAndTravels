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
import com.toursandtravel.dto.LodgingResponseDto;
import com.toursandtravel.entity.Lodging;
import com.toursandtravel.resource.LodgingResource;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api/lodge")
@CrossOrigin(origins = "http://localhost:3000")
public class LodgingController {
	
	@Autowired
	private LodgingResource lodgeResource;
	
	@PostMapping("/add")
	@Operation(summary = "Api to add lodge")
	public ResponseEntity<CommonApiResponse> addLodging(@RequestBody Lodging lodge) {
		return lodgeResource.addLodging(lodge);
	}
	
	@PutMapping("/update")
	@Operation(summary = "Api to update lodge")
	public ResponseEntity<CommonApiResponse> updateLodging(@RequestBody Lodging lodge) {
		return lodgeResource.updateLodging(lodge);
	}
	
	@GetMapping("/fetch/all")
	@Operation(summary = "Api to fetch all lodge")
	public ResponseEntity<LodgingResponseDto> fetchAllLodging() {
		return lodgeResource.fetchAllLodging();
	}
	
	@DeleteMapping("/delete")
	@Operation(summary = "Api to delete lodge all its events")
	public ResponseEntity<CommonApiResponse> deleteLodging(@RequestParam("lodgeId") int lodgeId) {
		return lodgeResource.deleteLodging(lodgeId);
	}

}
