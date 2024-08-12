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
import com.toursandtravel.dto.TransportResponseDto;
import com.toursandtravel.entity.Transport;
import com.toursandtravel.resource.TransportResource;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api/transport")
@CrossOrigin(origins = "http://localhost:3000")
public class TransportController {
	
	@Autowired
	private TransportResource transportResource;
	
	@PostMapping("/add")
	@Operation(summary = "Api to add transport")
	public ResponseEntity<CommonApiResponse> addTransport(@RequestBody Transport transport) {
		return transportResource.addTransport(transport);
	}
	
	@PutMapping("/update")
	@Operation(summary = "Api to update transport")
	public ResponseEntity<CommonApiResponse> updateTransport(@RequestBody Transport transport) {
		return transportResource.updateTransport(transport);
	}
	
	@GetMapping("/fetch/all")
	@Operation(summary = "Api to fetch all transport")
	public ResponseEntity<TransportResponseDto> fetchAllTransport() {
		return transportResource.fetchAllTransport();
	}
	
	@DeleteMapping("/delete")
	@Operation(summary = "Api to delete transport all its events")
	public ResponseEntity<CommonApiResponse> deleteTransport(@RequestParam("transportId") int transportId) {
		return transportResource.deleteTransport(transportId);
	}

}
