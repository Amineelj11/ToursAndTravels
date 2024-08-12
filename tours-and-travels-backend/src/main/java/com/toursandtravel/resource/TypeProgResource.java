package com.toursandtravel.resource;

import com.toursandtravel.dto.CommonApiResponse;

import com.toursandtravel.dto.TypeResponseDto;
import com.toursandtravel.entity.Type;
import com.toursandtravel.exception.TransportSaveFailedException;

import com.toursandtravel.service.TypeService;
import com.toursandtravel.utility.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class TypeProgResource {
    @Autowired
    private TypeService transportService;

    private final Logger LOG = LoggerFactory.getLogger(TypeProgResource.class);

    public ResponseEntity<CommonApiResponse> addTransport(Type transport) {

        LOG.info("Request received for add Type");

        CommonApiResponse response = new CommonApiResponse();

        if (transport == null) {
            response.setResponseMessage("missing input");
            response.setSuccess(false);

            return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
        }

        transport.setStatus(Constants.ActiveStatus.ACTIVE.value());

        Type savedTransport = this.transportService.add(transport);

        if (savedTransport == null) {
            throw new TransportSaveFailedException("Failed to add Type");
        }

        response.setResponseMessage("Type Added Successful");
        response.setSuccess(true);

        return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);

    }

    public ResponseEntity<CommonApiResponse> updateTransport(Type transport) {

        LOG.info("Request received for add Type");

        CommonApiResponse response = new CommonApiResponse();

        if (transport == null) {
            response.setResponseMessage("missing input");
            response.setSuccess(false);

            return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
        }

        if (transport.getId() == 0) {
            response.setResponseMessage("missing Type Id");
            response.setSuccess(false);

            return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
        }

        transport.setStatus(Constants.ActiveStatus.ACTIVE.value());
        Type savedTransport = this.transportService.update(transport);

        if (savedTransport == null) {
            throw new TransportSaveFailedException("Failed to update Type");
        }

        response.setResponseMessage("Type Updated Successful");
        response.setSuccess(true);

        return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);

    }

    public ResponseEntity<TypeResponseDto> fetchAllTransport() {

        LOG.info("Request received for fetching all Types");

        TypeResponseDto response = new TypeResponseDto();

        List<Type> typeProgs = new ArrayList<>();

        typeProgs = this.transportService.getAllByStatus(Constants.ActiveStatus.ACTIVE.value());

        if (CollectionUtils.isEmpty(typeProgs)) {
            response.setResponseMessage("No Types found");
            response.setSuccess(false);

            return new ResponseEntity<TypeResponseDto>(response, HttpStatus.OK);
        }

        response.setTransports(typeProgs);
        response.setResponseMessage("Type fetched successful");
        response.setSuccess(true);

        return new ResponseEntity<TypeResponseDto>(response, HttpStatus.OK);
    }

    public ResponseEntity<CommonApiResponse> deleteTransport(int transportId) {

        LOG.info("Request received for deleting Type");

        CommonApiResponse response = new CommonApiResponse();

        if (transportId == 0) {
            response.setResponseMessage("missing Type Id");
            response.setSuccess(false);

            return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
        }

        Type transport = this.transportService.getById(transportId);

        if (transport == null) {
            response.setResponseMessage("Type not found");
            response.setSuccess(false);

            return new ResponseEntity<CommonApiResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        transport.setStatus(Constants.ActiveStatus.DEACTIVATED.value());
        Type updatedTransport = this.transportService.update(transport);

        if (updatedTransport == null) {
            throw new TransportSaveFailedException("Failed to delete the Type");
        }

        response.setResponseMessage("Type Deleted Successful");
        response.setSuccess(true);

        return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);

    }

}
