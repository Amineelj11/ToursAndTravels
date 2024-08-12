package com.toursandtravel.resource;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.toursandtravel.dto.CommonApiResponse;
import com.toursandtravel.dto.LodgingResponseDto;
import com.toursandtravel.entity.Lodging;
import com.toursandtravel.exception.LodgingSaveFailedException;
import com.toursandtravel.service.LodgingService;
import com.toursandtravel.utility.Constants.ActiveStatus;

@Component
public class LodgingResource {

	@Autowired
	private LodgingService lodgeService;

	private final Logger LOG = LoggerFactory.getLogger(LodgingResource.class);

	public ResponseEntity<CommonApiResponse> addLodging(Lodging lodge) {

		LOG.info("Request received for add lodge");

		CommonApiResponse response = new CommonApiResponse();

		if (lodge == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		lodge.setStatus(ActiveStatus.ACTIVE.value());

		Lodging savedLodging = this.lodgeService.add(lodge);

		if (savedLodging == null) {
			throw new LodgingSaveFailedException("Failed to add lodge");
		}

		response.setResponseMessage("Lodging Added Successful");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);

	}

	public ResponseEntity<CommonApiResponse> updateLodging(Lodging lodge) {

		LOG.info("Request received for add lodge");

		CommonApiResponse response = new CommonApiResponse();

		if (lodge == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (lodge.getId() == 0) {
			response.setResponseMessage("missing lodge Id");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		lodge.setStatus(ActiveStatus.ACTIVE.value());
		Lodging savedLodging = this.lodgeService.update(lodge);

		if (savedLodging == null) {
			throw new LodgingSaveFailedException("Failed to update lodge");
		}

		response.setResponseMessage("Lodging Updated Successful");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);

	}

	public ResponseEntity<LodgingResponseDto> fetchAllLodging() {

		LOG.info("Request received for fetching all lodges");

		LodgingResponseDto response = new LodgingResponseDto();

		List<Lodging> lodges = new ArrayList<>();

		lodges = this.lodgeService.getAllByStatus(ActiveStatus.ACTIVE.value());

		if (CollectionUtils.isEmpty(lodges)) {
			response.setResponseMessage("No Lodgings found");
			response.setSuccess(false);

			return new ResponseEntity<LodgingResponseDto>(response, HttpStatus.OK);
		}

		response.setLodges(lodges);
		response.setResponseMessage("Lodging fetched successful");
		response.setSuccess(true);

		return new ResponseEntity<LodgingResponseDto>(response, HttpStatus.OK);
	}

	public ResponseEntity<CommonApiResponse> deleteLodging(int lodgeId) {

		LOG.info("Request received for deleting lodge");

		CommonApiResponse response = new CommonApiResponse();

		if (lodgeId == 0) {
			response.setResponseMessage("missing lodge Id");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		Lodging lodge = this.lodgeService.getById(lodgeId);

		if (lodge == null) {
			response.setResponseMessage("lodge not found");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		lodge.setStatus(ActiveStatus.DEACTIVATED.value());
		Lodging updatedLodging = this.lodgeService.update(lodge);

		if (updatedLodging == null) {
			throw new LodgingSaveFailedException("Failed to delete the Lodging");
		}

		response.setResponseMessage("Lodging Deleted Successful");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);

	}

}
