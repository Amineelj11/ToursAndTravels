package com.toursandtravel.resource;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.toursandtravel.entity.*;
import com.toursandtravel.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;

import com.toursandtravel.dto.ActivityOrMealResponseDto;
import com.toursandtravel.dto.AddTourActivityOrMeal;
import com.toursandtravel.dto.AddTourRequest;
import com.toursandtravel.dto.CommonApiResponse;
import com.toursandtravel.dto.TourResponseDto;
import com.toursandtravel.dto.UpdateTourDetailRequestDto;
import com.toursandtravel.utility.Constants.ActiveStatus;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TourResource {

	private final Logger LOG = LoggerFactory.getLogger(TourResource.class);

	@Autowired
	private LocationService locationService;

	@Autowired
	private TourService tourService;

	@Autowired
	private TransportService transportService;

	@Autowired
	private LodgingService lodgingService;

	@Autowired
	private UserService userService;

	@Autowired
	private StorageService storageService;

	@Autowired
	private ActivityService activityService;

	@Autowired
	private MealService mealService;

	@Autowired
	private TypeService typeProgService;

	public ResponseEntity<CommonApiResponse> addTour(AddTourRequest request) {

		LOG.info("request received for Tour add");

		CommonApiResponse response = new CommonApiResponse();

		if (request == null) {
			response.setResponseMessage("missing request body");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (AddTourRequest.validateAddTour(request)) {
			response.setResponseMessage("missing inputs");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getFromLocationId().equals(request.getToLocationId())) {
			response.setResponseMessage("From & To Loction should be different!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		String addedDateTime = String
				.valueOf(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
		Long addedTime = Long.valueOf(addedDateTime);

		Long tourStartTime = Long.valueOf(request.getStartDate());

		if (tourStartTime < addedTime) {
			response.setResponseMessage("Tour Start Time should be Future Date!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (Long.valueOf(request.getStartDate()) > Long.valueOf(request.getEndDate())) {
			response.setResponseMessage("Tour Start Time should be less then End Date!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		Tour tour = AddTourRequest.toTourEntity(request);

		Location fromLocation = this.locationService.getById(request.getFromLocationId());

		if (fromLocation == null || !fromLocation.getStatus().equals(ActiveStatus.ACTIVE.value())) {
			response.setResponseMessage("From Location not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		tour.setFromLocation(fromLocation);

		Location toLocation = this.locationService.getById(request.getToLocationId());

		if (toLocation == null || !toLocation.getStatus().equals(ActiveStatus.ACTIVE.value())) {
			response.setResponseMessage("To Location not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		tour.setToLocation(toLocation);

		Transport transport = this.transportService.getById(request.getTransportId());

		if (transport == null || !transport.getStatus().equals(ActiveStatus.ACTIVE.value())) {
			response.setResponseMessage("Transport not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		tour.setTransport(transport);

		/*Type typeProg = this.typeProgService.getById(request.getTypeProgId());

		if (typeProg == null || !typeProg.getStatus().equals(ActiveStatus.ACTIVE.value())) {
			response.setResponseMessage("typeProg not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		tour.setTypeProg(typeProg);*/

		Lodging lodging = this.lodgingService.getById(request.getLodgingId());

		if (lodging == null || !lodging.getStatus().equals(ActiveStatus.ACTIVE.value())) {
			response.setResponseMessage("Lodging not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		tour.setLodging(lodging);

		User guide = this.userService.getUserById(request.getGuideId());

		if (guide == null || !guide.getStatus().equals(ActiveStatus.ACTIVE.value())) {
			response.setResponseMessage("Tour Guide Not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		tour.setGuide(guide);

		String image1Name = this.storageService.store(request.getImage1());
		String image2Name = this.storageService.store(request.getImage2());
		String image3Name = this.storageService.store(request.getImage3());

		tour.setAvailableTickets(request.getTotalTickets());
		tour.setAddedDate(addedDateTime);
		tour.setImage1(image1Name);
		tour.setImage2(image2Name);
		tour.setImage3(image3Name);
		tour.setStatus(ActiveStatus.ACTIVE.value());

		Tour savedTour = this.tourService.addTour(tour);

		if (savedTour == null) {
			response.setResponseMessage("Failed to save the Tour!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		response.setResponseMessage("Tour Added Successful!!!");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);

	}

	public ResponseEntity<ActivityOrMealResponseDto> addTourActivity(AddTourActivityOrMeal request) {

		LOG.info("request received for adding the Tour Activity");

		ActivityOrMealResponseDto response = new ActivityOrMealResponseDto();

		if (request == null || request.getTourId() == 0) {
			response.setResponseMessage("missing request body");
			response.setSuccess(false);

			return new ResponseEntity<ActivityOrMealResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		Tour tour = this.tourService.getById(request.getTourId());

		if (tour == null) {
			response.setResponseMessage("Tour not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<ActivityOrMealResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		List<Activity> tourActivities = this.activityService.getByTour(tour);

		Activity activity = new Activity();
		activity.setName(request.getName());
		activity.setDescription(request.getDescription());
		activity.setTour(tour);

		Activity savedActivity = activityService.addActivity(activity);

		if (savedActivity == null) {
			response.setActivities(tourActivities != null ? tourActivities : new ArrayList<>());
			response.setResponseMessage("Failed to add the activity!!!");
			response.setSuccess(false);

			return new ResponseEntity<ActivityOrMealResponseDto>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (CollectionUtils.isEmpty(tourActivities)) {
			response.setActivities(Arrays.asList(savedActivity));
			response.setResponseMessage("Activity Added Successful!!");
			response.setSuccess(true);

			return new ResponseEntity<ActivityOrMealResponseDto>(response, HttpStatus.OK);
		} else {
			tourActivities.add(savedActivity);

			response.setActivities(tourActivities);
			response.setResponseMessage("Activity Added Successful!!");
			response.setSuccess(true);

			return new ResponseEntity<ActivityOrMealResponseDto>(response, HttpStatus.OK);
		}

	}

	public ResponseEntity<ActivityOrMealResponseDto> deleteTourActivity(Integer activityId) {

		LOG.info("request received for deleting the Activity");

		ActivityOrMealResponseDto response = new ActivityOrMealResponseDto();

		if (activityId == null || activityId == 0) {
			response.setResponseMessage("missing request parameter");
			response.setSuccess(false);

			return new ResponseEntity<ActivityOrMealResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		Activity activity = this.activityService.getById(activityId);

		if (activity == null) {
			response.setResponseMessage("Activity not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<ActivityOrMealResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		Tour tour = activity.getTour();

		this.activityService.delete(activity);

		List<Activity> activities = this.activityService.getByTour(tour);

		response.setActivities(activities);
		response.setResponseMessage("Activity Deleted Successful!!");
		response.setSuccess(true);

		return new ResponseEntity<ActivityOrMealResponseDto>(response, HttpStatus.OK);

	}

	public ResponseEntity<ActivityOrMealResponseDto> addTourMeal(AddTourActivityOrMeal request) {

		LOG.info("request received for adding the Tour Meal");

		ActivityOrMealResponseDto response = new ActivityOrMealResponseDto();

		if (request == null || request.getTourId() == 0) {
			response.setResponseMessage("missing request body");
			response.setSuccess(false);

			return new ResponseEntity<ActivityOrMealResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		Tour tour = this.tourService.getById(request.getTourId());

		if (tour == null) {
			response.setResponseMessage("Tour not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<ActivityOrMealResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		List<Meal> tourMeals = this.mealService.getByTour(tour);

		Meal meal = new Meal();
		meal.setName(request.getName());
		meal.setDescription(request.getDescription());
		meal.setTour(tour);

		Meal savedMeal = mealService.addMeal(meal);

		if (savedMeal == null) {
			response.setMeals(tourMeals != null ? tourMeals : new ArrayList<>());
			response.setResponseMessage("Failed to add the meal!!!");
			response.setSuccess(false);

			return new ResponseEntity<ActivityOrMealResponseDto>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (CollectionUtils.isEmpty(tourMeals)) {
			response.setMeals(Arrays.asList(savedMeal));
			response.setResponseMessage("Meal Added Successful!!");
			response.setSuccess(true);

			return new ResponseEntity<ActivityOrMealResponseDto>(response, HttpStatus.OK);
		} else {
			tourMeals.add(savedMeal);

			response.setMeals(tourMeals);
			response.setResponseMessage("Meal Added Successful!!");
			response.setSuccess(true);

			return new ResponseEntity<ActivityOrMealResponseDto>(response, HttpStatus.OK);
		}

	}

	public ResponseEntity<ActivityOrMealResponseDto> deleteTourMeal(Integer mealId) {

		LOG.info("request received for deleting the Meal");

		ActivityOrMealResponseDto response = new ActivityOrMealResponseDto();

		if (mealId == null || mealId == 0) {
			response.setResponseMessage("missing request parameter");
			response.setSuccess(false);

			return new ResponseEntity<ActivityOrMealResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		Meal meal = this.mealService.getById(mealId);

		if (meal == null) {
			response.setResponseMessage("Meal not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<ActivityOrMealResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		Tour tour = meal.getTour();

		this.mealService.delete(meal);

		List<Meal> meals = this.mealService.getByTour(tour);

		response.setMeals(meals);
		response.setResponseMessage("Meal Deleted Successful!!");
		response.setSuccess(true);

		return new ResponseEntity<ActivityOrMealResponseDto>(response, HttpStatus.OK);

	}

	public ResponseEntity<CommonApiResponse> updateTour(UpdateTourDetailRequestDto request) {

		LOG.info("request received for updating the tour detail");

		CommonApiResponse response = new CommonApiResponse();

		if (request == null) {
			response.setResponseMessage("missing request body");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (!UpdateTourDetailRequestDto.validateAddTour(request)) {
			response.setResponseMessage("missing inputs");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getFromLocationId().equals(request.getToLocationId())) {
			response.setResponseMessage("From & To Loction should be different!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		String addedDateTime = String
				.valueOf(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
		Long addedTime = Long.valueOf(addedDateTime);

		Long tourStartTime = Long.valueOf(request.getStartDate());

		if (tourStartTime < addedTime) {
			response.setResponseMessage("Tour Start Time should be Future Date!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (Long.valueOf(request.getStartDate()) > Long.valueOf(request.getEndDate())) {
			response.setResponseMessage("Tour Start Time should be less then End Date!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		Tour tour = UpdateTourDetailRequestDto.toTourEntity(request);

		Tour dbTour = this.tourService.getById(request.getId());

		if (request.getFromLocationId() != null && request.getFromLocationId() != 0
				&& dbTour.getFromLocation().getId() != request.getFromLocationId()) {
			dbTour.setFromLocation(this.locationService.getById(request.getFromLocationId()));
		}

		if (request.getToLocationId() != null && request.getToLocationId() != 0
				&& dbTour.getToLocation().getId() != request.getToLocationId()) {
			dbTour.setToLocation(this.locationService.getById(request.getToLocationId()));
		}

		if (request.getTransportId() != null && request.getTransportId() != 0
				&& dbTour.getTransport().getId() != request.getTransportId()) {
			dbTour.setTransport(this.transportService.getById(request.getTransportId()));
		}

		/*if (request.getTypeProgId() != null && request.getTypeProgId() != 0
				&& dbTour.getTypeProg().getId() != request.getTypeProgId()) {
			dbTour.setTypeProg(this.typeProgService.getById(request.getTypeProgId()));
		}*/

		if (request.getLodgingId() == null && request.getLodgingId() == 0
				&& dbTour.getLodging().getId() != request.getLodgingId()) {
			dbTour.setLodging(this.lodgingService.getById(request.getLodgingId()));
		}

		tour.setStatus(ActiveStatus.ACTIVE.value());

		dbTour.setName(request.getName());
		dbTour.setDescription(request.getDescription());
		dbTour.setTotalDaysOfTour(request.getTotalDaysOfTour());
		dbTour.setVehicleRegistrationNo(request.getVehicleRegistrationNo());
		dbTour.setTransportDescription(request.getTransportDescription());
		dbTour.setLodgingName(request.getLodgingName());
		dbTour.setLodgingAddress(request.getLodgingAddress());
		dbTour.setTotalTickets(request.getTotalTickets());
		dbTour.setTicketPrice(request.getTicketPrice());
		dbTour.setStartDate(request.getStartDate());
		dbTour.setEndDate(request.getEndDate());
		dbTour.setSpecialNote(request.getSpecialNote());

		Tour savedTour = this.tourService.addTour(dbTour);

		if (savedTour == null) {
			response.setResponseMessage("Failed to update the Tour Details!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		response.setResponseMessage("Tour Updated Successful!!!");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);

	}

	public ResponseEntity<CommonApiResponse> updateTourImages(AddTourRequest request) {

		LOG.info("request received for Tour Images update");

		CommonApiResponse response = new CommonApiResponse();

		if (request == null || request.getId() == 0) {
			response.setResponseMessage("missing request body");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getImage1() == null || request.getImage2() == null || request.getImage3() == null) {
			response.setResponseMessage("missing request body");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		Tour tour = this.tourService.getById(request.getId());

		String existingImage1 = tour.getImage1();
		String existingImage2 = tour.getImage2();
		String existingImage3 = tour.getImage3();

		storageService.delete(existingImage1);
		storageService.delete(existingImage2);
		storageService.delete(existingImage3);

		String newImage1 = storageService.store(request.getImage1());
		String newImage2 = storageService.store(request.getImage2());
		String newImage3 = storageService.store(request.getImage3());

		tour.setImage1(newImage1);
		tour.setImage2(newImage2);
		tour.setImage3(newImage3);

		tour.setStatus(ActiveStatus.ACTIVE.value());

		Tour savedTour = this.tourService.addTour(tour);

		if (savedTour == null) {
			response.setResponseMessage("Failed to update the Tour Images!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		response.setResponseMessage("Tour Images updated Successful!!!");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);

	}

	public ResponseEntity<CommonApiResponse> deleteTour(Integer tourId) {

		LOG.info("request received for deleting the  tour");

		CommonApiResponse response = new CommonApiResponse();

		if (tourId == null || tourId == 0) {
			response.setResponseMessage("missing tour id");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		Tour tour = this.tourService.getById(tourId);

		tour.setStatus(ActiveStatus.DEACTIVATED.value());

		Tour savedTour = this.tourService.addTour(tour);

		if (savedTour == null) {
			response.setResponseMessage("Failed to delete the Tour!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		response.setResponseMessage("Tour Deleted Successful!!!");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);

	}

	public ResponseEntity<TourResponseDto> fetchActiveTours() {

		TourResponseDto response = new TourResponseDto();

		String currentTime = String
				.valueOf(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

		List<Tour> tours = this.tourService.getAllStatusAndStartDate(ActiveStatus.ACTIVE.value(), currentTime);

		if (CollectionUtils.isEmpty(tours)) {
			response.setResponseMessage("Tours not found");
			response.setSuccess(false);

			return new ResponseEntity<TourResponseDto>(response, HttpStatus.OK);
		}

		response.setTours(tours);
		response.setResponseMessage("Tours fetched successful!!");
		response.setSuccess(true);

		return new ResponseEntity<TourResponseDto>(response, HttpStatus.OK);
	}

	public ResponseEntity<TourResponseDto> fetchAllTours(String status) {

		TourResponseDto response = new TourResponseDto();

		if (status == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<TourResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		List<Tour> tours = this.tourService.getAllByStatus(status);

		if (CollectionUtils.isEmpty(tours)) {
			response.setResponseMessage("Tours not found");
			response.setSuccess(false);

			return new ResponseEntity<TourResponseDto>(response, HttpStatus.OK);
		}

		response.setTours(tours);
		response.setResponseMessage("Tours fetched successful!!");
		response.setSuccess(true);

		return new ResponseEntity<TourResponseDto>(response, HttpStatus.OK);
	}

	public ResponseEntity<TourResponseDto> fetchActiveToursByLocations(Integer fromLocationId, Integer toLocationId) {

		TourResponseDto response = new TourResponseDto();

		String currentTime = String
				.valueOf(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

		if (fromLocationId == null || toLocationId == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<TourResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		Location fromLocation = this.locationService.getById(fromLocationId);

		if (fromLocation == null) {
			response.setResponseMessage("from location not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<TourResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		Location toLocation = this.locationService.getById(toLocationId);

		if (toLocation == null) {
			response.setResponseMessage("to location not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<TourResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		List<Tour> tours = this.tourService.getAllByStatusAndFromAndToLocationAndStartDate(ActiveStatus.ACTIVE.value(),
				fromLocation, toLocation, currentTime);

		if (CollectionUtils.isEmpty(tours)) {
			response.setResponseMessage("Tours not found");
			response.setSuccess(false);

			return new ResponseEntity<TourResponseDto>(response, HttpStatus.OK);
		}

		response.setTours(tours);
		response.setResponseMessage("Tours fetched successful!!");
		response.setSuccess(true);

		return new ResponseEntity<TourResponseDto>(response, HttpStatus.OK);
	}

	public ResponseEntity<TourResponseDto> searchActiveToursByName(String tourName) {

		TourResponseDto response = new TourResponseDto();

		String currentTime = String
				.valueOf(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

		if (tourName == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<TourResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		List<Tour> tours = this.tourService.getAllStatusAndTourNameAndStartDate(ActiveStatus.ACTIVE.value(), tourName,
				currentTime);

		if (CollectionUtils.isEmpty(tours)) {
			response.setResponseMessage("Tours not found");
			response.setSuccess(false);

			return new ResponseEntity<TourResponseDto>(response, HttpStatus.OK);
		}

		response.setTours(tours);
		response.setResponseMessage("Tours fetched successful!!");
		response.setSuccess(true);

		return new ResponseEntity<TourResponseDto>(response, HttpStatus.OK);
	}

	public ResponseEntity<TourResponseDto> getAllToursByTourGuide(Integer tourGuideId) {

		TourResponseDto response = new TourResponseDto();

		if (tourGuideId == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<TourResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		User guide = this.userService.getUserById(tourGuideId);

		List<Tour> tours = this.tourService.getAllByGuide(guide);

		if (CollectionUtils.isEmpty(tours)) {
			response.setResponseMessage("Tours not found");
			response.setSuccess(false);

			return new ResponseEntity<TourResponseDto>(response, HttpStatus.OK);
		}

		response.setTours(tours);
		response.setResponseMessage("Tours fetched successful!!");
		response.setSuccess(true);

		return new ResponseEntity<TourResponseDto>(response, HttpStatus.OK);
	}

	public ResponseEntity<TourResponseDto> fetchTourByTourId(Integer tourId) {

		TourResponseDto response = new TourResponseDto();

		if (tourId == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<TourResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		Tour tour = this.tourService.getById(tourId);

		if (tour == null) {
			response.setResponseMessage("Tour not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<TourResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		response.setTours(Arrays.asList(tour));
		response.setResponseMessage("Tours fetched successful!!");
		response.setSuccess(true);

		return new ResponseEntity<TourResponseDto>(response, HttpStatus.OK);
	}

	public void fetchTourImage(String tourImageName, HttpServletResponse resp) {
		Resource resource = storageService.load(tourImageName);
		if (resource != null) {
			try (InputStream in = resource.getInputStream()) {
				ServletOutputStream out = resp.getOutputStream();
				FileCopyUtils.copy(in, out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
