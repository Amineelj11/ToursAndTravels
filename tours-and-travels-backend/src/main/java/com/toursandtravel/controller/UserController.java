package com.toursandtravel.controller;

import com.toursandtravel.entity.User;
import com.toursandtravel.service.UserServiceImpl;
import com.toursandtravel.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.toursandtravel.dto.CommonApiResponse;
import com.toursandtravel.dto.RegisterUserRequestDto;
import com.toursandtravel.dto.UserLoginRequest;
import com.toursandtravel.dto.UserLoginResponse;
import com.toursandtravel.dto.UserResponseDto;
import com.toursandtravel.resource.UserResource;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

	@Autowired
	private UserResource userResource;
	@Autowired
	private UserServiceImpl userService;

	// RegisterUserRequestDto, we will set only email, password & role from UI
	@PostMapping("/admin/register")
	@Operation(summary = "Api to register Admin")
	public ResponseEntity<CommonApiResponse> registerAdmin(@RequestBody RegisterUserRequestDto request) {
		return userResource.registerAdmin(request);
	}

	// for customer and tour guide register
	@PostMapping("register")
	@Operation(summary = "Api to register customer or seller user")
	public ResponseEntity<CommonApiResponse> registerUser(@RequestBody RegisterUserRequestDto request) {
		return this.userResource.registerUser(request);
	}

	@PostMapping("login")
	@Operation(summary = "Api to login any User")
	public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
		return userResource.login(userLoginRequest);
	}

	@GetMapping("/fetch/role-wise")
	@Operation(summary = "Api to get Users By Role")
	public ResponseEntity<UserResponseDto> fetchAllUsersByRole(@RequestParam("role") String role)
			throws JsonProcessingException {
		return userResource.getUsersByRole(role);
	}

	@DeleteMapping("tourGuide/delete")
	@Operation(summary = "Api to delete the Tour Guide and all it's tour")
	public ResponseEntity<CommonApiResponse> deleteGuide(@RequestParam("guideId") Integer guideId) {
		return userResource.deleteGuide(guideId);
	}

	@GetMapping("/fetch/user-id")
	@Operation(summary = "Api to get User Detail By User Id")
	public ResponseEntity<UserResponseDto> fetchUserById(@RequestParam("userId") int userId) {
		return userResource.getUserById(userId);
	}

	@PostMapping("/admin/approve-tourguide/{userId}")
	public ResponseEntity<CommonApiResponse> approveTourGuide(@PathVariable int userId) {
		return userResource.approveTourGuide(userId);
	}
	@GetMapping("/fetch/deactivated-tour-guides")
	public ResponseEntity<List<User>> getDeactivatedTourGuides() {
		List<User> deactivatedTourGuides = userService.getUserByRoleAndStatus("ROLE_TOUR_GUIDE", Constants.ActiveStatus.DEACTIVATED.value());
		return new ResponseEntity<>(deactivatedTourGuides, HttpStatus.OK);
	}


}
