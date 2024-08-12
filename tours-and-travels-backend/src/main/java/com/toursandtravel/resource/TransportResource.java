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
import com.toursandtravel.dto.TransportResponseDto;
import com.toursandtravel.entity.Transport;
import com.toursandtravel.exception.TransportSaveFailedException;
import com.toursandtravel.service.TransportService;
import com.toursandtravel.utility.Constants.ActiveStatus;

@Component
public class TransportResource {

	@Autowired
	private TransportService transportService;

	private final Logger LOG = LoggerFactory.getLogger(TransportResource.class);

	public ResponseEntity<CommonApiResponse> addTransport(Transport transport) {

		LOG.info("Request received for add transport");

		CommonApiResponse response = new CommonApiResponse();

		if (transport == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		transport.setStatus(ActiveStatus.ACTIVE.value());

		Transport savedTransport = this.transportService.add(transport);

		if (savedTransport == null) {
			throw new TransportSaveFailedException("Failed to add transport");
		}

		response.setResponseMessage("Transport Added Successful");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);

	}

	public ResponseEntity<CommonApiResponse> updateTransport(Transport transport) {

		LOG.info("Request received for add transport");

		CommonApiResponse response = new CommonApiResponse();

		if (transport == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (transport.getId() == 0) {
			response.setResponseMessage("missing transport Id");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		transport.setStatus(ActiveStatus.ACTIVE.value());
		Transport savedTransport = this.transportService.update(transport);

		if (savedTransport == null) {
			throw new TransportSaveFailedException("Failed to update transport");
		}

		response.setResponseMessage("Transport Updated Successful");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);

	}

	public ResponseEntity<TransportResponseDto> fetchAllTransport() {

		LOG.info("Request received for fetching all transports");

		TransportResponseDto response = new TransportResponseDto();

		List<Transport> transports = new ArrayList<>();

		transports = this.transportService.getAllByStatus(ActiveStatus.ACTIVE.value());

		if (CollectionUtils.isEmpty(transports)) {
			response.setResponseMessage("No Transports found");
			response.setSuccess(false);

			return new ResponseEntity<TransportResponseDto>(response, HttpStatus.OK);
		}

		response.setTransports(transports);
		response.setResponseMessage("Transport fetched successful");
		response.setSuccess(true);

		return new ResponseEntity<TransportResponseDto>(response, HttpStatus.OK);
	}

	public ResponseEntity<CommonApiResponse> deleteTransport(int transportId) {

		LOG.info("Request received for deleting transport");

		CommonApiResponse response = new CommonApiResponse();

		if (transportId == 0) {
			response.setResponseMessage("missing transport Id");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		Transport transport = this.transportService.getById(transportId);

		if (transport == null) {
			response.setResponseMessage("transport not found");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		transport.setStatus(ActiveStatus.DEACTIVATED.value());
		Transport updatedTransport = this.transportService.update(transport);

		if (updatedTransport == null) {
			throw new TransportSaveFailedException("Failed to delete the Transport");
		}

		response.setResponseMessage("Transport Deleted Successful");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);

	}

}
