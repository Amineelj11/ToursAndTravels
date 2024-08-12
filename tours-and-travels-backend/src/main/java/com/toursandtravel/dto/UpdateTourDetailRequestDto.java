package com.toursandtravel.dto;

import java.math.BigDecimal;

import org.springframework.beans.BeanUtils;

import com.toursandtravel.entity.Tour;

import lombok.Data;

@Data
public class UpdateTourDetailRequestDto {

	private Integer id;

	private String name;

	private String description;

	private Integer totalDaysOfTour;

	private Integer guideId;

	private Integer fromLocationId;

	private Integer toLocationId;

	private Integer transportId;

	private Integer typeProgId;

	private String vehicleRegistrationNo;

	private String transportDescription;

	private Integer lodgingId;

	private String lodgingName;

	private String lodgingAddress;

	private Integer totalTickets;

	private BigDecimal ticketPrice;

	private String startDate;

	private String endDate;

	private String specialNote;

	public static boolean validateAddTour(UpdateTourDetailRequestDto request) {

		if (request.getId() == 0 || request.getName() == null || request.getDescription() == null
				|| request.getTotalDaysOfTour() == null || request.getVehicleRegistrationNo() == null
				|| request.getLodgingName() == null || request.getLodgingAddress() == null
				|| request.getTotalTickets() == null || request.getTicketPrice() == null
				|| request.getStartDate() == null || request.getEndDate() == null || request.getSpecialNote() == null) {
			return false;
		}

		return true;
	}

	public static Tour toTourEntity(UpdateTourDetailRequestDto request) {
		Tour tour = new Tour();
		BeanUtils.copyProperties(request, tour, "guideId", "fromLocationId", "toLocationId", "transportId","typeProgId", "lodgingId",
				"image1", "image2", "image3", "activities", "meals");
		return tour;
	}

}
