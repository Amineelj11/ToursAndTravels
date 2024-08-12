package com.toursandtravel.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.toursandtravel.entity.Activity;
import com.toursandtravel.entity.Meal;
import com.toursandtravel.entity.Tour;

import lombok.Data;

@Data
public class AddTourRequest {

	private Integer id;

	private String name;

	private String description;

	private Integer totalDaysOfTour;

	private Integer guideId;

	private Integer fromLocationId;

	private Integer toLocationId;

	private List<Activity> activities = new ArrayList<>();

	private List<Meal> meals = new ArrayList<>();

	private Integer transportId;

	private Integer typeProgId;

	// Registration no of Bus, Train or Flight
	private String vehicleRegistrationNo;

	// In this column we will store complete note added by Tour Guide, basically it
	// will be complete guide for eg
	// We will reach ABC Station by Bus then from ABC Station we will go to XYZ
	// station and by reaching there we will reach the destination with in 15 to 20
	// min
	private String transportDescription;

	private Integer lodgingId;

	private String lodgingName;

	private String lodgingAddress;

	private Integer totalTickets;

	private BigDecimal ticketPrice;

//	private String addedDate;

	private String startDate;

	private String endDate;

	private String specialNote; // like Reach Busstop by 9am

	private MultipartFile image1;

	private MultipartFile image2;

	private MultipartFile image3;

	public static boolean validateAddTour(AddTourRequest request) {

		if (request.getName() == null || request.getDescription() == null
				|| CollectionUtils.isEmpty(request.getActivities()) || CollectionUtils.isEmpty(request.getMeals())
				|| request.getTotalDaysOfTour() == null || request.getGuideId() == null
				|| request.getFromLocationId() == null || request.getToLocationId() == null
				|| request.getTransportId() == null || request.getTypeProgId() == null
				|| request.getVehicleRegistrationNo() == null
				|| request.getLodgingId() == null || request.getLodgingName() == null
				|| request.getLodgingAddress() == null || request.getTotalTickets() == null
				|| request.getTicketPrice() == null || request.getStartDate() == null || request.getEndDate() == null
				|| request.getSpecialNote() == null || request.getImage1() == null || request.getImage2() == null
				|| request.getImage3() == null)

			return false;

		return true;
	}

	public static Tour toTourEntity(AddTourRequest request) {
		Tour tour = new Tour();
		BeanUtils.copyProperties(request, tour, "id", "guideId", "fromLocationId", "toLocationId", "transportId",
				"lodgingId","typeProgId", "image1", "image2", "image3", "activities", "meals");
		return tour;
	}

}
