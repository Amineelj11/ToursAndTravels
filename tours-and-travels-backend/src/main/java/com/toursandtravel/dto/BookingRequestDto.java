package com.toursandtravel.dto;

import lombok.Data;

@Data
public class BookingRequestDto {

	private int tourId;

	private int customerId;

	private int noOfTickets;

	private String cardNo;

	private String nameOnCard;

	private String cvv;

	private String expiryDate;

}
