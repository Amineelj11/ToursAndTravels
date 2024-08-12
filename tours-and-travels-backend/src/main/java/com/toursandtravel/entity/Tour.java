package com.toursandtravel.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Tour {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	private String description;

	private Integer totalDaysOfTour;

	@ManyToOne
	@JoinColumn(name = "guide_id")
	private User guide; // tour guide id

	@ManyToOne
	@JoinColumn(name = "from_location_id")
	private Location fromLocation;

	@ManyToOne
	@JoinColumn(name = "to_location_id")
	private Location toLocation;

	// One tour can have multiple activities
	@OneToMany(mappedBy = "tour", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Activity> activities;

	// One tour can have multiple meals
	@OneToMany(mappedBy = "tour", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Meal> meals;

	@ManyToOne
	@JoinColumn(name = "transport_id")
	private Transport transport;

	@ManyToOne
	@JoinColumn(name = "typeProg_id")
	private Type typeProg;

	// Registration no of Bus, Train or Flight
	private String vehicleRegistrationNo;

	// In this column we will store complete note added by Tour Guide, basically it
	// will be complete guide for eg
	// We will reach ABC Station by Bus then from ABC Station we will go to XYZ
	// station and by reaching there we will reach the destination with in 15 to 20
	// min
	@Column(length = 5000)
	private String transportDescription;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "lodging_id")
	private Lodging lodging;

	private String lodgingName;

	@Column(length = 2000)
	private String lodgingAddress;

	private Integer totalTickets;

	private Integer availableTickets;

	private BigDecimal ticketPrice;

	private String addedDate; // tour added time in current millis

	private String startDate; // trip start date time in epoch current millis

	private String endDate; // trip end date time in epoch current millis

	private String specialNote; // like Reach ABC Busstop by 9am

	private String image1;

	private String image2;

	private String image3;

	private String status; // active, deactivated

	@OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Feedback> feedbacks;

}
